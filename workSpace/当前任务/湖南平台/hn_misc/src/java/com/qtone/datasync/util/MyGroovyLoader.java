package com.qtone.datasync.util;

import groovy.lang.GroovyClassLoader;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.control.CompilationFailedException;

/**
 * @author ���ڷ� 2009-9-2 ר�����ڼ���Groovy�ű�����Щ�ű��Ѿ�ʵ������صĽӿڣ����ط��� �ӿڵ�ʵ��
 * 
 *         T �Ǹýű���ʵ�ֹ��Ľӿ�
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
