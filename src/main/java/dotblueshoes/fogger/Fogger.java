package dotBlueShoes.fogger;

import dotBlueShoes.fogger.config.ConfigHandler;
import dotBlueShoes.fogger.utility.FogColor;
import dotBlueShoes.fogger.utility.FogDefinition;
import dotBlueShoes.fogger.utility.FogSetting;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class Fogger implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {

    public static final String MOD_ID = "fogger";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static boolean isFogCustomColorAppliedToBackground = true;
	public static boolean isFogAutoDarkenByNightSky = true;
	public static boolean isFogColorsOverridden = true;

	public static FogDefinition[] fogDefinitions = {};
	public static FogSetting[] fogSettings = {};
	public static FogColor[] fogColors = {};

    @Override
    public void onInitialize() {
        LOGGER.info("Fogger initialized.");
    }

	@Override
	public void beforeGameStart() {
		ConfigHandler config = new ConfigHandler(MOD_ID);
		config.create();

		// TODO:
		//  Create a Look-Up-Table for Biomes to store byte instead of int.
		//  biomeLUT = new int[Registries.BIOMES.size()];
		//  for (byte iBiome = 0; iBiome < Registries.BIOMES.size(); ++iBiome) {
		//      biomeLUT[iBiome] = Registries.BIOMES.getItemByNumericId(iBiome).hashCode();
		//  }
	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {

	}

	@Override
	public void initNamespaces() {

	}
}
