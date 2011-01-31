package cn.elamzs.common.eimport.inter;

import java.io.File;

/**
 * 
 * @author Ethan.Lam
 * 文件导入工具接口
 * 
 */
public interface EImporter {

	//下载导入模版
	public File downTemplate();
	
	// 上传文件
	public String upLoadFile(String dataFile);

	
	// 返回源文件（上传的文件）
	public File getResourceFile(String fileIdentifyId);

	
	// 返回用户导入文件后的结果
	public File getImportedResult(String fileIdentifyId);


	
}
