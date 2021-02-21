package dotblueshoes.fogger;

public class FogConfiguration {
    public String biomeName;
    public float 
        fogMinClamp, fogMaxClamp; 
        //red, green, blue; 

    public FogConfiguration(
        String biomeName, float fogMinClamp, float fogMaxClamp
        //float red, float green, float blue
    ) {
        this.biomeName = biomeName;
        this.fogMinClamp = fogMinClamp;
        this.fogMaxClamp = fogMaxClamp;
        //this.red = red;
        //this.green = green;
        //this.blue = blue;

    }

    public String toString() {
        return biomeName + " " + 
            Float.toString(fogMinClamp) + " " +
               Float.toString(fogMaxClamp); // + " " +
            //Float.toString(red) + " " +
            //Float.toString(green) + " " +
            //Float.toString(blue);
    }
};