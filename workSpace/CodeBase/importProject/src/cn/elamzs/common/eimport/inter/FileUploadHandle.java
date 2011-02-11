package cn.elamzs.common.eimport.inter;

import java.io.File;

/**
 * 上传文件服务接口
 * @author Ethan.Lam  2011-2-11
 *
 */
public interface FileUploadHandle {
   
	
	/**
	  * 当文件上传完后执行的执行的动作
	  * @param file
	  * @param oldFileName
	  * @param newFileName
	*/
	public void uploadFinishedEvent(File file,String oldFileName,String newFileName);
	
}
