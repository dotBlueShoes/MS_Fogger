package dotblueshoes.fogger.config.utility;


public class FogMapDefinition {
    public String biomeName, fogDefinition;
    public float yLevel;

    public FogMapDefinition( String biomeName, float yLevel, String fogDefinition) {
        this.fogDefinition = fogDefinition;
        this.biomeName = biomeName;
        this.yLevel = yLevel;
    }

    public String toString() {
        return biomeName + " " + Float.toString(yLevel) + " " + fogDefinition;
    }

    public static FogMapDefinition parseString(String data) {
        //data.replaceAll("\\s+", " ");

        final String[] separatedData = data.split("\\s+", 4);
        return new FogMapDefinition(
            separatedData[0], 
            Float.parseFloat(separatedData[1]),
            separatedData[2]
        );
    }

    // if i instead addup the lines creating one string and then run the split with the "\\s+" and 4 unitl i hit eof.
    //public static FogMapDefinition parseConfig(String data, int length) {}

    public boolean equals(FogMapDefinition map) {
        return (biomeName.equals(map.biomeName) && yLevel == map.yLevel && fogDefinition.equals(map.fogDefinition));
    }

    public static void sort(FogMapDefinition[] maps) {
        FogMapDefinition temp;
        for (int i = 0; i < maps.length; i++) 
            for (int j = 0; j < maps.length - 1; j++)
                if (maps[j].yLevel < maps[j + 1].yLevel) {
                    temp = maps[j];
                    maps[j] = maps[j + 1];
                    maps[j + 1] = temp;
                }
    }
};