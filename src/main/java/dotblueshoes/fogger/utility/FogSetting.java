package dotBlueShoes.fogger.utility;

public class FogSetting {

	public byte iFogDefinition = 0;
	public byte weather = 0;
	public int yLevel = 0;
	public byte season = 0;
	public byte biome = 0;
	public byte world = 0;
	public byte time = 0;

	public FogSetting(byte iFogDefinition, byte whether, int yLevel, byte season, byte biome, byte world, byte time) {
		this.iFogDefinition = iFogDefinition;
		this.weather = whether;
		this.yLevel = yLevel;
		this.season = season;
		this.biome = biome;
		this.world = world;
		this.time = time;
	}

}
