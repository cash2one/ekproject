package com.elam.util.task;

public class TaskLog {

	public static void info(String task, String message) {
		System.out.println("����[" + task + "]" + "������Ϣ��" + message);
	}

	public static void error(String task, String message, String errorMsg) {
		System.out.println("����[" + task + "]" + "���д�����Ϣ��" + message);
	}
}
