package com.elam.util.task;

/**
 * 
 * @author ethanlam ����
 * 
 */
public abstract class BaseTask {
	
	boolean locked = false;
	TaskItem task = null;

	public BaseTask(TaskItem task) {

	}

	/**
	 * ��������
	 */
	protected abstract void task();

	
}
