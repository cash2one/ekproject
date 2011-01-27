package cn.qtone.xxt.oa.sms.SendSms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.qtone.xxt.admin.jzdx.SmsSource;
import cn.qtone.xxt.base.db.DBControl;
import cn.qtone.xxt.base.login.TeacherBean;
import cn.qtone.xxt.base.share.BaseData;
import cn.qtone.xxt.base.share.BaseResult;
import cn.qtone.xxt.base.share.PubFunction;
import cn.qtone.xxt.edu.util.Util;
import cn.qtone.xxt.jzdx.Const;
import cn.qtone.xxt.jzdx.MessageUtiltity;
import cn.qtone.xxt.oa.InitInfo;
import cn.qtone.xxt.oa.sms.SendSms.inter.ISendSmsDAO;

/**
 * ���Ͷ������ݴ�����
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class SendSmsDAO implements ISendSmsDAO {
	/**
	 * sendSms ���Ͷ���
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return BaseResult
	 */

	public BaseResult sendSmsForm(HttpServletRequest request) {
		DBControl db = new DBControl(request);
		BaseResult valueMap = new BaseResult();
		String cent = "";
		TeacherBean teacherbean = (TeacherBean) request.getSession()
				.getAttribute("teacher");
		int teacherId = teacherbean.getId();
		String teacherName = teacherbean.getUserName();
		try {
			cent = db.getValue("select cdata from xj_teacher where id="
					+ teacherId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.closePrepared();
				db = null;
			}
		}
		valueMap.putValue("teacherName", teacherName);
		valueMap.putValue("cent", cent);
		valueMap.putValue("name", request.getParameter("name"));
		valueMap.putValue("id", request.getParameter("id"));
		return valueMap;
	}

	/**
	 * 
	 * �����Զ���
	 * 
	 */
	public BaseResult sendSms(HttpServletRequest request) {
		DBControl db = new DBControl(request);
		ResultSet rs = null;
		PubFunction pf = new PubFunction();
		BaseResult br = new BaseResult();
		HttpSession session = request.getSession();
		TeacherBean teacherbean = (TeacherBean) session.getAttribute("teacher");
		String content = "��" + ("".equals(teacherbean.getSchoolShortName())?teacherbean.getSchoolName():teacherbean.getSchoolShortName())+ "��"
				+ pf.sqlStr(request.getParameter("content"));

		String sFilter1 = cn.qtone.xxt.jzdx.MessageUtiltity.checkSms1(db,
				content);
		if (!"".equals(Util.valueOf(sFilter1))) {
			br.setMessage("���Ͷ����к���" + sFilter1.substring(1)
					+ "�����ۣ�������˴�������Ӱ�죬���޸ĺ��ٷ��ͣ�");
			if (db != null)
				db.closePrepared();
			db = null;
			return br;
		}

		// ����2
		String sFilter2 = cn.qtone.xxt.jzdx.MessageUtiltity.checkSms2(db,
				content);
		if (!"".equals(Util.valueOf(sFilter2))) {
			br.setMessage("���Ͷ����к���" + sFilter2 + "�����ۣ�������˴�������Ӱ�죬���޸ĺ��ٷ��ͣ�");
			if (db != null)
				db.closePrepared();
			db = null;
			return br;
		}
		// �޸���ҳ����TO_ID����Ե��Զ��ŵ���Ӧ�޸� caijunxiong 071016
		String receiverIds = request.getParameter("TO_ID"); // ������id
		String receiverName = request.getParameter("TO_NAME"); // ����������
		String teacher_id = receiverIds.split("_")[0];
		StringBuffer name_buf = new StringBuffer();
		StringBuffer id_buf = new StringBuffer();
		int sendId = teacherbean.getId();
		String sendName = teacherbean.getUserName();
		String sql;
		int state = 0;
		int count = 0;
		try {
			sql = "select id,USERNAME from xj_teacher where id in("
					+ teacher_id.substring(0, teacher_id.length() - 1) + ")";
			db.prepareDB(sql);
			rs = db.preparedQuery();
			while (rs.next()) {
				name_buf.append(rs.getString("USERNAME"));
				name_buf.append(",");
				id_buf.append(rs.getString("id"));
				id_buf.append(",");
				receiverName = receiverName.replaceFirst(rs
						.getString("USERNAME")
						+ ",", "");
			}
			closeRS(rs);
			closeDBPre(db);
			String[] arr_id = id_buf.toString().split(",");
			String[] arr_name = name_buf.toString().split(",");
			sql = "insert into oa_computersms(receiverid,receivername,content,sendtime,senderid,sendername,kind)"
					+ " values(?,?,?,sysdate,?,?,?)";
			db.prepareDB(sql);
			for (int i = 0; i < arr_id.length; i++) {
				db.setInt(1, Integer.parseInt(arr_id[i]));
				db.setString(2, arr_name[i]);
				db.setString(3, content);
				db.setInt(4, sendId);
				db.setString(5, sendName);
				db.setInt(6, 0);
				db.preparedAddBatch();
				count++;
			}
			if (count != 0) {
				db.PreparedExeBatch();
			}
			if (db != null) {
				db.clearBatch();
			}
			state = 1;

			if (count == 0) {
				br.setMessage("ͨѶ¼�����ϵ�˲��ܷ��͵��Զ��ţ�");
			} else {
				String message = "";
				if (receiverName.intern() != "") {
					message = "��"
							+ receiverName.substring(0,
									receiverName.length() - 1)
							+ "����ͨѶ¼�����ϵ�ˣ����ܷ��͵��Զ��ţ�����";
				}
				br.setMessage(message + "���ͳɹ�!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			if (db != null) {
				db.closePrepared();
				db = null;
			}
			teacherbean = null;
			id_buf = null;
			name_buf = null;
			// idsTypeArray = null;
			// namesArray = null;
		}
		br.setState(state);

		return br;
	}

	/**
	 * 
	 * 
	 * 
	 * �ն����б�
	 * 
	 * @param request
	 * @return
	 */
	public BaseResult recSmsList(HttpServletRequest request) {
		DBControl db = new DBControl(request);
		BaseResult br = new BaseResult();
		BaseData bd = new BaseData();
		HttpSession session = request.getSession();
		TeacherBean teacherbean = (TeacherBean) session.getAttribute("teacher");
		int userId = teacherbean.getId();
		String querySql = "select id,sendername,senderid,content,sendtime,isnew,kind from oa_computersms where   receiverid="
				+ userId + " order by sendtime desc";
		InitInfo.print(querySql);
		db.prepareDB(querySql);
		ResultSet rs = null;
		int i = 0;
		try {
			rs = db.preparedQuery();
			while (rs.next()) {
				bd.addRow();
				bd.setField(i, "kind", rs.getInt("kind") + "");
				bd.setField(i, "sendername", rs.getString("sendername"));
				bd.setField(i, "senderid", rs.getInt("senderid") + "");
				bd.setField(i, "content", rs.getString("content"));
				bd.setField(i, "sendtime", InitInfo.getHMS(rs
						.getString("sendtime")));
				bd.setField(i, "isnew", rs.getInt("isnew") + "");
				bd.setField(i, "id", rs.getInt("id") + "");
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if (db != null) {
				db.closePrepared();
				db = null;
			}

			teacherbean = null;
		}
		br.putValue("iFlag", i + "");
		br.putValue("bd", bd);

		return br;
	}

	/**
	 * 
	 * 
	 * ɾ���б����
	 */

	public BaseResult delSms(HttpServletRequest request) {
		DBControl db = new DBControl(request);
		PubFunction pf = new PubFunction();
		BaseResult br = new BaseResult();
		HttpSession session = request.getSession();
		TeacherBean teacherbean = (TeacherBean) session.getAttribute("teacher");
		int userId = teacherbean.getId();
		String id = pf.IsNull(request.getParameter("id"));
		String s = pf.isNull(request.getParameter("s"));
		if ("".equals(s))
			br.putValue("s", "2");
		String sql = "";
		if (!"".equals(id))
			sql = "delete from oa_computersms where id=" + id;
		else {
			if ("2".equals(s)) // ɾ�����ж���
				sql = "delete from oa_computersms where senderid=" + userId
						+ " and sendtime<sysdate";
			else
				sql = "delete from oa_computersms where receiverid=" + userId
						+ " and sendtime<sysdate";
		}
		int state = 0;
		InitInfo.print(sql);
		try {
			db.prepareDB(sql);
			state = db.preparedExeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (db != null) {
				db.closePrepared();
				db = null;
			}

		}
		br.setState(state);

		return br;
	}

	/**
	 * 
	 * �ظ�����
	 * 
	 * 
	 */

	public BaseResult replySms(HttpServletRequest request) {
		DBControl db = new DBControl(request);
		PubFunction pf = new PubFunction();
		BaseResult br = new BaseResult();
		HttpSession session = request.getSession();
		TeacherBean teacherbean = (TeacherBean) session.getAttribute("teacher");
		int userId = teacherbean.getId();
		String sendname = teacherbean.getUserName();
		String receivername = pf.sqlStr(request.getParameter("receivername"));
		String receiverid = pf.sqlStr(request.getParameter("receiverid"));
		String content = pf.sqlStr(request.getParameter("content"));
		String sql = "insert into oa_computersms(receiverid,receivername,content,sendtime,senderid,sendername,kind) values("
				+ receiverid
				+ ",'"
				+ receivername
				+ "','"
				+ content
				+ "',sysdate," + userId + ",'" + sendname + "',0)";
		int state = 0;
		try {
			db.prepareDB(sql);
			state = db.preparedExeUpdate();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (db != null) {
				db.closePrepared();
				db = null;
			}
			teacherbean = null;
		}
		br.setState(state);

		return br;
	}

	/**
	 * 
	 * AJAX������Ϣ
	 * 
	 * ���¶��� ��kind<=4 ֱ�ӷ��ض���msg ��û�ж��ŷ���msg=null (br����ֵ) ������Ϣ ����msg=null(br����ֵ)
	 * 
	 * 
	 */

	public BaseResult flashPage(HttpServletRequest request) {
		HttpSession session = request.getSession();
		TeacherBean teacherbean = (TeacherBean) session.getAttribute("teacher");
		int userId = teacherbean.getId();
		BaseResult br = new BaseResult();
		DBControl db = new DBControl(request);
		String sysTime = InitInfo.getSysTime(); // ��ǰϵͳʱ��
		String sql = "select id ,sendername,sendtime ,kind ,senderid,content from oa_computersms where isnew=1  and receiverid ="
				+ userId
				+ " and ((kind<5 and sendtime<=to_date('"
				+ sysTime
				+ "','YYYY-MM-DD HH24-MI-SS') )   or (kind>=5 and sendtime<=to_date('5000-12-31 23-59-59','YYYY-MM-DD HH24-MI-ss'))) order by kind , sendtime  desc";

		ResultSet rs = null;
		StringBuffer msg = new StringBuffer();
		try {
			db.prepareDB(sql);
			rs = db.preparedQuery();
			if (rs != null && rs.next()) {
				msg.append(rs.getInt("id")).append("~").append(
						rs.getString("sendername")).append("~").append(
						InitInfo.getHMS(rs.getString("sendtime"))).append("~")
						.append(rs.getString("senderid")).append("~").append(
								rs.getString("content")).append("~").append(
								rs.getString("kind"));
				int id = rs.getInt("id");
				int kind = rs.getInt("kind");
				if (kind > 4) {
					if (ifshow(kind, id, sysTime, db) == 1) {
						br.putValue("msg", msg.toString());
					} else {
						msg = null;
					}

				} else {
					br.putValue("msg", msg.toString());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
			if (db != null) {
				db.closePrepared();
				db = null;
			}
		}
		return br;
	}

	public BaseResult showSms(HttpServletRequest request) { // �˷���û�е��� ������������ݿ�
		String id = request.getParameter("id");
		DBControl db = new DBControl(request);
		BaseResult br = new BaseResult();
		ResultSet rs = null;
		String sysTime = InitInfo.getSysTime(); // ��ǰϵͳʱ��
		String year = sysTime.substring(0, 4); // ʱ����
		String newtime = null; // ��ʾʱ��
		String sql = "select id,sendername,sendtime ,kind ,senderid,content from oa_computersms where id="
				+ id;
		db.prepareDB(sql);
		try {
			rs = db.preparedQuery();
			if (rs.next()) {
				br.putValue("id", rs.getString("id"));
				if (rs.getInt("kind") >= 5)
					newtime = year
							+ InitInfo.getHMS(rs.getString("sendtime")).substring(4);
				else
					newtime = InitInfo.getHMS(rs.getString("sendtime"));
				br.putValue("sendtime", newtime);
				br.putValue("kind", rs.getString("kind"));
				br.putValue("sendername", rs.getString("sendername"));
				br.putValue("senderid", rs.getString("senderid"));
				br.putValue("content", rs.getString("content"));
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				rs = null;

			}
			if (db != null) {
				db.closePrepared();
				db = null;

			}

		}

		return br;
	}

	public BaseResult setknow(HttpServletRequest request, int id) {
		DBControl db = new DBControl(request);
		BaseResult br = new BaseResult();
		String sql = "update oa_computersms set isnew=0,receivetime=sysdate where id="
				+ id;

		int state = 1;
		try {
			db.prepareDB(sql);
			db.preparedExeUpdate();
		} catch (Exception e) {
			state = 0;
			e.printStackTrace();

		} finally {
			if (db != null) {
				db.closePrepared();
				db = null;
			}
		}
		br.setState(state);
		return br;
	}

	/**
	 * kind �������� now��ǰʱ�� ������ʱ����Ƚϵ�3000��ͷ��ʱ��
	 * 
	 * nowϵͳʱ�� Rtime���ݿ�Ĳ���ʱ�� id����ID
	 * 
	 */

	private int ifshow(int kind, int id, String now, DBControl db) { //
		int isAlert = 0; // �Ƿ����� 0��1��
		String newSendTime = null;
		String userid = "";
		String userName = "";
		String content = "";
		String Rtime = ""; // ���ݿ��ʱ��3000��ͷ
		String sql = "select receiverid,receivername,content,sendtime from  oa_computersms  where id="
				+ id;
		ResultSet rs = null;
		try {
			db.prepareDB(sql);
			rs = db.preparedQuery();
			if (rs.next()) {
				userid = rs.getString(1);
				userName = rs.getString(2);
				content = rs.getString(3);
				Rtime = rs.getString(4);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
				rs = null;
			}
		}
		// InitInfo.print(Rtime);
		String realtime = now.substring(0, now.indexOf("-"))
				+ Rtime.substring(now.indexOf("-")); // ��װ���µĶ�������ʱ��
		if (!now.substring(0, now.indexOf(" ")).equals(
				realtime.substring(0, realtime.indexOf(" ")))) {
			String updateSmsSql = "delete from oa_computersms where id=" + id; // ����ö����Թ��ڣ��ͽ���ɾ�����ٲ����µĶ���
			db.prepareDB(updateSmsSql);
			db.preparedExeUpdate();
			String remindTime = realtime.substring(realtime.indexOf(" ") + 1);
			String currentHms = now.substring(now.indexOf(" ") + 1); // ��ǰʱ���ʱ����
			Date currentDate = InitInfo.parseDate(now); // ��ǰʱ��ת��Ϊ���ڸ�ʽ
			Date updateDate = InitInfo.parseDate(realtime); // ����ʱ��ת��Ϊ���ڸ�ʽ
			if (kind == 5) { // ÿ������һ��
				if (remindTime.compareTo(currentHms) < 0) { // �������ʱ��С�ڵ�ǰʱ��,��Ϊ��һ������
					newSendTime = "3000"
							+ InitInfo.getSpecifiedDay(currentDate, 1) + " "
							+ remindTime;
				} else { // �������ʱ����ڵ��ڵ�ǰʱ�䣬��Ϊ��ǰ����
					newSendTime = "3000"
							+ InitInfo.getSpecifiedDay(currentDate, 0) + " "
							+ remindTime;
				}
			} else if (kind == 6) { // ����
				int currentDayOfWeek = InitInfo.getDayOfWeek(currentDate);
				int remindDate = InitInfo.getDayOfWeek(updateDate); // ��ǰ�������ڼ�
				if (remindDate == currentDayOfWeek) { // �����������ڼ����ճ��������ѵ����ڼ�һ��
					if (remindTime.compareTo(currentHms) < 0) { // �������ʱ��С�ڵ�ǰʱ��,��Ϊ�¸���������
						newSendTime = "3000"
								+ InitInfo.getSpecifiedDay(currentDate, 7)
								+ " " + remindTime;
					} else { // �������ʱ����ڵ��ڵ�ǰʱ�䣬��Ϊ��������
						newSendTime = "3000"
								+ InitInfo.getSpecifiedDay(currentDate, 0)
								+ " " + remindTime;
					}
				} else if (remindDate < currentDayOfWeek) { // ������ѵ����ڼ�С�ڵ�������ڼ�
					newSendTime = "3000"
							+ InitInfo.getSpecifiedDay(currentDate, 7
									- currentDayOfWeek + remindDate) + " "
							+ remindTime;
				} else { // ������ѵ����ڼ����ڵ�������ڼ�
					newSendTime = "3000"
							+ InitInfo.getSpecifiedDay(currentDate, remindDate
									- currentDayOfWeek) + " " + remindTime;
				}
			} else if (kind == 7) { // ����
				int currentDay = currentDate.getDate();
				int iRemindDate = updateDate.getDate();
				String remindDate = String.valueOf(iRemindDate);
				remindDate = (remindDate.length() == 1) ? ("0" + remindDate)
						: remindDate;
				if (currentDay == iRemindDate) { // ����������ֵ���������յ�ֵ
					if (remindTime.compareTo(currentHms) < 0) { // �������ʱ��С�ڵ�ǰʱ��,��Ϊ�¸�������
						newSendTime = "3000"
								+ InitInfo.getSpecifiedMonth(currentDate, 1)
								+ remindDate + " " + remindTime;
					} else { // �������ʱ����ڵ��ڵ�ǰʱ�䣬��Ϊ��������
						newSendTime = "3000"
								+ InitInfo.getSpecifiedDay(currentDate, 0)
								+ " " + remindTime;
					}
				} else if (currentDay > iRemindDate) { // ����������ֵ���������յ�ֵ,��Ϊ��������
					newSendTime = "3000"
							+ InitInfo.getSpecifiedMonth(currentDate, 1)
							+ remindDate + " " + remindTime;
				} else { // ����������ֵС�������յ�ֵ,��Ϊ��������
					newSendTime = "3000"
							+ InitInfo.getSpecifiedMonth(currentDate, 0)
							+ remindDate + " " + remindTime;
				}
			}
			isAlert = 0;
		} else { // �����ǰ�����뷢���������,����¸ö��ŵķ�����ݣ�Ȼ���ٲ���һ���µĶ���
			String updateSmsSql = "update oa_computersms set sendtime=to_date('"
					+ InitInfo.getHMS(realtime)
					+ "', 'YYYY-MM-DD HH24:MI:SS') where id=" + id;
			db.prepareDB(updateSmsSql);
			db.preparedExeUpdate();
			java.util.Date d = InitInfo.parseDate(realtime);
			String day = String.valueOf(d.getDate());
			day = (day.length() < 2) ? ("0" + day) : day;
			String remindTime = realtime.substring(realtime.indexOf(" ") + 1);
			if (kind == 5) { // ����
				newSendTime = "3000" + InitInfo.getSpecifiedDay(d, 1) + " "
						+ remindTime;
			} else if (kind == 6) { // ����
				newSendTime = "3000" + InitInfo.getSpecifiedDay(d, 7) + " "
						+ remindTime;
			} else if (kind == 7) { // ����
				newSendTime = "3000" + InitInfo.getSpecifiedMonth(d, 1) + day
						+ " " + remindTime;
			}
			isAlert = 1;
		}
		newSendTime = newSendTime.substring(0, 19);
		StringBuffer insertComputersmsSql = new StringBuffer(
				"insert into oa_computersms(receiverid,receivername,content,isnew,sendtime,senderid,sendername,kind) ");
		insertComputersmsSql.append("values(" + userid + ",'" + userName
				+ "','" + content + "',1,to_date('" + newSendTime
				+ "','yyyy-mm-dd hh24:mi:ss')," + userid + ",'" + userName
				+ "'," + kind + ")");
		db.prepareDB(insertComputersmsSql.toString()); // �������Ѷ���Ϣ,��������kind��5-7�ֱ��ʾ�������Ѷ��ţ��������Ѷ��ţ��������Ѷ���
		InitInfo.print(insertComputersmsSql.toString());
		db.preparedExeUpdate();
		db.closePrepared();
		return isAlert;
	}

	/**
	 * 
	 * ȡ����ʾ
	 * 
	 * 
	 * 
	 */
	public BaseResult setknow(HttpServletRequest request) {
		String id = request.getParameter("id");
		return this.setknow(request, Integer.parseInt(id));
	}

	/**
	 * 
	 * �ظ���
	 * 
	 */
	public BaseResult replySmsForm(HttpServletRequest request) {
		BaseResult br = new BaseResult();
		br.putValue("receivername", request.getParameter("replyname"));
		br.putValue("receiverid", request.getParameter("replyid"));
		int id = Integer.parseInt(request.getParameter("id"));
		this.setknow(request, id);
		return br;
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * ���ֻ��������ݲ���OA���ű���
	 * 
	 * 
	 * 
	 * 
	 */
	private void sendOA_MobileMsg(String receiverIds, String receiverName,
			String content, TeacherBean teacherbean, DBControl db, String ip)
			throws SQLException {
		String[] idsArray = receiverIds.split("_"); // �ֱ���ѧУ����ʦid��ͨѶ¼����ϵ�˵�id
		String[] idsSchool = idsArray[0].split(",");
		String[] idsOther = idsArray[1].split(",");
		String[] namesArray = receiverName.split("_"); // �û���������
		String[] namesSchool = namesArray[0].split(",");
		String[] namesOther = namesArray[1].split(",");

		int sendId = teacherbean.getId();
		String sendName = teacherbean.getUserName();
		String sql = "insert into oa_mobile_msg(receiverid,receivername,content,sendtime,senderid,sendername,type,ip) values(?,?,?,sysdate,?,?,?,?)";
		try {
			db.prepareDB(sql);
			if (idsSchool != null && !"".equals(idsSchool)) {
				for (int i = 0; i < idsSchool.length; i++) {

					db.setInt(1, Integer.parseInt(idsSchool[i]));
					db.setString(2, namesSchool[i]);
					db.setString(3, content);
					db.setInt(4, sendId);
					db.setString(5, sendName);
					db.setInt(6, 0);
					db.setString(7, ip);
					db.preparedAddBatch();
				}
			}

			if (idsOther != null && !"".equals(idsOther)) {
				for (int i = 0; i < idsOther.length; i++) {
					db.setInt(1, Integer.parseInt(idsOther[i]));
					db.setString(2, namesOther[i]);
					db.setString(3, content);
					db.setInt(4, sendId);
					db.setString(5, sendName);
					db.setInt(6, 1);
					db.setString(7, ip);
					db.preparedAddBatch();
				}
			}

			db.PreparedExeBatch();
			db.clearBatch();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closePrepared();
			namesArray = null;
			idsArray = null;
			idsSchool = null;
			idsOther = null;
			namesSchool = null;
			namesOther = null;
		}
	}

	/**
	 * 
	 * 
	 * ��OA�ڲ����Ų������Ⱥ������
	 * 
	 * 
	 */

	public BaseResult sendMobileMsg(HttpServletRequest request) {
		PubFunction pf = new PubFunction();
		BaseResult br = new BaseResult();
		HttpSession session = request.getSession();
		TeacherBean teacherbean = (TeacherBean) session.getAttribute("teacher");
		String sql = "";
		String content = "��" + ("".equals(teacherbean.getSchoolShortName())?teacherbean.getSchoolName():teacherbean.getSchoolShortName()) + "��" + pf.sqlStr(request.getParameter("content"));
		String receiverIds = Util.valueOf(request.getParameter("TO_ID")); // ������id
		StringBuffer temp_receiverIds = new StringBuffer();
		StringBuffer temp_receiverName = new StringBuffer();
		String tempIds; // ���Ҫ��OA�ֻ����ű����˵�ID
		String tempNames; // ���Ҫ��OA�ֻ����ű����˵�����
		int sendId = teacherbean.getId();
		String sendname = teacherbean.getUserName();
		String tName = Util.valueOf(request.getParameter("tname"));
		String area = "cs";// teacherbean.getAreaAbb();
		String strMessageCode=teacherbean.getMessageCode();
		int schoolid = teacherbean.getSchoolId();
		String strSenderTeacherCode = teacherbean.getCode();
		String withPostfix = request.getParameter("ispostfix"); //�Ƿ��ڶ������ݺ�����Ͻ�ʦ����
		
		ArrayList<String> tea_mphone = new ArrayList<String>(); // ���ն��ŵı�У��ʦ����
		ArrayList<String> link_mphone = new ArrayList<String>(); // ���ն��ŵ�ͨѶ¼����
		ArrayList list_mphone = new ArrayList(); // ���ն��ŵ��ֶ��������
		DBControl db = new DBControl(request);
		String sch_code = this.getSchoolCode(db,teacherbean.getSchoolId());
		
		int cent = 0; // ��ʦʣ�����(cjx)
		int sms_sum = 0;// ���ͳ�����ͨѶ¼��Ķ���������۳�������(cjx)
		String strIsLong=Util.valueOf(request.getParameter("islong"));//�Ƿ񳤶���
		String str_phone = Util.valueOf(request.getParameter("TO_PHONE"));// ���봮(cjx)
		String[] arr_phone = null;
		StringBuffer return_buf = new StringBuffer();// ������������ƶ����룬���صĴ�����������͸���ͨѶ¼��
		if("1".equals(withPostfix)){
			content += "/" + tName;
		}
		String sFilter1 = cn.qtone.xxt.jzdx.MessageUtiltity.checkSms1(db, content);
		if (!"".equals(Util.valueOf(sFilter1))) {
			br.setMessage("���Ͷ����к���" + sFilter1.substring(1) + "�����ۣ�������˴�������Ӱ�죬���޸ĺ��ٷ��ͣ�");
			if (db != null)
				db.closePrepared();
			db = null;
			return br;
		}
		// ����2
		String sFilter2 = cn.qtone.xxt.jzdx.MessageUtiltity.checkSms2(db, content);
		if (!"".equals(Util.valueOf(sFilter2))) {
			br.setMessage("���Ͷ����к���" + sFilter2 + "�����ۣ�������˴�������Ӱ�죬���޸ĺ��ٷ��ͣ�");
			if (db != null)
				db.closePrepared();
			db = null;
			return br;
		}
		int state = 0;
		String sqlStr = "";
		ResultSet rs = null;
		int i = 0;
		String mphone = "";
		StringBuffer allName = new StringBuffer();
		StringBuffer allId = new StringBuffer();
		try {
			// �Ż�����TO_ID����Ϊ��"_"�ָ���ʦ��ͨѶ¼�Ľ�����ID 071016 caijunxiong
			String[] obj_id = receiverIds.split("_");
			String teaIdStr = ""; // �����ֻ����ŵı�У��ʦ
			String linkIdStr = ""; // �����ֻ����ŵ�ͨѶ¼��ϵ��ID
			if (obj_id.length > 0) {
				teaIdStr = obj_id[0];
				if (obj_id.length > 1) {
					linkIdStr = obj_id[1];
				}
			}
			if (!"".equals(teaIdStr)) {
				sqlStr = "select mphone||'_'||code||'_'||id,id,username from xj_teacher where id in("
						+ teaIdStr.substring(0, teaIdStr.length() - 1) + ")";
				db.prepareDB(sqlStr);
				rs = db.preparedQuery();
				while (rs.next()) {
					mphone = rs.getString(1);
					allName.append(rs.getString(3));
					allName.append(",");
					allId.append(rs.getString(2));
					allId.append(",");
//					if (cn.qtone.xxt.jzdx.PubFunction.getPhoneType(mphone.split("_")[0]) != 0) { // ���ƶ�
//						return_buf.append(rs.getString(3)).append(",");
//						continue;
//					}
					tea_mphone.add(mphone);
					temp_receiverIds.append(",").append(rs.getInt(2));
					temp_receiverName.append(",").append(rs.getString(3));
				}
				closeRS(rs);
				closeDBPre(db);
			}
			if (str_phone.indexOf("��") != -1) {
				arr_phone = str_phone.split("��");
			} else {
				arr_phone = str_phone.split(",");
			}
			if (!"".equals(allId.toString())) {
				String[] arr_id = allId.substring(0, allId.length() - 1).split(",");
				String[] arr_name = allName.substring(0, allName.length() - 1).split(",");
				sql = "insert into oa_computersms(receiverid,receivername,content,sendtime,senderid,"
						+ "sendername,kind) values(?,?,?,sysdate,?,?,?)";
				db.prepareDB(sql);
				int count = 0;
				for (i = 0; i < arr_id.length; i++) {
					db.setInt(1, Integer.parseInt(arr_id[i]));
					db.setString(2, arr_name[i]);
					db.setString(3, content);
					db.setInt(4, sendId);
					db.setString(5, sendname);
					db.setInt(6, 0);
					db.preparedAddBatch();
					count++;
				}
				if (count != 0) {
					db.PreparedExeBatch();
				}
				if (db != null) {
					db.clearBatch();
				}
			}
			cent = Integer.parseInt(db.getValue("select cdata from xj_teacher where id=" + sendId)); // ��ý�ʦ����(cjx)
			// add by chenqian 0322�������ͨѶ¼������ֻ�������ȷ����ϵ��

			if (temp_receiverIds.length() < 1) {
				temp_receiverIds.append(",");
				temp_receiverName.append(",");
			}

			temp_receiverIds.append(",_"); // ��'_'Ϊ����ͨѶ¼�͸���ͨѶ¼�ķָ��
			temp_receiverName.append(",_");

			if (!"".equals(linkIdStr)) {
				String group_type = "";
				sqlStr = "select a.id id,a.mobile mobile,a.linkman_name linkman_name,a.linkman_type grouptype from oa_linkman_info a where a.id in("
						+ linkIdStr.substring(0, linkIdStr.length() - 1) + ") ";
				db.prepareDB(sqlStr);
				rs = db.preparedQuery();
				while (rs.next()) {
					mphone = rs.getString("mobile");
//					if (cn.qtone.xxt.jzdx.PubFunction.getPhoneType(mphone) != 0) {
//						return_buf.append(rs.getString("linkman_name")).append(",");
//						continue;
//					}
					group_type = Util.valueOf(rs.getString("grouptype"));
					if (group_type.equals("2")) { // ����ͨѶ¼
						link_mphone.add(mphone + "_" + Util.valueOf(rs.getString("id")));
						temp_receiverIds.append(rs.getInt("id")).append(",");
						temp_receiverName.append(rs.getString("linkman_name")).append(",");
					} else { // ����ͨѶ¼
						// modify by caijunxiong 070613 �ж��Ƿ��з���ʹ��
						if (sms_sum < cent) { // ���뷢�ʹ�
							link_mphone.add(mphone + "_" + Util.valueOf(rs.getString("id")));
							temp_receiverIds.append(rs.getInt("id")).append(",");
							temp_receiverName.append(rs.getString("linkman_name")).append(",");
							sms_sum++;
						} else { // ���뷵�ش�
							return_buf.append(rs.getString("linkman_name")).append(",");
						}
					}
				}
				closeRS(rs);
				closeDBPre(db);
			}
			String obj_mobile = "";
			for (int j = 0; j < tea_mphone.size(); j++) {
				obj_mobile += String.valueOf(tea_mphone.get(j)).split("_")[0]+ ",";
			}
			for (int j = 0; j < link_mphone.size(); j++) {
				String temp = link_mphone.get(j).toString();
				if (temp.length() >= 11) {
					temp = temp.substring(0, 11);
				}
				obj_mobile += temp + ",";
			}
			obj_mobile += str_phone;
			// add by caijunxiong 070613 ���봮�Ĵ���
			if (str_phone.indexOf("��") != -1) {
				arr_phone = str_phone.split("��");
			} else {
				arr_phone = str_phone.split(",");
			}
			String temp_phone = "";
			if (arr_phone != null && arr_phone.length > 0 && !"".equals(arr_phone[0])) {
				for (i = 0; i < arr_phone.length; i++) {
					// �ж��Ƿ�Ϸ��ĺ���
					if (!cn.qtone.xxt.jzdx.PubFunction.checkIncorrectPhone(arr_phone[i])) {
						return_buf.append(arr_phone[i]).append(",");
						continue;
					}
					temp_phone = String.valueOf(arr_phone[i].trim());
					if (sms_sum < cent) {
//						if (cn.qtone.xxt.jzdx.PubFunction.getPhoneType(temp_phone) != 0) {
//							return_buf.append(temp_phone).append(",");
//							continue;
//						}
						list_mphone.add(temp_phone);
						temp_receiverIds.append("-1,");
						temp_receiverName.append(temp_phone).append(",");
						sms_sum++;
					} else {
						return_buf.append(temp_phone).append(",");
					}
				}
			}
			if (sms_sum < 1) {
				temp_receiverIds.append(",");
				temp_receiverName.append(",");
			}
			tempIds = temp_receiverIds.toString().substring(1);
			tempNames = temp_receiverName.toString().substring(1);
			temp_receiverIds = null;
			temp_receiverName = null;

			
			//���㱾�η��͵�����
            int currentSendNum = 0;
            if(receiverIds!=null){
            	String[] temp = receiverIds.split("_");
            	for(String str:temp){
            		currentSendNum+=str.split(",").length;
            	}
            }
            if(null!=str_phone){
            	currentSendNum+=str_phone.split(",").length;
            }
            
			boolean isNeedAudit = currentSendNum>1?isNeedTransformToAuditingModule(db,teacherbean,content,currentSendNum,receiverIds,str_phone):false;
			if(isNeedAudit){
				br.setMessage("���Ͷ�����������ϵͳ���������ƣ���Ҫ������Ա��˺������ɷ��͡�");
			}else{
				
				//����Ҫ��˵�ֱ�ӷ��Ͷ���
				sql = "insert into dx_groupsend(source_mobile,object_mobile,create_time,content,school_id,"
						+ "user_id,area_abb,kind,sp_id,tran_code,flag,islong) values(?,?,sysdate,?,?,?,?,0,?,?,?,?)";
				i = 0;
				db.prepareDB(sql);
				String[] idAndMobile = null;
				boolean flag = false;
				while (i < link_mphone.size()) { // ͨѶ¼,�ɻظ�
					idAndMobile = String.valueOf(link_mphone.get(i)).split("_");
					//getOaSourceLinkMan(code_hb��code_jx)
	//				db.setString(1, SmsSource.getOaSourceLinkMan(idAndMobile[1], strSenderTeacherCode+idAndMobile[1]));
					db.setString(1, SmsSource.getOaSourceLinkMan(idAndMobile[1], sch_code + strSenderTeacherCode));
					db.setString(2, String.valueOf(idAndMobile[0]));
					db.setString(3, content);
					db.setInt(4, schoolid);
					db.setInt(5, sendId);
					db.setString(6, area);
					db.setString(7, Const.getSpId());
					db.setString(8, Const.getTranCode());
					db.setInt(9, 1);
					db.setString(10, strIsLong);
					db.preparedAddBatch();
					flag = true;
					i++;
				}
				i = 0;
				while (i < tea_mphone.size()) { // ��У��ʦ,�ɻظ�
					db.setString(1, SmsSource.getOaSourceTeacher(String.valueOf(tea_mphone.get(i)).split("_")[1], sch_code+strSenderTeacherCode));
					db.setString(2, String.valueOf(tea_mphone.get(i)).split("_")[0]);
					db.setString(3, content);
					db.setInt(4, schoolid);
					db.setInt(5, sendId);
					db.setString(6, area);
					db.setString(7, Const.getSpId());
					db.setString(8, Const.getTranCode());
					db.setInt(9, 1);
					db.setString(10, strIsLong);
					db.preparedAddBatch();
					flag = true;
					i++;
				}
				i = 0;
				
				while (i < list_mphone.size()) { // �ֶ��������,�ɻظ�
	//				db.setString(1, SmsSource.getOaSourceOther("0", strSenderTeacherCode+schoolid));
					db.setString(1, SmsSource.getOaSourceOther("0", sch_code + strSenderTeacherCode));
					db.setString(2, String.valueOf(list_mphone.get(i)));
					db.setString(3, content);
					db.setInt(4, schoolid);
					db.setInt(5, sendId);
					db.setString(6, area);
					db.setString(7, Const.getSpId());
					db.setString(8,Const.getTranCode());
					db.setInt(9, 1);
					db.setString(10, strIsLong);
					db.preparedAddBatch();
					flag = true;
					i++;
				}
				if (flag == false) {
					br.setMessage("���ڽ��ܺ���Ƿ��������ĵ���������ԭ��,�ֻ����ŷ���ʧ�ܣ�");
					br.setState(0);
					closeRS(rs);
					closeDB(db);
					return br;
				}
				db.PreparedExeBatch();
				db.clearBatch();
				db.closePrepared();
				String ip = request.getRemoteAddr();
				sendOA_MobileMsg(tempIds, tempNames, content, teacherbean, db, ip);
				state = 1;
				if (return_buf.length() > 0) {
					br.setMessage("���ͳɹ�(���ڽ������ֻ�����Ϊ���ƶ���������ĵ���������ԭ��" + return_buf.substring(0, return_buf.length() - 1)
							+ "û�з��ͳɹ�)");
				} else {
					br.setMessage("���ͳɹ���");
				}
				cent = cent - sms_sum;
				if (cent < 0)
					cent = 0;
				sql = "update xj_teacher set cdata=" + cent + " where id=" + sendId;
				db.prepareDB(sql);
				db.preparedExeUpdate();
				db.closePrepared();
	
			}//end if else  ������Ҫ��ˣ�			
		} catch (Exception e) {
			state = 0;
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			allId = null;
			allName = null;
			closeRS(rs);
			closeDB(db);
			return_buf = null;
			clearList(link_mphone);
			clearList(tea_mphone);
			clearList(link_mphone);
		}
		br.setState(state);
		return br;
	}

	private String getSchoolCode(DBControl db, int schoolId) {
		String sql = "select sch_code from xj_school where id=" + schoolId;
		String sch_code = Util.valueOf(db.getValue(sql));
		return sch_code;
	}

	private String getTeacherSICode(HttpServletRequest request, TeacherBean teacherbean) {
		DBControl db = new DBControl(request);
		String sql="select a.code from qx_admin_si a ,qx_user_school b, xj_teacher c where " +
		"b.school_id=c.school_id and b.area_id=a.area_id and a.id=b.user_id and c.id="+teacherbean.getId();
		String sicode=db.getValue(sql);
		return sicode;
	}

	public void closeRS(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
			}
			rs = null;
		}
	}

	public void closeDBPre(DBControl db) {
		if (db != null) {
			db.closePrepared();
		}
	}

	public void closeDB(DBControl db) {
		if (db != null) {
			db.closePrepared();
			db = null;
		}
	}

	public void clearList(List list){
		if(list!=null){
			list.clear();
			list=null;
		}
	}
	
	
    /**
     * 
     *�ж��Ƿ�� OA����ת������˱��У��ж��Ƿ񳬳�Ⱥ�������������ã�
     * ���緢�ͣ�Ⱥ����Ŀ��Ķ�����>�����õ�Ⱥ����������������Ҫ�Ѷ���ת�浽��ȷ�ϱ�dx_admin_confirmsms��
     * ���Ҹ���Ӧ��SI���Ͷ��ţ���֪��˴���
     * @param db
     * @param teacher
     * @param sendComment
     * @param currentSendCount
     * @param linkmanIdsStr
     * @param otherPhonesStr
     */
	public boolean isNeedTransformToAuditingModule(DBControl db,TeacherBean teacher,String sendComment,int currentSendCount,String linkmanIdsStr,String otherPhonesStr){
    	
		StringBuffer sqlBuf = new StringBuffer();
        ResultSet rs = null;
        
        boolean isAuditing = false;  //�Ƿ���Ҫ���
        
        int limite = 0;
        String siPhone = "";  
        
        try{
//    		sqlBuf.append("select a.oa_num,a.phone from si_set a ");
//    		sqlBuf.append("left join qx_admin_si b on a.si_id=b.id ");
//    		sqlBuf.append("left join qx_admin_role c on a.si_id=c.si_id ");
//    		sqlBuf.append("left join qx_admin_user d on d.role_id=c.id ");
//    		sqlBuf.append("left join qx_user_school e on e.user_id=b.id ");
//    		sqlBuf.append("where a.user_id=d.id and e.school_id=");
        	sqlBuf.append(" select d.oa_num,d.phone from xj_school a ");
        	sqlBuf.append(" left join qx_user_school b on a.id = B.SCHOOL_ID ");
            sqlBuf.append(" left join qx_admin_si c on C.ID = B.USER_ID ");
            sqlBuf.append(" left join si_set d on D.SI_ID = c.id ");
            sqlBuf.append(" where school_id=");
        	
            
    		sqlBuf.append(teacher.getSchoolId());
    		db.prepareDB(sqlBuf.toString());
			rs = db.preparedQuery();
		   
			if(rs!=null&&rs.next()){
			   limite = rs.getInt("oa_num");
		       siPhone = rs.getString("phone");
		    }
			db.closePrepared();
            rs.close();			    
		   
		    String siMessage = "��" + teacher.getSchoolName()+ "������OAȺ���Ͷ�����������" + limite + "��������ˣ�";
			sqlBuf.delete(0,sqlBuf.length());
			if(currentSendCount>limite){
				isAuditing = true;
				
				sqlBuf.append("insert into dx_admin_confirmsms(");
				sqlBuf.append("create_time,");
				sqlBuf.append("content,school_id,user_id,area_abb,sp_id,");
				sqlBuf.append("tran_code,flag,ispreparent,sms_type,check_over,deal_type,linkman_ids,object_mobile) ");
				sqlBuf.append("values(sysdate,?,?,?,?,?,?,?,6,3,0,?,?,?)");
				
				
				db.prepareDB(sqlBuf.toString());
				db.setString(1, sendComment);
				db.setInt(2, teacher.getSchoolId());
				db.setInt(3, teacher.getId());
				db.setString(4, teacher.getAreaAbb());
				db.setString(5, cn.qtone.xxt.jzdx.Const.free_sp_id);
				db.setString(6, cn.qtone.xxt.jzdx.Const.free_tran_code);
				db.setInt(7, 1);
				db.setInt(8, 1);
				db.setString(9, linkmanIdsStr);
				db.setString(10, otherPhonesStr);
				db.preparedExeDB();

				
				//������֪ͨSI
				if (cn.qtone.xxt.jzdx.PubFunction.getPhoneType(siPhone) <= 1) {
					MessageUtiltity mu = new MessageUtiltity();

					mu.saveSingleGroupSend(db, "00012", siPhone, null,
							siMessage, teacher.getSchoolId(),
							teacher.getId(), "0", teacher
							.getAreaAbb(), 1, 0, "0",
							cn.qtone.xxt.jzdx.Const.free_sp_id,
							cn.qtone.xxt.jzdx.Const.free_tran_code);
				}
				
				
			}
        }catch(Exception e){
			e.printStackTrace();
		}finally{
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				rs = null;
			}
			if (db != null) {
				db.closePrepared();
			}
			sqlBuf = null;
		}
		return isAuditing;
	}
	
	
}
