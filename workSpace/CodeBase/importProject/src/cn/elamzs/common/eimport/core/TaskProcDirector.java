package cn.elamzs.common.eimport.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * 导入任务管理器
 * 管理导入任务的执行计划安排
 * 监控导入任务的线程情况，并做对应的日志处理
 * @author Ethan.Lam   2011-3-8
 *
 */
public final class TaskProcDirector {

	   private static TaskProcDirector container = new TaskProcDirector();
	  
	   private Map<String,List<Runnable>> taskSet = new HashMap<String,List<Runnable>>();  
	   
	   public void createNewThreadTask(String type,Runnable threader){
           if(!taskSet.containsKey(type)){    
		     taskSet.put(type, new ArrayList<Runnable>());   
           } 
           taskSet.get(type).add(threader);   
	   }
	 
	
	
	
	
	
}
