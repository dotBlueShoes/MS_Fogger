package dotBlueShoes.fogger.utility;

import net.minecraft.core.block.Block;
import net.minecraft.core.world.biome.*;
import net.minecraft.core.world.weather.Weather;

public class FogSetting {

	public byte iFogDefinition = 0; // max 256 fogDefinitions
	public byte weather = 0;        // max 256 weathers
	public int yLevel = 0;          // - Works with +/- and above 255 blocks.
	public int season = 0;         // - Pointer Address and not a byte index!
	public int biome = 0;           // - Pointer Address and not a byte index!
	public byte world = 0;          // Max 256 dimensions
	public int time = 0;           // - 64-bit // TODO: Make it smaller!

	public FogSetting(byte world, int season, byte whether, int time, int biome, int yLevel, byte iFogDefinition) {
		this.iFogDefinition = iFogDefinition;
		this.weather = whether;
		this.yLevel = yLevel;
		this.season = season;
		this.biome = biome;
		this.world = world;
		this.time = time;
	}

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
