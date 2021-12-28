package dotblueshoes.fogger.event;

import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.SeasonHelper.ISeasonDataProvider;
import dotblueshoes.fogger.dependency.SereneSeasonsDependency;
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
import sereneseasons.handler.season.SeasonHandler;

//import net.minecraft.world.World;

public class FogEvent {

    private static final float INCREASE_MULTIPLIER = 0.002F;

    private static FogSetting[] fogSettings;
    private static float 
        currentFogStartPoint = 0F,
        currentFogEndPoint = 0F;

    public FogEvent(FogDefinition[] fogDefinitions, FogMapDefinition[] fogMapDefinitions) {
        FogMapDefinition.sort(fogMapDefinitions);

        fogSettings = new FogSetting[fogMapDefinitions.length];
        for (int i = 0; i < fogSettings.length; i++)
            for (FogDefinition fogDefinition : fogDefinitions)
                if (fogMapDefinitions[i].fogDefinition.equals(fogDefinition.name)) {
                    fogSettings[i] = new FogSetting(
                            fogMapDefinitions[i].biomeName,
                            fogMapDefinitions[i].yLevel,
                            fogDefinition.fogStartPoint,
                            fogDefinition.fogEndPoint
                    );
                    break;
                }
    }

    private void moonTimer() {

        // What I could do..
        // So we have a config file in which we want those timestamps to be.

        // Soo this is how minecraft represents time...
        // the long is quite "long" and there's no cycle system it simply
        // overflows the hard way after about 3 years of playing...

        final long moonCycle = 192000, dayCycle = 24000;
        final long dawn = 6000, noon = 12000, sunset = 18000, night = 23000; /*sunrise = 24000;*/
        final long fullMoon = 24000, waningGibbous = 48000, thirdQuarter = 72000,
                waningCrescent = 96000, newMoon = 120000, waxingCrescent = 142000,
                firstQuarter = 164000; /*waxingGibbous = 196000;*/

        int moonTime = ((int) (Minecraft.getMinecraft().world.getWorldTime() % moonCycle));
        int dayTime = ((int) (Minecraft.getMinecraft().world.getWorldTime() % dayCycle));
        //Fogger.logInfo(Long.toString(moonTime));

        // Adding row ranges to each other to determinate the timestamp in ticks.
        //  dawn actually occurs in the range of (0 - 5999), so we're adding 0 here
        //  and same goes for other timestamps:
        //  0 - dawn : fullMoon : EARLY_SPRING                  x 5
        //  24000 - dawn : waningGibbous : EARLY_SPRING         x 8
        //  168000 - dawn : fullMoon : MID_SPRING               x 12
        // This however would mean if the user wanted to commit a change in day cycle only
        //  he wouldn't be able to. He would have to create 8 x 12 (96) those commits to
        //  fulfill his needs. ( for a specific fog, at a specific y-level, in a specific world...)

        // after the setting I simply compare the obtained array to the
        //  vanilla:    max moon phase
        //  ss:         year cycle time
        // ... the array needs optimizations ... why?
        // if I will be comparing each value in the array until I get the if
        // that says yeah that's the range we're in right now.
        // It might be better for me to either shuffle the array:
        //  because we're always moving forward in that array we might simply
        //  pop the first key and place it at the end. I feel like there's something easier.
        //  also, the state of the array would (doesn't have to actually) have to be stored for each world.
        // or come with a better solution.

        //// This is not how it works these timestamps are specific to the point they're not a range.
        ////  and I think I will simply use them on wiki page as for information about setting fog on a specific time.
        ////  here it's going to work just like y-level. So we take a value from 0 to 195999 to treat time stems for vanilla.
        ////  and we simply do "Minecraft.getMinecraft().world.getWorldTime() % 196000" to get time as we treat it.

//        if (moonTime < fullMoon) {
//            Fogger.logInfo("fullMoon");
//        } else if (moonTime < waningGibbous) {
//            Fogger.logInfo("waningGibbous");
//        } else if (moonTime < thirdQuarter) {
//            Fogger.logInfo("thirdQuarter");
//        } else if (moonTime < waningCrescent) {
//            Fogger.logInfo("waningCrescent");
//        } else if (moonTime < newMoon) {
//            Fogger.logInfo("newMoon");
//        } else if (moonTime < waxingCrescent) {
//            Fogger.logInfo("waxingCrescent");
//        } else if (moonTime < firstQuarter) {
//            Fogger.logInfo("firstQuarter");
//        } else /*if (moonTime < waxingGibbous)*/ {
//            Fogger.logInfo("waxingGibbous");
//        }
//
//        if (dayTime < dawn) {
//            Fogger.logInfo("dawn");
//        } else if (dayTime < noon) {
//            Fogger.logInfo("noon");
//        } else if (dayTime < sunset) {
//            Fogger.logInfo("sunset");
//        } else if (dayTime < night) {
//            Fogger.logInfo("night");
//        } else /*if (dayTime < sunrise)*/ {
//            Fogger.logInfo("sunrise");
//        }

        // About SereneSeasons.
        // Yes. It uses it's own clock which is "getSeasonCycleTicks()" however theres a different one
        //  for client and server. if we want to use the server one which we obviously want we need to
        //  replace "getClientSeasonState()" with "getServerSeasonState()" and do that at world load event
        //  then refer to the "getServerSeasonState()" world previously associated value.
        //  and here or as I am thinking in WorldFogEvent variation of this class do literary the same as
        //  I was doing with vanilla.
        //  Because we know the day cycle we can easily create corresponding to this day phases and
        //  moon phases. However, I don't need to? I don't want to correspond to already defined timestamps
        //  I can easly allow creating them to users.

        // So in config I am stating at what time what event occurs in vanilla and what's default for
        // serene seasons or where to find that information.
        // And the information that maximal value for
        // vanilla is : 196000 - last moon cycle
        // serene default is : 2016000 - year cycle
        // But user adds timestamps
        // season -> sub season -> moon phase -> day phase
        // meaning that

        if (SereneSeasonsDependency.seasonHandler != null) {
            ISeasonState seasonState = SereneSeasonsDependency.seasonHandler.getClientSeasonState();
            Fogger.logInfo("SereneSeasons day duration: " + seasonState.getDayDuration());
            Fogger.logInfo("SereneSeasons subseason duration: " + seasonState.getSubSeasonDuration());
            Fogger.logInfo("SereneSeasons season duration: " + seasonState.getSeasonDuration());
            Fogger.logInfo("SereneSeasons cycle duration: " + seasonState.getCycleDuration());
            Fogger.logInfo("SereneSeasons season cycle ticks: " + seasonState.getSeasonCycleTicks()); // THIS GUY!
            Fogger.logInfo("SereneSeasons day: " + seasonState.getDay()); // does not work or gives a broken value at least for client.
        } else {
            Fogger.logInfo("seasonHandler is null!");
        }

//        registerTag("sereneseasonsdayduration", ISeasonState::getDayDuration);
//        registerTag("sereneseasonssubseasonduration", ISeasonState::getSubSeasonDuration);
//        registerTag("sereneseasonsseasonduration", ISeasonState::getSeasonDuration);
//        registerTag("sereneseasonscycleduration", ISeasonState::getCycleDuration);
//        registerTag("sereneseasonsseasoncycleticks", ISeasonState::getSeasonCycleTicks);
//        registerTag("sereneseasonsday", ISeasonState::getDay);
//        registerTag("sereneseasonscurrentseason", s -> s.getSeason().name());
//        registerTag("sereneseasonscurrentsubseason", s -> s.getSubSeason().name());
//        registerTag("sereneseasonscurrenttropicalseason", ISeasonState::getDay);
//        registerTag("sereneseasonscurrentseasonord", s -> s.getSeason().ordinal());
//        registerTag("sereneseasonscurrentsubseasonord", s -> s.getSubSeason().ordinal());
//        registerTag("sereneseasonsdayofseason", s -> s.getDay() % (s.getSeasonDuration() / s.getDayDuration()));

    }

