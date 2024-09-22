package dotBlueShoes.fogger.config;

public class PropertyFogSetting {

	public String name;
	public String nameDefinition;
	public int weather;
	public int yLevel;
	public String season;
	public String biome;
	public int world;
	public int time;

	public PropertyFogSetting(String name, int world, String season, int whether, int time, String biome, int yLevel, String nameDefinition) {
		this.nameDefinition = nameDefinition;
		this.weather = whether;
		this.yLevel = yLevel;
		this.season = season;
		this.biome = biome;
		this.world = world;
		this.time = time;
		this.name = name;
	}

}
