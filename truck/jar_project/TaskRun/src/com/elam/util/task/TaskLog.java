package com.elam.util.task;

import java.util.Date;

public class TaskLog {

	public static void info(String task, String message) {
		System.out.println("任务[" + task + "]" + "运行信息：" + message+" : "+new Date(System.currentTimeMillis()).toLocaleString());
	}

	public static void error(String task, String message, String errorMsg) {
		System.out.println("任务[" + task + "]" + "运行错误信息：" + message+" : "+new Date(System.currentTimeMillis()).toLocaleString());
	}
}
