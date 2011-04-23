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
			int st = (int)(Math.random()*100);
//			if(st>0)
//				Thread.sleep((st*3)*1000);
//			System.out.println("故意耗时"+(st*2)+"秒。");
			if(st/2==1){
				TaskLog.info(getTaskItem().getName(),"故意耗时");
				while(true){
					st = (int)(Math.random()*10);
					if(st/2==1)
						break;
				}
			}	
		} catch (Exception e) {
			TaskLog.error(getTaskItem().getName(),"发生异常", e);
		}
		TaskLog.info(getTaskItem().getName(), " execute TestTask....");
	}


}
