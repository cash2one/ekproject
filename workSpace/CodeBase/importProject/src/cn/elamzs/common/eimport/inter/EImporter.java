package cn.elamzs.common.eimport.inter;

import java.io.File;

import cn.elamzs.common.eimport.sample.TestValidator;

/**
 * 
 * @author Ethan.Lam
 * 文件导入工具接口
 * 
 */
public interface EImporter {

	
	//下载导入模版
	public File downTemplate(Class<? extends DataValidator> validator);
	
	// 上传文件
	public String importFile(String dataFile);


	// 返回源文件（上传的文件）
	public File getResourceFile(String fileIdentifyId);

	
	// 返回用户导入文件后的结果
	public File getImportedResult(String fileIdentifyId);


	
}
