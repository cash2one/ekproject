package qtone.xxt.cache.service.exam;

import java.util.List;

import qtone.xxt.cache.model.exam.ImportVo;

/**
 * 
 * 新版成绩管理的 成绩导入
 * 
 * @author Ethan.Lam 2011-12-5
 * 
 */
public interface IScoreImportService {

	/**
	 * 
	 * 新增导入数据到临时空间
	 * 
	 * @param batchDatas
	 * 
	 */
	public String setDatas(List<ImportVo> batchDatas);

	/**
	 * 获取导入数据（临时数据）
	 * 
	 * @param batchSeq
	 * @return
	 */
	public List<ImportVo> getDatas(String generateBatchSeq);

	/**
	 * 
	 * 删除成绩导入的数据（临时数据）
	 * 
	 * @param generateBatchSeq
	 * 
	 */
	public void deleteData(String generateBatchSeq);

}
