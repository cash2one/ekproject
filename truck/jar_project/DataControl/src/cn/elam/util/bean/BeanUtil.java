package cn.elam.util.bean;

import java.lang.reflect.Constructor;

/**
 * 
 * @author ethanLam 
 * 类加载器工具
 */
public class BeanUtil {

	/**
	 * 创建新的对象
	 * 
	 * @param clazz
	 * @param params
	 * @return
	 */
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

	/**
	 * 返回参数对应的类类型
	 * 
	 * @param params
	 * @return
	 */
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

	/**
	 * 加载类
	 * 
	 * @param classPath
	 * @return
	 */
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
		Class[] types = getClassType("123", 1);
		for (Class c : types) {
			System.out.println(c);
		}
		Test test = (Test) createInstance(loadClass("cn.elam.util.bean.Test"),
				"123", 1);
	}

}

class Test {
	public Test(String me) {

	}

	public Test(String m, Integer i) {

	}

}