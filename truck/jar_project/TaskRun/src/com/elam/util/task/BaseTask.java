package com.elam.util.task;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * 任务抽象类
 * @author ethanlam 任务
 * 
 */
public abstract class BaseTask implements Task {

	Object locker = new Object();
	boolean worked = true;
	long firstRunTime = 0; // 计算距离第一次被执行的时间差，倒计时
	long maxOvertime = 0 ; // 线程超时
	ScheduledExecutorService scheduler = null;
	TaskItem taskItem = null;
	ScheduledFuture taskHandle = null;
	TaskStatus statusBean = new TaskStatus();
	
	public BaseTask(TaskItem taskItem) {
		this.taskItem = taskItem;
		TaskLog.info(taskItem.getName(), "任务初始化加载。");
		statusBean.setMaxOvertime(maxOvertime);
		statusBean.setName(taskItem.getName());
		loadAndRun();
	}

	
	/**
	 * 加载并运行任务
	 */
	void loadAndRun(){
		scheduler = Executors.newScheduledThreadPool(1);
		initialize();
		firstRunDelayTime();
		startTask();
		TaskLog.info(taskItem.getName(), "已完成任务加载并进入任务管理器中。");
	}
	
	
   /**
    * 首次运行的延时时间
    */
	void firstRunDelayTime() {
		this.firstRunTime = 10;
	}

	/**
	 * 加载任务
	 */
	void startTask() {
		//创建任务
		Runnable task = new Runnable() {
			public void run() {
				if (worked)
					synchronized (locker) {
						try {
							statusBean.setRunTimes();
							statusBean.setLastExeTimestamp(System.currentTimeMillis());
							TaskLog.info(taskItem.getName(), "开始执行任务。");
							task();
							statusBean.setExecuteTime(System.currentTimeMillis() - statusBean.getLastExeTimestamp());
							TaskLog.info(taskItem.getName(), "退出任务,本次总耗时:"+(statusBean.getExecuteTime()/1000)+"s。");
							TaskLog.info(taskItem.getName(),statusBean.getStateMessage());
						} catch (Exception e) {
							statusBean.setErrorTimes();
							TaskLog.error(taskItem.getName(), e.getMessage(), e);
						} finally {

						}
					}
				;
			}
		};
		
		
      //周期地执行任务
	  taskHandle = scheduler.scheduleAtFixedRate(task,
				(firstRunTime >= 0 ? firstRunTime : 60), taskItem.getSeconds()*taskItem.getMinTimeAccuMethod(),
				SECONDS);

	}

	/**
	 * 
	 * 判断任务是否超时，如果发生超时，则需要重启该任务线程，此方法供轮询使用
	 * @return
	 */
	public boolean isOverTime(){
		if((System.currentTimeMillis()-statusBean.getLastExeTimestamp())/1000>getTaskItem().getSeconds()*2)
			return true;
		else if(maxOvertime>0&&(System.currentTimeMillis()-statusBean.getLastExeTimestamp())/1000>this.maxOvertime)
			return true;
		else
			return false;
	}
	
	
	/**
	 * 中断任务
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
     * 重启任务
     */
    public void reStart(){
    	TaskLog.info(taskItem.getName(), "任务重新加载中......");
    	this.worked = true;
    	interrupt(); //中断当前任务的执行
    	loadAndRun(); //重新加载任务
    }
  
    /**
     * 获取任务对象（设置）属性
     * @return
     */
    public TaskItem getTaskItem() {
		return taskItem;
	}

    /**
     * 任务初始化，当任务首次初始化时被调用，任务循环重复执行期间 不再被 调用此方法。
     * 
     * 
     */
	protected abstract void initialize();

	
	/**
	 * 实际任务业务逻辑
	 */
	protected abstract void task();

	
	/**
	 * 
	 * 任务退出，当任务退出任务对象容器时 被调用，任务循环重复执行期间   不会重复调用此方法。
	 * 
	 */
	protected abstract void release();

}
