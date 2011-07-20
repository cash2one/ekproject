package cn.qtone.xxt.jzdx.smsinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.qtone.xxt.base.db.DBControl;
import cn.qtone.xxt.jzdx.smsinter.bean.PurviewBean;
import cn.qtone.xxt.jzdx.smsinter.util.PurviewUtil;

public class CheckPurviewInter extends HttpServlet {

	private static final long serialVersionUID = 447331174801114047L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = null;
		ServletOutputStream out = null;
		boolean errorFlag = false;
		String responseMessage = null;
		try {
			response.setContentType("text/xml; charset=gb2312");
			// 读取HTTP请求正文内容
			in = new BufferedReader(new InputStreamReader(request.getInputStream(), "gb2312"));
			String message = "";
			String line;
			while ((line = in.readLine()) != null) {
				message += line;
			}
			in.close();

			// 没有报文,则终止
			if (message.trim().equals("")) {
				errorFlag = true;
				return;
			}

			System.out.println("请求报文:" + message);

			// 解析报文
			PurviewUtil util = new PurviewUtil();
			PurviewBean bean = util.parse(message);
			// 检测数据
			if (!util.validate(bean)) {
				errorFlag = true;
				return;
			}

			// 获取role_id
			DBControl db = new DBControl(request);
			List list = db.getValues("select a.role_id,b.type_id from xj_teacher a,qx_sch_role b where a.role_id=b.id and a.id=" + bean.getUserID(), 2);
			String roleID = "0", roleType = "0";
			if (list.size() > 0) {
				roleID = (String) list.get(0);
				roleType = (String) list.get(1);
			}
			// 判断权限
			HashMap purMap = (HashMap) request.getSession().getServletContext().getAttribute("sch_pur_" + roleType);
			boolean isPurview = isPurview(bean.getFlag(), bean.getOperate(), Integer.parseInt(roleID), purMap);
			responseMessage = util.getMessage(isPurview);

			System.out.println("返回报文:" + responseMessage);
		} catch (Exception e) {
			e.printStackTrace();
			errorFlag = true;
		} finally {
			if (errorFlag) {
				responseMessage = "<?xml version=\"1.0\" encoding=\"gb2312\" ?><logusers><loguser><result>-1</result><remark>很抱歉，出现异常！</remark></loguser></logusers>";
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

	private boolean isPurview(String flag, String op, int role_id, HashMap purMap) {
		HashMap tmpMap = new HashMap();
		if (purMap == null) {
			return false;
		}
		tmpMap = (HashMap) purMap.get(flag);
		if (tmpMap == null) {
			// 连功能都没有权限，返回false
			return false;
		} else {
			// 有功能权限
			if (op == null || "".equals(op)) { // 无子功能判断
				return true;
			}
			if (tmpMap.get(op + "_btn").toString().equals("1")) {
				// 有子功能（op）的权限，返回true
				return true;
			} else {
				// 没有子功能（op）的权限，返回false
				return false;
			}
		}
	}
}
