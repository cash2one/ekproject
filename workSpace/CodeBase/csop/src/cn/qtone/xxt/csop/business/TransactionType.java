package cn.qtone.xxt.csop.business;

/**
 * 
 * 基本业务类型,信息
 * @author linhansheng 
 */
public enum TransactionType {
	
	DXX(1,"家长短信箱","is_dxx"),
	KAOQIN(2,"考勤","is_kaoqin"),
	QINQING(3,"亲情电话","is_qin_qing_tel"),
	LIUYANBAN(4,"电子留言板","is_liuyanban");
	
	int code;
	String cname;
	String fieldState;
	
	TransactionType(int code,String cname,String fieldState){
	   this.code = code;
	   this.cname = cname;
	   this.fieldState = fieldState;
	}
	
    public int code(){
       return this.code;
    }
    
    public String cname(){
       return this.cname;
    }

    /**
     * 返回对应 family 表中的 代表该业务的 状态   字段名
     * @return
     */
    public String field(){
    	return this.fieldState;
    } 
    
}
