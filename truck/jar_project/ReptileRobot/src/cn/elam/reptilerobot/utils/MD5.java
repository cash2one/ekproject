package cn.elam.reptilerobot.utils;

import java.security.MessageDigest;

/**
 * 
 * MD5算法
 * @author Ethan.Lam  
 * @createTime 2011-12-3
 *
 */
public class MD5 {

	static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f' };
	/**
	 * 
	 * 方法：
	 * 
	 * @param source
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-3
	 */
	public static String generateMD5Str(byte[] source) {
		String s = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	
	/**
	 * 
	 * 方法：
	 * 
	 * @param str
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-3
	 */
	public static String generateMD5Str(String str){
		byte[] source  = str.getBytes();
		return generateMD5Str(source);
	}

	 
}
