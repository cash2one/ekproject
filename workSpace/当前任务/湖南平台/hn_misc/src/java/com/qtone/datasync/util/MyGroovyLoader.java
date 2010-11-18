package com.qtone.datasync.util;

import groovy.lang.GroovyClassLoader;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.control.CompilationFailedException;

/**
 * @author 杨腾飞 2009-9-2 专门用于加载Groovy脚本（这些脚本已经实现了相关的接口），关返回 接口的实例
 * 
 *         T 是该脚本所实现过的接口
 */
public class MyGroovyLoader {
	private static Log log = LogFactory.getLog(MyGroovyLoader.class);

	@SuppressWarnings("unchecked")
	public static Object getInstance(String groovyFile) {
		Object ret = null;
		try {
			ClassLoader parent = MyGroovyLoader.class.getClassLoader();
			GroovyClassLoader gcl = new GroovyClassLoader(parent);

			String filePath = FileUtil.getFilePath(groovyFile);
			Class clazz = gcl.parseClass(new File(filePath));

			ret = clazz.newInstance();
		} catch (CompilationFailedException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} catch (InstantiationException e) {
			log.error(e);
		} catch (IllegalAccessException e) {
			log.error(e);
		}

		return ret;
	}
}
