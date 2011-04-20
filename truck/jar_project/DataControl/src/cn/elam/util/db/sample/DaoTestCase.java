package cn.elam.util.db.sample;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.elam.util.db.impl.PersistWrapperDao;
import cn.elam.util.db.impl.QueryWrapperDao;
import cn.elam.util.db.inter.DataModel;
import cn.elam.util.db.inter.PersistAction;
import cn.elam.util.db.inter.QueryAction;

/**
 * 测试例子
 * 
 * @author Ethan.Lam 2011-2-27
 * 
 */
public class DaoTestCase {

	public static void main(String... args) throws Exception {
       
	    querySample();
		
		//持久化	
//		persistSample();
	}
	
	
	/**
	 * 持久化	
	 * @throws Exception
	 */
	public static void persistSample() throws Exception{
		
		TaskModel data = new TaskModel();
		data.setFileName(Math.floor(Math.random()*1000)+"_test.xls");
		data.setSrcPath("src/"+Math.floor(Math.random()*1000)+"_test.xls");
		data.setResultPath("res/"+Math.floor(Math.random()*1000)+"_test.xls");
		data.setStartTime("sysdate");
		data.setState(1);
		
		new PersistWrapperDao<TaskModel>("xxt").persist(new PersistAction<TaskModel>(){

			public Map<String, Object> persistParamValues(TaskModel data)
					throws Exception {
				// TODO Auto-generated method stub
				Map<String,Object> valueSet = new HashMap<String,Object>();
				valueSet.put("file_name", data.getFileName());
				valueSet.put("src_path",data.getSrcPath());
				valueSet.put("result_path",data.getResultPath());
				valueSet.put("state",data.getState());
				valueSet.put("start_time", new Date(System.currentTimeMillis()));
				valueSet.put("finish_time", new Date(System.currentTimeMillis()+(1000*20)));
				return valueSet;
			}
			
		}, data, "eimport_task_status");
		
		
	}
	
	
	
	//查询接口测试
	public static void querySample() throws Exception{
 		StringBuffer sql =new StringBuffer(" select handler_id,file_name,src_path,result_path,state,to_char(start_time,'yyyy-MM-dd hh:mm:ss') start_time,to_char(finish_time,'yyyy-MM-dd hh:mm:ss') finish_time from eimport_task_status ");
		List<TaskModel> list = new QueryWrapperDao<TaskModel>("xxt").list(new QueryAction<TaskModel>(){
			
			public TaskModel wrapperItem(ResultSet rs) throws Exception {
				// TODO Auto-generated method stub
				TaskModel model = new TaskModel();
				model.setHanderId(rs.getString("handler_id"));
				model.setFileName(rs.getString("file_name"));
				model.setSrcPath(rs.getString("src_path"));
				model.setResultPath(rs.getString("result_path"));
				model.setStartTime(rs.getString("start_time"));
				model.setState(rs.getInt("state"));
				model.setFinishedTime(rs.getString("finish_time"));
				return model;
			}
		}, sql.toString(), 1, 10);
		
		
		for(TaskModel task : list){
			System.out.println(task.getFileName()+","+task.getSrcPath()+","+task.getResultPath()+","+task.getState()+","+task.getStartTime()+","+task.getFinishedTime());
		}
	}

}

class TaskModel implements DataModel {

	private String handerId; // 导入时创建的ID 唯一
	private String fileName; // 导入的原文件名称
	private String srcPath; // 导入源文件路径
	private String resultPath; // 导入结果文件
	private String startTime; // 任务开始时间
	private String finishedTime; // 任务结束时间
	private int state; // 任务状态

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getHanderId() {
		return handerId;
	}

	public void setHanderId(String handerId) {
		this.handerId = handerId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public String getResultPath() {
		return resultPath;
	}

	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(String finishedTime) {
		this.finishedTime = finishedTime;
	}

}
