package com.qtone.datasync.local.service;

import java.net.MalformedURLException;

/**
 * @author ���ڷ� 2009-7-6 ����ͬ�������� �������һ�����ӣ�ʹ���߿���ֱ��ʹ�ø������е����ݡ�
 */
public class RevertSyncServiceCaller {

//	private URL wsdlLocation = null;
//	private QName serviceName = new QName(
//			"http://service.local.datasync.qtone.com/",
//			"RevertSyncServiceImplService");

	public void doSync(String areaAbb) throws MalformedURLException {
//		wsdlLocation = new URL("http://127.0.0.1/hnxxt/services/sync?wsdl");
//
//		Service service = Service.create(wsdlLocation, serviceName);
//		IRevertSyncService port = service.getPort(IRevertSyncService.class);
		//port.sync(areaAbb);
	}

	public static void main(String[] args) throws MalformedURLException {
		if (args.length == 0) {
			System.out.println("���������������");
			System.exit(0);
		}

		RevertSyncServiceCaller call = new RevertSyncServiceCaller();
		for (String abb : args) {
			call.doSync(abb);
		}
	}
}