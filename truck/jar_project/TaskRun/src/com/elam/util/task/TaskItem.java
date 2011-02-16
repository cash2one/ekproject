package com.elam.util.task;

/**
 * ��������bean
 * 
 * @author ethanlam
 * 
 */
public class TaskItem {

	String name = "";
	int runType = 0; // �������� 0����ʱ����1����������
	long seconds;
	String startTime; // �����һ����Чʱ��
	String timeType = "sec";

	int minTimeAccuMethod = 1; //

	public String getTimeType() {
		return timeType;
	}

	/**
	 * �ж�ʱ�䵥λ
	 * @param timeType
	 */
	public void setTimeType(String timeType) {
		if (timeType.toLowerCase().startsWith("sec")
				|| timeType.toLowerCase().startsWith("min")
				|| timeType.toLowerCase().startsWith("hour")) {
			this.timeType = timeType;
			
			if(timeType.toLowerCase().startsWith("sec"))
			    this.minTimeAccuMethod = 1;
			else if(timeType.toLowerCase().startsWith("min"))
				this.minTimeAccuMethod = 60;
			else if(timeType.toLowerCase().startsWith("hour"))
				this.minTimeAccuMethod = 3600;
			
		} else
			this.timeType = "sec";
	}

	public int getMinTimeAccuMethod() {
		return minTimeAccuMethod;
	}

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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

}
