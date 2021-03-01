package dotblueshoes.fogger;

import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

import dotblueshoes.fogger.ConfigHandler;
import dotblueshoes.fogger.Fogger;

public class GlobalFogEvent {

    private float visibleDistance;

    @SubscribeEvent
    public void renderFog(RenderFogEvent event) {

        GlStateManager.setFog(GlStateManager.FogMode.LINEAR);
        visibleDistance = event.getFarPlaneDistance();

        /* Is the horizontal vertical. -1 stands for ceiling Fog. */
		if (event.getFogMode() == 0) {
			GlStateManager.setFogStart(visibleDistance * ConfigHandler.defaultFogDefinition.fogMinIntensity);
			GlStateManager.setFogEnd(visibleDistance * ConfigHandler.defaultFogDefinition.fogMaxIntensity);
		}

    }

    // @SubscribeEvent
	// public void colorFog(FogColors event) {

    //     worldClient = Minecraft.getMinecraft().world;
    //     entity = event.getEntity();
    //     biomeName = worldClient.getBiome(new BlockPos(entity.posX, entity.posY, entity.posZ)).getRegistryName().toString();
        
    //     for (int i = 0; i < ConfigHandler.biomeFogs.length; i++)
    //         if(biomeName.equals(ConfigHandler.biomeFogs[i].biomeName)) {
    //             //event.setRed(ConfigHandler.biomeFogs[i].red);
    //             //event.setGreen(ConfigHandler.biomeFogs[i].green);
    //             //event.setBlue(ConfigHandler.biomeFogs[i].blue);
    //             event.setRed(0F);
    //             event.setGreen(0F);
    //             event.setBlue(0F);
    //          }

    //     event.setGreen(0.5F);
    //     event.setBlue(0.5F);
    //     event.setRed(0.5F);
	// }

}