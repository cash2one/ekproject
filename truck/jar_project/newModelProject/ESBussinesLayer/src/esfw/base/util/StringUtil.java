package esfw.base.util;

import java.util.Random;

/**
 * 字符串工具类
 * 
 * @author Ethan.Lam 2011-7-27
 * 
 */
public class StringUtil {

	public static boolean isNull(String str) {
		return str == null || "".equals(str.trim()) ? true : false;
	}

	// 把对象转换成String
	public static String valueOf(Object obj) {
		return (obj == null || "".equals(obj) || "null".equals(obj)) ? "" : obj
				.toString().trim();
	}
	
	
	 /**
	   * 自动补对应的 字符位
	   * @param isLeft    是否从左边补位
	   * @param fillChar  自动填补的字符
	   * @param length    填补后的总字符串长度
	   * @param originalValue 原字符串 
	   * @return
	   */
	  public static String fillString(boolean isLeft,char fillChar,int length,String originalValue){
		  if(null==originalValue||"".equals(originalValue))
			  return "";
		 
		  int originalLength = originalValue.length();
		  if(length<=originalLength)
			  return originalValue;
		  
		  String fillCharStr = ""; 
	      for(int op=1;op<=length-originalLength;op++){
	    	  fillCharStr = fillChar+fillCharStr;
	      }  
		  return isLeft?(fillCharStr+originalValue):(originalValue+fillCharStr);
	  }
	  
	  /**
	   * 去除填充字符
	   * @param isLeft
	   * @param fillChar
	   * @param originalValue
	   * @return
	   */
	  public static String removeFillChar(boolean isLeft,char fillChar,String originalValue){
		  if(null==originalValue||"".equals(originalValue))
			  return "";
		  int fillLength = 0; 
		  int originalLength = originalValue.length();
		  
		  if(isLeft){
			  for(int op=0;op<originalLength;op++){
				  if(originalValue.substring(op, op+1).equals(String.valueOf(fillChar)))
					  fillLength++;
				  else
					  break;
			  }
			  return originalValue.substring(fillLength);
		  }else{
			  for(int op=originalLength;op>0;op--){
				  if(originalValue.substring(op-1, op).equals(String.valueOf(fillChar)))
					  fillLength++;
				  else
					  break;
			  }
			  return originalValue.substring(0,originalLength-fillLength);
		  }
	  }
	
	  /**
	   * 检查是否是数字
	   */
	public static boolean isNumeric(String str){
		for (int i = 0; i < str.length(); i++){
			if (!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 获取随机的6位数的字符串形式
	 * @return
	 */
	public static String genRandom() {
		int[] intRet = new int[6];
		int intRd = 0; // 存放随机数
		int count = 0; // 记录生成的随机数个数
		int flag = 0; // 是否已经生成过标志
		while (count < 6) {
			Random rdm = new Random(System.currentTimeMillis());
			intRd = Math.abs(rdm.nextInt()) % 9 + 1;
			for (int i = 0; i < count; i++) {
				if (intRet[i] == intRd) {
					flag = 1;
					break;
				} else {
					flag = 0;
				}
			}
			if (flag == 0) {
				intRet[count] = intRd;
				
			}
			count++;
		}
		String str = "";
		for (int t = 0; t < 6; t++) {
			str = str + intRet[t];
		}
		return str;
	}

	/**
	 * 字符数字转化为int型数字
	 * @param value
	 * @return
	 */
	public static int getInt(String value) {
		int i = 0;
		try {
			if (value != null)
				value = value.trim();
			i = Integer.parseInt(value);
		} catch (Exception e) {
		}
		return i;
	}

}
