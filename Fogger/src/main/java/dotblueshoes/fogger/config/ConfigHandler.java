package dotblueshoes.fogger.config;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

import dotblueshoes.fogger.config.FogMapDefinition;
import dotblueshoes.fogger.config.FogDefinition;
import dotblueshoes.fogger.Fogger;

import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ConfigHandler {

	// Listener Object, registration thingy.
	public static class ChangeListener {
        @SubscribeEvent
        public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID() == Fogger.MODID);
        }
    }

	// Gets the DefaultDefinition into the definitions array.
	public static FogDefinition[] getFogDefinitions() {
        FogDefinition[] definitions = new FogDefinition[fogDefinitions.length + 1];
        definitions[0] = defaultDefinition;
        for (int i = 1; i < definitions.length; i++)
            definitions[i] = fogDefinitions[i - 1];
		return definitions;
	}

	// public static FogMapDefinition[] getFogMapDefinitions() {
	// 	// Count all the duplicates in FogMapDefinitions
    //     for (int i = 0; i < fogMapDefinitions.length; i++)
    //         for (int j = 0; j < fogMapDefinitions.length; j++)
    //             if (fogMapDefinitions[i].equals(fogMapDefinitions[j]))
    //                 Fogger.LogInfo(fogMapDefinitions[i].toString());

	// 	// Get registered biomes. - With this config file could be over-identifing biomes.
	// 	Fogger.LogInfo(ForgeRegistries.BIOMES.getValuesCollection()); // - returns a list of those biomes.
	// 	return new FogMapDefinition[3];
	// }

	public static Configuration config;
	public static boolean isFogGlobal = false, isFogConstant = false;

	public static FogDefinition defaultDefinition = 
		new FogDefinition("default", 0.10F, 0.95F);

	private static FogDefinition[] fogDefinitions = {
		new FogDefinition("crazy-1",			0.49F, 0.50F),
		new FogDefinition("warm-forest-1", 		0.10F, 0.85F),
		new FogDefinition("warm-desert-1", 		0.05F, 0.75F),
		new FogDefinition("warm-desert-2", 		0.10F, 0.80F),
		new FogDefinition("warm-water-1", 		0.15F, 0.95F),
		new FogDefinition("warm-swamp-1", 		0.10F, 0.75F),
		new FogDefinition("warm-swamp-2", 		0.10F, 0.65F),
		new FogDefinition("temperate-fields-1", 0.15F, 1.00F),
		new FogDefinition("temperate-desert-1", 0.10F, 0.85F),
		new FogDefinition("temperate-hills-1", 	0.05F, 0.95F),
		new FogDefinition("temperate-river-1", 	0.05F, 0.60F),
		new FogDefinition("cold-fields-1", 		0.05F, 0.65F),
		new FogDefinition("cold-forest-1", 		0.05F, 0.85F),
		new FogDefinition("cold-forest-2", 		0.05F, 0.75F),
		new FogDefinition("cold-forest-3", 		0.05F, 0.55F),
		new FogDefinition("cold-hills-1", 		0.05F, 0.65F),
		new FogDefinition("cold-hills-2", 		0.05F, 0.80F),
		new FogDefinition("cold-hills-3", 		0.05F, 0.75F),
		new FogDefinition("cold-hills-4", 		0.05F, 0.85F),
		new FogDefinition("cold-river-1", 		0.05F, 0.55F),
		new FogDefinition("cold-water-1", 		0.10F, 0.75F),
		new FogDefinition("cold-beach-1", 		0.10F, 0.75F),
		new FogDefinition("mushroom-island", 	0.20F, 0.95F),
		new FogDefinition("mangrove", 			0.05F, 0.75F)
	};

    public static FogMapDefinition[] fogMapDefinitions = {
		new FogMapDefinition("minecraft:desert", 							0F,		"warm-desert-1"),
		new FogMapDefinition("minecraft:extreme_hills", 					0F,		"temperate-hills-1"),
		new FogMapDefinition("minecraft:river", 							70F,	"default"),
		new FogMapDefinition("minecraft:river", 							46F, 	"temperate-river-1"),
		new FogMapDefinition("minecraft:frozen_river", 						70F, 	"default"),
		new FogMapDefinition("minecraft:frozen_river", 						46F, 	"cold-river-1"),
		new FogMapDefinition("minecraft:ice_flats", 						0F, 	"cold-fields-1"),
		new FogMapDefinition("minecraft:ice_mountains", 					0F, 	"cold-hills-1"),
		new FogMapDefinition("minecraft:mushroom_island", 					0F, 	"mushroom-island"),
		new FogMapDefinition("minecraft:mushroom_island_shore", 			0F, 	"mushroom-island"),
		new FogMapDefinition("minecraft:desert_hills", 						0F, 	"temperate-hills-1"),
		new FogMapDefinition("minecraft:forest_hills", 						0F, 	"temperate-hills-1"),
		new FogMapDefinition("minecraft:taiga_hills", 						0F, 	"temperate-hills-1"),
		new FogMapDefinition("minecraft:smaller_extreme_hills", 			0F, 	"temperate-hills-1"),
		new FogMapDefinition("minecraft:stone_beach", 						0F, 	"temperate-hills-1"),
		new FogMapDefinition("minecraft:cold_beach", 						0F, 	"cold-fields-1"),
		new FogMapDefinition("minecraft:taiga_cold", 						0F, 	"cold-fields-1"),
		new FogMapDefinition("minecraft:taiga_cold_hills", 					0F, 	"temperate-hills-1"),
		new FogMapDefinition("minecraft:redwood_taiga_hills", 				0F, 	"temperate-hills-1"),
		new FogMapDefinition("minecraft:extreme_hills_with_trees", 			0F, 	"temperate-hills-1"),
		new FogMapDefinition("minecraft:wooded_badlands_plateau", 			0F, 	"temperate-hills-1"),
		new FogMapDefinition("minecraft:cold_ocean", 						0F, 	"cold-water-1"),
		new FogMapDefinition("minecraft:deep_cold_ocean", 					0F, 	"cold-water-1"),
		new FogMapDefinition("minecraft:deep_frozen_ocean", 				0F, 	"cold-water-1"),
		new FogMapDefinition("minecraft:mutated_desert", 					0F, 	"warm-desert-2"),
		new FogMapDefinition("minecraft:mutated_extreme_hills", 			0F, 	"temperate-hills-1"),
		new FogMapDefinition("minecraft:mutated_ice_flats", 				0F, 	"cold-fields-1"),
		new FogMapDefinition("minecraft:mutated_birch_forest_hills", 		0F, 	"temperate-hills-1"),
		new FogMapDefinition("minecraft:mutated_taiga_cold", 				0F, 	"cold-fields-1"),
		new FogMapDefinition("minecraft:mutated_redwood_taiga_hills", 		0F, 	"temperate-hills-1"),
		new FogMapDefinition("minecraft:mutated_extreme_hills_with_trees", 	0F, 	"temperate-hills-1"),
		new FogMapDefinition("minecraft:mutated_mesa_rock", 				0F, 	"temperate-hills-1"),
		new FogMapDefinition("minecraft:mutated_mesa_clear_rock", 			0F, 	"temperate-hills-1"),
		new FogMapDefinition("minecraft:bamboo_jungle_hills",		 		0F, 	"temperate-hills-1"),
		new FogMapDefinition("biomesoplenty:oasis", 						0F, 	"warm-forest-1"),
  		new FogMapDefinition("biomesoplenty:steppe", 						0F, 	"temperate-hills-1"),
  		new FogMapDefinition("biomesoplenty:alps_foothills", 				0F, 	"cold-hills-1"),
  		new FogMapDefinition("biomesoplenty:bayou", 						0F, 	"warm-swamp-1"),
  		new FogMapDefinition("biomesoplenty:mountain", 						0F, 	"cold-hills-2"),
  		new FogMapDefinition("biomesoplenty:overgrown_cliffs", 				0F, 	"temperate-hills-1"),
  		new FogMapDefinition("biomesoplenty:highland", 						0F, 	"temperate-hills-1"),
  		new FogMapDefinition("biomesoplenty:marsh",							0F, 	"warm-swamp-1"),
  		new FogMapDefinition("biomesoplenty:wetland", 						0F, 	"warm-swamp-2"),
  		new FogMapDefinition("biomesoplenty:mystic_grove", 					0F, 	"temperate-hills-1"),
  		new FogMapDefinition("biomesoplenty:cold_desert", 					0F, 	"cold-fields-1"),
  		new FogMapDefinition("biomesoplenty:mountain_foothills", 			0F, 	"cold-hills-1"),
  		new FogMapDefinition("biomesoplenty:coral_reef", 					0F, 	"warm-water-1"),
  		new FogMapDefinition("biomesoplenty:orchard", 						0F, 	"temperate-fields-1"),
  		new FogMapDefinition("biomesoplenty:snowy_tundra", 					0F, 	"cold-fields-1"),
  		new FogMapDefinition("biomesoplenty:alps", 							0F, 	"cold-hills-4"),
  		new FogMapDefinition("biomesoplenty:lush_desert", 					0F, 	"warm-forest-1"),
  		new FogMapDefinition("biomesoplenty:mangrove", 						0F, 	"mangrove"),
  		new FogMapDefinition("biomesoplenty:boreal_forest", 				0F, 	"cold-forest-1"),
  		new FogMapDefinition("biomesoplenty:snowy_forest", 					0F, 	"cold-forest-3"),
  		new FogMapDefinition("biomesoplenty:glacier", 						0F, 	"cold-hills-3"),
  		new FogMapDefinition("biomesoplenty:prairie", 						0F, 	"temperate-hills-1"),
  		new FogMapDefinition("biomesoplenty:snowy_coniferous_forest", 		0F, 	"cold-fields-1"),
  		new FogMapDefinition("biomesoplenty:tundra", 						0F, 	"cold-forest-2"),
  		new FogMapDefinition("biomesoplenty:origin_island", 				0F, 	"temperate-hills-1"),
  		new FogMapDefinition("biomesoplenty:gravel_beach", 					0F, 	"cold-beach-1"),
  		new FogMapDefinition("biomesoplenty:origin_beach",	 				0F, 	"temperate-hills-1"),
  		new FogMapDefinition("biomesoplenty:rainforest", 					0F, 	"warm-forest-1")
	};

	// Here the config file gets created/loaded.
	public static void loadConfigurationFile(File file) {

        final String
            commentFogMapDefinitions = "Assign created Fog Definitions to specific minecraft biome and world's y-axis. Note that defining over 1000 biomes will become a bottleneck. To make the y-axis work define them by y-axis in descending order. Soon it will be fixed.",
			commentDefaultFogStartPoint = "Procentage value of where the fog starts relative to the full visible distance",
			commentDefaultFogEndPoint = "Procentage value of where the fog ends relative to the full visible distance",
			commentFogDefinitions = "Define or use exsisting fog references to use them in FogMapDefinitions List.",
			commentIsFogConstant = "Set it to true to make fog rendering independant from view distance. Remember to change fog start, end values as they're no longer in percents. '2F' is a distance of two blocks.",
			commentIsFogGlobal = "Set it to true to simplify Fog rendering and apply single rule to all the biomes.",
			headingDefault = "default-fog",
			headingBiome = "dependant-fog",
			headingGeneral = "general";
			
        
        config = new Configuration(file);
        config.load();

		isFogConstant = loadBool (
			headingGeneral,
			"IsFogConstant",
			commentIsFogConstant,
			isFogConstant
		);

		isFogGlobal = loadBool (
			headingGeneral,
			"IsFogGlobal",
			commentIsFogGlobal,
			isFogGlobal
		);

		defaultDefinition.fogStartPoint = loadFloat (
			headingDefault,
			"DefaultWhereTheFogStarts",
			commentDefaultFogStartPoint,
			defaultDefinition.fogStartPoint
		);

		defaultDefinition.fogEndPoint = loadFloat (
			headingDefault,
			"DefaultWhereTheFogEnds",
			commentDefaultFogEndPoint,
			defaultDefinition.fogEndPoint
		);

		fogDefinitions = loadFogDefinitions (
			headingBiome,
		 	"FogDefinitions",
		 	commentFogDefinitions,
		 	fogDefinitions
		);

		fogMapDefinitions = loadFogDefinitionsMapper (
			headingBiome,
		 	"MappedFogDefinitions",
		 	commentFogMapDefinitions,
		 	fogMapDefinitions
		);

        if (config.hasChanged()) config.save();
        	MinecraftForge.EVENT_BUS.register(new ChangeListener());
    }


	private static FogMapDefinition[] loadFogDefinitionsMapper ( String category, String name, String comment, FogMapDefinition[] data) {

		String[] lines = new String[data.length];
		FogMapDefinition[] output;
		Property prop;

		// Fill the String array.
		for (int i = 0; i < data.length; i++)
			lines[i] = data[i].toString();
		
		// Get/Create the property.
		prop = config.get(category, name, lines);
		prop.setComment(comment);

		// Get the values/array-length from config.
		lines = prop.getStringList();
		output = new FogMapDefinition[lines.length];

		// Fill the output array.
		for (int i = 0; i < output.length; i++)
			output[i] = FogMapDefinition.parseString(lines[i]);

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
	//  	final String line = data.toString();
	//  	final Property prop = config.get(category, name, line);
	//  	prop.setComment(comment);
	//  	return FogDefinition.parseString(prop.getString());
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