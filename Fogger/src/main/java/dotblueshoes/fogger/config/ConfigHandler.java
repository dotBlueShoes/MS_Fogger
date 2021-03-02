package dotblueshoes.fogger.config;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

import dotblueshoes.fogger.config.FogDefinitionMapper;
import dotblueshoes.fogger.config.FogDefinition;
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
	public static float
		defaultFogMinIntensity = 0.10F,
		defaultFogMaxIntensity = 0.95F;

	public static FogDefinition[] fogDefinitions = {
		new FogDefinition("warm-forest-1", 		0.10F, 0.85F), // biomesoplenty:rainforest
		new FogDefinition("warm-desert-1", 		0.05F, 0.75F), // minecraft:desert
		new FogDefinition("warm-desert-2", 		0.10F, 0.80F), // minecraft:mutated_desert
		new FogDefinition("warm-water-1", 		0.15F, 0.95F), // biomesoplenty:coral_reef
		new FogDefinition("warm-swamp-1", 		0.10F, 0.75F), // biomesoplenty:bayou
		new FogDefinition("warm-swamp-2", 		0.10F, 0.65F), // biomesoplenty:wetland
		new FogDefinition("temperate-fields-1", 0.15F, 1.00F), // biomesoplenty:orchard
		new FogDefinition("temperate-desert-1", 0.10F, 0.85F), // biomesoplenty:lush_desert
		new FogDefinition("temperate-hills-1", 	0.05F, 0.95F), // minecraft:extreme_hills, minecraft:desert_hills, minecraft:forest_hills, minecraft:taiga_hills, minecraft:smaller_extreme_hills, minecraft:stone_beach, 
		new FogDefinition("temperate-river-1", 	0.05F, 0.60F), // river
		new FogDefinition("cold-fields-1", 		0.05F, 0.65F), // cold
		new FogDefinition("cold-forest-1", 		0.05F, 0.85F), // boreal-forest
		new FogDefinition("cold-forest-2", 		0.05F, 0.75F), // tundra
		new FogDefinition("cold-forest-3", 		0.05F, 0.55F), // snowy-forest
		new FogDefinition("cold-hills-1", 		0.05F, 0.65F), // cold-hills
		new FogDefinition("cold-hills-2", 		0.05F, 0.80F), // hills-variation-1
		new FogDefinition("cold-hills-3", 		0.05F, 0.75F), // glacier
		new FogDefinition("cold-hills-4", 		0.05F, 0.85F), // alps
		new FogDefinition("cold-river-1", 		0.05F, 0.55F), // cold-river
		new FogDefinition("cold-water-1", 		0.10F, 0.75F), // cold-ocean
		new FogDefinition("cold-beach-1", 		0.10F, 0.75F), // gravel-beach
		new FogDefinition("mushroom-island", 	0.20F, 0.95F), // mushroom_island
		new FogDefinition("mangrove", 			0.05F, 0.75F) // mangrove
	};

    public static FogDefinitionMapper[] mappedFogDefinitions = {
		new FogDefinitionMapper("minecraft:desert", 							0F, "warm-desert-1"),
		new FogDefinitionMapper("minecraft:extreme_hills", 						0F, "temperate-hills-1"),
		new FogDefinitionMapper("minecraft:river", 								0F, "temperate-river-1"),
		new FogDefinitionMapper("minecraft:frozen_river", 						0F, "cold-river-1"),
		new FogDefinitionMapper("minecraft:ice_flats", 							0F, "cold-fields-1"),
		new FogDefinitionMapper("minecraft:ice_mountains", 						0F, "cold-hills-1"),
		new FogDefinitionMapper("minecraft:mushroom_island", 					0F, "mushroom-island"),
		new FogDefinitionMapper("minecraft:mushroom_island_shore", 				0F, "mushroom-island"),
		new FogDefinitionMapper("minecraft:desert_hills", 						0F, "temperate-hills-1"),
		new FogDefinitionMapper("minecraft:forest_hills", 						0F, "temperate-hills-1"),
		new FogDefinitionMapper("minecraft:taiga_hills", 						0F, "temperate-hills-1"),
		new FogDefinitionMapper("minecraft:smaller_extreme_hills", 				0F, "temperate-hills-1"),
		new FogDefinitionMapper("minecraft:stone_beach", 						0F, "temperate-hills-1"),
		new FogDefinitionMapper("minecraft:cold_beach", 						0F, "cold-fields-1"),
		new FogDefinitionMapper("minecraft:taiga_cold", 						0F, "cold-fields-1"),
		new FogDefinitionMapper("minecraft:taiga_cold_hills", 					0F, "temperate-hills-1"),
		new FogDefinitionMapper("minecraft:redwood_taiga_hills", 				0F, "temperate-hills-1"),
		new FogDefinitionMapper("minecraft:extreme_hills_with_trees", 			0F, "temperate-hills-1"),
		new FogDefinitionMapper("minecraft:wooded_badlands_plateau", 			0F, "temperate-hills-1"),
		new FogDefinitionMapper("minecraft:cold_ocean", 						0F, "cold-water-1"),
		new FogDefinitionMapper("minecraft:deep_cold_ocean", 					0F, "cold-water-1"),
		new FogDefinitionMapper("minecraft:deep_frozen_ocean", 					0F, "cold-water-1"),
		new FogDefinitionMapper("minecraft:mutated_desert", 					0F, "warm-desert-2"),
		new FogDefinitionMapper("minecraft:mutated_extreme_hills", 				0F, "temperate-hills-1"),
		new FogDefinitionMapper("minecraft:mutated_ice_flats", 					0F, "cold-fields-1"),
		new FogDefinitionMapper("minecraft:mutated_birch_forest_hills", 		0F, "temperate-hills-1"),
		new FogDefinitionMapper("minecraft:mutated_taiga_cold", 				0F, "cold-fields-1"),
		new FogDefinitionMapper("minecraft:mutated_redwood_taiga_hills", 		0F, "temperate-hills-1"),
		new FogDefinitionMapper("minecraft:mutated_extreme_hills_with_trees", 	0F, "temperate-hills-1"),
		new FogDefinitionMapper("minecraft:mutated_mesa_rock", 					0F, "temperate-hills-1"),
		new FogDefinitionMapper("minecraft:mutated_mesa_clear_rock", 			0F, "temperate-hills-1"),
		new FogDefinitionMapper("minecraft:bamboo_jungle_hills",		 		0F, "temperate-hills-1"),
		new FogDefinitionMapper("biomesoplenty:oasis", 							0F, "warm-forest-1"),
  		new FogDefinitionMapper("biomesoplenty:steppe", 						0F, "temperate-hills-1"),
  		new FogDefinitionMapper("biomesoplenty:alps_foothills", 				0F, "cold-hills-1"),
  		new FogDefinitionMapper("biomesoplenty:bayou", 							0F, "warm-swamp-1"),
  		new FogDefinitionMapper("biomesoplenty:mountain", 						0F, "cold-hills-2"),
  		new FogDefinitionMapper("biomesoplenty:overgrown_cliffs", 				0F, "temperate-hills-1"),
  		new FogDefinitionMapper("biomesoplenty:highland", 						0F, "temperate-hills-1"),
  		new FogDefinitionMapper("biomesoplenty:marsh",							0F, "warm-swamp-1"),
  		new FogDefinitionMapper("biomesoplenty:wetland", 						0F, "warm-swamp-2"),
  		new FogDefinitionMapper("biomesoplenty:mystic_grove", 					0F, "temperate-hills-1"),
  		new FogDefinitionMapper("biomesoplenty:cold_desert", 					0F, "cold-fields-1"),
  		new FogDefinitionMapper("biomesoplenty:mountain_foothills", 			0F, "cold-hills-1"),
  		new FogDefinitionMapper("biomesoplenty:coral_reef", 					0F, "warm-water-1"),
  		new FogDefinitionMapper("biomesoplenty:orchard", 						0F, "temperate-fields-1"),
  		new FogDefinitionMapper("biomesoplenty:snowy_tundra", 					0F, "cold-fields-1"),
  		new FogDefinitionMapper("biomesoplenty:alps", 							0F, "cold-hills-4"),
  		new FogDefinitionMapper("biomesoplenty:lush_desert", 					0F, "warm-forest-1"),
  		new FogDefinitionMapper("biomesoplenty:mangrove", 						0F, "mangrove"),
  		new FogDefinitionMapper("biomesoplenty:boreal_forest", 					0F, "cold-forest-1"),
  		new FogDefinitionMapper("biomesoplenty:snowy_forest", 					0F, "cold-forest-3"),
  		new FogDefinitionMapper("biomesoplenty:glacier", 						0F, "cold-hills-3"),
  		new FogDefinitionMapper("biomesoplenty:prairie", 						0F, "temperate-hills-1"),
  		new FogDefinitionMapper("biomesoplenty:snowy_coniferous_forest", 		0F, "cold-fields-1"),
  		new FogDefinitionMapper("biomesoplenty:tundra", 						0F, "cold-forest-2"),
  		new FogDefinitionMapper("biomesoplenty:origin_island", 					0F, "temperate-hills-1"),
  		new FogDefinitionMapper("biomesoplenty:gravel_beach", 					0F, "cold-beach-1"),
  		new FogDefinitionMapper("biomesoplenty:origin_beach",	 				0F, "temperate-hills-1"),
  		new FogDefinitionMapper("biomesoplenty:rainforest", 					0F, "warm-forest-1")
	};

	// Here the config file gets created/loaded.
	public static void loadConfigurationFile(File file) {

        final String
            commentMappedFogDefinitions = "Assign created Fog Definitions to specific minecraft biome and world's y-axis. Note that defining over 1000 biomes will become a bottleneck. To make the y-axis work define them by y-axis in descending order. Soon it will be fixed.",
			commentDefaultFogMinIntensity = "Procentage value of where the fog starts relative to the full visible distance",
			commentDefaultFogMaxIntensity = "Procentage value of where the fog ends relative to the full visible distance",
			commentFogDefinitions = "Define or use exsisting fog references to use them in MappedFogDefinitions List.",
			commentGlobalFog = "Set it to true to simplify Fog rendering and apply single rule to all the biomes.";
        
        config = new Configuration(file);
        config.load();

		globalFog = loadBool (
			"GlobalFog",
			"GlobalFog",
			commentGlobalFog,
			globalFog
		);

		defaultFogMinIntensity = loadFloat (
			"GlobalFog",
			"DefaultWhereTheFogStarts",
			commentDefaultFogMinIntensity,
			defaultFogMinIntensity
		);

		defaultFogMaxIntensity = loadFloat (
			"GlobalFog",
			"DefaultWhereTheFogEnds",
			commentDefaultFogMaxIntensity,
			defaultFogMaxIntensity
		);

		fogDefinitions = loadFogDefinitions (
			Configuration.CATEGORY_GENERAL,
		 	"FogDefinitions",
		 	commentFogDefinitions,
		 	fogDefinitions
		);

		mappedFogDefinitions = loadFogDefinitionMapper (
			Configuration.CATEGORY_GENERAL,
		 	"MappedFogDefinitions",
		 	commentMappedFogDefinitions,
		 	mappedFogDefinitions
		);

        if (config.hasChanged()) config.save();
        	MinecraftForge.EVENT_BUS.register(new ChangeListener());
    }


	private static FogDefinitionMapper[] loadFogDefinitionMapper(String category, String name, String comment, FogDefinitionMapper[] data) {
		String[] lines = new String[data.length];
		FogDefinitionMapper[] output;
		Property prop;

		// Fill the String array.
		for (int i = 0; i < data.length; i++)
			lines[i] = data[i].toString();
		
		// Get/Create the property.
		prop = config.get(category, name, lines);
		prop.setComment(comment);

		// Get the values/array-length from config.
		lines = prop.getStringList();
		output = new FogDefinitionMapper[lines.length];

		// Fill the output array.
		for (int i = 0; i < output.length; i++)
			output[i] = FogDefinitionMapper.parseString(lines[i]);

		return output;
	}

	private static FogDefinition[] loadFogDefinitions(String category, String name, String comment, FogDefinition[] data) {
		String[] lines = new String[data.length];
		FogDefinition[] output;
		Property prop;

		// Fill the String array.
		for (int i = 0; i < data.length; i++)
			lines[i] = data[i].toString();
		
		// Get/Create the property.
		prop = config.get(category, name, lines);
		prop.setComment(comment);

		// Get the values/array-length from config.
		lines = prop.getStringList();
		output = new FogDefinition[lines.length];

		// Fill the output array.
		for (int i = 0; i < output.length; i++)
			output[i] = FogDefinition.parseString(lines[i]);

		return output;
	}

	// private static FogDefinition loadFogDefinition(String category, String name, String comment, FogDefinition data) {
	// 	final String line = data.toString();
	// 	final Property prop = config.get(category, name, line);
	// 	prop.setComment(comment);
	// 	return FogDefinition.parseString(prop.getString());
	// }

    private static boolean loadBool(String category, String name, String comment, boolean data) {
        final Property prop = config.get(category, name, data);
        prop.setComment(comment);
        return prop.getBoolean(data);
    }

	private static float loadFloat(String category, String name, String comment, float data) {
        final Property prop = config.get(category, name, (double)data);
        prop.setComment(comment);
        return (float)prop.getDouble();
    }

}