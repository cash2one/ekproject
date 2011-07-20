package cn.qtone.xxt.jzdx.smsinter.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.qtone.xxt.jzdx.smsinter.bean.PurviewBean;

public class PurviewUtil {

	public PurviewBean parse(String message) {
		PurviewBean bean = new PurviewBean();
		try {
			// 首先读入数据,创建Document
			Document xmlDoc = DocumentHelper.parseText(message);

			// 取得XML文档的各元素及值
			Element root = xmlDoc.getRootElement();// 根元素
			Element loguserEle = root.element("loguser");// <body>
			// 获取数据，并设置Bean
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
				isPurview ? "OK" : "很抱歉，您没有操作此功能的权限！").append("</remark>").append("</loguser></logusers>");
		return xml.toString();
	}

}
