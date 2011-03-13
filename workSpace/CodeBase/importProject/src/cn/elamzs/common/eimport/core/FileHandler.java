package cn.elamzs.common.eimport.core;

import java.io.File;

/**
 * 
 * @author Ethan.Lam 2011-2-7
 * 
 */
public interface FileHandler extends Runnable {

	/**
	 * 验证导入文档是否是符合模版要求
	 * @return 导入文件的列名(中文列名)
	 * @throws Exception
	 */
	public String[] returnImpDocColumnsName()throws Exception;
	
	
	/**
	 * 把文本中的数据读入到内存中，实施数据验证过程
	*  @return 导入文件数据的总记录行数
	 * @throws Exception
	 */
	public int loadDatas() throws Exception;

	
	/**
	 * 把对应的导入数据结果，生成并保存到对应的文件文档中
	 * 
	 * @param resultDatas
	 * @throws Exception
	 */
	public String createImportResultDocument(String[][] resultDatas)
			throws Exception;

	
}
