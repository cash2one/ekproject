package cn.elam.util.bean;

import java.lang.reflect.Constructor;

public class BeanUtil {

	public static Object createInstance(Class clazz,Object... params) {
		try {
			if (params == null || params.length == 0)
				return clazz.newInstance();
			else {
				Constructor constructor = clazz.getConstructor(getClassType(params));
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
		ClassLoader loader = BeanUtil.class.getClassLoader();
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
		Class[] types = getClassType("123",1);
		for (Class c : types) {
			System.out.println(c);
		}
		Test test = (Test) createInstance(loadClass("cn.elam.util.bean.Test"),"123",1);
	}

	
}
class Test {
	public Test(String me) {

	}

	public Test(String m, Integer i) {

	}

}