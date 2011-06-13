package com.elam.util.task.inter;

import java.util.Observable;
import java.util.Observer;

import com.elam.util.task.LoggerService;

/**
 * 日志观察者
 * @author Ethan.Lam   2011-4-20
 */
public abstract class TaskLogger implements Observer {

	@Override
	public void update(Observable obj, Object arg) {
		// TODO Auto-generated method stub
		if (obj instanceof LoggerService) {
			LoggerService logger = (LoggerService) obj;
			if ("ERROR".equals(logger.getType())) {
				error(logger.getMessage(), logger.getThrowable());
			} else {
				info(logger.getMessage());
			}
		}
	}

	public abstract void info(String message);

	public abstract void error(String message, Throwable t);

}
