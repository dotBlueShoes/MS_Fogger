package dotblueshoes.fogger;

import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

import dotblueshoes.fogger.VisibleDistanceListener;
import dotblueshoes.fogger.Fogger;
import dotblueshoes.fogger.config.FogMapDefinition;
import dotblueshoes.fogger.config.FogDefinition;
import dotblueshoes.fogger.config.ConfigHandler;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

// Different Weather 
//  This seems a littile bit more compicated.
//  i guess i can support rain and thunder.

public class FogEvent {

    private String biomeName;
    private Entity entity;

    private static final float increaseValue = 0.001F;

    private static FogSetting[] fogSettings;
    private static float 
        currentFogStartPoint = 0F,
        currentFogEndPoint = 0F;

    public static void initialize(FogDefinition[] fogDefinitions, FogMapDefinition[] fogMapDefinitions) {
        FogMapDefinition.sort(fogMapDefinitions);

        fogSettings = new FogSetting[fogMapDefinitions.length];

        for (int i = 0; i < fogSettings.length; i++)
            for (int j = 0; j < fogDefinitions.length; j++)
                if(fogMapDefinitions[i].fogDefinition.equals(fogDefinitions[j].name)) {
                    fogSettings[i] = new FogSetting (
                        fogMapDefinitions[i].biomeName, 
                        fogMapDefinitions[i].yLevel, 
                        fogDefinitions[j].fogStartPoint, 
                        fogDefinitions[j].fogEndPoint
                    );
                    break;
                }
    }

    @SubscribeEvent
    public void renderFogEvent(RenderFogEvent event) {
        //visibleDistance = event.getFarPlaneDistance();
        entity = event.getEntity();
        biomeName = Minecraft.getMinecraft().world.getBiome(new BlockPos(entity.posX, entity.posY, entity.posZ)).getRegistryName().toString();

        GlStateManager.setFog(GlStateManager.FogMode.LINEAR); // ? does it rly have to be here
        
        if (event.getFogMode() == 0) { /* Is the horizontal vertical. -1 stands for ceiling Fog. */
            for (int i = 0; i < fogSettings.length; i++) /* Looping throught all the defined biomes. */
                if (biomeName.equals(fogSettings[i].biomeName)) /* Checking the biome. */
                    if (entity.posY >= fogSettings[i].yLevel) { /* checking the y-level. */
                        renderFog (fogSettings[i].fogStartPoint, fogSettings[i].fogEndPoint);
                        return;
                    }
            renderFog ( /* If not matched with the list use Default Fog setting. */ 
                ConfigHandler.defaultDefinition.fogStartPoint,
                ConfigHandler.defaultDefinition.fogEndPoint
            );
        }
    }

    // The main thing where the fog is being set. // i do hope jvm inlines this function.
    private static void renderFog(float fogStartPoint, float fogEndPoint) {

        /* fogEndPoint */
        if (currentFogEndPoint < fogEndPoint) {
            currentFogEndPoint += increaseValue;
            /* This is a correction that assures as that we're gonna hit the defined Max 
            and that we're not gonna enter upper if statement every odd call */
            if (currentFogEndPoint > fogEndPoint)
                currentFogEndPoint = fogEndPoint;
        } else if (currentFogEndPoint > fogEndPoint) {
            currentFogEndPoint -= increaseValue;
            /* This is a correction that assures as that we're gonna hit the defined Max 
            and that we're not gonna enter upper if statement every odd call */
            if (currentFogEndPoint < fogEndPoint)
                currentFogEndPoint = fogEndPoint;
        }

         /* fogStartPoint */
        if (currentFogStartPoint < fogStartPoint) {
            currentFogStartPoint += increaseValue;
            /* This is a correction that assures as that we're gonna hit the defined Max 
            and that we're not gonna enter upper if statement every odd call */
            if (currentFogStartPoint > fogStartPoint)
                currentFogStartPoint = fogStartPoint;
        } else if (currentFogStartPoint > fogStartPoint) {
            currentFogStartPoint -= increaseValue;
            /* This is a correction that assures as that we're gonna hit the defined Max 
            and that we're not gonna enter upper if statement every odd call */
            if (currentFogStartPoint < fogStartPoint)
                currentFogStartPoint = fogStartPoint;
        }

        GlStateManager.setFogStart(VisibleDistanceListener.visibleDistance * currentFogStartPoint);
        GlStateManager.setFogEnd(VisibleDistanceListener.visibleDistance * currentFogEndPoint);
    }

}