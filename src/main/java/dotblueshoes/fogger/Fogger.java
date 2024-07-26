package dotBlueShoes.fogger;

import dotBlueShoes.fogger.config.MSConfigHandler;
import dotBlueShoes.fogger.utility.FogColor;
import dotBlueShoes.fogger.utility.FogDefinition;
import dotBlueShoes.fogger.utility.FogSetting;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.season.Seasons;
import net.minecraft.core.world.weather.Weather;
import net.minecraft.core.world.weather.WeatherRain;
import net.minecraft.core.world.weather.WeatherSnow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class Fogger implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {

    public static final String MOD_ID = "fogger";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static boolean isFogColorsOverridden = true;
	public static boolean isFogAutoDarkenByNightSky = true;
	public static boolean isFogCustomColorAppliedToBackground = true;

	public static FogColor[] fogColors = {
		FogColor.ZERO,
		FogColor.DEFAULT,
		FogColor.SKY_BLUE,
		FogColor.UnderWaterNBD,
		FogColor.UnderLava,
	};

	public static FogDefinition[] fogDefinitions = {
		FogDefinition.ZERO,
		FogDefinition.DEFAULT,
		new FogDefinition(0.00f, 0.95f, (byte)1),
		new FogDefinition(0.00f, 0.80f, (byte)1),
		new FogDefinition(0.00f, 0.50f, (byte)1),
		new FogDefinition(0.00f, 0.25f, (byte)1),
	};

	// Sorted by:
	// 1-world, 2-season, 3-weather, 4-yLevel

	public static FogSetting[] fogSettings = {
		/* 0 */ new FogSetting((byte)1, 0, (byte)0,       0, 0,  0, (byte)5), // NETHER-DEFAULT
		/* 1 */ new FogSetting((byte)0, 0, (byte)4,       0, 0,  0, (byte)5), // WeatherFog
		/* 2 */ new FogSetting((byte)0, 0, (byte)3,       0, 0,  0, (byte)4), // WeatherStorm
		/* 3 */ new FogSetting((byte)0, 0, (byte)2,       0, 0,  0, (byte)3), // WeatherSnow
		/* 4 */ new FogSetting((byte)0, 0, (byte)1,       0, 0,  0, (byte)2), // WeatherRain
		/* 5 */ new FogSetting((byte)0, 0, (byte)0,  144000, 0,  0, (byte)5), // 7th day and up
		/* 6 */ new FogSetting((byte)0, 0, (byte)0,       0, 0,  0, (byte)4), // WeatherClear - SeasonalForest
		/* 7 */ new FogSetting((byte)0, 0, (byte)0,       0, 0,150, (byte)5), // OVERWORLD-CLEAR-Y>72
		/* 8 */ new FogSetting((byte)0, 0, (byte)0,       0, 0,  0, (byte)1), // WeatherClear
	};

    @Override
    public void onInitialize() {
        LOGGER.info("Fogger initialized.");

		// Create a Look-Up-Table for Biomes to store byte instead of int.
	    //biomeLUT = new int[Registries.BIOMES.size()];
	    //for (byte iBiome = 0; iBiome < Registries.BIOMES.size(); ++iBiome) {
		//    biomeLUT[iBiome] = Registries.BIOMES.getItemByNumericId(iBiome).hashCode();
	    //}

	    final String seasonStr = "overworld.spring";
	    fogSettings[6].season = Seasons.getSeason(seasonStr).hashCode();

	    final String biomeStr = "minecraft:overworld.seasonal_forest"; // Read String from file
	    fogSettings[6].biome = Registries.BIOMES.getItem(biomeStr).hashCode();

	    //Seasons.getAllSeasons();

    }

	@Override
	public void beforeGameStart() {
		MSConfigHandler config = new MSConfigHandler(MOD_ID);
		config.create();
		//Config.Update();
		//LOGGER.info("int: {}", Config.cfg.getInt("Sample Sample.foggerInt"));
		//LOGGER.info("bool: {}", Config.cfg.getBoolean("Sample.foggerBool"));
		//LOGGER.info("float: {}", Config.cfg.getFloat("Sample.foggerFloat"));
		//LOGGER.info("double: {}", Config.cfg.getDouble("Sample.foggerDouble"));
		//LOGGER.info("int: {}", Config.cfg.getInt("Sample.foggerInt"));
		//LOGGER.info("string: {}", Config.cfg.getString("Sample.foggerString"));
		//LOGGER.info("length: {}", Config.fogDefinitions.length);
	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {

	}

	@Override
	public void initNamespaces() {

	}
}
