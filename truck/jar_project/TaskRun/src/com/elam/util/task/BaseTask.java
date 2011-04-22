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
	long firstRunTime = 0; // ��������һ�α�ִ�е�ʱ������ʱ
	long maxOvertime = 0 ; // �̳߳�ʱ
	ScheduledExecutorService scheduler = null;
	TaskItem taskItem = null;
	ScheduledFuture taskHandle = null;
	TaskStatus statusBean = new TaskStatus();
	
	public BaseTask(TaskItem taskItem) {
		this.taskItem = taskItem;
		TaskLog.info(taskItem.getName(), "�����ʼ�����ء�");
		statusBean.setMaxOvertime(maxOvertime);
		statusBean.setName(taskItem.getName());
		loadAndRun();
	}

	
	/**
	 * ���ز���������
	 */
	void loadAndRun(){
		scheduler = Executors.newScheduledThreadPool(1);
		initialize();
		firstRunDelayTime();
		startTask();
		TaskLog.info(taskItem.getName(), "�����������ز���������������С�");
	}
	
	
   /**
    * �״����е���ʱʱ��
    */
	void firstRunDelayTime() {
		this.firstRunTime = 10;
	}

	/**
	 * ��������
	 */
	void startTask() {
		//��������
		Runnable task = new Runnable() {
			public void run() {
				if (worked)
					synchronized (locker) {
						try {
							statusBean.setRunTimes();
							statusBean.setLastExeTimestamp(System.currentTimeMillis());
							TaskLog.info(taskItem.getName(), "��ʼִ������");
							task();
							statusBean.setExecuteTime(System.currentTimeMillis() - statusBean.getLastExeTimestamp());
							TaskLog.info(taskItem.getName(), "�˳�����,�����ܺ�ʱ:"+(statusBean.getExecuteTime()/1000)+"s��");
//							TaskLog.info(taskItem.getName(),statusBean.getStateMessage());
						} catch (Exception e) {
							statusBean.setErrorTimes();
							TaskLog.error(taskItem.getName(), e.getMessage(), e);
						} finally {

						}
					}
				;
			}
		};
		
		
      //���ڵ�ִ������
	  taskHandle = scheduler.scheduleAtFixedRate(task,
				(firstRunTime >= 0 ? firstRunTime : 60), taskItem.getSeconds()*taskItem.getMinTimeAccuMethod(),
				SECONDS);

	}

	/**
	 * 
	 * �ж������Ƿ�ʱ�����������ʱ������Ҫ�����������̣߳��˷�������ѯʹ��
	 * @return
	 */
	public boolean isOverTime(){
		if(statusBean.getLastExeTimestamp()==0)
		    return false;
		
		if((System.currentTimeMillis()-statusBean.getLastExeTimestamp())/1000>getTaskItem().getSeconds()*2)
			return true;
		else if(maxOvertime>0&&(System.currentTimeMillis()-statusBean.getLastExeTimestamp())/1000>this.maxOvertime)
			return true;
		else
			return false;
	}
	
	
	/**
	 * �ж�����
	 */
    public void interrupt(){
    	 this.worked = false;
    	 if(scheduler!=null){
	    	 scheduler.schedule(new Runnable() {
	    	      public void run() {
	    	    	  taskHandle.cancel(true);
	    	    	  release();
	    	      }
	    	    }, 30, SECONDS);
	    	 scheduler.shutdown();
    	 }
    	 scheduler = null;
    }
    
    /**
     * ��������
     */
    public void reStart(){
    	TaskLog.info(taskItem.getName(), "�������¼�����......");
    	this.worked = true;
    	interrupt(); //�жϵ�ǰ�����ִ��
    	loadAndRun(); //���¼�������
    }
  
    /**
     * ��ȡ����������ã�����
     * @return
     */
    public TaskItem getTaskItem() {
		return taskItem;
	}

    
    /**
     * ����״̬����
     */
	public TaskStatus getState() {
		return this.statusBean;
	}
    
    /**
     * �����ʼ�����������״γ�ʼ��ʱ�����ã�����ѭ���ظ�ִ���ڼ� ���ٱ� ���ô˷�����
     * 
     * 
     */
	protected abstract void initialize();

	
	/**
	 * ʵ������ҵ���߼�
	 */
	protected abstract void task();

	
	/**
	 * 
	 * �����˳����������˳������������ʱ �����ã�����ѭ���ظ�ִ���ڼ�   �����ظ����ô˷�����
	 * 
	 */
	protected abstract void release();

}
