package com.elam.util.task;

import java.util.Date;

/**
 * 任务日志
 * 
 * @author Ethan.Lam 2011-2-15
 * 
 */
public class TaskLog {

	public static void info(String task, String message) {
		LoggerService.getService().info(task, message);
	}

	public static void error(String task, String message, Throwable t) {
		LoggerService.getService().error(task, message, t);
	}

}
