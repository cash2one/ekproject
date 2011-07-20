package cn.qtone.xxt.jzdx.smsinter.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.qtone.xxt.jzdx.smsinter.bean.PurviewBean;

public class PurviewUtil {

	public PurviewBean parse(String message) {
		PurviewBean bean = new PurviewBean();
		try {
			// ���ȶ�������,����Document
			Document xmlDoc = DocumentHelper.parseText(message);

			// ȡ��XML�ĵ��ĸ�Ԫ�ؼ�ֵ
			Element root = xmlDoc.getRootElement();// ��Ԫ��
			Element loguserEle = root.element("loguser");// <body>
			// ��ȡ���ݣ�������Bean
			Element useridEle = loguserEle.element("userid");
			bean.setUserID(toString(useridEle.getText()));
			Element flagEle = loguserEle.element("flag");
			bean.setFlag(toString(flagEle.getText()));
			Element operateEle = loguserEle.element("operate");
			bean.setOperate(toString(operateEle.getText()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return bean;
	}

	public boolean validate(PurviewBean bean) {
		if (bean == null || bean.getUserID().equals("") || bean.getFlag().equals("") || !bean.getFlag().startsWith("school") || bean.getOperate().equals("")) {
			return false;
		} else {
			return true;
		}
	}

	private String toString(String str) {
		return str == null ? "" : str.toString().trim();
	}

	public String getMessage(boolean isPurview) {
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"gb2312\" ?>").append("<logusers><loguser>").append("<result>").append(isPurview ? 1 : 0).append("</result><remark>").append(
				isPurview ? "OK" : "�ܱ�Ǹ����û�в����˹��ܵ�Ȩ�ޣ�").append("</remark>").append("</loguser></logusers>");
		return xml.toString();
	}

}
