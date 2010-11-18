package com.qtone.datasync.server;


/**
 * 模拟校讯通发布的服务的客户端（也就是模拟Misc服务器的正向同步处理请求器）
 * 
 * @author 杨腾飞 2009-6-16
 */
public class XxtClientMock {
	//private Client client = null;

	public XxtClientMock() {
	}

	public String handleMiscData(String req) {
		Object[] res = null;

		try {
			res = new Object[]{};//client.invoke("SyncOrderRelationReq", req);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String result = "";

		if (res != null && res.length > 0)
			result = (String) res[0];

		return result;
	}
}
