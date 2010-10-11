package com.elam.util.task;

/**
 * 
 * @author ethanlam
 * 
 */
public class TaskItem {

	String name = "";
	int runType = 0; // 任务类型 0：定时任务；1：周期任务
	long seconds;
	long startTime; // 任务第一次生效时间

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRunType() {
		return runType;
	}

	public void setRunType(int runType) {
		this.runType = runType;
	}

	public long getSeconds() {
		return seconds;
	}

	public void setSeconds(long seconds) {
		this.seconds = seconds;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

}
