package dotblueshoes.fogger;

import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

import dotblueshoes.fogger.FogConfiguration;
import dotblueshoes.fogger.ConfigHandler;
import dotblueshoes.fogger.Fogger;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

//import java.awt.Color;

public class FogEvent {

    private final float increaseValue = 0.001F;

    private float 
        fogMinIntensity = 0F, 
        fogMaxIntensity = 0F;
        //redIntensity = 0F,
        //greenIntensity = 0F,
        //blueIntensity = 0F;

    private WorldClient worldClient;
    private String biomeName;
    private Entity entity;
    private float visibleDistance;

    // Functional Mod
    // - different dimensions
    //  -> different biomes // done
    // - different y-levels // untoched
    // - different time // color - optifine
    // - different weather // color - optifine
    // - different events(user defined? - no idea how would that look i guess potion effect) // untoched
    // water breathing, night vision, blindness does not get affected by this script!

    /* Different Y levels */
    // I think that i will support up to 2 different y level settings
    // whereas
    // 1 - is the global working wherever the 1st one isin't
    // 2 - is the specified from - to.

    /* Different Weather */
    // This seems a littile bit more compicated.
    // i guess i can support rain and thunder. 
    // but the more i think about it the more i think about using json.

    @SubscribeEvent
    public void renderFog(RenderFogEvent event) {

        visibleDistance = event.getFarPlaneDistance();
        worldClient = Minecraft.getMinecraft().world;
        entity = event.getEntity();

        biomeName = worldClient.getBiome(new BlockPos(entity.posX, entity.posY, entity.posZ)).getRegistryName().toString();

        GlStateManager.setFog(GlStateManager.FogMode.LINEAR);
        
        if (event.getFogMode() == 0) /* Is the horizontal vertical. -1 stands for ceiling Fog. */
            for (int i = 0; i < ConfigHandler.biomeFogs.length; i++) /* Looping throught all the defined biomes. */
                if(biomeName.equals(ConfigHandler.biomeFogs[i].biomeName)) {

                    // Co jeśli bym definiował przy patrzeniu czy biom się zmienił 0% - jako previus i 100% jako current.
                    // i procentowo zmieniał jedną wartośc w drugą.

                    /* fogMaxIntensity */
                    if (fogMaxIntensity < ConfigHandler.biomeFogs[i].fogMaxClamp) {
                        fogMaxIntensity += increaseValue;
                        /* This is a correction that assures as that we're gonna hit the defined Max 
                        and that we're not gonna calculate fogMaxIntensity every call. */
                        if (fogMaxIntensity > ConfigHandler.biomeFogs[i].fogMaxClamp)
                            fogMaxIntensity = ConfigHandler.biomeFogs[i].fogMaxClamp;
                    } else if (fogMaxIntensity > ConfigHandler.biomeFogs[i].fogMaxClamp) {
                        fogMaxIntensity -= increaseValue;
                        /* This is a correction that assures as that we're gonna hit the defined Max 
                        and that we're not gonna calculate fogMaxIntensity every call. */
                        if (fogMaxIntensity < ConfigHandler.biomeFogs[i].fogMaxClamp)
                            fogMaxIntensity = ConfigHandler.biomeFogs[i].fogMaxClamp;
                    }

                    /* fogMinIntensity */
                    if (fogMinIntensity < ConfigHandler.biomeFogs[i].fogMinClamp) {
                        fogMinIntensity += increaseValue;
                        /* This is a correction that assures as that we're gonna hit the defined Max 
                        and that we're not gonna calculate fogMinIntensity every call. */
                        if (fogMinIntensity > ConfigHandler.biomeFogs[i].fogMinClamp)
                            fogMinIntensity = ConfigHandler.biomeFogs[i].fogMinClamp;
                    } else if (fogMinIntensity > ConfigHandler.biomeFogs[i].fogMinClamp) {
                        fogMinIntensity -= increaseValue;
                        /* This is a correction that assures as that we're gonna hit the defined Max 
                        and that we're not gonna calculate fogMinIntensity every call. */
                        if (fogMinIntensity < ConfigHandler.biomeFogs[i].fogMinClamp)
                            fogMinIntensity = ConfigHandler.biomeFogs[i].fogMinClamp;
                    }

                    //Fogger.LogInfo(Double.toString(entity.posY));

		    	    GlStateManager.setFogStart(visibleDistance * fogMinIntensity);
                    GlStateManager.setFogEnd(visibleDistance * fogMaxIntensity);
                    break;
                }
    }
}