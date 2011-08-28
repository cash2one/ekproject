package cn.elam.util.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import cn.elam.util.loger.AppLoger;
import cn.elam.util.task.inter.TaskLogger;

/**
 * 任务管理
 * @author ethanlam 
 */
public class TaskManager {

	TasksContainer container = null;

	String manageName = "管理器";
	int taskOverTimeCheckerSleepTime = 20;
	String configFile ="";
	
	
	/**
	 * 
	 * 方法：初始化任务容器 的基本运行环境
	 * 
	 * @param configFile  定时任务对象配置文件
	 * @throws Exception
	 *  
	 *    Add By Ethan Lam  At 2011-8-26
	 */
	public void initializeTaskContainer(String configFile) throws Exception {
		TaskLog.info(manageName,"initializeTaskContainer");
		if(null!=configFile&&!"".equals(configFile)&&(!new File(configFile).exists()||new File(configFile).isDirectory()))
			throw new FileNotFoundException("找不到配置文件["+configFile+"]，无法初始化 TaskManager！");
		else if(null==configFile||"".equals(configFile)){
		    container = new TasksContainer();
		}else{
			this.configFile = configFile;
			container = new TasksContainer(configFile);
		}
		setDaemonThreads();
	}
	
	
	/**
	 * 配置守护线程
	 */
	void setDaemonThreads(){
		
		//配置文件监视器
		ConfigWatchDog dog = new ConfigWatchDog();
		dog.setName("daemon_config_watchdog");//
		File configFileHander = new File(container.Config_File);
		dog.addFile(configFileHander);// 
		dog.start();// c
		
		
	    //任务状态监视器
		TaskOverTimeChecker checker = new TaskOverTimeChecker();
		checker.setName("daemon_TaskOverTimeChecker");
		checker.start();
		
	}
	
	
	/**
	 * 开始执行定时任务服务
	 */
	public void start() throws Exception {
		TaskLog.info(manageName,"执行管理器中的任务！");
		if (container != null)
			container.initialize();
	}

	
	/**
	 * 关闭所有任务
	 * @throws Exception 
	 */
	public void stop() throws Exception {
		TaskLog.info(manageName,"正在关闭任务管理器！");
		if (container != null){
			container.interruptAllTask();
		    container.close();
		}
	}

	
	/**
	 * 重启所有任务对象
	 */
	public void reboot() throws Exception{
		try {
			TaskLog.info(manageName,"将重启任务管理器！");
			Thread.sleep(1 * 1000);// 
			stop();
			container = null;
			if(null!=configFile&&!"".equals(configFile))
				 container = new TasksContainer(configFile);
			else
				 container = new TasksContainer();
			container.initialize();
		} catch (Exception ex) {
			TaskLog.error(manageName,"Reboot任务管理器失败！",ex);
		}
	}

	/**
	 * 订阅日志服务
	 * @param observer
	 */
	public void appendLogger(TaskLogger observer){
		if(observer!=null)
	 	    LoggerService.getService().addObserver(observer);
	}
	
	/**
	 * @author Ethan.Lam 2011-3-30
	 */
	private class ConfigWatchDog extends FileWatchdog {
		@Override
		protected void doOnChange(File file) {
			TaskLog.info(manageName,"配置文件" + file.getName() + "发生改变，重新加载任务管理器！");
			try {
				reboot();
			} catch (Exception e) {
				TaskLog.error(manageName, "重启失败！", e);
			}
		}
	}
	
	
	public static void main(String...args) throws Exception{
		TaskManager mgr = new TaskManager();
		mgr.initializeTaskContainer(null);
		mgr.appendLogger(new TaskLogger() {
			
			@Override
			public void error(String message, Throwable t) {
               AppLoger.RuningLoggerError(message, t);				
			}

			@Override
			public void info(String message) {
				// TODO Auto-generated method stub
//				System.out.println("我观察到一般消息：" + message);
				AppLoger.RuningLoggerInfo(message);
			}

		});
		mgr.start();
	}
  
	
	/**
	 * 检查任务装该
	 */
	void checkTaskRunStatus()throws Exception{
		//所有任务	
		if(container!=null){
		List<Task> allTasks = container.listCurrentTasks(0);
			if(allTasks!=null){
	            for(Task o:allTasks){
	            	if(o.isOverTime()){
		            	TaskLog.info(manageName,"发现任务执行超时："+ o.getState().getStateMessage());
		            	o.reStart(); //发现任务有超时情况就重启改任务
	            	}else{
	            		if(o.getState()!=null)
	            		  TaskLog.info(manageName,o.getState().getStateMessage());
	            	}
	            }
			}
		}
	}
	
	
	/**
	 * 检查是否有线程执行超时
	 * @author Ethan.Lam   2011-4-22
	 */
	class TaskOverTimeChecker extends Thread{
	    		
		public TaskOverTimeChecker(){
			this.setDaemon(true);
		}
		
		@Override
		public void run() {
			while(true){
				// TODO Auto-generated method stub
				try{
//					TaskLog.info(manageName,"守护线程【TaskOverTimeChecker】，开始执行。");
					checkTaskRunStatus();
					TaskLog.info(manageName,"守护线程【TaskOverTimeChecker】，执行结束并退出！");
				    Thread.sleep(taskOverTimeCheckerSleepTime*1000);
				}catch(Exception e){
					TaskLog.error(manageName,"守护线程【TaskOverTimeChecker】执行出错！", e);
				}
				
			}
		}
	}
	
}



