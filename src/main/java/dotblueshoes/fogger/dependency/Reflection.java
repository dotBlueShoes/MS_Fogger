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

	// Same but for static fields.
	public static Object getObjectStaticFinalField(Class<?> type, String fieldName) {
		try {
			Field field = type.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(null);
		} catch (Exception exception) {
			Fogger.logInfo("Catched an exception during possessing private static field from an object.");
			exception.printStackTrace();
		}
	    return null;
	}

	// Checks whether a class is available at runtime or not.
	public static boolean isClassAvailableAtRuntime(final String classPackage) {
		try { Class.forName(classPackage, false, Reflection.class.getClassLoader());
		} catch (ClassNotFoundException exception) {
			Fogger.logInfo("DynamicSurroundings Are Not Present!");
			return false;
		}

		return true;
	}

}