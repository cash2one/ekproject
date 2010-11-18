package com.qtone.datasync.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.qtone.mm7.common.mail.MailUtils;

import com.qtone.datasync.misc.Constraints;
import com.qtone.datasync.misc.ErrorCode;
import com.qtone.datasync.xxt.server.bean.MiscRequestBean;

/**
 * SOAPMessage报文处理类，专门用于解析或生成SOAPMessage
 * 
 * @author 杨腾飞 2009-6-15
 */
public class SoapMsgHandler {
	private static final Log log = LogFactory.getLog(SoapMsgHandler.class);

	/*
	 * 	说明：解析SOAP格式的xml文档报文
	 * 	杨腾飞	2009-08-07
	 */
	public static MiscRequestBean parseMiscRequestMsg(String miscMsg) {
		MiscRequestBean bean = new MiscRequestBean();

		if (StringUtil.isEmpty(miscMsg)) {
			bean.setErrorCode("9014");
			return bean;
		}

		// 解析文档
		try {
			Document doc = DocumentHelper.parseText(miscMsg);
			Element root = doc.getRootElement();

			Element header = root.element("Header");
			bean.setTransactionID(header.elementTextTrim("TransactionID"));

			Element subRoot = root.element("Body").element("SyncOrderRelationReq");
			
			String msgType = subRoot.elementTextTrim("MsgType");
			bean.setMsgType(msgType);

			if (!Constraints.MSG_TYPE_REQUEST.equalsIgnoreCase(msgType)) {
				log.info("Misc请求消息类型不正常，为[" + msgType + "]");
				bean.setErrorCode(ErrorCode.INVALID_MSG_TYPE);
				return bean;
			}

			// 付费用户手机
			Element feeUserElem = subRoot.element("FeeUser_ID");
			bean.setPhoneFee(feeUserElem.elementTextTrim("MSISDN"));

			// 使用服务的用户手机
			Element userElem = subRoot.element("DestUser_ID");
			bean.setPhoneUse(userElem.elementTextTrim("MSISDN"));

			// 请求类型（订购/取消服务）
			bean.setAction(Integer.parseInt(subRoot.elementTextTrim("ActionID")));

			// 产生同步的原因
			bean.setActionReason(Integer.parseInt(subRoot
					.elementTextTrim("ActionReasonID")));

			// 这个值是可选的
			bean.setSpId(subRoot.elementTextTrim("SPID"));

			// 业务代码
			bean.setSpServiceId(subRoot.elementTextTrim("SPServiceID"));
			
			//服务访问方式，可选
			try{
				bean.setAccessMode(Integer.parseInt(subRoot.elementTextTrim("AccessMode")));
			}catch(NumberFormatException e){
				bean.setAccessMode(0);
			}
			
			//服务订购参数,可选
			bean.setFeatureStr(subRoot.elementTextTrim("FeatureStr"));
		} catch (DocumentException e) {
			log.error("发生在解析Misc正向同步数据请求时", e);

			// 如果异常代码已经存在，注意不要覆盖
			if (StringUtil.isEmpty(bean.getErrorCode()))
				bean.setErrorCode("9014");// 报文格式不完整
		}catch(Throwable t){
			System.out.println("111111      "+t);
			MailUtils.sendMail("同步程序", "同步程序在解析MISC报文时，发生未知异常。请及时核查！");
			log.error(t);
			
			if (StringUtil.isEmpty(bean.getErrorCode()))
				bean.setErrorCode("9014");// 报文格式不完整
		}

		return bean;
	}
}
