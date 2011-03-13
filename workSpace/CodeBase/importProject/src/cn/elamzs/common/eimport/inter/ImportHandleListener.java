package cn.elamzs.common.eimport.inter;

/**
 * ���������¼������ӿ�
 * 
 * @author Ethan.Lam 2011-2-14
 * 
 */
public interface ImportHandleListener {

	/**
	 * ���ݵ���ǰ
	 * @param importTaskSeqId
	 * @param fileAliasName
	 * @param srcFileLocation
	 */
	public void beforeImportData(final String importTaskSeqId,
			final String fileAliasName, final String srcFileLocation);

	
	/**
	 * ���ݵ�����ɺ�
	 * @param importTaskSeqId
	 * @param resultFileLocation
	 */
	public void afterImportData(final String importTaskSeqId,
			final String resultFileLocation);

}
