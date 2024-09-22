package dotBlueShoes.fogger.config;

public class PropertyFogDefinition {

	public String nameColor;
	public String name;

	public float start;
	public float end;

	public PropertyFogDefinition(String name, float start, float end, String nameColor) {
		this.nameColor = nameColor;
		this.start = start;
		this.name = name;
		this.end = end;
	}

}
