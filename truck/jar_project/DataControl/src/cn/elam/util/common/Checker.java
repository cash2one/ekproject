package cn.elam.util.common;

public class Checker {

	public static boolean isNull(String str){
		if(str==null||"".equals(str)||str.length()==0)
			return true;
		else
			return false;
	}
	
}
