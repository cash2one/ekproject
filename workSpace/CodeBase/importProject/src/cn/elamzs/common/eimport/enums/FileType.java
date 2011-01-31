package cn.elamzs.common.eimport.enums;
/**
 * 
 * @author Ethan.Lam
 * �ļ����� 
 */
public enum FileType {
      
	EXCEL_XLS("xls",0),
	EXCEL_XLSX("xlsx",0),
	TXT("txt",1);
	
	private String suffix; //��׺��
	private int type;//����
	
	/** 
	 * @param suffix
	 * @param type
	 */
	FileType(String suffix,int type){
		this.suffix = suffix;
		this.type = type;
	}

	public String suffix(){
		return suffix;
	}

	public int type() {
		return type;
	}

	
}
