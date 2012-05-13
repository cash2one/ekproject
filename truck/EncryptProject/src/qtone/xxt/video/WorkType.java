package qtone.xxt.video;

public class WorkType {
	
	
	public static void main(String[] args) { 
		    String s = "http://192.168.101.38:8080/vod/course/cp2/cs10072/preview/888888.flv"; 
			String a = byte2hex(s.getBytes());
			byte[] b = hex2byte(a.getBytes());
	        System.out.println("直接byte2hex: "+a);
	        System.out.println("直接byte2hex: "+new String(b));
	        System.out.println("直接byte2hex: "+s);
	} 

	
    protected static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0) {
			throw new IllegalArgumentException("des conver error!");
		}

		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) (Integer.parseInt(item, 16) ^ 2);
		}
		return b2;

	}

	// 字节数组转换成十六进制字符串
	protected static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (byte bt : b) {
			stmp = (Integer.toHexString(bt & 0XFF ^ 2));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;

			}
		}
		return hs.toUpperCase();
	}
	
	
//AS	
//	private function hex2byte(str:String):ByteArray{
//	    var tep:ByteArray = new ByteArray();
//		var lenght:int = str.length;
//		for(var i:int=0;i<lenght;i+=2){
//		   var s:String =  str.substr(i,2);
//		   //trace("f:"+s);
//		   tep[i/2] = parseInt(s,16)^2;
//		  // trace(parseInt(s,16));
//		}
//	    return tep;
//	}
	
	
	
}
