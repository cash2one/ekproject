/**
 * 
 */
package cn.qtone.xxt.android.common;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import android.os.Environment;

/**
 * 文件帮助类
 * 
 * @author 林子龙
 * 
 */
public class FileUtils {
	/**
	 * 建立并返回存取文件的目录,返回的目录格式为/sdcard/qtxxt/2012/9/6
	 */
	public static String getStorageDirAndMake() {
		String result;
		Calendar cal = Calendar.getInstance();
		result = Environment.getExternalStorageDirectory().getAbsolutePath() + "/qtxxt/" + cal.get(Calendar.MONTH)
				+ "/" + cal.get(Calendar.DAY_OF_MONTH);
		if (!new File(result).exists()) {
			new File(result).mkdirs();
		}
		return result;
	}

	/**
	 * 为输入的文件名建立新文件并返回所建立文件的全路径文件名
	 */
	public static String storageFile(String fileName) throws IOException {
		String file = getStorageDirAndMake() + "/" + fileName;
		new File(file).createNewFile();
		return file;
	}
}
