package dotBlueShoes.fogger.utility;

public class FogColor {

	public float r, g, b;

	public FogColor(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public FogColor(FogColor fogColor) {
		this.r = fogColor.r;
		this.g = fogColor.g;
		this.b = fogColor.b;
	}

	static final public FogColor ZERO = new FogColor(1.00f, 1.00f, 1.00f);
}
