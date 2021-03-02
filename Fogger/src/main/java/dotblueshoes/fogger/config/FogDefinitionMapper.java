package dotblueshoes.fogger.config;

import dotblueshoes.fogger.config.FogDefinition; 

public class FogDefinitionMapper {
    public String biomeName, fogDefinition;
    public float yLevel;

    public FogDefinitionMapper( String biomeName, float yLevel, String fogDefinition) {
        this.fogDefinition = fogDefinition;
        this.biomeName = biomeName;
        this.yLevel = yLevel;
    }

    public String toString() {
        return biomeName + " " + Float.toString(yLevel) + " " + fogDefinition;
    }

    public static FogDefinitionMapper parseString(String data) {
        final String[] separatedData = data.split(" ");
        return new FogDefinitionMapper(
            separatedData[0], 
            Float.parseFloat(separatedData[1]),
            separatedData[2]
        );
    }

    public boolean equals(FogDefinitionMapper mapper) {
        return (biomeName.equals(mapper.biomeName) && yLevel == mapper.yLevel && fogDefinition.equals(mapper.fogDefinition));
    }
};