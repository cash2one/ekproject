package cn.elamzs.common.eimport.inter;

import java.io.File;

import cn.elamzs.common.eimport.enums.FileType;

/**
 * 
 * @author Ethan.Lam 文件导入工具接口
 * 
 */
public interface EImporter {

	// 下载导入模版
	public File downTemplate(FileType type) throws Exception;

	// 上传文件
	public String importFile(String dataFile,String alias,String storeSubDir) throws Exception;

	// 返回源文件（上传的文件）
	public File getResourceFile(String importTaskSeq) throws Exception;

	// 返回用户导入文件后的结果
	public File getImportedResult(String importTaskSeq) throws Exception;

}
