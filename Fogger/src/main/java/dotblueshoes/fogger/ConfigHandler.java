package dotblueshoes.fogger;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

import dotblueshoes.fogger.FogConfiguration;
import dotblueshoes.fogger.Fogger;

public class ConfigHandler {

    public static Configuration config;

    public static FogConfiguration[] biomeFogs = {
        new FogConfiguration("minecraft:ocean", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:plains", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:desert", 0.05F, 0.75F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:extreme_hills", 0.05F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:forest", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:taiga", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:swampland", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:river", 0.05F, 0.6F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:hell", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:sky", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:frozen_ocean", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:frozen_river", 0.05F, 0.55F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:ice_flats", 0.1F, 0.65F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:ice_mountains", 0.1F, 0.65F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mushroom_island", 0.2F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mushroom_island_shore", 0.2F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:beaches", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:desert_hills", 0.05F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:forest_hills", 0.05F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:taiga_hills", 0.05F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:smaller_extreme_hills", 0.05F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:jungle", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:jungle_hills", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:jungle_edge", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:deep_ocean", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:stone_beach", 0.05F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:cold_beach", 0.1F, 0.65F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:birch_forest", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:birch_forest_hills", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:roofed_forest", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:taiga_cold", 0.1F, 0.65F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:taiga_cold_hills", 0.05F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:redwood_taiga", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:redwood_taiga_hills", 0.05F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:extreme_hills_with_trees", 0.05F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:savanna", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:savanna_rock", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mesa", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:wooded_badlands_plateau", 0.05F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mesa_rock", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mesa_clear_rock", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:warm_ocean", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:lukewarm_ocean", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:cold_ocean", 0.1F, 0.75F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:deep_warm_ocean", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:deep_lukewarn_ocean", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:deep_cold_ocean", 0.1F, 0.75F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:deep_frozen_ocean", 0.1F, 0.75F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mutated_plains", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mutated_desert", 0.1F, 0.80F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mutated_extreme_hills", 0.05F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mutated_forest", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mutated_taiga", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mutated_swampland", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mutated_ice_flats", 0.1F, 0.65F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mutated_jungle", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mutated_jungle_edge", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mutated_birch_forest", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mutated_birch_forest_hills", 0.05F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mutated_roofed_forest", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mutated_taiga_cold", 0.1F, 0.65F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mutated_redwood_taiga", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mutated_redwood_taiga_hills", 0.05F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mutated_extreme_hills_with_trees", 0.05F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mutated_savanna", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mutated_savanna_rock", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mutated_mesa", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mutated_mesa_rock", 0.05F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:mutated_mesa_clear_rock", 0.05F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:bamboo_jungle", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:bamboo_jungle_hills", 0.05F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:small_end_islands", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:end_midlands", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:end_highlands", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:end_barrens", 0.1F, 0.95F),//, 168F, 201F, 255F),
		new FogConfiguration("minecraft:void", 0.1F, 0.95F),//, 168F, 201F, 255F)
		new FogConfiguration("biomesoplenty:oasis", 0.1F, 0.85F),
  		new FogConfiguration("biomesoplenty:woodland", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:white_beach", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:shrubland", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:xeric_shrubland", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:tropical_rainforest", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:fen", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:steppe", 0.05F, 0.95F),
  		new FogConfiguration("biomesoplenty:alps_foothills", 0.05F, 0.65F),
  		new FogConfiguration("biomesoplenty:redwood_forest_edge", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:bayou", 0.1F, 0.75F),
  		new FogConfiguration("biomesoplenty:mountain", 0.05F, 0.80F),
  		new FogConfiguration("biomesoplenty:phantasmagoric_inferno", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:overgrown_cliffs", 0.05F, 0.95F),
  		new FogConfiguration("biomesoplenty:highland", 0.05F, 0.95F),
  		new FogConfiguration("biomesoplenty:volcanic_island", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:quagmire", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:marsh", 0.1F, 0.75F),
  		new FogConfiguration("biomesoplenty:chaparral", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:flower_island", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:flower_field", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:wetland", 0.1F, 0.65F),
  		new FogConfiguration("biomesoplenty:mystic_grove", 0.05F, 0.95F),
  		new FogConfiguration("biomesoplenty:cold_desert", 0.1F, 0.65F),
  		new FogConfiguration("biomesoplenty:mountain_foothills", 0.05F, 0.65F),
  		new FogConfiguration("biomesoplenty:maple_woods", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:coral_reef", 0.15F, 0.95F),
  		new FogConfiguration("biomesoplenty:orchard", 0.15F, 1F),
  		new FogConfiguration("biomesoplenty:moor", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:land_of_lakes", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:bog", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:coniferous_forest", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:eucalyptus_forest", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:outback", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:shield", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:snowy_tundra", 0.1F, 0.65F),
  		new FogConfiguration("biomesoplenty:alps", 0.05F, 0.85F),
  		new FogConfiguration("biomesoplenty:wasteland", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:lush_desert", 0.1F, 0.85F),
  		new FogConfiguration("biomesoplenty:mangrove", 0.05F, 0.75F),
  		new FogConfiguration("biomesoplenty:boreal_forest", 0.1F, 0.85F),
  		new FogConfiguration("biomesoplenty:redwood_forest", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:cherry_blossom_grove", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:visceral_heap", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:snowy_forest", 0.1F, 0.55F),
  		new FogConfiguration("biomesoplenty:seasonal_forest", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:temperate_rainforest", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:dead_forest", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:bamboo_forest", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:corrupted_sands", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:dead_swamp", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:kelp_forest", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:pasture", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:glacier", 0.05F, 0.75F),
  		new FogConfiguration("biomesoplenty:meadow", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:lush_swamp", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:fungi_forest", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:tropical_island", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:grassland", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:prairie", 0.05F, 0.95F),
  		new FogConfiguration("biomesoplenty:snowy_coniferous_forest", 0.1F, 0.65F),
  		new FogConfiguration("biomesoplenty:tundra", 0.1F, 0.75F),
  		new FogConfiguration("biomesoplenty:undergarden", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:origin_island", 0.05F, 0.95F),
  		new FogConfiguration("biomesoplenty:gravel_beach", 0.1F, 0.75F),
  		new FogConfiguration("biomesoplenty:grove", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:lavender_fields", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:origin_beach", 0.05F, 0.95F),
  		new FogConfiguration("biomesoplenty:rainforest", 0.1F, 0.85F),
  		new FogConfiguration("biomesoplenty:sacred_springs", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:crag", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:brushland", 0.1F, 0.95F),
  		new FogConfiguration("biomesoplenty:ominous_woods", 0.1F, 0.95F)
    };

	public static float globalFogMinIntensity = 0.05F, globalFogMaxIntensity = 0.95F;
    public static boolean globalFog = false;

    public static void loadConfig(File file) {
        final String
            commentGlobalFog = "Set it to true to simplify Fog rendering and applay single rule to all biomes.",
			commentGlobalMin = "Procentage value of full visible distance where the fog starts.",
			commentGlobalMax = "Procentage value of full visible distance where the fog ends.",
            commentBiomes = "Describe new biomes fog or change exsisting biomes fog params. Note that defining over 1000 biomes will become a bottleneck.";
        
        config = new Configuration(file);
        config.load();

		globalFog = loadBool(
			Configuration.CATEGORY_GENERAL,
			"GlobalFog",
			commentGlobalFog,
			globalFog
		);

		globalFogMinIntensity = (float)loadDouble(
			Configuration.CATEGORY_GENERAL,
			"GlobalFogMinIntensity",
			commentGlobalMin,
			(double)globalFogMinIntensity
		);

		globalFogMaxIntensity = (float)loadDouble(
			Configuration.CATEGORY_GENERAL,
			"GlobalFogMaxIntensity",
			commentGlobalMax,
			(double)globalFogMaxIntensity
		);

        // Fog - Begin
        String[] biomes = new String[biomeFogs.length];
        for (int i = 0; i < biomes.length; i++) {
            biomes[i] = biomeFogs[i].toString();
        } 
		biomes = loadStringArray(
			Configuration.CATEGORY_GENERAL,
			"Biomes",
			commentBiomes,
			biomes
		);
        biomeFogs = new FogConfiguration[biomes.length];
        for (int i = 0; i < biomeFogs.length; i++) {
            biomeFogs[i] = toFogConfiguration(biomes[i]);
        }
        // Fog -End

        if (config.hasChanged()) config.save();
        MinecraftForge.EVENT_BUS.register(new ChangeListener());
    }

    public static FogConfiguration toFogConfiguration(String data) {
        String[] separated = data.split(" ");
        return new FogConfiguration(
            separated[0], 
			Float.parseFloat(separated[1]), 
            Float.parseFloat(separated[2])
            //Float.parseFloat(separated[3]), 
            //Float.parseFloat(separated[4]), 
            //Float.parseFloat(separated[5])
        );
    }

    private static String[] loadStringArray(String category, String name, String comment, String[] data) {
        final Property prop = config.get(category, name, data);
        prop.setComment(comment);
        return prop.getStringList();
    }

    private static boolean loadBool(String category, String name, String comment, boolean data) {
        final Property prop = config.get(category, name, data);
        prop.setComment(comment);
        return prop.getBoolean(data);
    }

	private static double loadDouble(String category, String name, String comment, double data) {
		final Property prop = config.get(category, name, data);
		prop.setComment(comment);
		return prop.getDouble(data);
	}

    public static class ChangeListener {
        @SubscribeEvent
        public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID() == Fogger.MODID);
        }
    }

}