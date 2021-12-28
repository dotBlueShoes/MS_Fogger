package dotblueshoes.fogger.dependency;

import dotblueshoes.fogger.*;
import java.lang.reflect.*;

public class Reflection {

	// Retrives the specified property from an instance of an object.
	//  That is no matter what public.
	public static Object getObjectInstanceField(Object instance, String fieldName) {

		try {
			Field field = instance.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);

			return field.get(instance);
		} catch (Exception exception) {
			Fogger.logInfo("Catched an exception during possessing private field from an object instance.");
			exception.printStackTrace();
		}

		return null;
	}

//	static void setFinalStatic(Field field, Object newValue) throws Exception {
//		field.setAccessible(true);
//		Field modifiersField = Field.class.getDeclaredField("modifiers");
//		modifiersField.setAccessible(true);
//		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
//		field.set(null, newValue);
//	}
//	// Same but for static fields.
//	public static Object getObjectStaticField(Class classType, String fieldName) {
//	    try {
//	        Field field = classType.getDeclaredField(fieldName);
//	        field.setAccessible(true);
//	        return field;
//	    } catch (Exception exception) {
//	        Fogger.logInfo("Catched an exception during possessing private static field from an object.");
//	        exception.printStackTrace();
//	    }
//	    return null;
//	}

	// Checks wheater a class is available at runtime or not.
	public static boolean isClassAvailableAtRuntime(final String classPackage) {
		try { Class.forName(classPackage, false, Reflection.class.getClassLoader());
		} catch (ClassNotFoundException exception) {
			Fogger.logInfo("DynamicSurroundings Are Not Present!");
			return false;
		}

		return true;
	}

}