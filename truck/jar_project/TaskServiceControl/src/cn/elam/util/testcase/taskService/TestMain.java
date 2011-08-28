package cn.elam.util.testcase.taskService;

import cn.elam.util.loger.AppLoger;
import cn.elam.util.task.TaskManager;
import cn.elam.util.task.inter.TaskLogger;


public class TestMain {

	public static void main(String...s) throws Exception{
		TaskManager mgr = new TaskManager();
		mgr.initializeTaskContainer(null);
		mgr.appendLogger(new TaskLogger() {
			
			@Override
			public void error(String message, Throwable t) {
               AppLoger.RuningLoggerError(message, t);				
			}

			@Override
			public void info(String message) {
				// TODO Auto-generated method stub
//				System.out.println("我观察到一般消息：" + message);
				AppLoger.RuningLoggerInfo(message);
			}

		});
		mgr.start();
	}
}
