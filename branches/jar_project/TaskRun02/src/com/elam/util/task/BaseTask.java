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

	Object locker = null; //������
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
		statusBean.setMaxOvertime(taskItem.getSeconds()*2*1000);//Ĭ������Ϊ������ʱ����
		statusBean.setName(taskItem.getName());
		loadAndRun();
	}

	
	/**
	 * ���ز���������
	 */
	void loadAndRun(){
		scheduler = Executors.newScheduledThreadPool(1);
		locker = new Object();//����������
		initialize();
		firstRunDelayTime();
		this.worked = true;
		startTask();
		TaskLog.info(taskItem.getName(), "�����������ز���������������С�");
	    statusBean.setLastExeTimestamp(0);
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
				if (worked){
					synchronized (locker) {
						try {
							statusBean.setRunTimes();
							TaskLog.info(taskItem.getName(), "��ʼִ������");
							statusBean.setLastExeTimestamp(System.currentTimeMillis());
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
				}		
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
		
		if((System.currentTimeMillis()-statusBean.getLastExeTimestamp())/1000>getTaskItem().getSeconds())
			return true;
		else if(maxOvertime>0&&(System.currentTimeMillis()-statusBean.getLastExeTimestamp())/1000>maxOvertime)
			return true;
		else
			return false;
	}
	
	
	/**
	 * �ж�����
	 */
    public void interrupt() throws Exception{
    	 this.worked = false;
    	 if(scheduler!=null){
	    	 scheduler.schedule(new Runnable() {
	    	      public void run() {
	    	    	  taskHandle.cancel(true);
	    	    	  TaskLog.info(taskItem.getName(), "�����ѱ��жϣ�");
	    	    	  release();
	    	      }
	    	    }, 30, SECONDS);
//    		  taskHandle.cancel(true);
//    		  if(taskHandle.isCancelled())
    		  scheduler.shutdown();
    		  release();
	    	  TaskLog.info(taskItem.getName(), "�����ѱ��жϣ�");
    	 }
    	 taskHandle = null;
    	 scheduler = null;
    }
    
    /**
     * ��������
     * @throws Exception 
     */
    public void reStart() throws Exception{
    	TaskLog.info(taskItem.getName(), "�������¼�����......");
    	interrupt(); //�жϵ�ǰ�����ִ��
    	statusBean.setRebootTimes();
    	loadAndRun(); //���¼�������
    }
  
    
    /**
     * �˳�����
     */
    public void exit()throws Exception{
    	worked = false;
    	if(taskHandle!=null)
    	    taskHandle.cancel(true);
    	taskHandle = null;
    	if(scheduler!=null)
    	scheduler.shutdownNow();
    	scheduler = null;
    	statusBean = null;
    	TaskLog.info(taskItem.getName(), "���˳�����");
    	taskItem = null;
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
