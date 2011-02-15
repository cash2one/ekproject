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
 * @author Ethan.Lam  2011-2-15
 *
 */
public class TasksContainer {

	List<Task> tasks = null;

	public TasksContainer() {
		initialize();
	}

	void initialize() {
		Document doc = XmlHandler.loadXML("configs/TaskConfig.xml");
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
	}

	Task createTaskObject(String classPath, TaskItem item) {
		Class clazz = BeanUtil.loadClass(classPath);
		if (clazz == null)
			return null;
		return (Task) BeanUtil.createInstance(clazz, item);
	}

	boolean isConfigChange() {
          return false;
	}
	
	void listCurrentTasks(){
         for(Task task :this.tasks){
                     	 
         }	
	}
	
	public static void main(String...s){
		TasksContainer container = new TasksContainer();
	}
}
