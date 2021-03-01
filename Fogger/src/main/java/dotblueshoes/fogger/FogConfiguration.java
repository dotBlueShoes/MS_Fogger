package dotblueshoes.fogger;

import dotblueshoes.fogger.FogDefinition; 

public class FogConfiguration {
    public String biomeName;
    public FogDefinition fogSetting;
    public float yLevel = 64F;

    public FogConfiguration( String biomeName, FogDefinition fogSetting) {
        this.biomeName = biomeName;
        this.fogSetting = fogSetting;
    }

    public String toString() {
        return biomeName + " " + 
            Float.toString(fogSetting.fogMinIntensity) + " " +
            Float.toString(fogSetting.fogMaxIntensity);
    }

    public static FogConfiguration parseString(String data) {
        final String[] separatedData = data.split(" ");
        return new FogConfiguration(
            separatedData[0], 
			new FogDefinition("", Float.parseFloat(separatedData[1]), Float.parseFloat(separatedData[2]))
        );
    }
};