package dotblueshoes.fogger.config.utility;

public class FogDefinition {
    public String name;
    public float fogStartPoint, fogEndPoint;

    public FogDefinition(String name, float fogStartPoint, float fogEndPoint) {
        this.fogStartPoint = fogStartPoint;
        this.fogEndPoint = fogEndPoint;
        this.name = name;
    }

    public String toString() {
        return name + " " + Float.toString(fogStartPoint) + " " + Float.toString(fogEndPoint);
    }

    public static FogDefinition parseString(String data) {
        final String[] separatedData = data.split(" ");
        return new FogDefinition ( 
            separatedData[0],
            Float.parseFloat(separatedData[1]), 
            Float.parseFloat(separatedData[2])
        );
    }
}