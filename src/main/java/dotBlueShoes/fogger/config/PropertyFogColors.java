package dotBlueShoes.fogger.config;

public class PropertyFogColors implements IProperty {

	@Override
	public final String getName() {
		return "FogColors";
	}

	@Override
	public void read(int initialReadPosition) {
		// Skip spaces/tabs/newlines if exists
		// Skip comment if exists
		// USER DEFINED
		//  READ Array element that consists of:
		//  - READ String
		//  - READ float
		//  - READ float
		//  - READ float
		// End reading at '}' sign

		// Based on that information initialize FogColors array (define it's size first!).
		// Based on that information store names somewhere along the way for further processing.
	}

}
