package cn.elamzs.common.eimport.inter;


/**
 * �����¼������ӿ�
 * @author Ethan.Lam  2011-2-14
 *
 */
public interface ImportHandleListener {

	
	public void beforeImportData(final String importTaskSeqId,final String fileAliasName,final  String srcFileLocation);
	
	public void afterImportData(final String importTaskSeqId,final String resultFileLocation);
	
	
}
