package cn.elamzs.common.eimport.core;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.elam.util.db.comom.PageModel;
import cn.elam.util.db.impl.PersistWrapperDao;
import cn.elam.util.db.impl.QueryWrapperDao;
import cn.elam.util.db.inter.DataModel;
import cn.elam.util.db.inter.PersistAction;
import cn.elam.util.db.inter.QueryAction;
import cn.elamzs.common.eimport.config.ConfigSetting;
import cn.elamzs.common.eimport.item.TaskModel;


/**
 * 
 * @author Ethan.Lam 2011-2-23 
 * 任务信息持久化服务
 * 
 */
public class FileStorePersisService {

	private String TASKS_TABLE = "eimport_task_status";

	private PageModel pageBean;


	
	public FileStorePersisService() {

	}

	public PageModel getCurrentPageBean() {
		return pageBean;
	}

	/**
	 * 查询列表
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<TaskModel> query(Map<String,String> options,int currentPage, int pageSize)
			throws Exception {
		QueryWrapperDao<TaskModel> dao = new QueryWrapperDao<TaskModel>(ConfigSetting.PERSIST_POOL_NAME);
		StringBuffer sql =new StringBuffer(" select handler_id,proc_time,record_num,file_name,task_type,src_path,result_path,state,to_char(start_time,'yyyy-MM-dd hh:mm:ss') start_time ,to_char(finish_time,'yyyy-MM-dd hh:mm:ss') finish_time from " + TASKS_TABLE);
		sql.append(" where 1=1 ");
        if(options!=null){
        	if(options.containsKey("state"))
        		sql.append(" and state=").append(options.get("state"));
        } 		
		sql.append(" order by handler_id desc ");
		
		return dao.list(new QueryAction<TaskModel>() {
			
			@Override
			public TaskModel wrapperItem(ResultSet rs) throws Exception {
				TaskModel model = new TaskModel();
				model.setHanderId(rs.getString("handler_id"));
				model.setFileName(rs.getString("file_name"));
				model.setTaskType(rs.getString("task_type"));
				model.setSrcPath(rs.getString("src_path"));
				model.setResultPath(rs.getString("result_path"));
				model.setStartTime(rs.getString("start_time"));
				model.setState(rs.getInt("state"));
				model.setFinishedTime(rs.getString("finish_time"));
				model.setProcTime(rs.getInt("proc_time"));
				model.setRecordNum(rs.getInt("record_num"));
				return model;
			}
		}, sql.toString(), currentPage, pageSize);
	}
	
	
    /**
    * 查询导入的任务信息
    * @param taskHandlerId
    * @return
    * @throws Exception
    */
	public TaskModel getTaskInfo(String taskHandlerId) throws Exception{
		QueryWrapperDao<TaskModel> dao = new QueryWrapperDao<TaskModel>(ConfigSetting.PERSIST_POOL_NAME);
		String sql = " select handler_id,file_name,proc_time,record_num,src_path,task_type,result_path,state,to_char(start_time,'yyyy-MM-dd hh:mm:ss') start_time,to_char(finish_time,'yyyy-MM-dd hh:mm:ss') finish_time from " + TASKS_TABLE;
		sql +=" where handler_id = "+taskHandlerId;
		
		return dao.getItem(new QueryAction<TaskModel>(){

			@Override
			public TaskModel wrapperItem(ResultSet rs) throws Exception {
				// TODO Auto-generated method stub
				TaskModel model = new TaskModel();
				model.setHanderId(rs.getString("handler_id"));
				model.setFileName(rs.getString("file_name"));
				model.setTaskType(rs.getString("task_type"));
				model.setSrcPath(rs.getString("src_path"));
				model.setResultPath(rs.getString("result_path"));
				model.setStartTime(rs.getString("start_time"));
				model.setState(rs.getInt("state"));
				model.setFinishedTime(rs.getString("finish_time"));
				model.setProcTime(rs.getInt("proc_time"));
				model.setRecordNum(rs.getInt("record_num"));
				return model;
			}}, sql);
		
	}
	
   /**
    * 保存任务信息	
    * @param task
    * @throws Exception 
    */
	public void crateTask(TaskModel task) throws Exception{
		PersistWrapperDao<TaskModel> dao = new PersistWrapperDao<TaskModel>(ConfigSetting.PERSIST_POOL_NAME);
		String insertSql = "insert into "+TASKS_TABLE+"(handler_id,file_name,src_path,task_type,state,start_time)values(?,?,?,?,?,sysdate)";
		dao.persist(insertSql, task.getHanderId(),task.getFileName(),task.getSrcPath(),task.getTaskType(),task.getState());
	}
	
	
	/**
	 * 更新导入任务信息
	 * @param task
	 * @param type   
	 * @throws Exception
	 */
	public void updateTask(TaskModel task,UPADTE_OPERATION operateType) throws Exception{
		PersistWrapperDao dao = new PersistWrapperDao(ConfigSetting.PERSIST_POOL_NAME);
		int newState = 0;
		String updateSql = " update "+TASKS_TABLE+" set state = ?,result_path=?,finish_time=sysdate,record_num=?,proc_time=? where handler_id = ? ";
		switch(operateType.type){
		    case 1:dao.persist(updateSql,task.getState(),task.getResultPath(),task.getRecordNum(),task.getProcTime(),task.getHanderId());break; //完成任务
		}
	}
	
	
	/**
	 * 更新操作选项
	 * @author Ethan.Lam   2011-3-1
	 *
	 */
	enum UPADTE_OPERATION{
		
		IMP_FINISH_STATE(1,"导入完成时");
		
		private int type;
	    private String mean;
		
		UPADTE_OPERATION(int type,String mean){
		   this.type = type;
		   this.mean = mean;
		}
		
		public int type(){
			return this.type;
		}
 
		public String mean(){
			return this.mean;
		}
		
	}
	
    public static void main(String...art) throws Exception{
    	try{
    	FileStorePersisService ser = new FileStorePersisService();
        ser.query(null, 1, 100);   	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	}
	
}
