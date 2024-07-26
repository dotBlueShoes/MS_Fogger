package dotBlueShoes.fogger;

import dotBlueShoes.fogger.utility.FogDefinition;
import dotBlueShoes.fogger.utility.FogSetting;
import turniplabs.halplibe.util.ConfigUpdater;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

public class Config {

	public static ConfigUpdater updater = ConfigUpdater.fromProperties();
	private static final Toml properties = new Toml("Fogger TOML Config");
	public static TomlConfigHandler cfg;

	public static String[] fogDefinitionNames = { "Default" };
	public static FogDefinition[] fogDefinitions = { FogDefinition.DEFAULT };



	static Toml tomlDefinitions = properties.addCategory("Definitions")
		.addEntry("size", fogDefinitions.length);

	static Toml tomlColors = properties.addCategory("Colors")
		.addEntry("size", fogDefinitions.length);

	static Toml tomlSettings = properties.addCategory("Settings")
		.addEntry("size", fogDefinitions.length);

	static {
		cfg = new TomlConfigHandler(updater, Fogger.MOD_ID, properties);
	}


	public static void Update() {
		/*
		int definitionsCount = cfg.getInt("Definitions.size");
		fogDefinitions = new FogDefinition[definitionsCount];

		for (int i = 0; i < fogDefinitions.length; ++i) {
			tomlDefinitions.addCategory(Integer.toString(i))
				.addEntry("n", "huh")
				.addEntry("s", 0)
				.addEntry("e", 0)
				.addEntry("c", 0);
		}

		updater.update();

		//Fogger.LOGGER.info("{}, {}, {}, {}", sName, sStart, sEnd, sIColor);

		for (int i = 0; i < fogDefinitions.length; ++i) {
			final String sName    = "Definitions." + i + ".n";
			final String sStart   = "Definitions." + i + ".s";
			final String sEnd     = "Definitions." + i + ".e";
			final String sIColor  = "Definitions." + i + ".c";

			final String name = cfg.getString(sName);
			final double start = cfg.getDouble(sStart);
			final double end = cfg.getDouble(sEnd);
			//final int iColor = cfg.getInt(sIColor);

			//Fogger.LOGGER.info("{}, {}, {}, {}", name, start, end, iColor);
		}
		*/
	}

}
