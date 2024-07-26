package dotBlueShoes.fogger.config;

public class ConfigTypes {

	// NOPE that's not the way..

	public static class ConfigFogColor {
		public String idName;
		public float r, g, b;
	}

	public static class ConfigFogDefinition {
		public String idName, idColor;
		public float start, end;
	}

	public static class ConfigFogSetting {
		public String idDefinition;
		public int yLevel, season, biome, time;
		public byte weather, world;
	}

}
