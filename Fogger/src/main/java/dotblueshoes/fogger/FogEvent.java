package dotblueshoes.fogger;

import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

import dotblueshoes.fogger.config.FogDefinitionMapper;
import dotblueshoes.fogger.config.FogDefinition;
import dotblueshoes.fogger.config.ConfigHandler;
import dotblueshoes.fogger.Fogger;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public class FogEvent {

    private WorldClient worldClient;
    private String biomeName;
    private Entity entity;

    private static final float increaseValue = 0.001F;
    private static float 
        currentFogMinIntensity = 0F,
        currentFogMaxIntensity = 0F,
        visibleDistance;

    /* Different Weather */
    // This seems a littile bit more compicated.
    // i guess i can support rain and thunder. 
    // but the more i think about it the more i think about using json.

    private static FogSetting[] fogSettings;

    public static void initialize(FogDefinition[] fogDefinitions, FogDefinitionMapper[] mappedDefinitions) {

        /* Parsing logic (getting rid of duplications, y-level, spaces, tabs, newlines) */
        // idea - FogDefinitionMapper[] sortedMapper = sort();
        // to be done.

        // // get rid of duplicates
        // for (int i = 0; i < mappedDefinitions.length; i++) {
        //     int duplicationCount = 0;
        //     for (int j = 0; j < mappedDefinitions.length; j++) {
        //         if (mappedDefinitions[i].equals(mappedDefinitions[j])) {
        //             duplicationCount += 1;
        //         }
        //     }
        //     Fogger.LogInfo(mappedDefinitions[i].biomeName + Integer.toString(duplicationCount));
        // }

        // // sort them y-level
        // int temp;
        // for (int i = 0; i < mappedDefinitions.length; i++)
        //     for (int j = 0; j < mappedDefinitions.length; j++)
        //         if (mappedDefinitions[i].yLevel > mappedDefinitions[j].yLevel) {
                    
        //         }

        /* More on parsing */
        // 1. look for biomes with the same name 
        // 2. see if the biome isn't a full duplicate, if so 
        // 3. 

        // for (int i = 0; i < mappedDefinitions.length; i++) {
        //     FogDefinitionMapper[] biomeFogs;
        //     int count;

        //     for (int j = 0; j < mappedDefinitions.length; j++) { // Get the length of biomeFogs.
        //         if (mappedDefinitions[i].biomeName.equals(mappedDefinitions[j].biomeName)) {
        //             if (mappedDefinitions[i].equals(mappedDefinitions[j])) // If its a full duplicate then ignore it. (user-config-error)
        //                 continue;
        //             count++;
        //         }
        //     }

        //     biomeFogs = FogDefinitionMapper[count];

        //     for (int j = 0; j < mappedDefinitions.length; j++) { // Load the elements it.
        //         if (mappedDefinitions[i].biomeName.equals(mappedDefinitions[j].biomeName)) {
        //             if (mappedDefinitions[i].equals(mappedDefinitions[j])) // If its a full duplicate then ignore it. (user-config-error)
        //                 continue;
        //             biomeFogs[j] = mappedDefinitions[j];
        //         }
        //     }
        // }

        // FogDefinitionMapper[] proccessedDefinitions;
        // int count = mappedDefinitions.length;

        // for (int i = 0; i < mappedDefinitions.length; i++) {

        //     /* Getting rid of duplicates */
        //     for (int j = 0; j < mappedDefinitions.length; j++) {
        //         if (mappedDefinitions[i].biomeName.equals(mappedDefinitions[j].biomeName)) {
        //             if (mappedDefinitions[i].equals(mappedDefinitions[j])) {
        //                 count--;
        //             }
        //         }
        //     }

        //     proccessedDefinitions = new FogDefinitionMapper[mappedDefinitions.length];

        //     /* Loading not duplicate elements in. */
        //     for (int j = 0; j < mappedDefinitions.length; j++) {
        //         if (mappedDefinitions[i].biomeName.equals(mappedDefinitions[j].biomeName)) {
        //             if (mappedDefinitions[i].equals(mappedDefinitions[j])) {
        //                 continue;
        //             }
        //             proccessedDefinitions[i] = 
        //         }
        //     }

        // }

        fogSettings = new FogSetting[mappedDefinitions.length];

        for (int i = 0; i < fogSettings.length; i++)
            for (int j = 0; j < fogDefinitions.length; j++)
                if(mappedDefinitions[i].fogDefinition.equals(fogDefinitions[j].name)) {
                    fogSettings[i] = new FogSetting (
                        mappedDefinitions[i].biomeName, 
                        mappedDefinitions[i].yLevel, 
                        fogDefinitions[j].fogMinIntensity, 
                        fogDefinitions[j].fogMaxIntensity
                    );
                    break;
                }
    }

    @SubscribeEvent
    public void renderFogEvent(RenderFogEvent event) {
        visibleDistance = event.getFarPlaneDistance();
        worldClient = Minecraft.getMinecraft().world;
        entity = event.getEntity();

        biomeName = worldClient.getBiome(new BlockPos(entity.posX, entity.posY, entity.posZ)).getRegistryName().toString();

        GlStateManager.setFog(GlStateManager.FogMode.LINEAR); // ? does it rly have to be here
        
        if (event.getFogMode() == 0) { /* Is the horizontal vertical. -1 stands for ceiling Fog. */
            for (int i = 0; i < fogSettings.length; i++) /* Looping throught all the defined biomes. */
                if (biomeName.equals(fogSettings[i].biomeName)) /* Checking the biome. */
                    if (entity.posY >= fogSettings[i].yLevel) { /* checking the y-level. */
                        renderFog (fogSettings[i].fogMaxIntensity, fogSettings[i].fogMinIntensity);
                        return;
                    }
            renderFog ( /* If not matched with the list use Default Fog setting. */
                ConfigHandler.defaultFogMaxIntensity, 
                ConfigHandler.defaultFogMinIntensity
            );
        }
    }

    // The main thing where the fog is being set. // i do hope jvm inlines this function.
    private static void renderFog(float fogMaxIntensity, float fogMinIntensity) {

        /* fogMaxIntensity */
        if (currentFogMaxIntensity < fogMaxIntensity) {
            currentFogMaxIntensity += increaseValue;
            /* This is a correction that assures as that we're gonna hit the defined Max 
            and that we're not gonna enter upper if statement every odd call */
            if (currentFogMaxIntensity > fogMaxIntensity)
                currentFogMaxIntensity = fogMaxIntensity;
        } else if (currentFogMaxIntensity > fogMaxIntensity) {
            currentFogMaxIntensity -= increaseValue;
            /* This is a correction that assures as that we're gonna hit the defined Max 
            and that we're not gonna enter upper if statement every odd call */
            if (currentFogMaxIntensity < fogMaxIntensity)
                currentFogMaxIntensity = fogMaxIntensity;
        }

         /* fogMinIntensity */
        if (currentFogMinIntensity < fogMinIntensity) {
            currentFogMinIntensity += increaseValue;
            /* This is a correction that assures as that we're gonna hit the defined Max 
            and that we're not gonna enter upper if statement every odd call */
            if (currentFogMinIntensity > fogMinIntensity)
                currentFogMinIntensity = fogMinIntensity;
        } else if (currentFogMinIntensity > fogMinIntensity) {
            currentFogMinIntensity -= increaseValue;
            /* This is a correction that assures as that we're gonna hit the defined Max 
            and that we're not gonna enter upper if statement every odd call */
            if (currentFogMinIntensity < fogMinIntensity)
                currentFogMinIntensity = fogMinIntensity;
        }

        GlStateManager.setFogStart(visibleDistance * currentFogMinIntensity);
        GlStateManager.setFogEnd(visibleDistance * currentFogMaxIntensity);
    }

}