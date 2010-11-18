package com.qtone.datasync.util;

import com.qtone.datasync.misc.client.XxtXmlHandler;
import com.qtone.datasync.xxt.server.DefaultMiscRequestHandler;
import com.qtone.datasync.xxt.server.bean.XxtRequestBean;

/**
 * @author ÑîÌÚ·É	2009-11-3 
 *
 */
public class TestXmlGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		XxtRequestBean userInfoBean = new XxtRequestBean();
		userInfoBean.setAction(1);
		userInfoBean.setPhone("15973195209");
		userInfoBean.setSpServiceId("133351");
		userInfoBean.setTransactionId("00240301801050");
		
//		System.out.println(XxtXmlHandler.toXxtRequestXml(userInfoBean));
		String strMsg="<?xml version=\"1.0\" encoding=\"utf-8\"?><SOAP-ENV:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"    xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"    xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\"><SOAP-ENV:Header>    <TransactionID xmlns=\"http://www.monternet.com/dsmp/schemas/\">00230045687781</TransactionID></SOAP-ENV:Header><SOAP-ENV:Body><SyncOrderRelationReq xmlns=\"http://www.monternet.com/dsmp/schemas/\"><Version>1.5.0</Version><MsgType>SyncOrderRelationReq</MsgType><Send_Address><DeviceType>0</DeviceType><DeviceID>0023</DeviceID></Send_Address><Dest_Address><DeviceType>400</DeviceType><DeviceID>0</DeviceID></Dest_Address><FeeUser_ID><UserIDType>1</UserIDType><MSISDN>15973195209</MSISDN><PseudoCode></PseudoCode></FeeUser_ID><DestUser_ID><UserIDType>1</UserIDType><MSISDN>15973195209</MSISDN><PseudoCode></PseudoCode></DestUser_ID><LinkID>SP</LinkID><ActionID>2</ActionID><ActionReasonID>1</ActionReasonID><SPID>918522</SPID><SPServiceID>133351</SPServiceID><AccessMode>3</AccessMode><FeatureStr></FeatureStr></SyncOrderRelationReq></SOAP-ENV:Body></SOAP-ENV:Envelope>";
		DefaultMiscRequestHandler test =new DefaultMiscRequestHandler();
		test.SyncOrderRelationReq(strMsg);
		
	}

}
