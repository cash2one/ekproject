package com.qtone.datasync.util;

public class StringUtil {
	/**
	 * �ж��ַ��Ƿ�Ϊ��
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
