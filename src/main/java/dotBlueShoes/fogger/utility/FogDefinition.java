package dotBlueShoes.fogger.utility;

public class FogDefinition {

	public float start;
	public float end;
	public int iColor;

	public FogDefinition(float start, float end, int iColor) {
		this.start = start;
		this.end = end;
		this.iColor = iColor;
	}

	public static final FogDefinition ZERO = new FogDefinition(0.00f, 0.00f, 0);

}
