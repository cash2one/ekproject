package cn.elamzs.common.eimport.core;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import cn.elam.util.db.comom.PageModel;
import cn.elam.util.db.impl.QueryWrapperDao;
import cn.elam.util.db.inter.DataModel;
import cn.elam.util.db.inter.QueryAction;
import cn.elamzs.common.eimport.item.TaskModel;


/**
 * 
 * @author Ethan.Lam 2011-2-23 保存文件信息服务
 * 
 */
public class FileStorePersisService {

	private String POOL_NAME = "impDB";

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
		QueryWrapperDao dao = new QueryWrapperDao(POOL_NAME);
		StringBuffer sql =new StringBuffer(" select handler_id,file_name,src_path,result_path,state,to_char(start_time,'yyyy-MM-dd hh:mmLss'),to_char(finish_time,'yyyy-MM-dd hh:mmLss') from " + TASKS_TABLE);
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
				model.setHanderId(rs.getInt("handler_id"));
				model.setFileName("file_name");
				model.setSrcPath(rs.getString("src_path"));
				model.setResultPath(rs.getString("result_path"));
				model.setStartTime(rs.getString("start_time"));
				model.setState(rs.getInt("state"));
				model.setFinishedTime(rs.getString("finish_time"));
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
		QueryWrapperDao dao = new QueryWrapperDao(POOL_NAME);
		String sql = " select handler_id,file_name,src_path,result_path,state,to_char(start_time,'yyyy-MM-dd hh:mmLss'),to_char(finish_time,'yyyy-MM-dd hh:mmLss') from " + TASKS_TABLE;
		sql +=" where handler_id = "+taskHandlerId;
		
		return (TaskModel) dao.getItem(new QueryAction<TaskModel>(){

			@Override
			public TaskModel wrapperItem(ResultSet rs) throws Exception {
				// TODO Auto-generated method stub
				TaskModel model = new TaskModel();
				model.setHanderId(rs.getInt("handler_id"));
				model.setFileName("file_name");
				model.setSrcPath(rs.getString("src_path"));
				model.setResultPath(rs.getString("result_path"));
				model.setStartTime(rs.getString("start_time"));
				model.setState(rs.getInt("state"));
				model.setFinishedTime(rs.getString("finish_time"));
				return model;
			}}, sql);
		
	}

}
