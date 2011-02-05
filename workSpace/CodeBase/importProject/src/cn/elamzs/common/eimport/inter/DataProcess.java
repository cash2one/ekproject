package cn.elamzs.common.eimport.inter;


/**
 * 
 * 定义了数据导入各个环节中 对应的  过程处理  的接口  
 * @author Ethan.Lam
 *
 */
public interface DataProcess {


	/**
	 * 
	 * 当每读取完文档中的一行数据后，
	 * 实现完成对该行数据将执行的操作，如把当前行数据插入到目标数据库
	 *                      
	 * @param data
	 */
	public void currentRowValueProcess(DataElement data);
	

	
	/**
	 * 
	 * 当执行完逐行数据加载后，调用 afterLoadRowsDataEvent 方法，
	 * 注释，在这个方法中，可对数据进行批处理
	 * 
	 */
	public void afterLoadAllRowsDataProcess();

	
    
	
}
