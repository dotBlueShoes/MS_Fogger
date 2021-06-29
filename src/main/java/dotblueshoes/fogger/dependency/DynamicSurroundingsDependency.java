package dotblueshoes.fogger.dependency;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;

import dotblueshoes.fogger.*;

import org.orecruncher.dsurround.client.handlers.EffectManager;
import org.orecruncher.dsurround.client.handlers.EffectHandlerBase;
import org.orecruncher.lib.collections.ObjectArray;

public class DynamicSurroundingsDependency {

    // Global variable to check wheater mod is available or not.
    public static boolean isPresent = false;

    // Requires an instance. Sets the isPresent var to true as if it successes finding mod classes.
    public static void checkPresence() {
        final String classPath = "org.orecruncher.dsurround.client.handlers.EffectManager";

        if (isPresent = Reflection.isClassAvailableAtRuntime(classPath)) {
            MinecraftForge.EVENT_BUS.register(new DynamicSurroundingsDependency());
            Fogger.logInfo("DynamicSurroundings Are Present!");
        }
    }

    // Unregisters FogEvent ds sets.
    public static void unregisterFogEvent() {
        final int fogHandlerIndex = 2; // Because he registers them in order we know the index.

        @SuppressWarnings("unchecked")
        ObjectArray<EffectHandlerBase> effectHandlers = 
            (ObjectArray<EffectHandlerBase>)Reflection.getObjectInstanceField(EffectManager.instance(), "effectHandlers");

        MinecraftForge.EVENT_BUS.unregister(effectHandlers.get(fogHandlerIndex));

        Fogger.logInfo("DynamicSurroundings FogEvent Have Been Unregistred!");
    }

    // This event likley happends somewhat just after the DynamicSurroundings fogHandler registration.
    //  tho i do belive that this event in some cases might run before the registration....
    //  a case where cpu is being overloaded with stuff.
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void attachCapabilitiesEvent(AttachCapabilitiesEvent<World> event) {
        unregisterFogEvent();
    }

}

//@SubscribeEvent(priority = EventPriority.LOWEST)
//public void playerJoinsServerEvent(ClientConnectedToServerEvent event) {
//    unregisterFogEvent();
//    Fogger.logInfo("Player joins server!");
//}

// try { Class.forName(classPath, false, getClass().getClassLoader());
// } catch (ClassNotFoundException exception) {
//     Fogger.logInfo("DynamicSurroundings Are Not Present!");
//     return;
// }