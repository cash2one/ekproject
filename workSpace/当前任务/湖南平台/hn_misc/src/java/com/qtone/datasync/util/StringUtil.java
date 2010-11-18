package com.qtone.datasync.util;

public class StringUtil {
	/**
	 * ÅÐ¶Ï×Ö·ûÊÇ·ñÎª¿Õ
	 * 
	 * @param msg
	 * @return
	 */
	public static boolean isEmpty(String msg) {
		if (msg == null || "".equals(msg) || msg.length() == 0
				|| "null".equalsIgnoreCase(msg))
			return true;
		
		return false;
	}

}
