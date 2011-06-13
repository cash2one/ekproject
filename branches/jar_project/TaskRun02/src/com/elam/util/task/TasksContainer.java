package com.elam.util.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import cn.elam.util.common.Checker;
import cn.elam.util.common.Trans;
import cn.elam.util.file.xml.XmlHandler;

/**
 * ��������
 * 1���������������ļ��еĶ���
 * 2����ʼ�����񣬲��������ڵ�ִ������
 * @author Ethan.Lam  2011-2-15
 *
 */
class TasksContainer {

	List<Task> tasks = null;

	public String Config_File = "configs/TaskConfig.xml";
	
	public TasksContainer() {
	
	}

	public TasksContainer(String configFile) throws Exception {
		this.Config_File = configFile!=null&&!"".equals(configFile)?configFile:this.Config_File;
		if(!new File(configFile).exists()||new File(configFile).isDirectory())
			throw new Exception("�޷��ҵ������ļ�["+configFile+"],����ʧ�ܡ�");
	}
	
	/**
	 * ��������ʼ��
	 */
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
				TaskLog.info("������", "û�����ö�Ӧ������");
			}
		}catch(Exception e){
			TaskLog.error("������","�������������ʼ��ʧ��!", e);
		}
		
	}

	/**
	 * ��������������
	 * @param classPath
	 * @param item
	 * @return
	 */
	Task createTaskObject(String classPath, TaskItem item) {
		TaskLog.info("������", "�������������["+item.getName()+"]");
		Class clazz = com.elam.util.task.bean.BeanUtil.loadClass(classPath);
		if (clazz == null)
			return null;
		return (Task) com.elam.util.task.bean.BeanUtil.createInstance(clazz, item);
	}

	
	boolean isConfigChange() {
          return false;
	}
	
	/**
	 * 
	 * �г���Ӧ״̬���߳�
	 * @param state �� 0�����������̣߳�1��δ������ʱ������ ��2����ʱ����
	 * @return
	 */
	public List<Task> listCurrentTasks(int state){
		 List<Task> ctasks = new ArrayList<Task>();
		 if(tasks!=null)
		 for(Task task :tasks){
             if(state==2&&task.isOverTime())//����ʱ��
            	 ctasks.add(task);
             else if(state==1&&!task.isOverTime()) //δ������ʱ������
            	 ctasks.add(task);
             else 
            	 ctasks.add(task);
         }	
		 return ctasks;
	}
	
	/**
	 * �ر����������
	 */
	public void close()throws Exception{
		if(tasks!=null)
		for(Task task :this.tasks){
        	 task.exit();
        }
		tasks.clear();
		tasks = null;
		TaskLog.info("������", "�ѹر������������");
	}
	
	/**
	 * �ж�����
	 */
	public void interruptAllTask()throws Exception{
		TaskLog.info("������", "�жϹ��������е���������");
		if(tasks!=null)
		for(Task task :this.tasks){
        	 task.interrupt();
        }
	}
	
	
	
	
	public static void main(String...s){
		TasksContainer container = new TasksContainer();
		container.initialize();
	}
}
