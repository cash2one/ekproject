package cn.elam.util.common;

/**
 * 
 * 数据验证工具类
 * @author Ethan.Lam   2011-2-26
 *
 */
public class Checker {

	public static boolean isNull(String str){
		if(str==null||"".equals(str)||str.length()==0)
			return true;
		else
			return false;
	}
	
}
