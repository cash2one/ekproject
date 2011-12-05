package qtone.xxt.cache.service.exam.imp;

import java.util.ArrayList;
import java.util.List;

import qtone.xxt.cache.base.BaseServiceImpl;
import qtone.xxt.cache.model.exam.ImportVo;
import qtone.xxt.cache.service.exam.IScoreImportService;

/**
 * 
 * 新版成绩管理的 成绩导入,数据缓存
 * 
 * @author Ethan.Lam 2011-12-5
 * 
 */
public class ScoreImportServiceImp extends BaseServiceImpl<ImportVo> implements
		IScoreImportService {

	//生成导入批号
	private static final String GLOBAL_BATCH_SEQ = "global:nextScoreBatchSeq";
	
	//为每个批号创建对应的 list集合存放导入数据
	private static final String BATCH_LIST_KEY_FORMAT = "scoreitem:batch:%s";
	
	//生成的导入批号
	private static final String BATCH_ID_LIST = "scoreitem:batch:ids";
	
	
	/**
	 * 
	 * 新增导入数据到临时空间
	 * @param batchDatas
	 * 
	 */
	public String setDatas(List<ImportVo> batchDatas) {
          //生成本次数据导入的批号
          long generateBatchSeq = super.incr(GLOBAL_BATCH_SEQ);
          String batchListKey = String.format(BATCH_LIST_KEY_FORMAT,generateBatchSeq); 
          //把带导入的成绩数据插入到对应的空间中
          for(ImportVo vo : batchDatas){
              super.addHeadList(batchListKey,vo);        	  
          }
          super.addTailList(BATCH_ID_LIST,generateBatchSeq+"");
          return batchListKey;
	}

	/**
	 * 获取导入数据（临时数据）
	 * @param batchSeq
	 * @return
	 */
	public List<ImportVo> getDatas(String generateBatchSeq){
		long dataRows = super.getListLength(generateBatchSeq.getBytes());
		int pageSize = 2000;
		int totalPages = 1;
		int page = 1;
		List<ImportVo> datas = super.loadObjListByPage(generateBatchSeq, page, pageSize);
		return datas;
	}
	
	
	/**
	 * 
	 * 删除成绩导入的数据（临时数据）
	 * @param generateBatchSeq
	 * 
	 */
	public void deleteData(String generateBatchSeq){
           		
	}

	
	public static void main(String...args){
	    IScoreImportService t = new ScoreImportServiceImp();
		List<ImportVo> batchDatas = new ArrayList<ImportVo>();
		ImportVo vo = new ImportVo();
		vo.setResult("数据合法！");
		vo.setScoreList(new String[]{"语文 100","数学 80","英语 80"});
		vo.setStuName("测试学生");
		vo.setStuSequence("111111");
		batchDatas.add(vo);
//		String seq = t.setDatas(batchDatas);
		String seq ="scoreitem:batch:2";
		System.out.println(seq);
		
		List<ImportVo> gds = t.getDatas(seq);
		for(ImportVo d :gds){
			System.out.print(d.getStuName()+" , ");
			System.out.print(d.getStuSequence()+" , ");
			for(String v:d.getScoreList())
			   System.out.print(v+" , ");
			System.out.print(d.getResult()+" ");
			System.out.println();
		}
		
	}
	
}
