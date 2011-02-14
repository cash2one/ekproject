package cn.elamzs.common.eimport.inter;


/**
 * 导入事件监听接口
 * @author Ethan.Lam  2011-2-14
 *
 */
public interface ImportHandleListener {

	
	public void beforeImportData(String importTaskSeqId,String fileAliasName,String srcFileLocation);
	
	public void afterImportData(String importTaskSeqId,String resultFileLocation);
	
	
}
