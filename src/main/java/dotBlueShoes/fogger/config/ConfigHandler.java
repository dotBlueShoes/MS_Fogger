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
				Fogger.fogDefinitions = new FogDefinition[configData.fogDefinitions.length];
				Fogger.fogSettings = new FogSetting[configData.fogSettings.length];
				Fogger.fogColors = new FogColor[configData.fogColors.length];

				{ // BOOLS
					Fogger.isFogCustomColorAppliedToBackground = configData.isFogCustomColorAppliedToBackground;
					Fogger.isFogAutoDarkenByNightSky = configData.isFogAutoDarkenByNightSky;
					Fogger.isFogColorsOverridden = configData.isFogColorsOverridden;
				}

				{ // COLORS
					for (int i = 0; i < configData.fogColors.length; ++i) {
						Fogger.fogColors[i] = new FogColor(
							configData.fogColors[i].r,
							configData.fogColors[i].g,
							configData.fogColors[i].b
						);
					}
				}

				{ // DEFINITIONS
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

						Fogger.LOGGER.info("Invalid name: {}", configData.fogDefinitions[iDefinition].nameColor);
						throw new RuntimeException("Config: fogDefinitions -> Color under specified name was not found!");
					}
				}

				{ // SETTINGS
					settings: for (int iSetting = 0; iSetting < configData.fogSettings.length; ++iSetting) {

						for (int iDefinition = 0; iDefinition < configData.fogDefinitions.length; ++iDefinition) {
							if (configData.fogDefinitions[iDefinition].name.equals(configData.fogSettings[iSetting].nameDefinition)) {

								int season = 0;
								int biome = 0;

								if (configData.fogSettings[iSetting].season != null) {
									season = Seasons.getSeason(configData.fogSettings[iSetting].season).hashCode();
									Fogger.LOGGER.info("Season: {}", season);
								}

								if (configData.fogSettings[iSetting].biome != null) {
									biome = Registries.BIOMES.getItem(configData.fogSettings[iSetting].biome).hashCode();
									Fogger.LOGGER.info("Biome: {}", biome);
								}

								Fogger.fogSettings[iSetting] = new FogSetting(
									configData.fogSettings[iSetting].world,
									season,
									configData.fogSettings[iSetting].weather,
									configData.fogSettings[iSetting].time,
									biome,
									configData.fogSettings[iSetting].yLevel,
									iDefinition
								);

								continue settings;

							}
						}

						// We only end up here if the definition was not found in the loop.
						Fogger.LOGGER.info("Invalid name: {}", configData.fogSettings[iSetting].nameDefinition);
						throw new RuntimeException("Config: fogSettings -> Definition under specified name was not found!");
					}
				}


			}



		} catch (IOException exception) {
			//noinspection CallToPrintStackTrace
			exception.printStackTrace();
		}
	}

}
