package qtone.generator.util;

public class StringHelper {


	public static String toLowerCase(String str){
		return str.toLowerCase();
	}
	
	/**
	 * ��������ĸ��д
	 * @param str
	 * @return
	 */
	public static String fistChartUpperCase(String str){
		return str.replaceFirst(str.substring(0, 1),str.substring(0, 1).toUpperCase()); 
	}
	
}
