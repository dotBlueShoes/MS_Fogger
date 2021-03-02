package dotblueshoes.fogger;

public class FogSetting {
    public String biomeName;
    public float
        fogMinIntensity,
        fogMaxIntensity,
        yLevel;

    FogSetting(String biomeName, float yLevel, float fogMinIntensity, float fogMaxIntensity) {
        this.fogMinIntensity = fogMinIntensity;
        this.fogMaxIntensity = fogMaxIntensity;
        this.biomeName = biomeName;
        this.yLevel = yLevel;
    }
}