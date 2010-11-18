package com.qtone.datasync.misc.client;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.qtone.datasync.misc.Constraints;
import com.qtone.datasync.misc.ErrorCode;
import com.qtone.datasync.misc.client.bean.MiscRespBean;
import com.qtone.datasync.misc.client.bean.XxtRespBean;
import com.qtone.datasync.util.StringUtil;
import com.qtone.datasync.xxt.server.bean.MiscRequestBean;
import com.qtone.datasync.xxt.server.bean.XxtRequestBean;

/**
 * 专门处理同步过程中涉及到Xml处理的部分，包括了解析报文及组成响应报文
 * 
 * @author 杨腾飞 2009-05-31
 */
public class XxtXmlHandler {
	private static final Log log = LogFactory.getLog(XxtXmlHandler.class);

	/**
	 * 解析Xml文档，将文档内容转化为UserInfoBean
	 * 
	 * @param miscMsg
	 * @return
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

			bean.setTransactionID(root.elementTextTrim("TransactionID"));

			String msgType = root.elementTextTrim("MsgType");
			bean.setMsgType(msgType);

			if (!Constraints.MSG_TYPE_REQUEST.equalsIgnoreCase(msgType)) {
				log.info("Misc请求消息类型不正常，为[" + msgType + "]");
				bean.setErrorCode(ErrorCode.INVALID_MSG_TYPE);
				return bean;
			}

			// 付费用户手机
			Element feeUserElem = root.element("FeeUser_ID");
			bean.setPhoneFee(feeUserElem.elementTextTrim("MSISDN"));

			// 使用服务的用户手机
			Element userElem = root.element("DestUser_ID");
			bean.setPhoneUse(userElem.elementTextTrim("MSISDN"));

			// 请求类型（订购/取消服务）
			bean.setAction(Integer.parseInt(root.elementTextTrim("ActionID")));

			// 产生同步的原因
			bean.setActionReason(Integer.parseInt(root
					.elementTextTrim("ActionReasonID")));

			// 这个值是可选的
			bean.setSpId(root.elementTextTrim("SPID"));

			// 业务代码
			bean.setSpServiceId(root.elementTextTrim("SPServiceID"));
		} catch (DocumentException e) {
			log.error("发生在解析Misc正向同步数据请求时", e);

			// 如果异常代码已经存在，注意不要覆盖
			if (StringUtil.isEmpty(bean.getErrorCode()))
				bean.setErrorCode("9014");// 报文格式不完整
		}

		return bean;
	}

	/**
	 * 处理反向同步过程中，Misc向校讯通的响应报文
	 * 
	 * @param respMsg
	 */
	public static MiscRespBean parseMiscRespMsg(String respMsg) {
		MiscRespBean bean = new MiscRespBean();

		try {
			Document doc = DocumentHelper.parseText(respMsg);
			Element root = doc.getRootElement();

			Element head = root.element("Header");
			bean.setTransactionId(head.elementTextTrim("TransactionID"));

			Element body = root.element("Body");
			
			//Element msgRoot = body.element("SubscribeServiceResp");
			Element msgRoot = body.element("UnSubscribeServiceResp");
			bean.setHRet(msgRoot.elementTextTrim("hRet"));
		} catch (DocumentException e) {
			log.error("反向同步时，解析Misc返回报文出现异常", e);
		}

		return bean;
	}

	/**
	 * 将响应信息Bean转化为Xml报文
	 * 
	 * @param respBean
	 * @return
	 */
	public static String toXxtResponseMsg(XxtRespBean respBean) {
		Document doc = DocumentFactory.getInstance().createDocument();
		Element root = doc.addElement(respBean.getMsgType());

		root.addElement("MsgType").addText(respBean.getMsgType());
		root.addElement("TransactionID").addText(respBean.getTransactionID());
		root.addElement("Version").addText(respBean.getVersion());
		root.addElement("hRet").addText(respBean.getHRet());

		StringWriter out = new StringWriter();
		OutputFormat outformat = OutputFormat.createPrettyPrint();
		outformat.setEncoding("GBK");
		XMLWriter writer = new XMLWriter(out, outformat);
		try {
			writer.write(doc);
			writer.flush();
		} catch (IOException e) {
			log.error(e);
		}

		String miscMsg = out.toString();
		
		return miscMsg;
	}

