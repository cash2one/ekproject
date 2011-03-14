package cn.elamzs.common.eimport.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * �������������
 * �����������ִ�мƻ�����
 * ��ص���������߳������������Ӧ����־����
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
