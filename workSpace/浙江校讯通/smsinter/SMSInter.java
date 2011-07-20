package cn.qtone.xxt.jzdx.smsinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.qtone.xxt.base.db.DBControl;
import cn.qtone.xxt.base.share.BaseResult;
import cn.qtone.xxt.jzdx.smsinter.bean.Info;
import cn.qtone.xxt.jzdx.smsinter.util.SMSUtil;

public class SMSInter extends HttpServlet {

	private static final long serialVersionUID = -4804624510821455062L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = null;
		ServletOutputStream out = null;
		boolean errorFlag = false;
		String responseMessage = null;
		DBControl db = new DBControl(request);
		try {
			response.setContentType("text/xml; charset=gb2312");
			// ��ȡHTTP������������
			in = new BufferedReader(new InputStreamReader(request.getInputStream(), "gb2312"));
			String message = "";
			String line;
			while ((line = in.readLine()) != null) {
				message += line;
			}
			in.close();

			// û�б���,����ֹ
			if (message.trim().equals("")) {
				errorFlag = true;
				return;
			}
			System.out.println("����ı���:"+message);

			// ��������
			SMSUtil util = new SMSUtil();
			Info info = util.parse(message);
			System.out.println(info.getIsAddParent());
			System.out.println(info.getIsLongSMS());
			// �������
			if (!util.validate(info)) {
				errorFlag = true;
				return;
			}

			BaseResult br = null;
			if (info.getSendKind().equals("1")) {
				// ȫУ����
				br = util.sendAll(db,info);
			} else if (info.getSendKind().equals("2")) {
				// ͳһ����
				br = util.sendUnite(db,info,request);
			} else if (info.getSendKind().equals("3")) {
				// ���Ի�����
				br = util.sendEach(db,info,request);
			}

			if (br == null) {
				errorFlag = true;
			}
			
			responseMessage = util.getResponseMessage(br);

		} catch (Exception e) {
			e.printStackTrace();
			errorFlag = true;
		} finally {
			db.closePrepared();
			if (errorFlag) {
				responseMessage = "<?xml version=\"1.0\" encoding=\"gb2312\" ?><logusers><loguser><result>-1</result><remark>�ܱ�Ǹ�����ŷ���ʧ�ܣ�</remark></loguser></logusers>";
			}
			out = response.getOutputStream();
			out.write(responseMessage.getBytes("gb2312"));
			out.flush();
			if (in != null)
				in.close();
			if (out != null)
				out.close();
		}

	}

}
