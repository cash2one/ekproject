package com.elam.util.task.sample;

import com.elam.util.task.BaseTask;
import com.elam.util.task.TaskItem;
import com.elam.util.task.TaskLog;
import com.elam.util.task.TaskStatus;

public class TestTask extends BaseTask{

	public TestTask(TaskItem taskItem) {
		super(taskItem);
	}

	@Override
	protected void initialize() {
	  TaskLog.info(getTaskItem().getName(), " initialize....");
	}

	@Override
	protected void release() {
		// TODO Auto-generated method stub
		TaskLog.info(getTaskItem().getName(), " release....");
	}

	@Override
	protected void task() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(3*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TaskLog.info(getTaskItem().getName(), " execute TestTask....");
	}

	@Override
	public TaskStatus getState() {
		// TODO Auto-generated method stub
		return null;
	}

}
