package com.elam.util.task;

/**
 * 
 * @author ethanlam 任务
 * 
 */
public abstract class BaseTask {
	
	boolean locked = false;
	TaskItem task = null;

	public BaseTask(TaskItem task) {

	}

	/**
	 * 任务内容
	 */
	protected abstract void task();

	
}
