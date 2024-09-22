package dotBlueShoes.fogger.utility;

public class FogSetting {

	public int iFogDefinition; // nope // max 256 fogDefinitions
	public int weather;        // nope // max 256 weathers
	public int yLevel;         // - Works with +/- and above 255 blocks.
	public int season;         // - Pointer Address and not a byte index!
	public int biome;          // - Pointer Address and not a byte index!
	public int world;          // nope // Max 256 dimensions
	public int time;           // - 64-bit // TODO: Make it smaller!

	public FogSetting(int world, int season, int weather, int time, int biome, int yLevel, int iFogDefinition) {
		this.iFogDefinition = iFogDefinition;
		this.weather = weather;
		this.yLevel = yLevel;
		this.season = season;
		this.biome = biome;
		this.world = world;
		this.time = time;
	}

	//public static final FogSetting VANILLA_RAIN
	// VANILLA BIOMES -> Biomes.class // NOPE
	// 00 - OVERWORLD_RAINFOREST
	// 01 - OVERWORLD_SWAMPLAND
	// 02 - OVERWORLD_SEASONAL_FOREST
	// 03 - OVERWORLD_FOREST
	// 04 - OVERWORLD_GRASSLANDS
	// 05 - OVERWORLD_OUTBACK
	// 06 - OVERWORLD_SHRUBLAND
	// 07 - OVERWORLD_TAIGA
	// 08 - OVERWORLD_BOREAL_FOREST
	// 09 - OVERWORLD_DESERT
	// 10 - OVERWORLD_PLAINS
	// 11 - OVERWORLD_GLACIER
	// 12 - OVERWORLD_TUNDRA
	// 13 - OVERWORLD_MEADOW
	// 14 - NETHER_NETHER
	// 15 - PARADISE_PARADISE
	// 16 - OVERWORLD_BIRCH_FOREST
	// 17 - OVERWORLD_RETRO
	// 18 - OVERWORLD_HELL
	// 19 - OVERWORLD_SWAMPLAND_MUDDY
	// 20 - OVERWORLD_OUTBACK_GRASSY
	// 21 - OVERWORLD_CAATINGA
	// 22 - OVERWORLD_CAATINGA_PLAINS

}
