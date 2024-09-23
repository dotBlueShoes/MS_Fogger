package dotBlueShoes.fogger.config;

public class PropertyFogSetting {

	public String name, _comment, nameDefinition, season, biome;
	public int weather, yLevel, world, time;

	public PropertyFogSetting(String name, String _comment, int world, String season, int whether, int time, String biome, int yLevel, String nameDefinition) {
		this.nameDefinition = nameDefinition;
		this._comment = _comment;
		this.weather = whether;
		this.yLevel = yLevel;
		this.season = season;
		this.biome = biome;
		this.world = world;
		this.time = time;
		this.name = name;
	}

}
