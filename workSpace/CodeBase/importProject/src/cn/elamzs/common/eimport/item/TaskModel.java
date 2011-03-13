package cn.elamzs.common.eimport.item;

import cn.elam.util.db.inter.DataModel;
import cn.elamzs.common.eimport.enums.TaskState;

/**
 * 
 * 导入任务信息的 持久化bean对象
 * @author Ethan.Lam   2011-2-24
 *
 */
public class TaskModel implements DataModel {

	private String handerId;      //导入时创建的ID 唯一
	private String fileName;      //导入的原文件名称
	private String srcPath;       //导入源文件路径
	private String resultPath;    //导入结果文件
	private String startTime;     //任务开始时间
	private String finishedTime;  //任务结束时间
	private String taskType;    //任务类型
	private int recordNum;     //文件的数据记录行
	private long procTime;  //任务总耗时（秒）
	private int state;  //任务状态      0:未执行；1：导入成功   2：导入失败

	
	public int getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(int recordNum) {
		this.recordNum = recordNum;
	}

	public long getProcTime() {
		return procTime;
	}

	public void setProcTime(long procTime) {
		this.procTime = procTime;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	
	public int getState() {
		return state;
	}

	public void setState(TaskState state) {
		this.state = state.type();
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public String getHanderId() {
		return handerId;
	}

	public void setHanderId(String handerId) {
		this.handerId = handerId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public String getResultPath() {
		return resultPath;
	}

	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(String finishedTime) {
		this.finishedTime = finishedTime;
	}
	
}
