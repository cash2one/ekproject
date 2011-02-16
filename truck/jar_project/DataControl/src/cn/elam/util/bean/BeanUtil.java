package cn.elam.util.bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Vector;

/**
 * Bean 工具
 * @author Ethan.Lam  2011-2-15
 *
 */
public class BeanUtil {

	public static Vector<Field> getAllDeclaredFields(Class clazz) {
		Vector<Field> fileds = new Vector<Field>(5);
		Vector<Field> temps = null;
		if (clazz.getSuperclass() != null) {
			temps = getAllDeclaredFields(clazz.getSuperclass());
		}
		if (temps != null)
			for (Field field : temps)
				fileds.add(field);
		temps = null;
		Field[] fs = clazz.getDeclaredFields();
		if (fs != null)
			for (Field field : fs)
				fileds.add(field);
		fs = null;
		return fileds;
	}

	public static Object createInstance(Class clazz, Object... params) {
		try {
			if (params == null || params.length == 0)
				return clazz.newInstance();
			else {
				Constructor constructor = clazz
						.getConstructor(getClassType(params));
				if (constructor != null)
					return constructor.newInstance(params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Class[] getClassType(Object... params) {
		if (params == null || params.length == 0)
			return null;
		else {
			Class[] types = new Class[params.length];
			for (int index = 0; index < params.length; index++) {
				types[index] = params[index].getClass();
			}
			return types;
		}
	}

	public static Class loadClass(String classPath) {
		ClassLoader loader = ClassLoader.getSystemClassLoader();
		if (loader == null)
			return null;
		try {
			return loader.loadClass(classPath);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String... srt) {
		Class[] types = getClassType("123", 1);
		for (Class c : types) {
			System.out.println(c);
		}
	}

}
