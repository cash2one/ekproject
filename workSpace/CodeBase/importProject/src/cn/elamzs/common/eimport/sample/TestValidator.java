
package cn.elamzs.common.eimport.sample;

import cn.elamzs.common.eimport.Anotation.Restriction;
import cn.elamzs.common.eimport.Anotation.ValidatorRule;
import cn.elamzs.common.eimport.inter.DataValidator;

/**
 * 
 * @author Ethan.Lam
 * ���� ������ ��Ҫ ���� ��������     name,phone,num,remarks 
 *
 *
 */
@Restriction(fileName = "�������ģ��",check=true)
public class TestValidator implements DataValidator{

    @ValidatorRule(ename = "name",check=true, columnSeq = 1,showName = "����")    
	public String checkName(String value){
		//��д��֤ value ֵ�Ƿ��������Ϸ���	
    	return "����Ϊ��";
	}
    
    @ValidatorRule(ename = "phone",check=true, columnSeq = 2,showName = "�ֻ�����")
    public String checkPhone(String value){
    	//��д��֤ value ֵ�Ƿ��������Ϸ���
    	return "���ǺϷ��ֻ�����";
    }
    
    @ValidatorRule(ename = "num",check=true, columnSeq = 3,showName = "����")
    public String checkNum(String value){
    	//��д��֤ value ֵ�Ƿ��������Ϸ���	
    	return "���ǺϷ�����";
    }
    
    @ValidatorRule(ename = "remarks",check=false, columnSeq = 4,showName = "��ע")
    public String checkRemarks(String value){
    	//����Ҫ��֤ 
    	return null;
    }
    
    
    
}
