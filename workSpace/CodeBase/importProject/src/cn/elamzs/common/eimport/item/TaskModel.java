package cn.elamzs.common.eimport.item;

import cn.elam.util.db.inter.DataModel;

/**
 * 导入任务信息的 持久化bean
 * @author Ethan.Lam   2011-2-24
 *
 */
public class TaskModel implements DataModel {

	private int handerId;   //导入时创建的ID 唯一
	private String fileName;  //导入的原文件名称
	private String srcPath;  //导入源文件路径
	private String resultPath; //导入结果文件
	private String startTime;  //任务开始时间
	private String finishedTime;  //任务结束时间
	private int state;  //任务状态

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getHanderId() {
		return handerId;
	}

	public void setHanderId(int handerId) {
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
