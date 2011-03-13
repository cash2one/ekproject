package cn.elamzs.common.eimport.item;

import cn.elam.util.db.inter.DataModel;
import cn.elamzs.common.eimport.enums.TaskState;

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
	private long procTime;  //�����ܺ�ʱ���룩
	private int state;  //����״̬      0:δִ�У�1������ɹ�   2������ʧ��

	
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
