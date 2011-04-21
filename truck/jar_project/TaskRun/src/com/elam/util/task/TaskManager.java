package com.elam.util.task;

import java.io.File;

import com.elam.util.task.inter.TaskLogger;

/**
 * 
 * @author ethanlam �������
 */
public class TaskManager {

	TasksContainer container = null;

	/**
	 * ��ʼ���������� �����������
	 */
	void initializeTaskContainer() {
		TaskLog.info("������","initializeTaskContainer");
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
		TaskLog.info("������","ִ�й������е�����");
		if (container != null)
			container.initialize();
	}

	/**
	 * �ر���������
	 */
	public void stop() {
		TaskLog.info("������","���ڹر������������");
		if (container != null)
			container.interruptAllTask();
	}

	/**
	 * ���������������
	 */
	public void reboot() {
		try {
			TaskLog.info("������","�����������������");
			Thread.sleep(1 * 1000);// 
			stop();
			if (container == null){
				container = new TasksContainer();
			}
			container.initialize();
		} catch (Exception ex) {
			TaskLog.error("������","Reboot���������ʧ�ܣ�",ex);
		}
	}

	/**
	 * ������־����
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
			TaskLog.info("������","�����ļ�" + file.getName() + "�����ı䣬���¼��������������");
			reboot();
		}
	}
	
	public static void main(String...args){
		TaskManager mgr = new TaskManager();
		mgr.initializeTaskContainer();
		mgr.appendLogger(new TaskLogger() {
			
			@Override
			public void error(String message, Throwable t) {
				System.out.println("�ҹ۲쵽�׳�������Ϣ��" + message);
			}

			@Override
			public void info(String message) {
				// TODO Auto-generated method stub
				System.out.println("�ҹ۲쵽һ����Ϣ��" + message);
			}

		});
		mgr.start();
	}

}
