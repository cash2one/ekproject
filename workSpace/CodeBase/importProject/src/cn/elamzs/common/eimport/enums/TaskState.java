package cn.elamzs.common.eimport.enums;


/**
 * 
 * ��������״̬����
 * @author Ethan.Lam   2011-3-13
 *
 */
public enum TaskState {
	
	IMP_READY(0, "������"),
	IMP_SUC(1, "����ɹ�"), 
	IMP_ERROR(-1, "����ʧ��"),
    IMP_TEMPATE_NOMATCH(-2, "����ģ�治��ȷ");
	
	private int type;
	private String mean;

	TaskState(int type, String mean) {
		this.type = type;
		this.mean = mean;
	}

	public int type() {
		return this.type;
	}

	public String mean() {
		return this.mean;
	}

}