package com.qtone.datasync.xxt.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qtone.datasync.misc.client.XxtXmlHandler;
import com.qtone.datasync.misc.client.bean.XxtRespBean;
import com.qtone.datasync.util.SoapMsgHandler;
import com.qtone.datasync.xxt.server.bean.MiscRequestBean;

/**
 * ��������ͬ������ӿ�
 * 
 * @author ���ڷ� 2009-05-19
 */
public class DefaultMiscRequestHandler implements IMiscRequestHandler{

	private final Log msgLog = LogFactory.getLog("msg.xxt_server"); 
	/**
	 * @param msg
	 * SyncOrderRelationReq
	 * @return response SyncOrderRelationResp
	 */
	public String SyncOrderRelationReq(String msg)  {
		msgLog.fatal(msg);
		
		// �������ģ�����һ����Ӧ��Bean
		//XxtXmlHandler xmlHandler = new XxtXmlHandler();
		
		MiscRequestBean bean = SoapMsgHandler.parseMiscRequestMsg(msg);

		// ����Bean
		MiscRequestBusiness buss = new MiscRequestBusiness();
		XxtRespBean respBean = buss.handle(bean);

		String retMsg = XxtXmlHandler.toXxtResponseMsg(respBean);
		msgLog.fatal(retMsg);
		
		return retMsg;
	}
	
}
