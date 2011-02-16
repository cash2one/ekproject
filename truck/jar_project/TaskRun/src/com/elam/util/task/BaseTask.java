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
	int runTimes = 0;
	int errorTimes = 0;
	long firstRunTime = 0; // 计算距离第一次被执行的时间差，倒计时
	ScheduledExecutorService scheduler = null;
	TaskItem taskItem = null;
	ScheduledFuture taskHandle = null;
	
	public BaseTask(TaskItem taskItem) {
		this.taskItem = taskItem;
		TaskLog.info(taskItem.getName(), "任务初始化加载。");
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
		Runnable task = new Runnable() {
			public void run() {
				if (worked)
					synchronized (locker) {
						try {
							runTimes++;
							TaskLog.info(taskItem.getName(), "开始执行任务。");
							task();
							TaskLog.info(taskItem.getName(), "退出任务。");
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
				(firstRunTime >= 0 ? firstRunTime : 60), taskItem.getSeconds()*taskItem.getMinTimeAccuMethod(),
				SECONDS);

	}

	/**
	 * 中断任务
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
     * 重启任务
     */
    public void reStart(){
    	TaskLog.info(taskItem.getName(), "任务重新加载中......");
    	this.worked = true;
    	loadAndRun();
    }
  
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
