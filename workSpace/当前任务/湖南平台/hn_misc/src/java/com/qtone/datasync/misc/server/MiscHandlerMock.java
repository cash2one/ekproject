package com.qtone.datasync.misc.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MiscHandlerMock {

	private final Log msgLog = LogFactory.getLog("msg.xxt_server");

	public String handle(String msg) {
		msgLog.fatal(msg);

		return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:dsmp=\"http://www.monternet.com/dsmp/schemas/\"> <SOAP-ENV:Header>  <dsmp:TransactionID xmlns:dsmp=\"http://www.monternet.com/dsmp/schemas/\">700001</dsmp:TransactionID>  </SOAP-ENV:Header> <SOAP-ENV:Body> <UnSubscribeServiceResp xmlns=\"http://10.1.2.122/misc/dsmp.xsd\">  <Version>1.5.0</Version>  <MsgType>UnSubscribeServiceResp</MsgType>  <hRet>0</hRet>  </UnSubscribeServiceResp>  </SOAP-ENV:Body>  </SOAP-ENV:Envelope>";
	}
}
