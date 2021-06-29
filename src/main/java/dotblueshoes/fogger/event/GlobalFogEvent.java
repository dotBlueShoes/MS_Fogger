package dotblueshoes.fogger.event;

import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.client.renderer.GlStateManager;


import dotblueshoes.fogger.config.*;

public class GlobalFogEvent {
    @SubscribeEvent
    public void renderFog(RenderFogEvent event) {
		if (event.getFogMode() == 0) {  /* Is the horizontal vertical. -1 stands for ceiling Fog. */
      		GlStateManager.setFog(GlStateManager.FogMode.LINEAR);
			GlStateManager.setFogStart(FogHelper.visibleDistance * ConfigHandler.defaultFogDefinition.fogStartPoint);
			GlStateManager.setFogEnd(FogHelper.visibleDistance * ConfigHandler.defaultFogDefinition.fogEndPoint);
		}
    }
    
}