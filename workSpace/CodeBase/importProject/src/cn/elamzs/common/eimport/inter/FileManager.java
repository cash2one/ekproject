package cn.elamzs.common.eimport.inter;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 
 * 文件管理操作
 * @author Ethan.Lam  2011-2-10
 *
 */
public interface FileManager {
  
	
	/**
	 * 保存文件，并返回文件 ID
	 * @param file
	 * @param fileName
	 * @param type
	 * @return
	 */
	public String storeFile(File file, String fileName,String dstDir,String type);

	
	/**
	 * 利用文件ID 找到对应 的文件存放的路径
	 * @param fileId
	 * @param type
	 * @return
	 */
	public String getFileLocatePath(String fileId, String type);

	
	/**
	 * 打开对应的文件
	 * @param fileId
	 * @param type
	 * @return
	 */
	public File openFile(String fileId, String type);

}
