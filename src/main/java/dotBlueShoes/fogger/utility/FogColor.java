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
	//static final public FogColor DEFAULT = new FogColor(0.65098f, 0.8f, 1.0f);
	//static final public FogColor SKY_BLUE = new FogColor(0.65098f, 0.8f, 1.0f);
	//static final public FogColor UnderWaterNBD = new FogColor(0.02f, 0.02f, 0.2f); // NonBiomeDependant
	//static final public FogColor UnderLava = new FogColor(0.6f, 0.1f, 0.0f);
}
