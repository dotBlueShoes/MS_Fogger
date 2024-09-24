package dotBlueShoes.fogger.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dotBlueShoes.fogger.Fogger;
import dotBlueShoes.fogger.utility.FogColor;
import dotBlueShoes.fogger.utility.FogDefinition;
import dotBlueShoes.fogger.utility.FogSetting;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.world.season.Seasons;

import java.io.*;
import java.nio.file.Files;

public class ConfigHandler {

	private static final String CONFIG_DIRECTORY = FabricLoader.getInstance().getGameDir().toString() + "/config/";
	public final String configFileName;

	public ConfigHandler(String configFileName) {
		this.configFileName = configFileName + ".json";
	}

	public String getFilePath() {
		return CONFIG_DIRECTORY + configFileName;
	}

	public void create () {
		File configFile = new File(getFilePath());

		//noinspection ResultOfMethodCallIgnored
		configFile.getParentFile().mkdirs();

		try {

			if (configFile.createNewFile()) {

				Fogger.LOGGER.info("Config file does not exist. Creating...");


				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String jsonString = gson.toJson(new ConfigData());
				write(configFile, jsonString);
				Fogger.LOGGER.info("Config file created at {}!", configFile.getAbsolutePath());
				load(configFile);

			} else {
				Fogger.LOGGER.info("Config file does exist. Loading...");
			}

			load(configFile);
			Fogger.LOGGER.info("Config file loaded!");

		} catch (IOException exception) {
			Fogger.LOGGER.error("Could not create the config File!");

			//noinspection CallToPrintStackTrace
			exception.printStackTrace();
		}
	}

	public void write(File configFile, String data) {
		try (OutputStream output = Files.newOutputStream(configFile.toPath())) {
			output.write(data.getBytes());
		} catch (IOException exception) {
			//noinspection CallToPrintStackTrace
			exception.printStackTrace();
		}
	}

	public void load(File configFile) {
		try (InputStream input = Files.newInputStream(configFile.toPath())) {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			Gson gson = new Gson();

			while (true) {
				byte[] buffer = new byte[Math.max(2048, input.available())];
				int count = input.read(buffer);
				if (count == -1) break;
				stream.write(buffer, 0, count);
			}

			final String data = stream.toString();
			ConfigData configData = gson.fromJson(data, ConfigData.class);

			{ // PARSING
				Fogger.fogDefinitions = new FogDefinition[configData.fogDefinitions.length]; // + zero (additional fallback setting)
				Fogger.fogSettings = new FogSetting[configData.fogSettings.length];
				Fogger.fogColors = new FogColor[configData.fogColors.length]; // + zero (additional fallback setting)

				{ // BOOLS
					Fogger.isFogAutoDarkenByNightSky = configData.isFogAutoDarkenByNightSky;
					Fogger.isFogColorsOverridden = configData.isFogColorsOverridden;
				}

				{ // COLORS
					//Fogger.fogColors[0] = new FogColor(FogColor.ZERO);
					for (int i = 0; i < configData.fogColors.length; ++i) {
						Fogger.fogColors[i] = new FogColor(
							configData.fogColors[i].r,
							configData.fogColors[i].g,
							configData.fogColors[i].b
						);
					}
				}

				{ // DEFINITIONS
					//Fogger.fogDefinitions[0] = new FogDefinition(FogDefinition.ZERO);
					definitions: for (int iDefinition = 0; iDefinition < configData.fogDefinitions.length; ++iDefinition) {

						for (int iColor = 0; iColor < configData.fogColors.length; ++iColor) {
							if (configData.fogColors[iColor].name.equals(configData.fogDefinitions[iDefinition].nameColor)) {

								Fogger.fogDefinitions[iDefinition] = new FogDefinition(
									configData.fogDefinitions[iDefinition].start,
									configData.fogDefinitions[iDefinition].end,
									iColor
								);

								continue definitions;
							}
						}

						// We only end up here if the color was not found in the loop.

						Fogger.LOGGER.error("Invalid name: {}", configData.fogDefinitions[iDefinition].nameColor);
						throw new RuntimeException("Config: fogDefinitions -> Color under specified name was not found!");
					}
				}

				{ // SETTINGS
					settings: for (int iSetting = 0; iSetting < configData.fogSettings.length; ++iSetting) {

						for (int iDefinition = 0; iDefinition < configData.fogDefinitions.length; ++iDefinition) {
							if (configData.fogDefinitions[iDefinition].name.equals(configData.fogSettings[iSetting].nameDefinition)) {

								FogSetting fogSetting = new FogSetting(
									configData.fogSettings[iSetting].world,
									getSeason(configData.fogSettings[iSetting].season),
									configData.fogSettings[iSetting].weather,
									configData.fogSettings[iSetting].time,
									getBiome(configData.fogSettings[iSetting].biome),
									configData.fogSettings[iSetting].yLevel,
									iDefinition
								);

								// 1. Get first element on stack that should be bubble-moved.
								int iTop = getSortTop(fogSetting, iSetting);

								// 2. Now bubble-move all elements from there down. Going from End to Start.
								for (int iBottom = iSetting; iBottom > iTop; --iBottom) {
									Fogger.fogSettings[iBottom] = Fogger.fogSettings[iBottom - 1];
								}

								// 3. ADD to ARRAY
								Fogger.fogSettings[iTop] = fogSetting;

								continue settings;
							}
						}

						// We only end up here if the definition was not found in the loop.
						Fogger.LOGGER.error("Invalid name: {}", configData.fogSettings[iSetting].nameDefinition);
						throw new RuntimeException("Config: fogSettings -> Definition under specified name was not found!");
					}
				}

				//log
				for (int i = 0; i < configData.fogSettings.length; ++i) {
					Fogger.LOGGER.info("{}, {}", i, Fogger.fogSettings[i]);
				}

			}



		} catch (IOException exception) {
			//noinspection CallToPrintStackTrace
			exception.printStackTrace();
		}
	}

