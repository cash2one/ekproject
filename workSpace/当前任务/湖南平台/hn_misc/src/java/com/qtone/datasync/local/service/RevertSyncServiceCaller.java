package com.qtone.datasync.local.service;

import java.net.MalformedURLException;

/**
 * @author 杨腾飞 2009-7-6 反向同步调用者 这个类是一个例子，使用者可以直接使用该例子中的内容。
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
			System.out.println("请输入地区参数！");
			System.exit(0);
		}

		RevertSyncServiceCaller call = new RevertSyncServiceCaller();
		for (String abb : args) {
			call.doSync(abb);
		}
	}
}