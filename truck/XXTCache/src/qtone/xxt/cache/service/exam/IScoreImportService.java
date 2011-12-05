package qtone.xxt.cache.service.exam;

import java.util.List;
import java.util.Map;

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
	public String setDatas(List<ImportVo> batchDatas,long examId,long classId,String areaAbb,String fileTime);

	/**
	 * 获取导入数据（临时数据）
	 * 
	 * @param batchSeq
	 * @return
	 */
	public List<ImportVo> getDatas(String generateBatchSeq);

	
	/**
	 * 
	 * 获取对应的导入操作对应的参数
	 * @param generateBatchSeq
	 * @return
	 */
	public Map<String,Object> getParams(String generateBatchSeq);
	
	
	/**
	 * 删除成绩导入的数据（临时数据）
	 * @param generateBatchSeq
	 */
	public void deleteData(String generateBatchSeq);

	/**
	 * 返回下一个待处理的批号
	 * @return
	 */
	public String getNextSeq();
	
}
