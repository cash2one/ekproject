package com.elam.util.task;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import cn.elam.util.bean.BeanUtil;
import cn.elam.util.common.Checker;
import cn.elam.util.common.Trans;
import cn.elam.util.file.xml.XmlHandler;

/**
 * 任务容器
 * 1、加载任务配置文件中的对象
 * 2、初始化任务，并设置周期地执行任务。
 * @author Ethan.Lam  2011-2-15
 *
 */
public class TasksContainer {

	List<Task> tasks = null;

	public String Config_File = "configs/TaskConfig.xml";
	
	public TasksContainer() {
	
	}

	public void initialize() {
		try{
			Document doc = XmlHandler.loadXML(Config_File);
			if (doc == null)
				return;
			
			tasks = new ArrayList<Task>();
			Element element = XmlHandler.getElement(doc, "global");
			List<Element> taskItems = XmlHandler.getElements(doc, "tasks/task");
			TaskItem taskItem = null;
			Task task = null;
			if (taskItems != null) {
				for (Element item : taskItems) {
					taskItem = new TaskItem();
					taskItem.setName(item.attributeValue("name"));
					taskItem.setTimeType(Checker.isNull(item
							.attributeValue("periodType")) ? "sec" : item
							.attributeValue("periodType"));
					taskItem.setSeconds(Checker.isNull(item
							.attributeValue("period")) ? 10 : Trans
							.StringToInt(item.attributeValue("period")));
					task = createTaskObject(item.getText().trim(), taskItem);
					if (task != null)
						tasks.add(task);
				}
			} else {
				TaskLog.info("管理器", "没有配置对应的任务！");
			}
		}catch(Exception e){
			TaskLog.error("管理器","任务容器对象初始化失败!", e);
		}
		
	}

	Task createTaskObject(String classPath, TaskItem item) {
		TaskLog.info("管理器", "正创建任务对象["+item.getName()+"]");
		Class clazz = com.elam.util.task.bean.BeanUtil.loadClass(classPath);
		if (clazz == null)
			return null;
		return (Task) com.elam.util.task.bean.BeanUtil.createInstance(clazz, item);
	}

	boolean isConfigChange() {
          return false;
	}
	
	void listCurrentTasks(){
		 for(Task task :this.tasks){
                     	 
         }	
	}
	
	
	/**
	 * 中断任务
	 */
	public void interruptAllTask(){
		TaskLog.info("管理器", "中断管理容器中的所有任务！");
		if(tasks!=null)
		for(Task task :this.tasks){
        	 if(task instanceof BaseTask){
        		 ((BaseTask) task).interrupt();
        	 }
        }
	}
	
	public static void main(String...s){
		TasksContainer container = new TasksContainer();
		container.initialize();
	}
}
