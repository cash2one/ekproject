
package cn.qtone.xxt.logonmock.util;




/**
 * 同步课堂登录参数编码器
 * @author liudong 
 */
public class KetangLoginEncoder {
   

	private final static String _KEY = ".keTang.";

	/**
	 * 测试入口
	 * @param args
	 */
	public static void main(String[] args) {
		String service_id = "QuanTong";
		String role = "2";
		String username = "cszhangqi";
		String ip = "127.0.0.1";
		String user_agent = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)";
		//编码
		String uuid = encodeKetangLoginParam(service_id,role,username,ip,user_agent);
		System.out.println("###加密后"+uuid);
		String queryString=CryptUtils.encrypt("QuanTong|2|cszhangqi|127.0.0.1|55|1291086198809", _KEY);
		System.out.println("-------"+queryString );
		
		System.out.println("------------------------");
		
		try {
			String str=CryptUtils.decrypt("4A584FAC9F2929E0B18FAB43821D7E06FABA05E782169DFCCFE4AE81594833921D7A74239187388628743942C90D58BF",_KEY);
			
			System.out.println(str);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		
		
	}

	/**
	 * 对参数进行加密以便在URL中传输
	 * 
	 * @param service_id 	服务商标识，由同步课堂指定一个固定值
	 * @param role 			用户的角色(STUDENT:学生,TEACHER:教师,PARENT:家长)
	 * @param username 		登录帐号(用户帐号名)
	 * @param ip 			用户访问的IP地址
	 * @param user_agent 	通过 request.getHeader("user-agent") 获取到的值
	 *
	 * @return 返回的字符串为URL中?后面的内容
	 */
	public static String encodeKetangLoginParam(
		String service_id,
		String role,
		String username,
		String ip,
		String user_agent)
	{
	    StringBuffer buf = new StringBuffer();
	    buf.append(service_id);
	    buf.append('|');
	    buf.append(role);
	    buf.append('|');
	    buf.append(username);
	    buf.append('|');
	    buf.append(ip);
	    buf.append('|');
	    buf.append((user_agent==null)?0:user_agent.length());
	    buf.append('|');
	    buf.append(System.currentTimeMillis());
//      System.out.println("加密前========"+buf.toString());
      
		return CryptUtils.encrypt(buf.toString(), _KEY);
	}

}
