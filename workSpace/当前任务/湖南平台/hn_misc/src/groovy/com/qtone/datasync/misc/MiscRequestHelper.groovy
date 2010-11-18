package com.qtone.datasync.misc

import groovy.text.SimpleTemplateEngine;
import com.qtone.datasync.xxt.server.bean.*
import com.qtone.datasync.misc.client.bean.*
import com.qtone.datasync.util.*

public class MiscRequestHelper{
	private static final String miscReqXmlTpl = new File(ClassUtil.getFilePath(MiscRequestHelper.class,
			"com/qtone/datasync/xml/SyncOrderRelationReq.xml")).getText()
	
	/**
	 * 生成请求报文
	 */
	public static String getRequestXml(params){
		SimpleTemplateEngine tplEngine = new SimpleTemplateEngine();
		String reqXmlStr = tplEngine.createTemplate(miscReqXmlTpl).make(params).toString();
		
		return reqXmlStr;
	}
	
	/**
	 * 将返回的报文转化为相应的Bean，以便于对结果的对比
	 */
	public static XxtRespBean toRespBean(xmlStr){
		xmlStr = xmlStr.replace('<?xml version="1.0" encoding="GBK"?>','').trim()
		
		def root = new XmlParser().parseText(xmlStr)
		def msgType = root.MsgType[0]
		def tranId = root.TransactionID[0]
		def version = root.Version[0]
		def hRet = root.hRet[0]
		
		return new XxtRespBean(["msgType":"${msgType.text()}","transactionID":"${tranId.text()}","version":"${version.text()}","hRet":hRet.text()])
	}
	
	/**
	 * 
	 */
	public static Object[][] createMiscRequestAndResultBean(){
		def reqParams = [['msgType':'SyncOrderRelation','transactionID':'1111','phoneFee':'13800138000','phoneUse':'13800138000','action':1,'actionReason':1,'spId':'QTONE_SP_ID','spServiceId':'59194725'],
		             	['msgType':'SyncOrderRelationReq','transactionID':'2222','phoneFee':'13800138000','phoneUse':'13800138000','action':1,'actionReason':1,'spId':'QTONE_SP_ID','spServiceId':'59194725'],
		            	['msgType':'SyncOrderRelationReq','transactionID':'1001','phoneFee':'','phoneUse':'13800138000','action':1,'actionReason':1,'spId':'QTONE_SP_ID','spServiceId':'528400'],
		            	['msgType':'SyncOrderRelationReq','transactionID':'1963','phoneFee':'13800138000','phoneUse':'','action':1,'actionReason':1,'spId':'QTONE_SP_ID','spServiceId':'528400'],
		            	['msgType':'SyncOrderRelationReq','transactionID':'1123','phoneFee':'13800138000','phoneUse':'13800138000','action':100,'actionReason':1,'spId':'QTONE_SP_ID','spServiceId':'528400'],
		            	['msgType':'SyncOrderRelationReq','transactionID':'1123','phoneFee':'13800138000','phoneUse':'13800138000','action':1,'actionReason':100,'spId':'QTONE_SP_ID','spServiceId':'528400'],
		            	['msgType':'SyncOrderRelationReq','transactionID':'1123','phoneFee':'13800138000','phoneUse':'13800138000','action':1,'actionReason':1,'spId':'Wrong-QTONE_SP_ID','spServiceId':'528400'],
		            	['msgType':'SyncOrderRelationReq','transactionID':'1123','phoneFee':'13800138000','phoneUse':'13800138000','action':1,'actionReason':1,'spId':'Wrong-QTONE_SP_ID','spServiceId':'528400']]
		
		def expectedRetParams = [['msgType':'SyncOrderRelationResp','transactionID':'1111','version':'1.5.0','hRet':'4000'],
		                 ['msgType':'SyncOrderRelationResp','transactionID':'2222','version':'1.5.0','hRet':'0'],
		                 ['msgType':'SyncOrderRelationResp','transactionID':'1001','version':'1.5.0','hRet':'9014'],
		                 ['msgType':'SyncOrderRelationResp','transactionID':'1963','version':'1.5.0','hRet':'9014'],
		                 ['msgType':'SyncOrderRelationResp','transactionID':'1123','version':'1.5.0','hRet':'4001'],
		                 ['msgType':'SyncOrderRelationResp','transactionID':'1123','version':'1.5.0','hRet':'4002'],
		                 ['msgType':'SyncOrderRelationResp','transactionID':'1123','version':'1.5.0','hRet':'4003'],
		                 ['msgType':'SyncOrderRelationResp','transactionID':'1123','version':'1.5.0','hRet':'4003']
		                 ]
		
		def retArr = []
		for(int i=0;i<reqParams.size();i++){
			retArr << [reqParams[i],new MiscRespBean(expectedRetParams[i])]
		}
		
		return retArr
	}
}