
package cn.elamzs.common.eimport.sample;

import cn.elamzs.common.base.files.util.StringUtil;
import cn.elamzs.common.eimport.Anotation.Restriction;
import cn.elamzs.common.eimport.Anotation.ValidatorRule;
import cn.elamzs.common.eimport.inter.DataValidator;

/**
 * 
 * @author Ethan.Lam
 * 例子 ：假设 需要 导入 两列数据     name,phone,num,remarks 
 *
 *
 */
@Restriction(fileName = "导入测试模版",check=true)
public class TestValidator implements DataValidator{

    @ValidatorRule(ename = "name",check=true, columnSeq = 0,showName = "名称")    
	public String checkName(String value){
		//填写验证 value 值是否符合输入合法性	
    	return StringUtil.isNull(value)?"不能为空":"";
	}
    
    @ValidatorRule(ename = "phone",check=true, columnSeq = 1,showName = "手机号码")
    public String checkPhone(String value){
    	//填写验证 value 值是否符合输入合法性
    	return "";
    }
    
    @ValidatorRule(ename = "num",check=true, columnSeq = 2,showName = "数量")
    public String checkNum(String value){
    	//填写验证 value 值是否符合输入合法性	
    	try{
    	  Integer.parseInt(value);	
    	}catch(Exception e){
    	   return "不是合法数字";
    	}
    	return "";
    }
    
    @ValidatorRule(ename = "remarks",check=false, columnSeq = 3,showName = "备注")
    public String checkRemarks(String value){
    	//不需要验证 
    	return null;
    }
    
    
    
}
