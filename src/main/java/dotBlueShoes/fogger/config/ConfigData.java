package dotBlueShoes.fogger.config;

public class ConfigData {

	public static String VERSION = "1.0";

	public static class DefaultValues {
		public PropertyFogColor fogColor = new PropertyFogColor("default", "Fallback color.", 0.65098f, 0.8f, 1.0f);
		public PropertyFogDefinition fogDefinition = new PropertyFogDefinition("default", "Fallback definition.", 0.00f, 0.90f, "default");
		public PropertyFogSetting fogSetting = new PropertyFogSetting("default", "Fallback setting.", 0, null, 0, 0, null, 0, "default");
	}

	public String _comment = "Fogger's Configuration File";
	public String version = VERSION;

	public boolean isFogColorsOverridden                = true;
	public boolean isFogAutoDarkenByNightSky            = true;

	public DefaultValues defaultValues = new DefaultValues();

	public PropertyFogColor[] fogColors = {
		//// 00 - OVERWORLD_RAINFOREST
		//// 01 - OVERWORLD_SWAMPLAND
		//// 02 - OVERWORLD_SEASONAL_FOREST
		//// 03 - OVERWORLD_FOREST
		//// 04 - OVERWORLD_GRASSLANDS
		//// 05 - OVERWORLD_OUTBACK
		//// 06 - OVERWORLD_SHRUBLAND
		//// 07 - OVERWORLD_TAIGA
		//// 08 - OVERWORLD_BOREAL_FOREST
		//// 09 - OVERWORLD_DESERT
		//// 10 - OVERWORLD_PLAINS
		//// 11 - OVERWORLD_GLACIER
		//// 12 - OVERWORLD_TUNDRA
		//// 13 - OVERWORLD_MEADOW
		//// 14 - NETHER_NETHER
		//// 15 - PARADISE_PARADISE
		//// 16 - OVERWORLD_BIRCH_FOREST
		//// 17 - OVERWORLD_RETRO
		//// 18 - OVERWORLD_HELL
		//// 19 - OVERWORLD_SWAMPLAND_MUDDY
		//// 20 - OVERWORLD_OUTBACK_GRASSY
		//// 21 - OVERWORLD_CAATINGA
		//// 22 - OVERWORLD_CAATINGA_PLAINS
		new PropertyFogColor("blue_sky",    "", 0.65098f,   0.8f,       1.0f    ),
		new PropertyFogColor("nether",      "", 0.49609f,   0.07813f,   0.125f  ),
		new PropertyFogColor("under_water", "", 0.02f,      0.02f,      0.2f    ),
		new PropertyFogColor("under_lava",  "", 0.6f,       0.1f,       0.0f    ),
	};

	public PropertyFogDefinition[] fogDefinitions = {
		new PropertyFogDefinition("far",        "", 0.00f, 0.95f, "default"     ),
		new PropertyFogDefinition("normal",     "", 0.00f, 0.80f, "default"     ),
		new PropertyFogDefinition("short",      "", 0.00f, 0.50f, "under_lava"  ),
		new PropertyFogDefinition("tiny",       "", 0.00f, 0.25f, "default"     ),
		new PropertyFogDefinition("nether",     "", 0.00f, 0.80f, "nether"      ),
	};

	public PropertyFogSetting[] fogSettings = {
		new PropertyFogSetting("nether_default",            "", 1, null,                0, 0,      null,                                    0,      "nether" ),
		new PropertyFogSetting("overworld_weather_fog",     "", 0, null,                4, 0,      null,                                    0,      "tiny"   ),
		new PropertyFogSetting("overworld_weather_storm",   "", 0, null,                3, 0,      null,                                    0,      "short"  ),
		new PropertyFogSetting("overworld_weather_snow",    "", 0, null,                2, 0,      null,                                    0,      "normal" ),
		new PropertyFogSetting("overworld_weather_rain",    "", 0, null,                1, 0,      null,                                    0,      "far"    ),
		new PropertyFogSetting("after_the_7_day",           "", 0, null,                0, 144000, null,                                    0,      "tiny"   ),
		new PropertyFogSetting("seasonal_forest_clear",     "", 0, "overworld.spring",  0, 0,      "minecraft:overworld.seasonal_forest",   0,      "short"  ),
		new PropertyFogSetting("overworld_clear_y72up",     "", 0, null,                0, 0,      null,                                    150,    "tiny"   ),
	};

}
