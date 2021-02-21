package dotblueshoes.fogger;

import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

import dotblueshoes.fogger.Fogger;

public class GlobalFogEvent {

    private final float fogMaxClamp = 0.95F, fogMinClamp = 0.05F; 
    private float fogDistance;

    @SubscribeEvent
    public void renderFog(RenderFogEvent event) {

        GlStateManager.setFog(GlStateManager.FogMode.LINEAR);
        fogDistance = event.getFarPlaneDistance();

        /* Is the horizontal vertical. -1 stands for ceiling Fog. */
		if (event.getFogMode() == 0) {
			GlStateManager.setFogStart(fogDistance * fogMinClamp);
			GlStateManager.setFogEnd(fogDistance * fogMaxClamp);
		}

    }

    // @SubscribeEvent
	// public void colorFog(FogColors event) {
	// 	WorldClient worldclient = Minecraft.getMinecraft().world;
    //     Entity entity = event.getEntity();

    //     event.setGreen(0.5F);
    //     event.setBlue(0.5F);
    //     event.setRed(0.5F);
	// }

}