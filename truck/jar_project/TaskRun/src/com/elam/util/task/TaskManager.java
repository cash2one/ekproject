package com.elam.util.task;

import java.io.File;

import com.elam.util.task.inter.TaskLogger;

/**
 * 
 * @author ethanlam 任务管理
 */
public class TaskManager {

	TasksContainer container = null;

	/**
	 * 初始化任务容器 加载任务对象
	 */
	void initializeTaskContainer() {
		TaskLog.info("管理器","initializeTaskContainer");
		container = new TasksContainer();
		ConfigWatchDog dog = new ConfigWatchDog();
		dog.setName("daemon_demo_config_watchdog");//
		File configFile = new File(container.Config_File);
		dog.addFile(configFile);// b
		dog.start();// c
	}

	/**
	 * 
	 */
	public void start() {
		TaskLog.info("管理器","执行管理器中的任务！");
		if (container != null)
			container.initialize();
	}

	/**
	 * 关闭所有任务
	 */
	public void stop() {
		TaskLog.info("管理器","正在关闭任务管理器！");
		if (container != null)
			container.interruptAllTask();
	}

	/**
	 * 重启所有任务对象
	 */
	public void reboot() {
		try {
			TaskLog.info("管理器","将重启任务管理器！");
			Thread.sleep(1 * 1000);// 
			stop();
			if (container == null){
				container = new TasksContainer();
			}
			container.initialize();
		} catch (Exception ex) {
			TaskLog.error("管理器","Reboot任务管理器失败！",ex);
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
			TaskLog.info("管理器","配置文件" + file.getName() + "发生改变，重新加载任务管理器！");
			reboot();
		}
	}
	
	public static void main(String...args){
		TaskManager mgr = new TaskManager();
		mgr.initializeTaskContainer();
		mgr.appendLogger(new TaskLogger() {
			
			@Override
			public void error(String message, Throwable t) {
				System.out.println("我观察到抛出错误信息：" + message);
			}

			@Override
			public void info(String message) {
				// TODO Auto-generated method stub
				System.out.println("我观察到一般消息：" + message);
			}

		});
		mgr.start();
	}

}
