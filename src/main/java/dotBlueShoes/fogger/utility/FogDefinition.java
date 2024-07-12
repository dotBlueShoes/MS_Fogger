package dotBlueShoes.fogger.utility;

public class FogDefinition {

	public float start = 0;
	public float end = 0;
	public byte iColor = 0;

	public FogDefinition(float start, float end, byte iColor) {
		this.start = start;
		this.end = end;
		this.iColor = iColor;
	}

	//public static final FogDefinition DEFAULT_FOG = new FogDefinition(0.25f, 1.00f, (byte)0);
	public static final FogDefinition DEFAULT = new FogDefinition(0.00f, 0.90f, (byte)0);
	public static final FogDefinition ZERO = new FogDefinition(0.00f, 0.00f, (byte)0);

}
