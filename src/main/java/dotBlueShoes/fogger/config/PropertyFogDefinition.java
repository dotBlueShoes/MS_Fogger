package dotBlueShoes.fogger.config;

public class PropertyFogDefinition {

	public String name, _comment, nameColor;
	public float start, end;

	public PropertyFogDefinition(String name, String _comment, float start, float end, String nameColor) {
		this.nameColor = nameColor;
		this._comment = _comment;
		this.start = start;
		this.name = name;
		this.end = end;
	}

}
