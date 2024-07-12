package dotBlueShoes.fogger.utility;

public class FogColor {

	public float r, g, b;

	public FogColor(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	static final public FogColor DEFAULT = new FogColor(0.50f, 0.50f, 0.50f);
}
