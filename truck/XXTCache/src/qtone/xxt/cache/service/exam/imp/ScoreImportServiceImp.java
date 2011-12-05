package qtone.xxt.cache.service.exam.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private static final String BATCH_LIST_KEY_FORMAT = "scorebatch:%s";

	//导入操作对应的 其他参数
    private static final String PARAMS_HASHSET = "scorebatch:params:%s";

    //记录已经导入完成的批处理号
    private static final String BATCH_DONE_SET = "scorebatch:done:ids";

    //记录待执行导入的批处理号
	private static final String BATCH_ID_LIST = "scorebatch:wait:ids";
	
	/**
	 * 
	 * 新增导入数据到临时空间
	 * @param batchDatas
	 * @param examId   成绩对应的考试ID
	 * @param classId  对应的班级ID
	 * @param areaAbb  地区信息
	 * @param fileTime
	 * 
	 */
	public String setDatas(List<ImportVo> batchDatas,long examId,long classId,String areaAbb,String fileTime) {
          //生成本次数据导入的批号
          long generateBatchSeq = super.incr(GLOBAL_BATCH_SEQ);
          String batchListKey = String.format(BATCH_LIST_KEY_FORMAT,generateBatchSeq); 
          //把带导入的成绩数据插入到对应的空间中
          for(ImportVo vo : batchDatas){
              super.addHeadList(batchListKey,vo);        	  
          }
          super.addTailList(BATCH_ID_LIST,generateBatchSeq+"");
          
          //把其他参数存储到对应hashMap上
          String hashKey = String.format(PARAMS_HASHSET,generateBatchSeq); 
          super.hashSet(hashKey,"examId",examId+"");
          super.hashSet(hashKey,"classId",classId+"");
          super.hashSet(hashKey,"areaAbb",areaAbb);
          super.hashSet(hashKey,"fileTime",fileTime);
          
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
	 * 获取对应的导入操作对应的参数
	 * @param generateBatchSeq
	 * @return
	 * 
	 */
	public Map<String, Object> getParams(String generateBatchSeq) {
		// TODO Auto-generated method stub
	      generateBatchSeq = generateBatchSeq.substring(generateBatchSeq.lastIndexOf(":")+1, generateBatchSeq.length());
		  String hashKey = String.format(PARAMS_HASHSET,generateBatchSeq); 
		  Map<String,String> hash = super.gHashValStrs(hashKey);
		  Map<String, Object> values = new HashMap<String,Object>();
		  if(hash!=null){
			  values.put("examId", Integer.parseInt(hash.get("examId")));
			  values.put("classId",Integer.parseInt(hash.get("classId")));
			  values.put("areaAbb", hash.get("areaAbb"));
			  values.put("fileTime",hash.get("fileTime"));
		  }
		  return values;
	}
	
	/**
	 * 
	 * 删除成绩导入的数据（临时数据）
	 * @param generateBatchSeq
	 * 
	 */
	public void deleteData(String generateBatchSeq){
           		
	}


	/**
	 * 返回下一个待处理的批号
	 * @return
	 */
	public String getNextSeq() {
		// TODO Auto-generated method stub
		String nextSeq =  super.pop(BATCH_ID_LIST,true);
		//把已经获取的 存放到已处理的集合中
		if(nextSeq!=null)
	     	super.addTailList(BATCH_DONE_SET, nextSeq);
		return nextSeq;
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
		String seq = t.setDatas(batchDatas, 1,1,"11","11");
//		String seq ="scoreitem:batch:2";
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
		Map<String,Object> params = t.getParams(seq);
		for(String key :params.keySet())
		  System.out.println("<"+key+":"+params.get(key)+">");
		
	}


	
	
}
