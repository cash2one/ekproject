
package cn.elamzs.common.eimport.sample;

import cn.elamzs.common.eimport.Anotation.ValidatorRule;

/**
 * 
 * @author Ethan.Lam
 * 例子 ：假设 需要 导入 两列数据     name,phone,num,remarks 
 *
 *
 */
public class TestValidator {

    @ValidatorRule(name = "name",check=true)    
	public String checkName(String value){
		//填写验证 value 值是否符合输入合法性	
    	return "不能为空";
	}
    
    @ValidatorRule(name = "phone",check=true)
    public String checkPhone(String value){
    	//填写验证 value 值是否符合输入合法性
    	return "不是合法手机号码";
    }
    
    @ValidatorRule(name = "num",check=true)
    public String checkNum(String value){
    	//填写验证 value 值是否符合输入合法性	
    	return "不是合法数字";
    }
    
    @ValidatorRule(name = "remarks",check=false)
    public String checkRemarks(String value){
    	//不需要验证 
    	return null;
    }
    
    
}
