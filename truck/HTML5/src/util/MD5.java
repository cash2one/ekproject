package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

/**
 * from the internet
 * 
 * @author yongboy
 * @date 2011-4-3
 * @version 1.0
 */
public class MD5 {

	/**
	 * 利用MD5进行加密
	 * 
	 * @param str
	 *            待加密的字符串
	 * @return 加密后的字符串
	 * @throws NoSuchAlgorithmException
	 *             没有这种产生消息摘要的算法
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String str) {
		// 确定计算方法
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (md5 == null) {
			throw new NullPointerException();
		}

		BASE64Encoder base64en = new BASE64Encoder();
		// 加密后的字符串
		return base64en.encode(md5.digest(str.getBytes()));
	}

	/**
	 * 判断用户密码是否正确
	 * 
	 * @param newpasswd
	 *            用户输入的密码
	 * @param oldpasswd
	 *            数据库中存储的密码－－用户密码的摘要
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static boolean checkMD5(String newpasswd, String oldpasswd) {
		return encode(newpasswd).equals(oldpasswd);
	}
}