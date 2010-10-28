package cn.qtone.xxt.csop.business;

/**
 * 
 * 基本业务类型,信息
 * @author linhansheng 
 */
public enum TransactionType {
	
	DXX(1,"家长短信箱","DXX","is_dxx","is_dxx_charge","OPEN_DXX_DATE"),
	KAOQIN(2,"考勤短信","KAOQIN","is_kaoqin","is_kq_charge","OPEN_KAOQIN_DATE"),
	QINQING(3,"亲情电话","QINQING","is_qin_qing_tel","is_qq_charge","OPEN_QINQING_TEL"),
	LIUYANBAN(4,"留言板","LIUYANBAN","is_liuyanban","is_lyb_charge","OPEN_LIUYANBAN");
	
	int code;
	String ename;
	String cname;
	String familyField;  //家庭表中的标志位  业务开通状态
	String schoolField;  //学校表中的标志位  业务是否收费
	String openDate;  //家庭表中对应的业务开通时间
	
	TransactionType(int code,String ename,String cname,String familyField,String schoolField,String openDate){
	   this.code = code;
	   this.ename = ename;
	   this.cname = cname;
	   this.familyField = familyField;
	   this.schoolField = schoolField;
	   this.openDate = openDate;
	}
	
    public int code(){
       return this.code;
    }
    
    public String cname(){
       return this.cname;
    }
    
    public String ename(){
		return ename;
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
    
    public String openDate(){
    	return this.openDate;
    }
    
}
