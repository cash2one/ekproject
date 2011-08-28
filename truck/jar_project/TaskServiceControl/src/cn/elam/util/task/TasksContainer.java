package cn.elam.util.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import cn.elam.util.common.Checker;
import cn.elam.util.common.Trans;
import cn.elam.util.common.XmlHandler;

/**
 * 任务容器
 * 1、加载任务配置文件中的对象
 * 2、初始化任务，并设置周期地执行任务。
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
			throw new Exception("无法找到配置文件["+configFile+"],加载失败。");
	}
	
	/**
	 * 任务对象初始化
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
				TaskLog.info("管理器", "没有配置对应的任务！");
			}
		}catch(Exception e){
			TaskLog.error("管理器","任务容器对象初始化失败!", e);
		}
		
	}

	/**
	 * 创建并启动任务
	 * @param classPath
	 * @param item
	 * @return
	 */
	Task createTaskObject(String classPath, TaskItem item) {
		TaskLog.info("管理器", "正创建任务对象["+item.getName()+"]");
		Class clazz = cn.elam.util.task.bean.BeanUtil.loadClass(classPath);
		if (clazz == null)
			return null;
		return (Task) cn.elam.util.task.bean.BeanUtil.createInstance(clazz, item);
	}

	
	boolean isConfigChange() {
          return false;
	}
	
	/**
	 * 
	 * 列出对应状态的线程
	 * @param state ： 0：所有任务线程；1：未发生超时的任务 ；2：超时任务
	 * @return
	 */
	public List<Task> listCurrentTasks(int state){
		 List<Task> ctasks = new ArrayList<Task>();
		 if(tasks!=null)
		 for(Task task :tasks){
             if(state==2&&task.isOverTime())//任务超时的
            	 ctasks.add(task);
             else if(state==1&&!task.isOverTime()) //未发生超时的任务
            	 ctasks.add(task);
             else 
            	 ctasks.add(task);
         }	
		 return ctasks;
	}
	
	/**
	 * 关闭任务管理器
	 */
	public void close()throws Exception{
		if(tasks!=null)
		for(Task task :this.tasks){
        	 task.exit();
        }
		tasks.clear();
		tasks = null;
		TaskLog.info("管理器", "已关闭任务管理器！");
	}
	
	/**
	 * 中断任务
	 */
	public void interruptAllTask()throws Exception{
		TaskLog.info("管理器", "中断管理容器中的所有任务！");
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
