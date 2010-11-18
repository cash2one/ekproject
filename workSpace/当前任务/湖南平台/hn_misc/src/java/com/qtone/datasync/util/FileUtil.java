package com.qtone.datasync.util;

/**
 * @author 杨腾飞	2009-9-2 
 *
 */
public class FileUtil {
	/**
	 * 处理文件绝对路径的获取
	 * 
	 * @param clazz
	 * @param path
	 * @return
	 */
	public static String getFilePath(String path) {
		return FileUtil.class.getClassLoader().getResource(path).getPath();
	}
}
