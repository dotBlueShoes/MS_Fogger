package dotblueshoes.fogger;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

import dotblueshoes.fogger.FogConfiguration;
import dotblueshoes.fogger.FogDefinition;
import dotblueshoes.fogger.Fogger;

public class ConfigHandler {

	// Listener Object, registration thingy.
	public static class ChangeListener {
        @SubscribeEvent
        public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID() == Fogger.MODID);
        }
    }

	public static Configuration config;
	public static boolean globalFog = false;
	
	public static FogDefinition defaultFogDefinition = new FogDefinition("", 0.05F, 0.95F);

	//public static FogDefinition[] fogSettings = {
	//	new FogFefinition();
	//} 
	//FogDefinitionMapper

    public static FogConfiguration[] biomeFogs = {
        new FogConfiguration("minecraft:ocean", 							new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:plains",							new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:desert", 							new FogDefinition("", 0.05F, 0.75F)),
		new FogConfiguration("minecraft:extreme_hills", 					new FogDefinition("", 0.05F, 0.95F)),
		new FogConfiguration("minecraft:forest", 							new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:taiga", 							new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:swampland", 						new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:river", 							new FogDefinition("", 0.05F, 0.6F)),
		new FogConfiguration("minecraft:hell", 								new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:sky", 								new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:frozen_ocean", 						new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:frozen_river", 						new FogDefinition("", 0.05F, 0.55F)),
		new FogConfiguration("minecraft:ice_flats", 						new FogDefinition("", 0.1F, 0.65F)),
		new FogConfiguration("minecraft:ice_mountains", 					new FogDefinition("", 0.1F, 0.65F)),
		new FogConfiguration("minecraft:mushroom_island", 					new FogDefinition("", 0.2F, 0.95F)),
		new FogConfiguration("minecraft:mushroom_island_shore", 			new FogDefinition("", 0.2F, 0.95F)),
		new FogConfiguration("minecraft:beaches", 							new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:desert_hills", 						new FogDefinition("", 0.05F, 0.95F)),
		new FogConfiguration("minecraft:forest_hills", 						new FogDefinition("", 0.05F, 0.95F)),
		new FogConfiguration("minecraft:taiga_hills", 						new FogDefinition("", 0.05F, 0.95F)),
		new FogConfiguration("minecraft:smaller_extreme_hills", 			new FogDefinition("", 0.05F, 0.95F)),
		new FogConfiguration("minecraft:jungle", 							new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:jungle_hills", 						new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:jungle_edge", 						new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:deep_ocean", 						new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:stone_beach", 						new FogDefinition("", 0.05F, 0.95F)),
		new FogConfiguration("minecraft:cold_beach", 						new FogDefinition("", 0.1F, 0.65F)),
		new FogConfiguration("minecraft:birch_forest", 						new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:birch_forest_hills", 				new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:roofed_forest", 					new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:taiga_cold", 						new FogDefinition("", 0.1F, 0.65F)),
		new FogConfiguration("minecraft:taiga_cold_hills", 					new FogDefinition("", 0.05F, 0.95F)),
		new FogConfiguration("minecraft:redwood_taiga", 					new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:redwood_taiga_hills", 				new FogDefinition("", 0.05F, 0.95F)),
		new FogConfiguration("minecraft:extreme_hills_with_trees", 			new FogDefinition("", 0.05F, 0.95F)),
		new FogConfiguration("minecraft:savanna", 							new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:savanna_rock", 						new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:mesa", 								new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:wooded_badlands_plateau", 			new FogDefinition("", 0.05F, 0.95F)),
		new FogConfiguration("minecraft:mesa_rock", 						new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:mesa_clear_rock", 					new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:warm_ocean", 						new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:lukewarm_ocean", 					new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:cold_ocean", 						new FogDefinition("", 0.1F, 0.75F)),
		new FogConfiguration("minecraft:deep_warm_ocean", 					new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:deep_lukewarn_ocean", 				new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:deep_cold_ocean", 					new FogDefinition("", 0.1F, 0.75F)),
		new FogConfiguration("minecraft:deep_frozen_ocean", 				new FogDefinition("", 0.1F, 0.75F)),
		new FogConfiguration("minecraft:mutated_plains", 					new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:mutated_desert", 					new FogDefinition("", 0.1F, 0.80F)),
		new FogConfiguration("minecraft:mutated_extreme_hills", 			new FogDefinition("", 0.05F, 0.95F)),
		new FogConfiguration("minecraft:mutated_forest", 					new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:mutated_taiga", 					new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:mutated_swampland", 				new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:mutated_ice_flats", 				new FogDefinition("", 0.1F, 0.65F)),
		new FogConfiguration("minecraft:mutated_jungle", 					new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:mutated_jungle_edge", 				new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:mutated_birch_forest",				new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:mutated_birch_forest_hills", 		new FogDefinition("", 0.05F, 0.95F)),
		new FogConfiguration("minecraft:mutated_roofed_forest", 			new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:mutated_taiga_cold", 				new FogDefinition("", 0.1F, 0.65F)),
		new FogConfiguration("minecraft:mutated_redwood_taiga", 			new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:mutated_redwood_taiga_hills", 		new FogDefinition("", 0.05F, 0.95F)),
		new FogConfiguration("minecraft:mutated_extreme_hills_with_trees", 	new FogDefinition("", 0.05F, 0.95F)),
		new FogConfiguration("minecraft:mutated_savanna", 					new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:mutated_savanna_rock",				new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:mutated_mesa", 						new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:mutated_mesa_rock", 				new FogDefinition("", 0.05F, 0.95F)),
		new FogConfiguration("minecraft:mutated_mesa_clear_rock", 			new FogDefinition("", 0.05F, 0.95F)),
		new FogConfiguration("minecraft:bamboo_jungle", 					new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:bamboo_jungle_hills",		 		new FogDefinition("", 0.05F, 0.95F)),
		new FogConfiguration("minecraft:small_end_islands", 				new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:end_midlands", 						new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:end_highlands", 					new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:end_barrens", 						new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("minecraft:void", 								new FogDefinition("", 0.1F, 0.95F)),
		new FogConfiguration("biomesoplenty:oasis", 						new FogDefinition("", 0.1F, 0.85F)),
  		new FogConfiguration("biomesoplenty:woodland", 						new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:white_beach", 					new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:shrubland",						new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:xeric_shrubland", 				new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:tropical_rainforest", 			new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:fen", 							new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:steppe", 						new FogDefinition("", 0.05F, 0.95F)),
  		new FogConfiguration("biomesoplenty:alps_foothills", 				new FogDefinition("", 0.05F, 0.65F)),
  		new FogConfiguration("biomesoplenty:redwood_forest_edge", 			new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:bayou", 						new FogDefinition("", 0.1F, 0.75F)),
  		new FogConfiguration("biomesoplenty:mountain", 						new FogDefinition("", 0.05F, 0.80F)),
  		new FogConfiguration("biomesoplenty:phantasmagoric_inferno", 		new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:overgrown_cliffs", 				new FogDefinition("", 0.05F, 0.95F)),
  		new FogConfiguration("biomesoplenty:highland", 						new FogDefinition("", 0.05F, 0.95F)),
  		new FogConfiguration("biomesoplenty:volcanic_island", 				new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:quagmire", 						new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:marsh",							new FogDefinition("", 0.1F, 0.75F)),
  		new FogConfiguration("biomesoplenty:chaparral", 					new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:flower_island", 				new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:flower_field", 					new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:wetland", 						new FogDefinition("", 0.1F, 0.65F)),
  		new FogConfiguration("biomesoplenty:mystic_grove", 					new FogDefinition("", 0.05F, 0.95F)),
  		new FogConfiguration("biomesoplenty:cold_desert", 					new FogDefinition("", 0.1F, 0.65F)),
  		new FogConfiguration("biomesoplenty:mountain_foothills", 			new FogDefinition("", 0.05F, 0.65F)),
  		new FogConfiguration("biomesoplenty:maple_woods", 					new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:coral_reef", 					new FogDefinition("", 0.15F, 0.95F)),
  		new FogConfiguration("biomesoplenty:orchard", 						new FogDefinition("", 0.15F, 1F)),
  		new FogConfiguration("biomesoplenty:moor", 							new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:land_of_lakes", 				new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:bog", 							new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:coniferous_forest", 			new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:eucalyptus_forest", 			new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:outback", 						new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:shield", 						new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:snowy_tundra", 					new FogDefinition("", 0.1F, 0.65F)),
  		new FogConfiguration("biomesoplenty:alps", 							new FogDefinition("", 0.05F, 0.85F)),
  		new FogConfiguration("biomesoplenty:wasteland", 					new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:lush_desert", 					new FogDefinition("", 0.1F, 0.85F)),
  		new FogConfiguration("biomesoplenty:mangrove", 						new FogDefinition("", 0.05F, 0.75F)),
  		new FogConfiguration("biomesoplenty:boreal_forest", 				new FogDefinition("", 0.1F, 0.85F)),
  		new FogConfiguration("biomesoplenty:redwood_forest", 				new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:cherry_blossom_grove", 			new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:visceral_heap", 				new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:snowy_forest", 					new FogDefinition("", 0.1F, 0.55F)),
  		new FogConfiguration("biomesoplenty:seasonal_forest", 				new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:temperate_rainforest", 			new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:dead_forest",	 				new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:bamboo_forest", 				new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:corrupted_sands", 				new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:dead_swamp", 					new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:kelp_forest", 					new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:pasture", 						new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:glacier", 						new FogDefinition("", 0.05F, 0.75F)),
  		new FogConfiguration("biomesoplenty:meadow", 						new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:lush_swamp", 					new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:fungi_forest", 					new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:tropical_island", 				new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:grassland", 					new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:prairie", 						new FogDefinition("", 0.05F, 0.95F)),
  		new FogConfiguration("biomesoplenty:snowy_coniferous_forest", 		new FogDefinition("", 0.1F, 0.65F)),
  		new FogConfiguration("biomesoplenty:tundra", 						new FogDefinition("", 0.1F, 0.75F)),
  		new FogConfiguration("biomesoplenty:undergarden", 					new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:origin_island", 				new FogDefinition("", 0.05F, 0.95F)),
  		new FogConfiguration("biomesoplenty:gravel_beach", 					new FogDefinition("", 0.1F, 0.75F)),
  		new FogConfiguration("biomesoplenty:grove", 						new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:lavender_fields", 				new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:origin_beach",	 				new FogDefinition("", 0.05F, 0.95F)),
  		new FogConfiguration("biomesoplenty:rainforest", 					new FogDefinition("", 0.1F, 0.85F)),
  		new FogConfiguration("biomesoplenty:sacred_springs", 				new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:crag", 							new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:brushland", 					new FogDefinition("", 0.1F, 0.95F)),
  		new FogConfiguration("biomesoplenty:ominous_woods", 				new FogDefinition("", 0.1F, 0.95F))
	};

	// Here the config file gets created/loaded.
	public static void loadConfigurationFile(File file) {

        final String
            commentGlobalFog = "Set it to true to simplify Fog rendering and applay single rule to all biomes.",
			commentDefaultFogDefinition = "Procentage value of full visible distance where the fog starts and ends.",
            commentBiomes = "Describe new biomes fog or change exsisting biomes fog params. Note that defining over 1000 biomes will become a bottleneck.";
        
        config = new Configuration(file);
        config.load();

		// Global Fog Setup.
		globalFog = loadBool (
			Configuration.CATEGORY_GENERAL,
			"GlobalFog",
			commentGlobalFog,
			globalFog
		);

		// Default Fog Setting Setup.
		defaultFogDefinition = loadFogDefinition (
			Configuration.CATEGORY_GENERAL,
		 	"DefaultFogDefinition",
		 	commentDefaultFogDefinition,
		 	defaultFogDefinition
		);

		// Biome Fogs Setup.
		biomeFogs = loadFogConfigurations (
			Configuration.CATEGORY_GENERAL,
		 	"Biomes",
		 	commentBiomes,
		 	biomeFogs
		);

        if (config.hasChanged()) config.save();
        	MinecraftForge.EVENT_BUS.register(new ChangeListener());
    }


	private static FogConfiguration[] loadFogConfigurations(String category, String name, String comment, FogConfiguration[] data) {
		String[] lines = new String[data.length];
		FogConfiguration[] output;
		Property prop;

		// Fill the String array.
		for (int i = 0; i < data.length; i++)
			lines[i] = data[i].toString();
		
		// Get/Create the property.
		prop = config.get(category, name, lines);
		prop.setComment(comment);

		// Get the values/array-length from config.
		lines = prop.getStringList();
		output = new FogConfiguration[lines.length];

		// Fill the output array.
		for (int i = 0; i < output.length; i++)
			output[i] = FogConfiguration.parseString(lines[i]);

		return output;
	}

	private static FogDefinition loadFogDefinition(String category, String name, String comment, FogDefinition data) {
		final String line = data.toString();
		final Property prop = config.get(category, name, line);
		prop.setComment(comment);
		return FogDefinition.parseString(prop.getString());
	}

    private static boolean loadBool(String category, String name, String comment, boolean data) {
        final Property prop = config.get(category, name, data);
        prop.setComment(comment);
        return prop.getBoolean(data);
    }

}