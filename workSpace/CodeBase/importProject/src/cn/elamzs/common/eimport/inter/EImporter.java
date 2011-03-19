package cn.elamzs.common.eimport.inter;

import java.io.File;

import cn.elamzs.common.eimport.enums.FileType;
import cn.elamzs.common.eimport.item.TaskModel;

/**
 * 数据文件导入服务接口
 * @author Ethan.Lam 
 * 
 */
public interface EImporter {

	/**
	 * 
	 * 下载导入模版
	 * @param type   文件类型
	 * @return       导入模版
	 * @throws Exception
	 */
	public File downTemplate(FileType type) throws Exception;

	
	/**
	 * 上传文件
	 * @param dataFileFullPath   导入文件的全路径
	 * @param alias              文件名
	 * @param storeSubDir        子目录
	 * @param taskType           任务类型标识
	 * @return                   
	 * @throws Exception
	 */
	public String importFile(String dataFileFullPath,String alias,String storeSubDir,String taskType) throws Exception;

	
	/**
	 * 
	 * @param importTaskSeq    任务的序列号
	 * @return                 返回源上传的文件 
	 * @throws Exception
	 */
	public TaskModel getResourceFile(String importTaskSeq) throws Exception;

	
	/**
	 * 
	 * @param importTaskSeq  任务的序列号
	 * @return               返回用户导入文件后的结果
	 * @throws Exception
	 */
	public TaskModel getImportedResult(String importTaskSeq) throws Exception;

	
}
