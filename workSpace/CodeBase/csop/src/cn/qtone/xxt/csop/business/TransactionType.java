package cn.qtone.xxt.csop.business;

/**
 * 
 * ����ҵ������,��Ϣ
 * @author linhansheng 
 */
public enum TransactionType {
	
	DXX(1,"�ҳ�������","is_dxx","is_dxx_charge"),
	KAOQIN(2,"����","is_kaoqin","is_kq_charge"),
	QINQING(3,"����绰","is_qin_qing_tel","is_qq_charge"),
	LIUYANBAN(4,"�������԰�","is_liuyanban","is_lyb_charge");
	
	int code;
	String cname;
	String familyField;  //��ͥ���еı�־λ  ҵ��ͨ״̬
	String schoolField;  //ѧУ���еı�־λ  ҵ���Ƿ��շ�
	
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
     * ���ض�Ӧ family ���е� �����ҵ��� ״̬   �ֶ���
     * @return
     */
    public String familyField(){
    	return this.familyField;
    } 
    
    public String schoolField(){
    	return this.schoolField;
    }
    
}
