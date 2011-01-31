
package cn.elamzs.common.eimport.sample;

import cn.elamzs.common.eimport.Anotation.Restriction;
import cn.elamzs.common.eimport.Anotation.ValidatorRule;

/**
 * 
 * @author Ethan.Lam
 * 例子 ：假设 需要 导入 两列数据     name,phone,num,remarks 
 *
 *
 */
@Restriction(check=true)
public class TestValidator {

    @ValidatorRule(ename = "name",check=true, column = 1)    
	public String checkName(String value){
		//填写验证 value 值是否符合输入合法性	
    	return "不能为空";
	}
    
    @ValidatorRule(ename = "phone",check=true, column = 2)
    public String checkPhone(String value){
    	//填写验证 value 值是否符合输入合法性
    	return "不是合法手机号码";
    }
    
    @ValidatorRule(ename = "num",check=true, column = 3)
    public String checkNum(String value){
    	//填写验证 value 值是否符合输入合法性	
    	return "不是合法数字";
    }
    
    @ValidatorRule(ename = "remarks",check=false, column = 4)
    public String checkRemarks(String value){
    	//不需要验证 
    	return null;
    }
    
    
}
