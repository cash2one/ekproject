package cn.elamzs.common.eimport.inter;

import java.io.File;

/**
 * �ϴ��ļ�����ӿ�
 * @author Ethan.Lam  2011-2-11
 *
 */
public interface FileUploadHandle {
   
	
	/**
	  * ���ļ��ϴ����ִ�е�ִ�еĶ���
	  * @param file
	  * @param oldFileName
	  * @param newFileName
	*/
	public void uploadFinishedEvent(File file,String oldFileName,String newFileName);
	
}
