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
 * SOAPMessage���Ĵ����࣬ר�����ڽ���������SOAPMessage
 * 
 * @author ���ڷ� 2009-6-15
 */
public class SoapMsgHandler {
	private static final Log log = LogFactory.getLog(SoapMsgHandler.class);

	/*
	 * 	˵��������SOAP��ʽ��xml�ĵ�����
	 * 	���ڷ�	2009-08-07
	 */
	public static MiscRequestBean parseMiscRequestMsg(String miscMsg) {
		MiscRequestBean bean = new MiscRequestBean();

		if (StringUtil.isEmpty(miscMsg)) {
			bean.setErrorCode("9014");
			return bean;
		}

		// �����ĵ�
		try {
			Document doc = DocumentHelper.parseText(miscMsg);
			Element root = doc.getRootElement();

			Element header = root.element("Header");
			bean.setTransactionID(header.elementTextTrim("TransactionID"));

			Element subRoot = root.element("Body").element("SyncOrderRelationReq");
			
			String msgType = subRoot.elementTextTrim("MsgType");
			bean.setMsgType(msgType);

			if (!Constraints.MSG_TYPE_REQUEST.equalsIgnoreCase(msgType)) {
				log.info("Misc������Ϣ���Ͳ�������Ϊ[" + msgType + "]");
				bean.setErrorCode(ErrorCode.INVALID_MSG_TYPE);
				return bean;
			}

			// �����û��ֻ�
			Element feeUserElem = subRoot.element("FeeUser_ID");
			bean.setPhoneFee(feeUserElem.elementTextTrim("MSISDN"));

			// ʹ�÷�����û��ֻ�
			Element userElem = subRoot.element("DestUser_ID");
			bean.setPhoneUse(userElem.elementTextTrim("MSISDN"));

			// �������ͣ�����/ȡ������
			bean.setAction(Integer.parseInt(subRoot.elementTextTrim("ActionID")));

			// ����ͬ����ԭ��
			bean.setActionReason(Integer.parseInt(subRoot
					.elementTextTrim("ActionReasonID")));

			// ���ֵ�ǿ�ѡ��
			bean.setSpId(subRoot.elementTextTrim("SPID"));

			// ҵ�����
			bean.setSpServiceId(subRoot.elementTextTrim("SPServiceID"));
			
			//������ʷ�ʽ����ѡ
			try{
				bean.setAccessMode(Integer.parseInt(subRoot.elementTextTrim("AccessMode")));
			}catch(NumberFormatException e){
				bean.setAccessMode(0);
			}
			
			//���񶩹�����,��ѡ
			bean.setFeatureStr(subRoot.elementTextTrim("FeatureStr"));
		} catch (DocumentException e) {
			log.error("�����ڽ���Misc����ͬ����������ʱ", e);

			// ����쳣�����Ѿ����ڣ�ע�ⲻҪ����
			if (StringUtil.isEmpty(bean.getErrorCode()))
				bean.setErrorCode("9014");// ���ĸ�ʽ������
		}catch(Throwable t){
			System.out.println("111111      "+t);
			MailUtils.sendMail("ͬ������", "ͬ�������ڽ���MISC����ʱ������δ֪�쳣���뼰ʱ�˲飡");
			log.error(t);
			
			if (StringUtil.isEmpty(bean.getErrorCode()))
				bean.setErrorCode("9014");// ���ĸ�ʽ������
		}

		return bean;
	}
}
