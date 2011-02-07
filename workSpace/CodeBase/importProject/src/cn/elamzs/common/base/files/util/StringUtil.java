package cn.elamzs.common.base.files.util;

public class StringUtil {

	public static boolean isNull(String obj) {
		return obj == null || "".equals(obj.trim()) ? true : false;
	}

	public static boolean isEqual(String str1, String str2) {
		if (str1 == null)
			str1 = "";
		if (str2 == null)
			str2 = "";
		return str1.trim().equals(str2.trim());
	}

	public static String toString(Object obj) {
		if (obj == null)
			return "";
		return obj.toString();
	}

}
