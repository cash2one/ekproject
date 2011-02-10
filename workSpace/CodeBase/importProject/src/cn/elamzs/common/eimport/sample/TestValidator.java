
package cn.elamzs.common.eimport.sample;

import org.apache.poi.ss.usermodel.IndexedColors;

import cn.elamzs.common.base.files.util.StringUtil;
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

    @ValidatorRule(ename = "name",check=true, columnSeq = 0,showName = "����", color = IndexedColors.RED,comment="����")    
	public String checkName(String value){
		//��д��֤ value ֵ�Ƿ��������Ϸ���	
    	return StringUtil.isNull(value)?"����Ϊ��":"";
	}
    
    @ValidatorRule(ename = "phone",check=true, columnSeq = 1,showName = "�ֻ�����",comment="����Ϊ��")
    public String checkPhone(String value){
    	//��д��֤ value ֵ�Ƿ��������Ϸ���
    	return "";
    }
    
    @ValidatorRule(ename = "num",check=true, columnSeq = 2,showName = "����",color = IndexedColors.RED,comment="ֻ��������")
    public String checkNum(String value){
    	//��д��֤ value ֵ�Ƿ��������Ϸ���	
    	try{
    	  Integer.parseInt(value);	
    	}catch(Exception e){
    	   return "���ǺϷ�����";
    	}
    	return "";
    }
    
    @ValidatorRule(ename = "remarks",check=false, columnSeq = 3,showName = "��ע",comment="����Ϊ��")
    public String checkRemarks(String value){
    	//����Ҫ��֤ 
    	return null;
    }
    
    
    
}