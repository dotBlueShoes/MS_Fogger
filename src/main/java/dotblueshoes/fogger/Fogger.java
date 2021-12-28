package dotblueshoes.fogger;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
//import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.Logger;

import dotblueshoes.fogger.dependency.*;
import dotblueshoes.fogger.config.*;
import dotblueshoes.fogger.event.*;

@Mod( modid = Fogger.MODID, version = Fogger.VERSION, useMetadata = true)
public class Fogger {
    public static final String MODID = "fogger";
    public static final String VERSION = "3";

    //@Instance(MODID)
    //public static Fogger instance;
    private static Logger logger;

    @EventHandler
    public void preInitialize(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        ConfigHandler.initConfig(event.getSuggestedConfigurationFile());
    }

    @EventHandler
    public void initialize(FMLInitializationEvent event) {
        // MinecraftForge.EVENT_BUS.register(new ConfigHandler()); - GUI thingy

        // Registering the presence of DynamicSurroundings Mod.
        DynamicSurroundingsDependency.checkPresence();

        // Registering Fog Event.
        MinecraftForge.EVENT_BUS.register(new FogHelper());
        if (!ConfigHandler.isFogGlobal) MinecraftForge.EVENT_BUS.register(new FogEvent(ConfigHandler.getFogDefinitions(), ConfigHandler.getFogMapDefinitions()));
        else MinecraftForge.EVENT_BUS.register(new GlobalFogEvent());
    }

    // this.class.getName();

    public static void logInfo(String msg) {
        final String PREFIX = "INFO: ";
        logger.info(PREFIX + msg);
    }

}