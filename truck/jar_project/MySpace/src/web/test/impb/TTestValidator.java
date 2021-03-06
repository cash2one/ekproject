
package web.test.impb;

import org.apache.poi.ss.usermodel.IndexedColors;

import cn.elamzs.common.base.files.util.StringUtil;
import cn.elamzs.common.eimport.annotations.ColumnValidate;
import cn.elamzs.common.eimport.annotations.Restriction;
import cn.elamzs.common.eimport.inter.DataValidator;

/**
 * 
 * @author Ethan.Lam
 * 例子 ：假设 需要 导入 两列数据     name,phone,num,remarks 
 *
 *
 */
@Restriction(fileName = "导入模版",check=true)
public class TTestValidator implements DataValidator{

    @ColumnValidate(ename = "name",check=true, columnSeq = 0,showName = "名称", color = IndexedColors.RED,comment="不能为空")    
	public String checkName(String value){
		//填写验证 value 值是否符合输入合法性	
    	return StringUtil.isNull(value)?"不能为空":"";
	}
    
    @ColumnValidate(ename = "phone",check=true, columnSeq = 1,showName = "手机号码",comment="可以为空")
    public String checkPhone(String value){
    	//填写验证 value 值是否符合输入合法性
    	return "";
    }
    
    @ColumnValidate(ename = "num",check=true, columnSeq = 2,showName = "数量",color = IndexedColors.RED,comment="只能是数字")
    public String checkNum(String value){
    	//填写验证 value 值是否符合输入合法性	
    	try{
    	  Double.parseDouble(value);	
    	}catch(Exception e){
    	   return "不是合法数字";
    	}
    	return "";
    }
    
    @ColumnValidate(ename = "remarks",check=false, columnSeq = 3,showName = "备注",comment="可以为空")
    public String checkRemarks(String value){
    	//不需要验证 
    	return null;
    }
    
    
    
}
