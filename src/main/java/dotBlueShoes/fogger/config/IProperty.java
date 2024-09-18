package dotBlueShoes.fogger.config;

public interface IProperty {

	String getName();
	int read(String data, int initialReadPosition);

}
