package com.elam.util.task;

import java.util.Date;
import java.util.Observable;

import com.elam.util.task.inter.TaskLogger;

/**
 * ��־����
 * 
 * @author Ethan.Lam 2011-4-20
 * 
 */
public class LoggerService extends Observable {

	private String type;
	private String message;
	private Throwable throwable;
	private static LoggerService logger = null;

	private LoggerService() {

	}

	public static LoggerService getService() {
		if (logger == null)
			logger = new LoggerService();
		return logger;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	/**
	 * ����һ����־
	 * 
	 * @param message
	 */
	public void info(String task, String message) {
		message = "����[" + task + "]" + "������Ϣ��" + message + "   "
				+ new Date(System.currentTimeMillis()).toLocaleString();
		System.out.println(message);
		setType("INFO");
		setMessage(message);
		setChanged();
		notifyObservers();
	}

	/**
	 * 
	 * ����������־
	 * 
	 * @param message
	 * @param t
	 */
	public void error(String task, String message, Throwable t) {
		message = "����[" + task + "]" + "������Ϣ��" + message + "   "
				+ new Date(System.currentTimeMillis()).toLocaleString();
		System.out.println(message);
		setType("ERROR");
		setMessage(message);
		this.setThrowable(t);
		setChanged();
		notifyObservers();
	}

	public static void main(String... args) {
		LoggerService log = LoggerService.getService();
		log.addObserver(new TaskLogger() {

			@Override
			public void error(String message, Throwable t) {
				System.out.println("�ҹ۲쵽�׳�������Ϣ��" + message);
			}

			@Override
			public void info(String message) {
				// TODO Auto-generated method stub
				System.out.println("�ҹ۲쵽һ����Ϣ��" + message);
			}

		});

		log.info("TEST", "һ����Ϣ");
		log.error("TEST", "������Ϣ", null);
	}

}
