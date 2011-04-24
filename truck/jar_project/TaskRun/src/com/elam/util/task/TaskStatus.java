package com.elam.util.task;

import java.util.Date;

/**
 * ����״̬
 * 
 * @author Ethan.Lam 2011-4-22
 */
public class TaskStatus {

	int runTimes = 0; // ��ִ�д���
	int errorTimes = 0; // �������������
	long maxOvertime = 0; // ����ʱ�趨
	long lastExeTimestamp = 0; // �����ִ�е�ʱ���
	long executeTime = 0; // ����ִ�к�ʱ
	String name = "";
	int rebootTimes = 0 ; //��������

	
	
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
		return "״̬��Ϣ�������ѱ�ִ�С�" + this.getRunTimes()
				+ "���Σ�����ִ��ʱ�����쳣�С�" + this.getErrorTimes() + "���Σ�����������"+this.getRebootTimes()+"���Σ���һ��ִ�к�ʱΪ��"
				+ (this.lastExeTimestamp>0?this.getExecuteTime() / 1000:"N/A") + "���룬ʱ���Ϊ��"
				+ new Date(this.getLastExeTimestamp()) + "������ǰ��ʱ����Ϊ��"
				+ this.getMaxOvertime() / 1000 + "���롣";
	}

}
