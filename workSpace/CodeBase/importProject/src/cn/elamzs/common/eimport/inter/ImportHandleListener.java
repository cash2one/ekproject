package cn.elamzs.common.eimport.inter;


/**
 * �����¼������ӿ�
 * @author Ethan.Lam  2011-2-14
 *
 */
public interface ImportHandleListener {

	
	public void beforeImportData(String importTaskSeqId,String fileAliasName,String srcFileLocation);
	
	public void afterImportData(String importTaskSeqId,String resultFileLocation);
	
	
}
