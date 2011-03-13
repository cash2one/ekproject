package cn.elamzs.common.eimport.inter;

/**
 * 导入任务事件监听接口
 * 
 * @author Ethan.Lam 2011-2-14
 * 
 */
public interface ImportHandleListener {

	/**
	 * 数据导入前
	 * @param importTaskSeqId
	 * @param fileAliasName
	 * @param srcFileLocation
	 */
	public void beforeImportData(final String importTaskSeqId,
			final String fileAliasName, final String srcFileLocation);

	
	/**
	 * 数据导入完成后
	 * @param importTaskSeqId
	 * @param resultFileLocation
	 */
	public void afterImportData(final String importTaskSeqId,
			final String resultFileLocation);

}
