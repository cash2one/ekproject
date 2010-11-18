package com.qtone.datasync.misc

import groovy.text.SimpleTemplateEngine;

import com.qtone.datasync.xxt.server.bean.*
import com.qtone.datasync.misc.client.bean.*
import com.qtone.datasync.misc.*

public class MiscResponseHelper{
	/**
	 * 解析Xxt客户端发送的请求，并从请求中获取TransactionID
	 */
	public static MiscRequestBean parseXxtRequest(String xmlMsg){
		def bean = new MiscRequestBean()
		def root = new XmlParser().parseText(xmlMsg)
		
		def tranId = root."SOAP-ENV:Header"[0].TransactionID[0].text()
		bean.transactionID  =  tranId
		
		def msgRoot = root."SOAP-ENV:Body"[0].SubscribeServiceReq[0]
		
		def phoneFee = msgRoot.FeeUser_ID[0].MSISDN[0].text()
		bean.phoneFee = phoneFee
		
		def phoneUse = msgRoot.DestUser_ID[0].MSISDN[0].text()
		bean.phoneUse = phoneUse
		
		def spId = msgRoot.Service_ID[0].SPID[0].text()
		def spServiceId = msgRoot.Service_ID[0].SPServiceID[0].text()
		def msgType = msgRoot.MsgType[0].text()
		
		bean.msgType = msgType
		bean.spId = spId
		bean.spServiceId = spServiceId
		
		if(Constraints.MSG_TYPE_SUBSCRIBE == 'SubscribeServiceReq'){
			bean.action = 1;
		}else{
			bean.action = 2;
		}

		return bean
	}
	
	/**
	 * 模拟对对校讯通平台请求的响应
	 */
	public static String createXxtResp(String msgType,String tranId,String hRet){
		def params = ['msgType':msgType,'tranId':tranId,'hRet':hRet] 
		
		SimpleTemplateEngine tplEngine = new SimpleTemplateEngine();
		String reqXmlStr = tplEngine.createTemplate(
				new File(MiscResponseHelper.class.getClassLoader()
						.getResource("com/qtone/datasync/xml/RevertSyncResp.xml").getPath())).make(params).toString();
		
		return reqXmlStr;
	}
	
	public static void main(String[] args){
		println MiscResponseHelper.createXxtResp('a','b','c')
		
		//com/qtone/datasync/xml/SubscribeServiceReq.xml
		def xmlMsg = new File(MiscResponseHelper.class.getClassLoader()
				.getResource("com/qtone/datasync/xml/XxtRequest.xml").getPath()).getText();
		println parseXxtRequest(xmlMsg)
	}
}