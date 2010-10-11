package com.elam.util.task;

import java.util.Date;

public class TaskLog {

	public static void info(String task, String message) {
		System.out.println("����[" + task + "]" + "������Ϣ��" + message+" : "+new Date(System.currentTimeMillis()).toLocaleString());
	}

	public static void error(String task, String message, String errorMsg) {
		System.out.println("����[" + task + "]" + "���д�����Ϣ��" + message+" : "+new Date(System.currentTimeMillis()).toLocaleString());
	}
}
