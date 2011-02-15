package cn.elamzs.common.eimport.inter;


/**
 * 导入事件监听接口
 * @author Ethan.Lam  2011-2-14
 *
 */
public interface ImportHandleListener {

	
	public void beforeImportData(final String importTaskSeqId,final String fileAliasName,final  String srcFileLocation);
	
	public void afterImportData(final String importTaskSeqId,final String resultFileLocation);
	
	
}
