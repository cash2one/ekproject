package cn.elamzs.common.base.files;

import java.io.File;

/**
 * �ϴ��ļ�����ӿ�
 * @author Ethan.Lam  2011-2-11
 *
 */
public interface FileUploadHandle {
   
	
	/**
	  * �������ļ��ϴ����ִ�е�ִ�еĶ���
	  * @param file
	  * @param oldFileName
	  * @param newFileName
	*/
	public void forEachFileUploadFinishedEvent(File file,String oldFileName,String newFileName);
	
}
