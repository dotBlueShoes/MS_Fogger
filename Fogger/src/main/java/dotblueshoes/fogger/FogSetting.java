package dotblueshoes.fogger;

public class FogSetting {
    public String biomeName;
    public float
        fogStartPoint,
        fogEndPoint,
        yLevel;

    FogSetting(String biomeName, float yLevel, float fogStartPoint, float fogEndPoint) {
        this.fogStartPoint = fogStartPoint;
        this.fogEndPoint = fogEndPoint;
        this.biomeName = biomeName;
        this.yLevel = yLevel;
    }
}