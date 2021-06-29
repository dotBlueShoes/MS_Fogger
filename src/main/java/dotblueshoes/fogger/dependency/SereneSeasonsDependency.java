package dotblueshoes.fogger.dependency;

//import java.lang.reflect.*;

//import dotblueshoes.fogger.dependency.*;
//import dotblueshoes.fogger.*;

public class SereneSeasonsDependency {

    // Global variable to check wheater it's available or not.
    public static boolean isPresent = false;

    // Requires an instance. Sets the isPresent var to true as if it successes finding mod classes.
    public static void checkPresence() {
        //final String classPath = "org.orecruncher.dsurround.client.handlers.EffectManager";

        //if (isPresent = Reflection.isClassAvailableAtRuntime(classPath)) {
        //    Fogger.logInfo("SereneSeasons Are Present!");
        //}
    }

    // Links
    // https://github.com/Glitchfiend/SereneSeasons/blob/20759983fe8208d07481633723b160f47bcb54dd/src/main/java/sereneseasons/init/ModConfig.java#L28
    // https://github.com/Glitchfiend/SereneSeasons/blob/20759983fe8208d07481633723b160f47bcb54dd/src/main/java/sereneseasons/config/ConfigHandler.java#L38
    // https://github.com/Glitchfiend/SereneSeasons/blob/03ad22fb91689fe08d8f1be9d1328c842e00405f/src/main/java/sereneseasons/api/season/Season.java
    // https://github.com/Glitchfiend/SereneSeasons/blob/03ad22fb91689fe08d8f1be9d1328c842e00405f/src/main/java/sereneseasons/api/season/ISeasonState.java
    // https://github.com/Glitchfiend/SereneSeasons/blob/03ad22fb91689fe08d8f1be9d1328c842e00405f/src/main/java/sereneseasons/season/SeasonTime.java
    // https://github.com/Glitchfiend/SereneSeasons/search?q=SeasonTime
    // Open notepad++ (3 last files)
    // Open FogEvent.java - notes 
    // - also it's gonna be way better if first i am gonna implement it via global fog.

    // https://minecraft.fandom.com/wiki/Daylight_cycle
    // https://github.com/OreCruncher/DynamicSurroundings/blob/01707e27dd800509409c9830e7709aaac6cdc0de/src/main/java/org/orecruncher/dsurround/client/handlers/fog/MorningFogRangeCalculator.java
    // https://github.com/OreCruncher/DynamicSurroundings/blob/01707e27dd800509409c9830e7709aaac6cdc0de/src/main/java/org/orecruncher/dsurround/client/handlers/EnvironStateHandler.java
}

// To Support SereneSeasons i have to
//  choose whether to use vanilla system of time or sereneseasons.
//  this is due to the fact that sereneseasons change daily cycle and on top of that
//  they have SEASNS both NORMAL and TROPICAL...