package com.elam.util.task;

import java.util.Date;

/**
 * 任务状态
 * 
 * @author Ethan.Lam 2011-4-22
 */
public class TaskStatus {

	int runTimes = 0; // 被执行次数
	int errorTimes = 0; // 任务发生错误次数
	long maxOvertime = 0; // 任务超时设定
	long lastExeTimestamp = 0; // 最近被执行的时间戳
	long executeTime = 0; // 任务执行耗时
	String name = "";
	int rebootTimes = 0 ; //重启次数

	
	
	public int getRebootTimes() {
		return rebootTimes;
	}

	public void setRebootTimes() {
		this.rebootTimes++;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TaskStatus() {

	}

	public int getRunTimes() {
		return runTimes;
	}

	public void setRunTimes() {
		this.runTimes++;
	}

	public int getErrorTimes() {
		return errorTimes;
	}

	public void setErrorTimes() {
		this.errorTimes = errorTimes++;
	}

	public long getMaxOvertime() {
		return maxOvertime;
	}

	public void setMaxOvertime(long maxOvertime) {
		this.maxOvertime = maxOvertime;
	}

	public long getLastExeTimestamp() {
		return lastExeTimestamp;
	}

	public void setLastExeTimestamp(long lastExeTimestamp) {
		this.lastExeTimestamp = lastExeTimestamp;
	}

	public long getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(long executeTime) {
		this.executeTime = executeTime;
	}

	public String getStateMessage() {
		return "状态信息：任务已被执行【" + this.getRunTimes()
				+ "】次，其中执行时发生异常有【" + this.getErrorTimes() + "】次，重启次数【"+this.getRebootTimes()+"】次，上一次执行耗时为【"
				+ (this.lastExeTimestamp>0?this.getExecuteTime() / 1000:"N/A") + "】秒，时间戳为【"
				+ new Date(this.getLastExeTimestamp()) + "】，当前超时设置为【"
				+ this.getMaxOvertime() / 1000 + "】秒。";
	}

}
