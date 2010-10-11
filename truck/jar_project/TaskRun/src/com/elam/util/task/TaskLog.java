package com.elam.util.task;

public class TaskLog {

	public static void info(String task, String message) {
		System.out.println("任务[" + task + "]" + "运行信息：" + message);
	}

	public static void error(String task, String message, String errorMsg) {
		System.out.println("任务[" + task + "]" + "运行错误信息：" + message);
	}
}
