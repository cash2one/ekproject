package qtone.generator.util;

public class StringHelper {


	public static String toLowerCase(String str){
		return str.toLowerCase();
	}
	
	/**
	 * 单词首字母大写
	 * @param str
	 * @return
	 */
	public static String fistChartUpperCase(String str){
		return str.replaceFirst(str.substring(0, 1),str.substring(0, 1).toUpperCase()); 
	}
	
	
	/**
	 * 单词首字母小写
	 * @param str
	 * @return
	 */
	public static String fistChartLowerCase(String str){
		return str.replaceFirst(str.substring(0, 1),str.substring(0, 1).toLowerCase()); 
	}
}
