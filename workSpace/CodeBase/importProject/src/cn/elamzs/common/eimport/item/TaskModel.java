package cn.elamzs.common.eimport.item;

import cn.elam.util.db.inter.DataModel;

/**
 * 
 * ����������Ϣ�� �־û�bean����
 * @author Ethan.Lam   2011-2-24
 *
 */
public class TaskModel implements DataModel {

	private String handerId;      //����ʱ������ID Ψһ
	private String fileName;      //�����ԭ�ļ�����
	private String srcPath;       //����Դ�ļ�·��
	private String resultPath;    //�������ļ�
	private String startTime;     //����ʼʱ��
	private String finishedTime;  //�������ʱ��
	private String taskType;    //��������
	private int recordNum;     //�ļ������ݼ�¼��
	private int procTime;  //�����ܺ�ʱ���룩

	
	public int getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(int recordNum) {
		this.recordNum = recordNum;
	}

	public int getProcTime() {
		return procTime;
	}

	public void setProcTime(int procTime) {
		this.procTime = procTime;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	private int state;  //����״̬

	public int getState() {
		return state;
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
