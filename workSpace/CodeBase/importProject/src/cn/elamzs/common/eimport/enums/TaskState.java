package cn.elamzs.common.eimport.enums;


/**
 * 
 * 导入任务状态类型
 * @author Ethan.Lam   2011-3-13
 *
 */
public enum TaskState {
	
	IMP_READY(0, "待处理"),
	IMP_SUC(1, "处理成功"), 
	IMP_ERROR(-1, "导入失败"),
    IMP_TEMPATE_NOMATCH(-2, "数据模版不正确");
	
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