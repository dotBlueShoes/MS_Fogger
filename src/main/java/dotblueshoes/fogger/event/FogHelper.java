package dotblueshoes.fogger.event;

import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardInputEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.event.world.WorldEvent.Load;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import dotblueshoes.fogger.config.ConfigHandler;

// I belive this would make it sync with server viewDistance instead of client viewDiestance.
//  but i would need a better event and anything to check whether the player is in a world(server/integrated-server) or in screenmenu.
// visibleDistance = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getViewDistance() * chunkLength;

public class FogHelper {

    public static float visibleDistance = 0F;

    private static final int GUI_BTN_BACKTOGAME = 4, CHUNK_LENGTH = 16;

    // About...
    //  The gui_btn_backToGame Button is only available to be pressed
    // when we're ingame and in settings menu. So it's save to use it
    // for any action that requires world instance and any action that 
    // needs to take place right after any of the game settings could 
    // have changed.
    //  Sadly this line gets changed just after this event. So it reads the previous set distance.
    // visibleDistance = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getViewDistance() * chunkLength;
    @SubscribeEvent
    public void globalGuiEvent(ActionPerformedEvent event) {
        if (event.getButton().id == GUI_BTN_BACKTOGAME) {
            setVisibleDistance();
        }
    }

    // About...
    //  Well due to the fact that player can exit options by pressing 'escape'
    // now i have to Subscribe to that event to first check the escape key 
    // and second check if the player loaded a world. So that i can work it out.
    // whats funny is that the ESC key "release" action is being processed by something else
    //  while ingame when opening options but when we exit the options it gets it.
    @SubscribeEvent
    public void keyboardInputEvent(KeyboardInputEvent event) {
        // GameSettings gameSettings = new GameSettings();
        //KeyBinding keyESC = new KeyBinding("key.structure.desc", Keyboard.KEY_ESCAPE, "key.magicbeans.category");
        if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState())
            setVisibleDistance();
    }

    // About...
    //  This Event is being called when
    // 1. We're loading the server both multiplier and integrated one.
    //  it gets called for each world there is to load so typiclly 3 times (overworld, nether, end).
    // 2. A little later when we load the selective world.
    @SubscribeEvent
    public void worldEventLoad(WorldEvent.Load event) {
        setVisibleDistance();
    }

    public void setVisibleDistance() {
        if (ConfigHandler.isFogConstant == false)
            visibleDistance = Minecraft.getMinecraft().gameSettings.renderDistanceChunks * CHUNK_LENGTH;
        else visibleDistance = 1F;
    }
}

// Options distanceView Event Search
// https://forums.minecraftforge.net/topic/42952-list-of-all-events-available/
// https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.12.2/
// https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.12.2/net/minecraftforge/event/world/package-frame.html
// https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.12.2/net/minecraftforge/event/package-frame.html

// Other
// https://www.curseforge.com/minecraft/mc-mods/improved-mobs
// https://www.programcreek.com/java-api-examples/?api=net.minecraftforge.client.event.RenderGameOverlayEvent
// https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.12.2/net/minecraftforge/client/event/EntityViewRenderEvent.CameraSetup.html
// https://github.com/Gjum/morechunks-forge
// https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.12.2/ f(event)
// https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.12.2/net/minecraftforge/client/event/EntityViewRenderEvent.RenderFogEvent.html
// https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.12.2/net/minecraftforge/event/package-frame.html

// https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.12.2/net/minecraftforge/event/terraingen/package-frame.html

// https://forums.minecraftforge.net/topic/31795-18-events-dont-fire-on-server/
    
// https://jabelarminecraft.blogspot.com/p/minecraft-forge-1721710-keybinding.html
// https://github.com/jaideepheer/MinecraftForge-Mods-ServerPropertiesLAN/blob/master/common/ServerPropertiesLAN.java
// https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.12.2/net/minecraft/world/ServerWorldEventHandler.html
// https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.12.2/net/minecraftforge/common/ForgeChunkManager.ForceChunkEvent.html
// https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.12.2/net/minecraftforge/event/world/ChunkDataEvent.html
// https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.12.2/net/minecraftforge/event/terraingen/ChunkGeneratorEvent.html

// https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.12.2/net/minecraft/client/gui/GuiScreen.html
// https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.12.2/
// https://jabelarminecraft.blogspot.com/p/minecraft-forge-1721710-keybinding.html
// https://leo3418.github.io/2020/09/09/forge-mod-config-screen.html
// file:///C:/Users/Admin/Downloads/forgeevents.html
// https://www.programcreek.com/java-api-examples/?class=net.minecraftforge.fml.common.gameevent.InputEvent&method=KeyInputEvent
// https://jabelarminecraft.blogspot.com/p/minecraft-forge-172-event-handling.html