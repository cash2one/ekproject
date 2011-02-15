package com.elam.util.task;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * ���������
 * @author ethanlam ����
 * 
 */
public abstract class BaseTask implements Task {

	Object locker = new Object();
	boolean worked = true;
	int runTimes = 0;
	int errorTimes = 0;
	long firstRunTime = 0; // ��������һ�α�ִ�е�ʱ������ʱ
	ScheduledExecutorService scheduler = null;
	TaskItem taskItem = null;
	ScheduledFuture taskHandle = null;
	
	public BaseTask(TaskItem taskItem) {
		this.taskItem = taskItem;
		TaskLog.info(taskItem.getName(), "�����ʼ�����ء�");
		loadAndRun();
	}

	void loadAndRun(){
		scheduler = Executors.newScheduledThreadPool(1);
		initialize();
		firstRunDelayTime();
		startTask();
		TaskLog.info(taskItem.getName(), "�����������ز���������������С�");
	}
	
	void firstRunDelayTime() {
		this.firstRunTime = 10;
	}

	/**
	 * ��������
	 */
	void startTask() {
		Runnable task = new Runnable() {
			public void run() {
				if (worked)
					synchronized (locker) {
						try {
							runTimes++;
							TaskLog.info(taskItem.getName(), "��ʼִ������");
							task();
							TaskLog.info(taskItem.getName(), "�˳�����");
						} catch (Exception e) {
							errorTimes++;
							TaskLog.error(taskItem.getName(), e.getMessage(), e
									.getMessage());
							e.printStackTrace();
						} finally {

						}
					}
				;
			}
		};
	  taskHandle = scheduler.scheduleAtFixedRate(task,
				(firstRunTime >= 0 ? firstRunTime : 60), taskItem.getSeconds(),
				SECONDS);

	}

	/**
	 * �ж�����
	 */
    public void interrupt(){
    	 this.worked = false;
    	 scheduler.schedule(new Runnable() {
    	      public void run() {
    	    	  taskHandle.cancel(true);
    	    	  release();
    	      }
    	    }, 30, SECONDS);
    	 scheduler.shutdown();
    	 scheduler = null;
    }
    
    /**
     * ��������
     */
    public void reStart(){
    	TaskLog.info(taskItem.getName(), "�������¼�����......");
    	this.worked = true;
    	loadAndRun();
    }
  
    public TaskItem getTaskItem() {
		return taskItem;
	}

    /**
     * �����ʼ��
     */
	protected abstract void initialize();

	/**
	 * ʵ������ҵ���߼�
	 */
	protected abstract void task();

	/**
	 * �����˳�ʱ��Դ�ͷ�
	 */
	protected abstract void release();

}
