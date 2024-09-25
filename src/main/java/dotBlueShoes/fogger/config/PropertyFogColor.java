package dotBlueShoes.fogger.config;

public class PropertyFogColor {

	public String name, _comment;
	public int r, g, b;

	public PropertyFogColor(String name, String _comment, int r, int g, int b) {
		this._comment = _comment;
		this.name = name;
		this.r = r;
		this.g = g;
		this.b = b;
	}

}
