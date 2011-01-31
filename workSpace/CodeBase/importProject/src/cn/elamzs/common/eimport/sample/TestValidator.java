
package cn.elamzs.common.eimport.sample;

import cn.elamzs.common.eimport.Anotation.Restriction;
import cn.elamzs.common.eimport.Anotation.ValidatorRule;

/**
 * 
 * @author Ethan.Lam
 * ���� ������ ��Ҫ ���� ��������     name,phone,num,remarks 
 *
 *
 */
@Restriction(check=true)
public class TestValidator {

    @ValidatorRule(ename = "name",check=true, column = 1)    
	public String checkName(String value){
		//��д��֤ value ֵ�Ƿ��������Ϸ���	
    	return "����Ϊ��";
	}
    
    @ValidatorRule(ename = "phone",check=true, column = 2)
    public String checkPhone(String value){
    	//��д��֤ value ֵ�Ƿ��������Ϸ���
    	return "���ǺϷ��ֻ�����";
    }
    
    @ValidatorRule(ename = "num",check=true, column = 3)
    public String checkNum(String value){
    	//��д��֤ value ֵ�Ƿ��������Ϸ���	
    	return "���ǺϷ�����";
    }
    
    @ValidatorRule(ename = "remarks",check=false, column = 4)
    public String checkRemarks(String value){
    	//����Ҫ��֤ 
    	return null;
    }
    
    
}
