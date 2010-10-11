package com.elam.util.task;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * 
 * @author ethanlam ����
 * 
 */
public abstract class BaseTask {

	Object locker = new Object();
	boolean worked = false;
	int runTimes = 0;
	int errorTimes = 0;
	long firstRunTime = 0; // ��������һ�α�ִ�е�ʱ������ʱ
	ScheduledExecutorService service = null;
	TaskItem taskItem = null;

	
	public BaseTask(TaskItem taskItem) {
		this.taskItem = taskItem;
		service = Executors.newScheduledThreadPool(1);
		TaskLog.info(taskItem.getName(), "���������......");
		initialize();
		firstRunDelayTime();
		startTask();
		TaskLog.info(taskItem.getName(), "�����������ز���������������С�");
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
		ScheduledFuture taskHandle = service.scheduleAtFixedRate(task,
				(firstRunTime >= 0 ? firstRunTime : 60), taskItem.getSeconds(),
				SECONDS);

	}

	protected abstract void initialize();

	protected abstract void task();

	protected abstract void release();

}