	/**
	 * 组织正向同步请求报文
	 * 
	 * @param userInfoBean
	 * @return
	 */
	public static String toXxtRequestXml(final XxtRequestBean userInfoBean) {
		StringBuilder strBuf = new StringBuilder();
		strBuf.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append("\n");
		strBuf.append(" <SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns=\"http://www.monternet.com/dsmp/schemas/\">").append("\n");
		strBuf.append("<SOAP-ENV:Header>").append("\n");
		strBuf.append("<TransactionID xmlns=\"http://www.monternet.com/dsmp/schemas/\" xsi:type=\"xsd:string\">").append(userInfoBean.getTransactionId()).append("</TransactionID>").append("\n");
		strBuf.append("</SOAP-ENV:Header>").append("\n");
		strBuf.append("<SOAP-ENV:Body>").append("\n");
		strBuf.append("<UnSubscribeServiceReq xmlns=\"http://www.monternet.com/dsmp/schemas/\">").append("\n");
		strBuf.append("<Version>1.5.0</Version>").append("\n");
		strBuf.append("<MsgType>UnSubscribeServiceReq</MsgType>").append("\n");		
		strBuf.append("<Send_Address>").append("\n");
		strBuf.append("<DeviceType>400</DeviceType>").append("\n");
		strBuf.append("<DeviceID>").append(userInfoBean.getIsCp() == 1 ?("".equals(userInfoBean.getSpServiceId().replaceAll("[0-9]*",""))? Constraints.CP_SP_ID:Constraints.SP_ID)
				: Constraints.SP_ID).append("</DeviceID>").append("\n");		
		strBuf.append("</Send_Address>").append("\n");
		strBuf.append("<Dest_Address>").append("\n");
		strBuf.append("<DeviceType>0</DeviceType>").append("\n");
		strBuf.append("<DeviceID>0023</DeviceID>").append("\n");
		strBuf.append("</Dest_Address>").append("\n");
		strBuf.append("<FeeUser_ID>").append("\n");
		strBuf.append("<UserIDType>1</UserIDType>").append("\n");
		strBuf.append("<MSISDN>").append(userInfoBean.getPhone()).append("</MSISDN>").append("\n");
		strBuf.append("<PseudoCode />").append("\n");		
		strBuf.append("</FeeUser_ID>").append("\n");
		strBuf.append("<DestUser_ID>").append("\n");
		strBuf.append("<UserIDType>1</UserIDType>").append("\n");		
		strBuf.append("<MSISDN>").append(userInfoBean.getPhone()).append("</MSISDN>").append("\n");
		strBuf.append("<PseudoCode />").append("\n");
		strBuf.append("</DestUser_ID>").append("\n");		
		strBuf.append("<Service_ID>").append("\n");		
		strBuf.append("<ServiceIDType>1</ServiceIDType>").append("\n");
		strBuf.append("<SPID>").append(userInfoBean.getIsCp() == 1 ?("".equals(userInfoBean.getSpServiceId().replaceAll("[0-9]*",""))? Constraints.CP_SP_ID:Constraints.SP_ID)
						: Constraints.SP_ID).append("</SPID>\n");
		strBuf.append("<SPServiceID>").append(userInfoBean.getSpServiceId()).append("</SPServiceID>").append("\n");		
		strBuf.append("<AccessNo />").append("\n");		
		strBuf.append("<FeatureStr />").append("\n");
		strBuf.append("</Service_ID>").append("\n");
		strBuf.append("<FeatureStr />").append("\n");				
		strBuf.append("</UnSubscribeServiceReq>").append("\n");
		strBuf.append("</SOAP-ENV:Body>").append("\n");	
		strBuf.append("</SOAP-ENV:Envelope>");
		
//		System.out.println(strBuf.toString());
		return strBuf.toString();
		
//		strBuf.append(" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ");
//		strBuf.append(" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" ");
//		strBuf.append(" xmlns=\"http://www.monternet.com/dsmp/schemas/\"> ");
//		strBuf.append(" <SOAP-ENV:Header><TransactionID xmlns=\"http://www.monternet.com/dsmp/schemas/\" xsi:type=\"xsd:string\">");
//		strBuf.append(userInfoBean.getTransactionId());
//		strBuf.append("</TransactionID></SOAP-ENV:Header><SOAP-ENV:Body><UnSubscribeServiceReq xmlns=\"http://www.monternet.com/dsmp/schemas/\">");
//		strBuf.append("<Version>1.5.0</Version><MsgType>UnSubscribeServiceReq</MsgType>");
//		strBuf.append("<Send_Address><DeviceType>").append(Constraints.DEVICE_TYPE);
//		strBuf.append("</DeviceType><DeviceID>").append(Constraints.DEVICE_ID).append("</DeviceID>");
//		strBuf.append("</Send_Address><Dest_Address><DeviceType>").append(Constraints.MISC_DEVICE_TYPE);
//		strBuf.append("</DeviceType><DeviceID>").append(Constraints.MISC_DEVICE_ID).append("</DeviceID></Dest_Address>");
//		strBuf.append("<FeeUser_ID><UserIDType>1</UserIDType><MSISDN>").append(userInfoBean.getPhone()).append("</MSISDN><PseudoCode />");
//		strBuf.append("</FeeUser_ID><DestUser_ID><UserIDType>1</UserIDType><MSISDN>").append(userInfoBean.getPhone()).append("</MSISDN><PseudoCode />");
//		strBuf.append("</DestUser_ID><Service_ID><ServiceIDType>1</ServiceIDType><SPID>");
//		strBuf.append(Constraints.SP_ID).append("</SPID><SPServiceID>").append(userInfoBean.getSpServiceId());
//		strBuf.append("</SPServiceID><AccessNo /><FeatureStr /></Service_ID><FeatureStr />");
//		strBuf.append("</UnSubscribeServiceReq></SOAP-ENV:Body></SOAP-ENV:Envelope>");
//
//		return strBuf.toString();
				
		
//		Document doc = DocumentFactory.getInstance().createDocument();
//		Element root = doc.addElement(new QName("Envelope", new Namespace(
//				"SOAP-ENV", "http://schemas.xmlsoap.org/soap/envelope/")));
//		root.add(new Namespace("SOAP-ENC",
//				"http://schemas.xmlsoap.org/soap/encoding/"));
//		root.add(new Namespace("xsi",
//				"http://www.w3.org/2001/XMLSchema-instance"));
//		root.add(new Namespace("xsd", "http://www.w3.org/2001/XMLSchema"));
//		root.addAttribute("SOAP-ENV:encodingStyle",
//				"http://schemas.xmlsoap.org/soap/encoding/");
//		root.addNamespace("", "http://www.monternet.com/dsmp/schemas/");
//
//		Element head = root.addElement("SOAP-ENV:Header");
//		Element tranElem = head.addElement("TransactionID");
//		tranElem.addNamespace("", "http://www.monternet.com/dsmp/schemas/");
//		tranElem.addText(userInfoBean.getTransactionId());
//		tranElem.addAttribute("xsi:type", "xsd:string");
//
//		Element body = root.addElement("SOAP-ENV:Body");
//		Element subRoot = body.addElement("UnSubscribeServiceReq");
//		subRoot.addNamespace("", "http://www.monternet.com/dsmp/schemas/");
//
//		subRoot.addElement("Version").addText(Constraints.VERSION);
//		subRoot.addElement("MsgType").addText(userInfoBean.getMsgType());
//
//		Element sendAddrElem = subRoot.addElement("Send_Address");
//		sendAddrElem.addElement("DeviceType").addText(Constraints.DEVICE_TYPE);
//		sendAddrElem.addElement("DeviceID").addText(Constraints.DEVICE_ID);//"913002"
//
//		Element destAddrElem = subRoot.addElement("Dest_Address");
//		destAddrElem.addElement("DeviceType").addText(Constraints.MISC_DEVICE_TYPE);//"0"
//		destAddrElem.addElement("DeviceID").addText(Constraints.MISC_DEVICE_ID);//"0024"
//
//		Element feeUserElem = subRoot.addElement("FeeUser_ID");
//		feeUserElem.addElement("UserIDType").addText("1");
//		feeUserElem.addElement("MSISDN").addText(userInfoBean.getPhone());
//		feeUserElem.addElement("PseudoCode");
//
//		Element destUserElem = subRoot.addElement("DestUser_ID");
//		destUserElem.addElement("UserIDType").addText("1");
//		destUserElem.addElement("MSISDN").addText(userInfoBean.getPhone());
//		destUserElem.addElement("PseudoCode");
//
//		Element serviceIdElem = subRoot.addElement("Service_ID");
//		serviceIdElem.addElement("ServiceIDType").addText("1");
//		serviceIdElem.addElement("SPID").addText(Constraints.SP_ID);
//		serviceIdElem.addElement("SPServiceID").addText(
//				userInfoBean.getSpServiceId());
//		serviceIdElem.addElement("AccessNo");
//		serviceIdElem.addElement("FeatureStr");
//
//		subRoot.addElement("FeatureStr");
//
//		String msg = XmlUtil.toXmlStr(doc);
		//MsgLog.writeToFile(MsgLog.MSG_XXT_TO_MISC,"反向同步请求Xxt->Misc", msg);
		
//		return msg;
	}
}
