package dotBlueShoes.fogger.config;

import dotBlueShoes.fogger.Fogger;
import dotBlueShoes.fogger.utility.FogColor;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;

public class MSConfigHandler {

	private static final String CONFIG_DIRECTORY = FabricLoader.getInstance().getGameDir().toString() + "/config/";

	public final String configFileName;

	private final String CONFIG_INITIAL_CONTEXT =
		" Before the 'OPENING BRACKET' you can write what u want just don't use that sign.\n" +
		" You might want to use this space for notes or other. [link to wiki]\n" +
		"\n" +
		"{ # Fogger's Config\n" +
		"\n" +
		"    { Colors        # \"Color of the fog. 1st create a color here. Then reference it inside FogDefinitions table.\"\n" +
		"        \"c_zero\",       1.0,        1.0,    1.0\n" +
		"        \"c_default\",    0.65098,    0.8,    1.0\n" +
		"    }\n" +
		"\n" +
		"    { Definitions   # \"Definition of the for. 1st create a range here with associated color. Then reference it inside FogSetting table.\"\n" +
		"        \"d_default\",    0.0, 0.9, \"c_default\"\n" +
		"        \"d_abnormal\",   0.3, 0.5, \"c_default\"\n" +
		"        \"d_foggy\",      0.0, 0.5, \"c_default\"\n" +
		"    }\n" +
		"\n" +
		"    { Settings      # \"Setting of the fog. Here describe the conditions for the fog to occur. SORTED\"\n" +
		"        1, 0, 0, 0, 0, 0, \"d_foggy\"\n" +
		"        0, 0, 2, 0, 0, 0, \"d_foggy\"\n" +
		"        0, 0, 1, 0, 0, 0, \"d_abnormal\"\n" +
		"        0, 0, 0, 0, 0, 0, \"d_default\"\n" +
		"    }\n" +
		"\n" +
		"}\n";

	// property -> group.key_name, type, value
	// then ensure it writes as a group...
	// type can be deduced from value...

	// support of
	// - base types
	// - groups
	// - serialization

	public MSConfigHandler(String configFileName) {
		this.configFileName = configFileName + ".msc";
	}

	public String getFilePath() {
		return CONFIG_DIRECTORY + configFileName;
	}

	public void create () {
		File configFile = new File(getFilePath());
		Fogger.LOGGER.info("Config file name: {}", this.configFileName);
		Fogger.LOGGER.info("Config file path: {}", configFile.getAbsolutePath());

		//noinspection ResultOfMethodCallIgnored
		configFile.getParentFile().mkdirs();

		HashMap<String, String> properties = new HashMap<>();

		try {
			if (configFile.createNewFile()) {
				Fogger.LOGGER.info("Config file does not exist. Creating...");

				//addProperty(properties, "Count", "Describes the count", 5);
				//addProperty(properties, "Group.Count", 5);
				//writeProperties();
				//String data = "";
				//for (int i = 0; i < properties.size(); ++i) {
				//	data += properties.values();
				//}

				write(configFile, CONFIG_INITIAL_CONTEXT.getBytes());
			} else {
				load(configFile);
			}
		} catch (IOException exception) {
			Fogger.LOGGER.error("Could not create the config File!");
			//noinspection CallToPrintStackTrace
			exception.printStackTrace();
		}
	}

	public void write(File configFile, byte[] data) {
		try (OutputStream output = Files.newOutputStream(configFile.toPath())) {
			output.write(data);
		} catch (IOException exception) {
			//noinspection CallToPrintStackTrace
			exception.printStackTrace();
		}
	}

	public void load(File configFile) {
		try (InputStream input = Files.newInputStream(configFile.toPath())) {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();

			while (true) {
				byte[] buffer = new byte[Math.max(2048, input.available())];
				int count = input.read(buffer);
				if (count == -1) break;
				stream.write(buffer, 0, count);
			}

			final String data = stream.toString();

			{

				//final byte[] PROPERTY_ARRAY_COLORS = "FogColors".getBytes();
				//final byte[] PROPERTY_ARRAY_DEFINITIONS = "FogDefinitions".getBytes();
				//final byte[] PROPERTY_ARRAY_SETTINGS = "FogSettings".getBytes();

				IProperty[] PROPERTIES = new IProperty[] {
					new PropertyFogColors(),
					new PropertyFogDefinitions(),
					new PropertyFogSettings(),
				};

				// !!!!
				// TODO: The order (which property we read first) is important
				// So no matter what we're first looking for colors, then definitions, then settings.
				// !!!!

				int flag_position = 0;

				while (flag_position < data.length()) { // Skip Header
					if (data.charAt(flag_position) == '{') break;
					++flag_position;
				}

				while (flag_position < data.length()) {

					switch (data.charAt(flag_position)) {

						case '#': { // Skip the entire line when this sign is detected.
							++flag_position;
							while (flag_position < data.length()) {
								if (data.charAt(flag_position) == '\n') break;
								++flag_position;
							}
							continue;
						}

						case '\n':
						case '\t':
						case ' ':
						case '{': { // Skip property check when this sign is detected.
							++flag_position;
							continue;
						}

					}

					// Go through each property in array.
					propertyLoop: for (int iProperty = 0; iProperty < PROPERTIES.length; ++iProperty) {

						String propertyName = PROPERTIES[iProperty].getName();
						int iPropertySign = 0;

						// Match property
						for (; iPropertySign < propertyName.length(); ++iPropertySign) {
							if (data.charAt(flag_position + iPropertySign) != propertyName.charAt(iPropertySign)) {
								continue propertyLoop;
							}
						}

						// Skip property name we have already read.
						flag_position += iPropertySign;

						// Run the property reader.
						int propertyReadBytes = PROPERTIES[iProperty].read(data, flag_position);

						// Skip property bytes we have already read.
						flag_position = propertyReadBytes;

						// Because we have already found the property in this piece of text
						//  we skip newer iterations.
						break propertyLoop;
					}

					++flag_position;
				}

				//Fogger.LOGGER.info(String.valueOf(flag_position), flag_type);

			}


			//Fogger.LOGGER.info(String.valueOf(i));

		} catch (IOException exception) {
			//noinspection CallToPrintStackTrace
			exception.printStackTrace();
		}
	}

