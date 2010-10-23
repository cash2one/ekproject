package cn.qtone.xxt.csop.business;

/**
 * 
 * 基本业务类型,信息
 * @author linhansheng 
 */
public enum TransactionType {
	
	DXX(1,"家长短信箱","is_dxx","is_dxx_charge"),
	KAOQIN(2,"考勤","is_kaoqin","is_kq_charge"),
	QINQING(3,"亲情电话","is_qin_qing_tel","is_qq_charge"),
	LIUYANBAN(4,"电子留言板","is_liuyanban","is_lyb_charge");
	
	int code;
	String cname;
	String familyField;  //家庭表中的标志位  业务开通状态
	String schoolField;  //学校表中的标志位  业务是否收费
	
	TransactionType(int code,String cname,String familyField,String schoolField){
	   this.code = code;
	   this.cname = cname;
	   this.familyField = familyField;
	   this.schoolField = schoolField;
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
    public String familyField(){
    	return this.familyField;
    } 
    
    public String schoolField(){
    	return this.schoolField;
    }
    
}
