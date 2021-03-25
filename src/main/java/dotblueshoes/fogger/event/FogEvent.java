package dotblueshoes.fogger.event;

import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

import dotblueshoes.fogger.config.util.FogMapDefinition;
import dotblueshoes.fogger.config.util.FogDefinition;
import dotblueshoes.fogger.config.ConfigHandler;
import dotblueshoes.fogger.util.FogSetting;
import dotblueshoes.fogger.event.FogHelper;
import dotblueshoes.fogger.Fogger;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public class FogEvent {

    private String biomeName;
    private Entity entity;

    private static final float INCREASE_MULTIPLIER = 0.001F;

    private static FogSetting[] fogSettings;
    private static float 
        currentFogStartPoint = 0F,
        currentFogEndPoint = 0F;

    public FogEvent(FogDefinition[] fogDefinitions, FogMapDefinition[] fogMapDefinitions) {
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
        entity = event.getEntity();
        biomeName = Minecraft.getMinecraft().world.getBiome(new BlockPos(entity.posX, entity.posY, entity.posZ)).getRegistryName().toString();
        
        if (event.getFogMode() == 0) { /* Is the horizontal vertical. -1 stands for ceiling Fog. */
            for (int i = 0; i < fogSettings.length; i++) /* Looping throught all the defined biomes. */
                if (biomeName.equals(fogSettings[i].biomeName) /* Checking the biome. */
                    && entity.posY >= fogSettings[i].yLevel) { /* checking the y-level. */
                        renderFog(fogSettings[i].fogStartPoint, fogSettings[i].fogEndPoint);
                        return;
                    }
            renderFog ( /* If not matched with the list use Default Fog setting. */ 
                ConfigHandler.defaultFogDefinition.fogStartPoint,
                ConfigHandler.defaultFogDefinition.fogEndPoint
            );
        }
    }

    // The main thing where the fog is being set. // i do hope jvm inlines this function.
    private static void renderFog(float fogStartPoint, float fogEndPoint) {
        
        if (currentFogStartPoint < fogStartPoint) { /* fogStartPoint */
            currentFogStartPoint += fogStartPoint * INCREASE_MULTIPLIER;
            /* This is a correction that assures us that we're gonna hit the defined Max 
            and that we're not gonna enter lower if statement every odd call */
            if (currentFogStartPoint > fogStartPoint)
                currentFogStartPoint = fogStartPoint;
        } else if (currentFogStartPoint > fogStartPoint) {
            currentFogStartPoint -= fogStartPoint * INCREASE_MULTIPLIER;
            /* This is a correction that assures us that we're gonna hit the defined Max 
            and that we're not gonna enter upper if statement every odd call */
            if (currentFogStartPoint < fogStartPoint)
                currentFogStartPoint = fogStartPoint;
        }

        if (currentFogEndPoint < fogEndPoint) { /* fogEndPoint */
            currentFogEndPoint += fogEndPoint * INCREASE_MULTIPLIER;
            /* This is a correction that assures us that we're gonna hit the defined Max 
            and that we're not gonna enter lower if statement every odd call */
            if (currentFogEndPoint > fogEndPoint)
                currentFogEndPoint = fogEndPoint;
        } else if (currentFogEndPoint > fogEndPoint) {
            currentFogEndPoint -= fogEndPoint * INCREASE_MULTIPLIER;
            /* This is a correction that assures us that we're gonna hit the defined Max 
            and that we're not gonna enter upper if statement every odd call */
            if (currentFogEndPoint < fogEndPoint)
                 currentFogEndPoint = fogEndPoint;
        }

        GlStateManager.setFog(GlStateManager.FogMode.LINEAR);
        GlStateManager.setFogStart(FogHelper.visibleDistance * currentFogStartPoint);
        GlStateManager.setFogEnd(FogHelper.visibleDistance * currentFogEndPoint);
    }

}

//visibleDistance = event.getFarPlaneDistance();