    @SubscribeEvent
    public void renderFogEvent(RenderFogEvent event) {
        Entity entity = event.getEntity();

        @SuppressWarnings("ConstantConditions")
        String biomeName = Minecraft.getMinecraft().world.getBiome(new BlockPos(entity.posX, entity.posY, entity.posZ)).getRegistryName().toString();
        
        // !!!!!!!!!!! HERE
        moonTimer();

        if (event.getFogMode() == 0) { /* Is the horizontal vertical. -1 stands for ceiling Fog. */
            for (FogSetting fogSetting : fogSettings) /* Looping through all the defined biomes. */
                if (biomeName.equals(fogSetting.biomeName) /* Checking the biome. */
                        && entity.posY >= fogSetting.yLevel) { /* checking the y-level. */
                    renderFog(fogSetting.fogStartPoint, fogSetting.fogEndPoint);
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
            /* This is a correction that assures us that we're going to hit the defined Max
            and that we're not going to enter lower if statement every odd call */
            if (currentFogStartPoint > fogStartPoint)
                currentFogStartPoint = fogStartPoint;
        } else if (currentFogStartPoint > fogStartPoint) {
            currentFogStartPoint -= fogStartPoint * INCREASE_MULTIPLIER;
            /* This is a correction that assures us that we're going to hit the defined Max
            and that we're not going to enter upper if statement every odd call */
            if (currentFogStartPoint < fogStartPoint)
                currentFogStartPoint = fogStartPoint;
        }

        if (currentFogEndPoint < fogEndPoint) { /* fogEndPoint */
            currentFogEndPoint += fogEndPoint * INCREASE_MULTIPLIER;
            /* This is a correction that assures us that we're going to hit the defined Max
            and that we're not going to enter lower if statement every odd call */
            if (currentFogEndPoint > fogEndPoint)
                currentFogEndPoint = fogEndPoint;
        } else if (currentFogEndPoint > fogEndPoint) {
            currentFogEndPoint -= fogEndPoint * INCREASE_MULTIPLIER;
            /* This is a correction that assures us that we're going to hit the defined Max
            and that we're not going to enter upper if statement every odd call */
            if (currentFogEndPoint < fogEndPoint)
                 currentFogEndPoint = fogEndPoint;
        }

        GlStateManager.setFog(GlStateManager.FogMode.LINEAR);
        GlStateManager.setFogStart(FogHelper.visibleDistance * currentFogStartPoint);
        GlStateManager.setFogEnd(FogHelper.visibleDistance * currentFogEndPoint);
    }

}

//visibleDistance = event.getFarPlaneDistance();