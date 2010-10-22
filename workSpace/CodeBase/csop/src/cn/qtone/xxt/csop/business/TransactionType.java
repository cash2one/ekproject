package cn.qtone.xxt.csop.business;

/**
 * 
 * ����ҵ������,��Ϣ
 * @author linhansheng 
 */
public enum TransactionType {
	
	DXX(1,"�ҳ�������","is_dxx"),
	KAOQIN(2,"����","is_kaoqin"),
	QINQING(3,"����绰","is_qin_qing_tel"),
	LIUYANBAN(4,"�������԰�","is_liuyanban");
	
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
     * ���ض�Ӧ family ���е� �����ҵ��� ״̬   �ֶ���
     * @return
     */
    public String field(){
    	return this.fieldState;
    } 
    
}
