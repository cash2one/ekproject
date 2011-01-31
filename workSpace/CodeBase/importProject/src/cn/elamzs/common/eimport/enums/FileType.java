package cn.elamzs.common.eimport.enums;
/**
 * 
 * @author Ethan.Lam
 * 文件类型 
 */
public enum FileType {
      
	EXCEL_XLS("xls",0),
	EXCEL_XLSX("xlsx",0),
	TXT("txt",1);
	
	private String suffix; //后缀名
	private int type;//类型
	
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
