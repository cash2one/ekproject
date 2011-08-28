package cn.elam.util.task;

/**
 * 任务设置bean
 * 
 * @author ethanlam
 * 
 */
public class TaskItem {

	String name = "";
	int runType = 0; // 任务类型 0：定时任务；1：周期任务
	long seconds;
	String startTime; // 任务第一次生效时间
	String timeType = "sec";

	int minTimeAccuMethod = 1; //

	public String getTimeType() {
		return timeType;
	}

	/**
	 * 判断时间单位
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
