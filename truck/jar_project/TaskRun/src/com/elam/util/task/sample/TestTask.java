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
			int st = (int)(Math.random()*10);
			System.out.println("π “‚∫ƒ ±"+(st*2)+"√Î°£");
			Thread.sleep((st*2)*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		TaskLog.info(getTaskItem().getName(), " execute TestTask....");
	}


}