	private int getSortTop(FogSetting fogSetting, int iSetting) {
		int iTop = 0;

		for (; iTop < iSetting; ++iTop) {
			FogSetting nextFogSetting = Fogger.fogSettings[iTop];

			boolean isAWorld    = nextFogSetting.world      < fogSetting.world;
			boolean isASeason   = nextFogSetting.season     < fogSetting.season;
			boolean isAWeather  = nextFogSetting.weather    < fogSetting.weather;
			boolean isATime     = nextFogSetting.time       < fogSetting.time;
			boolean isABiome    = nextFogSetting.biome      < fogSetting.biome;
			boolean isAYLevel   = nextFogSetting.yLevel     < fogSetting.yLevel;

			boolean isWorld     = nextFogSetting.world      == fogSetting.world;
			boolean isSeason    = nextFogSetting.season     == fogSetting.season;
			boolean isWeather   = nextFogSetting.weather    == fogSetting.weather;
			boolean isTime      = nextFogSetting.time       == fogSetting.time;
			boolean isBiome     = nextFogSetting.biome      == fogSetting.biome;
			boolean isYLevel    = nextFogSetting.yLevel     == fogSetting.yLevel;

			// SORT (get top element we need to move)
			// (1) by world,
			// (2) by season,
			// (3) by weather,
			// (4) by Time,
			// (5) by Biome,
			// (6) by yLevel,

			if (
				isAWorld ||
					isWorld && isASeason ||
					isWorld && isSeason && isAWeather ||
					isWorld && isSeason && isWeather && isATime ||
					isWorld && isSeason && isWeather && isTime && isABiome ||
					isWorld && isSeason && isWeather && isTime && isBiome && isAYLevel
			) {
				break;
			}
		}

		return iTop;
	}

	private int getSeason(String seasonName) {
		int season = 0;

		if (seasonName != null) {
			season = Seasons.getSeason(seasonName).hashCode();
		}

		return season;
	}

	private int getBiome(String biomeName) {
		int biome = 0;

		if (biomeName != null) {
			biome = Registries.BIOMES.getItem(biomeName).hashCode();
		}

		return biome;
	}

}
