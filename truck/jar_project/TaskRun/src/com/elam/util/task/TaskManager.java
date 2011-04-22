package com.elam.util.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import com.elam.util.task.inter.TaskLogger;

/**
 * �������
 * @author ethanlam 
 */
public class TaskManager {

	TasksContainer container = null;

	String manageName = "������";
	int taskOverTimeCheckerSleepTime = 10;
	
	/**
	 * ��ʼ���������� �����������
	 * @throws Exception 
	 */
	void initializeTaskContainer(String configFile) throws Exception {
		TaskLog.info(manageName,"initializeTaskContainer");
		if(null!=configFile&&!"".equals(configFile)&&(!new File(configFile).exists()||new File(configFile).isDirectory()))
			throw new FileNotFoundException("�Ҳ��������ļ�["+configFile+"]���޷���ʼ�� TaskManager��");
		else if(null==configFile||"".equals(configFile)){
		    container = new TasksContainer();
		}else
			container = new TasksContainer(configFile);
		
		setDaemonThreads();
	}
	
	
	/**
	 * �����ػ��߳�
	 */
	void setDaemonThreads(){
		//�����ļ�������
		ConfigWatchDog dog = new ConfigWatchDog();
		dog.setName("daemon_config_watchdog");//
		File configFileHander = new File(container.Config_File);
		dog.addFile(configFileHander);// 
		dog.start();// c
	
		
	    //����״̬������
		TaskOverTimeChecker checker = new TaskOverTimeChecker();
		checker.setName("daemon_TaskOverTimeChecker");
		checker.start();
		
	}
	
	
	/**
	 * 
	 */
	public void start() {
		TaskLog.info(manageName,"ִ�й������е�����");
		if (container != null)
			container.initialize();
	}

	/**
	 * �ر���������
	 */
	public void stop() {
		TaskLog.info(manageName,"���ڹر������������");
		if (container != null)
			container.interruptAllTask();
	}

	/**
	 * ���������������
	 */
	public void reboot() {
		try {
			TaskLog.info(manageName,"�����������������");
			Thread.sleep(1 * 1000);// 
			stop();
			if (container == null){
				container = new TasksContainer();
			}
			container.initialize();
		} catch (Exception ex) {
			TaskLog.error(manageName,"Reboot���������ʧ�ܣ�",ex);
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
			TaskLog.info(manageName,"�����ļ�" + file.getName() + "�����ı䣬���¼��������������");
			reboot();
		}
	}
	
	
	public static void main(String...args) throws Exception{
		TaskManager mgr = new TaskManager();
		mgr.initializeTaskContainer(null);
		mgr.appendLogger(new TaskLogger() {
			
			@Override
			public void error(String message, Throwable t) {
//				System.out.println("�ҹ۲쵽�׳�������Ϣ��" + message);
			}

			@Override
			public void info(String message) {
				// TODO Auto-generated method stub
//				System.out.println("�ҹ۲쵽һ����Ϣ��" + message);
			}

		});
		mgr.start();
	}
  
	
	/**
	 * �������װ��
	 */
	void checkTaskRunStatus()throws Exception{
		//��������	
		List<Task> allTasks = container.listCurrentTasks(0);
		if(allTasks!=null){
            for(Task o:allTasks){
            	if(o.isOverTime()){
	            	TaskLog.info(manageName,"��������ִ�г�ʱ��"+ o.getState().getStateMessage());
	            	o.reStart(); //���������г�ʱ���������������
            	}else{
            		if(o.getState()!=null)
            		  TaskLog.info(manageName,o.getState().getStateMessage());
            	}
            }
		}
	}
	
	
	/**
	 * ����Ƿ����߳�ִ�г�ʱ
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
					TaskLog.info(manageName,"�ػ��̡߳�TaskOverTimeChecker������ʼִ�С�");
					checkTaskRunStatus();
					TaskLog.info(manageName,"�ػ��̡߳�TaskOverTimeChecker����ִ�н������˳���");
				    Thread.sleep(taskOverTimeCheckerSleepTime*1000);
				}catch(Exception e){
					TaskLog.error(manageName,"�ػ��̡߳�TaskOverTimeChecker��ִ�г���", e);
				}
				
			}
		}
	}
	
}



