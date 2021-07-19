package dotblueshoes.fogger.event;

import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
//import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

import dotblueshoes.fogger.config.util.FogMapDefinition;
import dotblueshoes.fogger.config.util.FogDefinition;
import dotblueshoes.fogger.config.ConfigHandler;
import dotblueshoes.fogger.util.FogSetting;

import net.minecraft.util.math.BlockPos;

import dotblueshoes.fogger.*;

//import net.minecraft.world.World;

public class FogEvent {

    private String biomeName;
    private Entity entity;

    private static final float INCREASE_MULTIPLIER = 0.002F;

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

    private void moonTimer() {

        // Soo this is how minecraft represents time...
        // the long is quite "long" and theres no cycle system it simply
        // overflows the hard way after about 3 years of playing...
        // a day takes 24000

        final long moonCycle = 192000, dayCycle = 24000;

        //final long dawn = 0, noon = 6000, sunset = 12000, night = 18000, sunrise = 23000;
        final long dawn = 6000, noon = 12000, sunset = 18000, night = 23000, sunrise = 24000;

        final long fullMoon = 24000, waningGibbous = 48000, thirdQuarter = 72000, waningCrescent = 96000,
                newMoon = 120000, waxingCrescent = 142000, firstQuarter = 164000, waxingGibbous = 196000;

        int moonTime = ((int) (Minecraft.getMinecraft().world.getWorldTime() % moonCycle));
        int dayTime = ((int) (Minecraft.getMinecraft().world.getWorldTime() % dayCycle));
        Fogger.logInfo(Long.toString(moonTime));

        // ughh this is not how it works these time stemps are specific to the the point they're not a range.
        //  and i think i will simply use them on wiki page as for information abour setting fog on a specifc time.
        //  here its gonna work just like y-level. So we take a value from 0 to 195999 to treat time stems for vanilla.
        //  and we simply do "Minecraft.getMinecraft().world.getWorldTime() % 196000" to get time as we treat it.

        // serene on the other hand .... ugh

        if (moonTime < fullMoon) {
            Fogger.logInfo("fullMoon");
        } else if (moonTime < waningGibbous) {
            Fogger.logInfo("waningGibbous");
        } else if (moonTime < thirdQuarter) {
            Fogger.logInfo("thirdQuarter");
        } else if (moonTime < waningCrescent) {
            Fogger.logInfo("waningCrescent");
        } else if (moonTime < newMoon) {
            Fogger.logInfo("newMoon");
        } else if (moonTime < waxingCrescent) {
            Fogger.logInfo("waxingCrescent");
        } else if (moonTime < firstQuarter) {
            Fogger.logInfo("firstQuarter");
        } else if (moonTime < waxingGibbous) {
            Fogger.logInfo("waxingGibbous");
        }

        if (dayTime < dawn) {
            Fogger.logInfo("dawn");
        } else if (dayTime < noon) {
            Fogger.logInfo("noon");
        } else if (dayTime < sunset) {
            Fogger.logInfo("sunset");
        } else if (dayTime < night) {
            Fogger.logInfo("night");
        } else if (dayTime < sunrise) {
            Fogger.logInfo("sunrise");
        }
    }

    @SubscribeEvent
    public void renderFogEvent(RenderFogEvent event) {
        entity = event.getEntity();
        biomeName = Minecraft.getMinecraft().world.getBiome(new BlockPos(entity.posX, entity.posY, entity.posZ)).getRegistryName().toString();
        
        // !!!!!!!!!!! HERE
        moonTimer();

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