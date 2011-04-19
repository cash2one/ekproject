package com.elam.util.task;

import java.io.File;

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
	public void reBoot() {
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
	 * @author Ethan.Lam 2011-3-30
	 */
	private class ConfigWatchDog extends FileWatchdog {
		@Override
		protected void doOnChange(File file) {
			TaskLog.info("������","�����ļ�" + file.getName() + "�����ı䣬���¼��������������");
			reBoot();
		}
	}
	
	public static void main(String...args){
		TaskManager mgr = new TaskManager();
		mgr.initializeTaskContainer();
		mgr.start();
	}

}
