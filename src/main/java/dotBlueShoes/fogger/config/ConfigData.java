package dotBlueShoes.fogger.config;

public class ConfigData {

	public boolean isFogColorsOverridden                = true;
	public boolean isFogAutoDarkenByNightSky            = true;
	public boolean isFogCustomColorAppliedToBackground  = true;

	public PropertyFogColor[] fogColors = {
		new PropertyFogColor("zero",        1.00f,      1.00f,  1.00f   ),
		new PropertyFogColor("default",     0.65098f,   0.8f,   1.0f    ),
		new PropertyFogColor("blue_sky",    0.65098f,   0.8f,   1.0f    ),
		new PropertyFogColor("under_water", 0.02f,      0.02f,  0.2f    ),
		new PropertyFogColor("under_lava",  0.6f,       0.1f,   0.0f    ),
	};

	public PropertyFogDefinition[] fogDefinitions = {
		new PropertyFogDefinition("zero",       0.00f, 0.00f, "zero"        ),
		new PropertyFogDefinition("default",    0.00f, 0.90f, "default"     ),
		new PropertyFogDefinition("far",        0.00f, 0.95f, "default"     ),
		new PropertyFogDefinition("normal",     0.00f, 0.80f, "default"     ),
		new PropertyFogDefinition("short",      0.00f, 0.50f, "under_lava"  ),
		new PropertyFogDefinition("tiny",       0.00f, 0.25f, "default"     ),
	};

	// TODO
	// - Sorting! (so it's easier to edit json!)
	// (1) by world,
	// (2) by season,
	// (3) by weather,
	// (4) by yLevel

	public PropertyFogSetting[] fogSettings = {
		new PropertyFogSetting("nether_default",            1, null,                0, 0,      null,                                    0,      "tiny"   ),
		new PropertyFogSetting("overworld_weather_fog",     0, null,                4, 0,      null,                                    0,      "tiny"   ),
		new PropertyFogSetting("overworld_weather_storm",   0, null,                3, 0,      null,                                    0,      "short"  ),
		new PropertyFogSetting("overworld_weather_snow",    0, null,                2, 0,      null,                                    0,      "normal" ),
		new PropertyFogSetting("overworld_weather_rain",    0, null,                1, 0,      null,                                    0,      "far"    ),
		new PropertyFogSetting("after_the_7_day",           0, null,                0, 144000, null,                                    0,      "tiny"   ),
		new PropertyFogSetting("seasonal_forest_clear",     0, "overworld.spring",  0, 0,      "minecraft:overworld.seasonal_forest",   0,      "short"  ),
		new PropertyFogSetting("overworld_clear_y72up",     0, null,                0, 0,      null,                                    150,    "tiny"   ),
		new PropertyFogSetting("default",                   0, null,                0, 0,      null,                                    0,      "default"),
	};

}
