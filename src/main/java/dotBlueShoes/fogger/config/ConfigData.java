package dotBlueShoes.fogger.config;

public class ConfigData {

	public static String VERSION = "1.0";

	public static class DefaultValues {

		public PropertyFogColor fogColor = new PropertyFogColor(
			"default", "Fallback color.",
			166, 204, 255
		);

		public PropertyFogDefinition fogDefinition = new PropertyFogDefinition(
			"default", "Fallback definition.",
			0.00f, 0.90f, "default"

		);

		public PropertyFogSetting fogSetting = new PropertyFogSetting(
			"default", "Fallback setting.",
			0, null, 0, 0, null, 0, "default"
		);

	}

	public String _comment = "Fogger's Configuration File";
	public String version = VERSION;

	public boolean isFogColorsOverridden                = true;
	public boolean isFogAutoDarkenByNightSky            = true;

	public DefaultValues defaultValues = new DefaultValues();

	public PropertyFogColor[] fogColors = {
		new PropertyFogColor("white",   "", 255, 255, 255),
		new PropertyFogColor("black",   "", 0,   0,   0  ),
		new PropertyFogColor("nether",  "", 127, 20,  32 ),
		new PropertyFogColor("snow",    "", 230, 230, 230),
		new PropertyFogColor("magical", "", 174, 165, 255),
	};

	public PropertyFogDefinition[] fogDefinitions = {
		new PropertyFogDefinition("nether",     "", 0.00f, 0.50f, "nether"  ),
		new PropertyFogDefinition("snow",       "", 0.00f, 0.30f, "snow"    ),
		new PropertyFogDefinition("high",       "", 0.00f, 0.50f, "default" ),
		new PropertyFogDefinition("very_high",  "", 0.00f, 0.30f, "white"   ),
		new PropertyFogDefinition("cold",       "", 0.00f, 0.75f, "default" ),
		new PropertyFogDefinition("colder",     "", 0.00f, 0.55f, "default" ),
		new PropertyFogDefinition("magical",    "", 0.00f, 0.60f, "magical" ),
	};

	public PropertyFogSetting[] fogSettings = {
		new PropertyFogSetting("nether",        "", 1, null,               0, 0, null,                                  64,     "nether"),
		new PropertyFogSetting("s_snow",        "", 0, "overworld.summer", 2, 0, null,                                  64,     "snow"),
		new PropertyFogSetting("s_very_high",   "", 0, "overworld.summer", 0, 0, null,                                  250,    "very_high"),
		new PropertyFogSetting("s_high",        "", 0, "overworld.summer", 0, 0, null,                                  150,    "high"),
		new PropertyFogSetting("summer",        "", 0, "overworld.summer", 0, 0, null,                                  64,     "default"),
		new PropertyFogSetting("a_snow",        "", 0, "overworld.autumn", 2, 0, null,                                  64,     "snow"),
		new PropertyFogSetting("a_very_high",   "", 0, "overworld.autumn", 0, 0, null,                                  250,    "very_high"),
		new PropertyFogSetting("a_high",        "", 0, "overworld.autumn", 0, 0, null,                                  150,    "high"),
		new PropertyFogSetting("autumn",        "", 0, "overworld.autumn", 0, 0, null,                                  64,     "cold"),
		new PropertyFogSetting("w_magical",     "", 0, "overworld.winter", 2, 0, "minecraft:overworld.seasonal_forest", 64,     "magical"),
		new PropertyFogSetting("w_snow",        "", 0, "overworld.winter", 2, 0, null,                                  64,     "snow"),
		new PropertyFogSetting("w_very_high",   "", 0, "overworld.winter", 0, 0, null,                                  250,    "very_high"),
		new PropertyFogSetting("w_high",        "", 0, "overworld.winter", 0, 0, null,                                  150,    "high"),
		new PropertyFogSetting("winter",        "", 0, "overworld.winter", 0, 0, null,                                  64,     "colder"),
		new PropertyFogSetting("s_snow",        "", 0, "overworld.spring", 2, 0, null,                                  64,     "snow"),
		new PropertyFogSetting("s_very_high",   "", 0, "overworld.spring", 0, 0, null,                                  250,    "very_high"),
		new PropertyFogSetting("s_high",        "", 0, "overworld.spring", 0, 0, null,                                  150,    "high"),
		new PropertyFogSetting("spring",        "", 0, "overworld.spring", 0, 0, null,                                  64,     "cold"),
	};

}
