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

public class FogEvent {

    private WorldClient worldClient;
    private String biomeName;
    private Entity entity;

    private final float increaseValue = 0.001F;
    private float 
        cFogMinIntensity = 0F, // c - current
        cFogMaxIntensity = 0F,
        visibleDistance;

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

        GlStateManager.setFog(GlStateManager.FogMode.LINEAR); // ? does it rly have to be here
        
        if (event.getFogMode() == 0) { /* Is the horizontal vertical. -1 stands for ceiling Fog. */
            for (int i = 0; i < ConfigHandler.biomeFogs.length; i++) /* Looping throught all the defined biomes. */
                if (biomeName.equals(ConfigHandler.biomeFogs[i].biomeName))
                    if (entity.posY >= ConfigHandler.biomeFogs[i].yLevel) {
                        Fogger.LogInfo(Float.toString(ConfigHandler.biomeFogs[i].yLevel));
                    

                    // Co jeśli bym definiował przy patrzeniu czy biom się zmienił 0% - jako previus i 100% jako current.
                    // i procentowo zmieniał jedną wartośc w drugą.

                    /* fogMaxIntensity */
                    if (cFogMaxIntensity < ConfigHandler.biomeFogs[i].fogSetting.fogMaxIntensity) {
                        cFogMaxIntensity += increaseValue;
                        /* This is a correction that assures as that we're gonna hit the defined Max 
                        and that we're not gonna enter upper if statement every odd call */
                        if (cFogMaxIntensity > ConfigHandler.biomeFogs[i].fogSetting.fogMaxIntensity)
                            cFogMaxIntensity = ConfigHandler.biomeFogs[i].fogSetting.fogMaxIntensity;
                    } else if (cFogMaxIntensity > ConfigHandler.biomeFogs[i].fogSetting.fogMaxIntensity) {
                        cFogMaxIntensity -= increaseValue;
                        /* This is a correction that assures as that we're gonna hit the defined Max 
                        and that we're not gonna enter upper if statement every odd call */
                        if (cFogMaxIntensity < ConfigHandler.biomeFogs[i].fogSetting.fogMaxIntensity)
                            cFogMaxIntensity = ConfigHandler.biomeFogs[i].fogSetting.fogMaxIntensity;
                    }

                    /* fogMinIntensity */
                    if (cFogMinIntensity < ConfigHandler.biomeFogs[i].fogSetting.fogMinIntensity) {
                        cFogMinIntensity += increaseValue;
                        /* This is a correction that assures as that we're gonna hit the defined Max 
                        and that we're not gonna enter upper if statement every odd call */
                        if (cFogMinIntensity > ConfigHandler.biomeFogs[i].fogSetting.fogMinIntensity)
                            cFogMinIntensity = ConfigHandler.biomeFogs[i].fogSetting.fogMinIntensity;
                    } else if (cFogMinIntensity > ConfigHandler.biomeFogs[i].fogSetting.fogMinIntensity) {
                        cFogMinIntensity -= increaseValue;
                        /* This is a correction that assures as that we're gonna hit the defined Max 
                        and that we're not gonna enter upper if statement every odd call */
                        if (cFogMinIntensity < ConfigHandler.biomeFogs[i].fogSetting.fogMinIntensity)
                            cFogMinIntensity = ConfigHandler.biomeFogs[i].fogSetting.fogMinIntensity;
                    }

                    //Fogger.LogInfo(Float.toString(cFogMaxIntensity));
                    //Fogger.LogInfo(Double.toString(entity.posY));

		    	    GlStateManager.setFogStart(visibleDistance * cFogMinIntensity);
                    GlStateManager.setFogEnd(visibleDistance * cFogMaxIntensity);
                    return;
                    }
            Fogger.LogInfo("LOOOOOOOOOOOOOOOOOOL");
        }
    }

}