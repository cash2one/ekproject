package esfw.base.util;

import java.util.List;

import esfw.core.helper.MethodAdapter;


/**
 * 
 * 验证器
 * @author Ethan.Lam  2011-7-20
 *
 */
public class ValidatorUtil {

	
	/**
	 * 根据号码判断号码类型
	 * @param phone
	 * @return -1 ：未知类型 ； 0：移动 ，1：联通  2：电信
	 */
	public static int getPhoneTypeByPhone(String phone){
		try {
			Long.parseLong(phone);
		} catch (Exception e) {
			// 包括非数字字符
			return -1; // 非数字的
		}
		
		if (phone == null || phone.length() != 11) {
			// 为NULL或长度不是11位
			return -1; // 其他
		}
		int iTmp = Integer.parseInt(phone.substring(0, 3));
		if (iTmp == 159 || iTmp == 158 || iTmp == 157 || iTmp == 150 || iTmp == 151 || iTmp == 152 || (iTmp >= 134 && iTmp <= 139) || iTmp == 147 || iTmp == 188|| iTmp == 187|| iTmp == 182) {
			return 0; // 移动
		} else if ((iTmp >= 130 && iTmp <= 132) || iTmp == 153 || iTmp == 155 || iTmp == 156  || iTmp == 185 || iTmp == 186) {
			return 1; // 联通
		} else if ( iTmp == 133 || iTmp == 189 || iTmp == 180){
			return 2; // 电信
		}else
			return -1;
	}
	
	/**
	 * 把字符串转化数字
	 * @param val
	 * @return
	 */
	public static int strToNumeric(String val){
	    if(StringUtil.isNull(val))
		   return -1; 
	    else {
	    	try{
	    		return Integer.parseInt(val);
	    	}catch(Exception e){
	        	return -1;
	        }
	    }
	}
	
	
	/**
	 * 判断对象是否为数字
	 * @param val
	 * @return
	 */
	public static boolean isNumeric(String val){
	   try{
	         Integer.parseInt(val);
	    	 return true;
	      }catch(Exception e){
	         return false;
	      }
	}
	
	 
}