	// types
	// - group   (nb) (count)
	// - integer (4b)
	// - long    (8b)
	// - float   (4b)
	// - double  (8b)
	// - byte    (1b)
	// - string  (nb) (count)

	// 01(11.1111) - group
	// 00(11.1111) - string
	// (1)111.1111 - other
	// this representation gives 127 other types. why
	// 8 types or 16 types is enough.

	// 0000.0000 - 00 = byte
	// 0000.0001 - 01 = integer
	// 0000.0010 - 02 = long
	// 0000.0011 - 03 = float
	//
	// 0000.0100 - 04 = double
	// 0000.0101 - 05 = string
	// 0000.0110 - 06 = group
	// 0000.0111 - 07 = reserved
	//
	// 0000.1000 - 08
	// 0000.1001 - 09
	// 0000.1010 - 10
	// 0000.1011 - 11
	//
	// 0000.1100 - 12
	// 0000.1101 - 13
	// 0000.1110 - 14
	// 0000.1111 - 15

	// - so we can check last 3 bits only!
	// then if its:
	//  group we do (whole_byte - group_bits) which gives us number of groups
	//  string same but for string.
	// Therefore, string and group can have max 255-7 which is 248 length.
	//  on top of that we could place another byte right after it for greater length representation.

	// (1b) description length
	// (max 255) description
	// (1b) groups length
	// (max 255) groups
	//  - (1b) description length
	//  - description
	//  - (1b) type (if string then it's string size)
	//  - value

	final byte TYPE_BYTE     = 0x00;
	final byte TYPE_INTEGER  = 0x01;
	final byte TYPE_LONG     = 0x02;
	final byte TYPE_FLOAT    = 0x03;
	final byte TYPE_DOUBLE   = 0x04;
	final byte TYPE_STRING   = 0x05;
	final byte TYPE_GROUP    = 0x06;
	final byte TYPE_RESERVED = 0x07;

	public void writeProperties() {

		final String DESCRIPTION_MAIN = "Fogger's Config";
		final int GROUPS_COUNT_MAIN = 3;

		final String COLORS_DESCRIPTION = "Color of the fog. 1st create a color here. Then reference it inside FogDefinitions table.";
		final String[] COLORS_NAMES = { "zero", "default" };
		final FogColor[] COLORS = { FogColor.ZERO, FogColor.DEFAULT };

		// FogColor
		// TYPE_FLOAT, TYPE_FLOAT, TYPE_FLOAT

		// FogDefinition
		// TYPE_FLOAT, TYPE_FLOAT, TYPE_STRING

		// FogSetting
		// TYPE_STRING, TYPE_BYTE, TYPE_INTEGER, TYPE_INTEGER, TYPE_INTEGER, TYPE_BYTE, TYPE_INTEGER

		final String STREAM =
			(char)DESCRIPTION_MAIN.length() +           //
			DESCRIPTION_MAIN +                          //
			(char)(TYPE_GROUP + GROUPS_COUNT_MAIN) +    //
			(char)COLORS_DESCRIPTION.length() +         //
			COLORS_DESCRIPTION +                        //
			(char)COLORS.length +                       //
			COLORS[0].toConfig(COLORS_NAMES[0]) +       //
			COLORS[1].toConfig(COLORS_NAMES[1])         //
		;

		Fogger.LOGGER.info(STREAM);

	}

	public <T> void addProperty(HashMap<String, String> properties, final String name, final T value) {
		properties.put("name", String.valueOf(value));
		//out = out + name + " = " + value + '\n';
	}

	//public <T> void addProperty(HashMap<String, String> properties, final String name, final String description, final T value) {
	//	out = out + "# " + description + '\n';
	//	addProperty(properties, name, value);
	//}

}
