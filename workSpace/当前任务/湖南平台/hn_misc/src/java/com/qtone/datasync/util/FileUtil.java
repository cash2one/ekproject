package com.qtone.datasync.util;

/**
 * @author ���ڷ�	2009-9-2 
 *
 */
public class FileUtil {
	/**
	 * �����ļ�����·���Ļ�ȡ
	 * 
	 * @param clazz
	 * @param path
	 * @return
	 */
	public static String getFilePath(String path) {
		return FileUtil.class.getClassLoader().getResource(path).getPath();
	}
}
