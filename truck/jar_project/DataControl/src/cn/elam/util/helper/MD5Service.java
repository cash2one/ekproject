package cn.elam.util.helper;

/**
 * 
 * @author Ethan.Lam 2011-4-3
 * 
 */
public class MD5Service {

	/**
	 * MD5 编码
	 * @param source
	 * @return
	 */
	public static String getMD5(byte[] source) {
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		java.security.MessageDigest md = null;
		try {
			md = java.security.MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);
		} catch (Exception e) {
			
		}
		md = null;
		hexDigits = null;
		return s;
	}
	
	public static void main(String...args){
		System.out.println(getMD5("TEST".getBytes()));
	}

}
