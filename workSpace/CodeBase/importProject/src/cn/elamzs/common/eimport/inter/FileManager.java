package cn.elamzs.common.eimport.inter;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 
 * �ļ��������
 * @author Ethan.Lam  2011-2-10
 *
 */
public interface FileManager {
  
	
	/**
	 * �����ļ����������ļ� ID
	 * @param file
	 * @param fileName
	 * @param type
	 * @return
	 */
	public String storeFile(File file, String fileName,String dstDir,String type);

	
	/**
	 * �����ļ�ID �ҵ���Ӧ ���ļ���ŵ�·��
	 * @param fileId
	 * @param type
	 * @return
	 */
	public String getFileLocatePath(String fileId, String type);

	
	/**
	 * �򿪶�Ӧ���ļ�
	 * @param fileId
	 * @param type
	 * @return
	 */
	public File openFile(String fileId, String type);

}
