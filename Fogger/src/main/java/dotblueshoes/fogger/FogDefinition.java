package dotblueshoes.fogger;

public class FogDefinition {
    public String name;
    public float
        fogMinIntensity,
        fogMaxIntensity;

    public FogDefinition(String name, float fogMinIntensity, float fogMaxIntensity) {
        this.fogMinIntensity = fogMinIntensity;
        this.fogMaxIntensity = fogMaxIntensity;
        this.name = name;
    }

    public String toString() {
        return Float.toString(fogMinIntensity) + " " + Float.toString(fogMaxIntensity);
    }

    public static FogDefinition parseString(String data) {
        final String[] separatedData = data.split(" ");
        return new FogDefinition ( 
            "",
            Float.parseFloat(separatedData[0]), 
            Float.parseFloat(separatedData[1])
        );
    }
}