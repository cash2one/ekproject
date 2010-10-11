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
		initialize();
		firstRunDelayTime();
		startTask();
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
							task();
						} catch (Exception e) {
							errorTimes++;
							e.printStackTrace();
						} finally {
							release();
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
