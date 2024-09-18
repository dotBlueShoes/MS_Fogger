package dotBlueShoes.fogger.config;

import dotBlueShoes.fogger.Fogger;

public class PropertyFogSettings implements IProperty{
	@Override
	public String getName() {
		return "FogSettings";
	}

	@Override
	public int read(String data, int initialReadPosition) {
		int propertyReadBytes = initialReadPosition;

		Fogger.LOGGER.info(getName());

		while (propertyReadBytes < data.length()) {

			switch (data.charAt(propertyReadBytes)) {

				case '#': { // Skip the entire line when this sign is detected.
					++propertyReadBytes;
					while (propertyReadBytes < data.length()) {
						if (data.charAt(propertyReadBytes) == '\n') break;
						++propertyReadBytes;
					}
					continue;
				}

				case '\n':
				case '\t':
				case ' ':
				case '{': { // Skip property check when this sign is detected.
					++propertyReadBytes;
					continue;
				}

				case '}': {
					break;
				}

			}
		}

		return propertyReadBytes;
	}
}
