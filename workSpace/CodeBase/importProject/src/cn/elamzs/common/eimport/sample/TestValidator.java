
package cn.elamzs.common.eimport.sample;

import cn.elamzs.common.eimport.Anotation.ValidatorRule;

/**
 * 
 * @author Ethan.Lam
 * ���� ������ ��Ҫ ���� ��������     name,phone,num,remarks 
 *
 *
 */
public class TestValidator {

    @ValidatorRule(name = "name",check=true)    
	public String checkName(String value){
		//��д��֤ value ֵ�Ƿ��������Ϸ���	
    	return "����Ϊ��";
	}
    
    @ValidatorRule(name = "phone",check=true)
    public String checkPhone(String value){
    	//��д��֤ value ֵ�Ƿ��������Ϸ���
    	return "���ǺϷ��ֻ�����";
    }
    
    @ValidatorRule(name = "num",check=true)
    public String checkNum(String value){
    	//��д��֤ value ֵ�Ƿ��������Ϸ���	
    	return "���ǺϷ�����";
    }
    
    @ValidatorRule(name = "remarks",check=false)
    public String checkRemarks(String value){
    	//����Ҫ��֤ 
    	return null;
    }
    
    
}
