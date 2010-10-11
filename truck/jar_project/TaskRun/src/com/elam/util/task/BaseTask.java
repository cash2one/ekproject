package com.elam.util.task;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * 
 * @author ethanlam 任务
 * 
 */
public abstract class BaseTask {

	Object locker = new Object();
	boolean worked = false;
	int runTimes = 0;
	int errorTimes = 0;
	long firstRunTime = 0; // 计算距离第一次被执行的时间差，倒计时
	ScheduledExecutorService service = null;
	TaskItem taskItem = null;

	
	public BaseTask(TaskItem taskItem) {
		this.taskItem = taskItem;
		service = Executors.newScheduledThreadPool(1);
		TaskLog.info(taskItem.getName(), "任务加载中......");
		initialize();
		firstRunDelayTime();
		startTask();
		TaskLog.info(taskItem.getName(), "已完成任务加载并进入任务管理器中。");
	}

	void firstRunDelayTime() {
		this.firstRunTime = 10;
	}

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
		ScheduledFuture taskHandle = service.scheduleAtFixedRate(task,
				(firstRunTime >= 0 ? firstRunTime : 60), taskItem.getSeconds(),
				SECONDS);

	}

	protected abstract void initialize();

	protected abstract void task();

	protected abstract void release();

}
