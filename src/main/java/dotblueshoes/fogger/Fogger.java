package dotblueshoes.fogger;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraft.init.Blocks;

import org.apache.logging.log4j.Logger;

import dotblueshoes.fogger.config.ConfigHandler;
import dotblueshoes.fogger.event.*;

@Mod( modid = Fogger.MODID,  version = Fogger.VERSION, useMetadata = true)
public class Fogger {
    public static final String MODID = "fogger";
    public static final String VERSION = "2";

    @Instance(MODID)
    public static Fogger instance;
    //private static Logger logger;

    @EventHandler
    public void preInitialize(FMLPreInitializationEvent event) {
        ConfigHandler.loadConfigurationFile(event.getSuggestedConfigurationFile());
    //    logger = event.getModLog();
    }

    @EventHandler
    public void initialize(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new FogHelper());
        if(ConfigHandler.isFogGlobal == false)
            MinecraftForge.EVENT_BUS.register(new FogEvent(ConfigHandler.getFogDefinitions(), ConfigHandler.getFogMapDefinitions()));
        else 
            MinecraftForge.EVENT_BUS.register(new GlobalFogEvent());
    }

    // public static void logInfo(String msg) {
    //     final String prefix = "$$$###$$$###";    
    //     logger.info(prefix + msg);
    // }

}
