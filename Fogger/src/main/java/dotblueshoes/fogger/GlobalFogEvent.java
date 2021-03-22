package dotblueshoes.fogger;

import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

import dotblueshoes.fogger.VisibleDistanceListener;
import dotblueshoes.fogger.config.ConfigHandler;
import dotblueshoes.fogger.Fogger;

public class GlobalFogEvent {
    @SubscribeEvent
    public void renderFog(RenderFogEvent event) {
		if (event.getFogMode() == 0) {  /* Is the horizontal vertical. -1 stands for ceiling Fog. */
            GlStateManager.setFog(GlStateManager.FogMode.LINEAR);
			GlStateManager.setFogStart(VisibleDistanceListener.visibleDistance * ConfigHandler.defaultDefinition.fogStartPoint);
			GlStateManager.setFogEnd(VisibleDistanceListener.visibleDistance * ConfigHandler.defaultDefinition.fogEndPoint);
		}
    }
    
}