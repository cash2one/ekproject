package com.qtone.datasync.xxt;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qtone.datasync.misc.client.XxtXmlHandler;
import com.qtone.datasync.misc.client.bean.MiscRespBean;

public class XxtXmlHandlerTest {
	private String msgSrc = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"	xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"	xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:dsmp=\"http://www.monternet.com/dsmp/schemas/\">	<SOAP-ENV:Header>		<dsmp:TransactionID xmlns:dsmp=\"http://www.monternet.com/dsmp/schemas/\">42724379351821203		</dsmp:TransactionID>	</SOAP-ENV:Header>	<SOAP-ENV:Body>		<SubscribeServiceResp xmlns=\"http://10.1.2.122/misc/dsmp.xsd\">			<Version>1.5.0</Version>			<MsgType>SubscribeServiceResp</MsgType>			<hRet>0</hRet>			<LinkID>00110402021549400001</LinkID>		</SubscribeServiceResp>	</SOAP-ENV:Body></SOAP-ENV:Envelope>";
	
	@Test
	public void testParseMiscRespMsg(){
		MiscRespBean bean = XxtXmlHandler.parseMiscRespMsg(msgSrc);

		System.out.println(bean);
		Assert.assertEquals(bean.getTransactionId(), "42724379351821203");
		Assert.assertEquals(bean.getHRet(), "0");
	}
}
