package dotblueshoes.fogger.dependency;

import java.lang.reflect.*;

import dotblueshoes.fogger.dependency.*;
import dotblueshoes.fogger.*;

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

}