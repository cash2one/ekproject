package qtone.xxt.video;

public class Test {

	protected static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0) {
			throw new IllegalArgumentException("des conver error!");
		}

		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			// System.out.println("test:"+Integer.parseInt(item, 16));
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;

	}

	// 字节数组转换成十六进制字符串
	protected static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (byte bt : b) {
			stmp = (Integer.toHexString(bt & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;

			}
		}
		return hs.toUpperCase();
	}

	public static void main(String... args) {
		String test = "/vod/course/cp2/cs10072/preview/10072.flv";
		String a = byte2hex(test.getBytes());
		System.out.println("byte2hex: " + a);

		byte[] b = hex2byte(a.getBytes());
		System.out.println("hex2byte: " + new String(b));

	}


}
