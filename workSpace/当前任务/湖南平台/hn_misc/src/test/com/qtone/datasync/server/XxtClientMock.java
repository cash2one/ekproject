package com.qtone.datasync.server;


/**
 * ģ��УѶͨ�����ķ���Ŀͻ��ˣ�Ҳ����ģ��Misc������������ͬ��������������
 * 
 * @author ���ڷ� 2009-6-16
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
