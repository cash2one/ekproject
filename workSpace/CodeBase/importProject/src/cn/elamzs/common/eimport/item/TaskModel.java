package cn.elamzs.common.eimport.item;

import cn.elam.util.db.inter.DataModel;

/**
 * 
 * @author Ethan.Lam   2011-2-24
 *
 */
public class TaskModel implements DataModel {

	private int handerId;
	private String fileName;
	private String srcPath;
	private String resultPath;
	private String startTime;
	private String finishedTime;
	private int state;

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
