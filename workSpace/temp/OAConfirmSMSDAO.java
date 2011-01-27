package cn.qtone.xxt.admin.jzdx.oaconfirmsms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.qtone.xxt.admin.jzdx.SmsSource;
import cn.qtone.xxt.admin.jzdx.oaconfirmsms.inter.IOAConfirmSMSDAO;
import cn.qtone.xxt.base.db.DBControl;
import cn.qtone.xxt.base.login.AdminBean;
import cn.qtone.xxt.base.login.TeacherBean;
import cn.qtone.xxt.base.share.BaseData;
import cn.qtone.xxt.base.share.BaseResult;
import cn.qtone.xxt.base.share.Paginate;
import cn.qtone.xxt.edu.util.Util;
import cn.qtone.xxt.jzdx.Const;
import cn.qtone.xxt.oa.sms.SendSms.dao.SendSmsDAO;

public class OAConfirmSMSDAO implements IOAConfirmSMSDAO {
	public OAConfirmSMSDAO() {
	}

	/**
	 * ��ѯ
	 *
	 * @author yuhl 2007-03-23
	 * @param request
	 * @return cn.qtone.xxt.base.share.BaseResult
	 * @throws java.lang.Exception
	 * @roseuid 44B1B0390271
	 */
	public BaseResult query(HttpServletRequest request) throws Exception {
		BaseResult br = new BaseResult();
		BaseData bd = new BaseData();
		DBControl db = new DBControl(request);
		ResultSet rs = null;
		String sql = "";
		cn.qtone.xxt.jzdx.PubFunction pub = new cn.qtone.xxt.jzdx.PubFunction();

		// 1.�ӹ��ܣ���ѯ���������޸ġ�ɾ����Ȩ��
		cn.qtone.xxt.base.utiltity.FunctionButton funButton = new cn.qtone.xxt.base.utiltity.FunctionButton();
		funButton.setQueryShowName("�� ��");
		br.putValue("purview", funButton.getFunBtn(db, request,"admin_jzdx_confirmsms"));
		// ���� between�����������ڣ�+1 yyyy��mm��dd��
		String beginDate = pub.sqlStr(request.getParameter("begindate"));
		br.putValue("begindate", beginDate); // 2 ��ʼ����
		String endDate = pub.sqlStr(request.getParameter("enddate"));
		br.putValue("enddate", endDate); // 3 ��������
		String keyword = pub.sqlStr(request.getParameter("keyword"));
		br.putValue("keyword", keyword); // 4 ���ݹؼ���
		String state = pub.sqlStr(request.getParameter("state"));
		if (state == null || "".equals(state)) {
			state = "-1";
		}
		br.putValue("state", state); // 6.״̬
		String sTmp1 = "", sTmp3 = "";
		AdminBean adminBean = (AdminBean) request.getSession().getAttribute("admin");
		int roleType = adminBean.getRoleType(); // �������ࣨ1��ʡ�ƶ� 2�����ƶ� 3��SI��
		
		//2008-05-15 ��ѯ�Ƿ���"add"Ȩ��
		cn.qtone.xxt.base.utiltity.CheckPurview checkPurview = new cn.qtone.xxt.base.utiltity.CheckPurview();
		boolean pur=checkPurview.isPurview(request, "admin_jzdx_oaconfirmsms", "add",adminBean.getRoleId());
		br.putValue("pur", pur+"");

		// ��ҳ
		int curPage = 1;
		if (request.getParameter("page") != null) {
			curPage = Integer.parseInt(request.getParameter("page"));
		}
		int pagesize = cn.qtone.xxt.jzdx.Const.pageSize;
		ResultSet rsRow = null;
		String sqlRow = "";
		String roll;
		String perNum = "&begindate=" + beginDate + "&enddate=" + endDate
				+ "&keyword=" + keyword + "&state=" + state;

		String whereClause = "";
		if (!beginDate.equals("")) {
			whereClause += " and a.create_time between to_date('" + beginDate
					+ "','yyyy-mm-dd hh24:mi:ss') and to_date('" + endDate
					+ "','yyyy-mm-dd hh24:mi:ss') ";
		}
		if (!keyword.equals("")) {
			whereClause += " and a.content like '%" + keyword + "%' ";
		}
		if (!state.equals("-1")) {
			whereClause += " and a.check_over=" + state;
		}

		/*sqlRow = "select count(*) from dx_admin_confirmsms a "
				+ "left join xj_school b on a.school_id=b.id "
				+ "left join xj_teacher c on a.school_id=c.school_id "
				+ "left join qx_user_school d on d.school_id=b.id "
				+ "where a.sms_type=3 and a.user_id=c.id and b.area_id="
				+ adminBean.getAreaId() + " and d.user_id="
				+ adminBean.getSIId() + whereClause;
		db.prepareDB(sqlRow);
		rsRow = db.preparedQuery();
		Paginate paginate = new Paginate(rsRow, pagesize, curPage);
		curPage = paginate.getCurPage();
		paginate.setRoll(perNum, request);
		roll = paginate.getRoll();
		br.putValue("roll", roll); // 5.��ҳ
		if (db != null) {
			db.closePrepared();
		}*/

		// ��ѯ���
		// if ("0".equals(kind)) {
		// sql = "select * from (select a.id,to_char(a.refertime,'yyyy-mm-dd
		// hh24:mi') create_time,c.school_name,b.username,"
		// + "a.method,a.timing,a.check_over,a.confirmType,"
		// + "row_number() over (order by a.refertime desc) rn from
		// confirmTaskMain a "
		// + "left join xj_teacher b on a.user_id=b.id "
		// + "left join xj_school c on b.school_id=c.id "
		// + (roleType == 3 ? " left join qx_user_school qus on
		// c.id=qus.school_id"
		// : "")
		// + " where c.id is not null "
		// + ("-1".equals(state) ? "" : " and check_over=" + state)
		// + (roleType == 1 ? "" : " and area_abb=?")
		// + (roleType == 3 ? " and qus.user_id=" + adminBean.getSIId()
		// : "")
		// + // ʡ�ƶ��û� ��ѯ���м�¼
		// sTmp1
		// + sTmp3
		// + ") where rn between "
		// + ((curPage - 1) * pagesize + 1) + " and " + curPage * pagesize;
		// // } else { // ����ԭ��
		// sql = "select * from (select a.id,to_char(a.create_time,'yyyy-mm-dd
		// hh24:mi') create_time,c.school_name,b.username,"
		// + "a.method,a.timing,a.content,a.flag,a.check_over,"
		// + "row_number() over (order by a.create_time desc) rn from "
		// + tabName
		// + " a left join xj_teacher b on a.user_id=b.id "
		// + "left join xj_school c on b.school_id=c.id "
		// + (roleType == 3 ? " left join qx_user_school qus on
		// c.id=qus.school_id"
		// : "")
		// + " where c.id is not null "
		// + ("-1".equals(state) ? "" : " and check_over=" + state)
		// + (roleType == 1 ? "" : " and area_abb=?")
		// + (roleType == 3 ? " and qus.user_id=" + adminBean.getSIId()
		// : "")
		// + // ʡ�ƶ��û� ��ѯ���м�¼
		// sTmp1
		// + sTmp3
		// + ") where rn between "
		// + ((curPage - 1) * pagesize + 1) + " and " + curPage * pagesize;
		// }

		/*sql = "select * from (select a.id,to_char(a.create_time,'yyyy-mm-dd "
				+ "hh24:mi:ss') create_time,b.school_name,c.username,content,"
				+ "a.check_over,row_number() over (order by a.create_time desc) "
				+ "rn from dx_admin_confirmsms a "
				+ "left join xj_school b on a.school_id=b.id "
				+ "left join xj_teacher c on a.school_id=c.school_id "
				+ "left join qx_user_school d on d.school_id=b.id "
				+ "where a.sms_type=3 and a.user_id=c.id and b.area_id="
				+ adminBean.getAreaId() + " and d.user_id="
				+ adminBean.getSIId() + whereClause + ") where rn between "
				+ ((curPage - 1) * pagesize + 1) + " and " + curPage * pagesize;*/
		
		if(roleType==3){
			sqlRow = "select count(*) from dx_admin_confirmsms a "
				+ "left join xj_school b on a.school_id=b.id "
				+ "left join xj_teacher c on a.school_id=c.school_id "
				+ "left join qx_user_school d on d.school_id=b.id "
				+ "where a.sms_type=3 and a.user_id=c.id and d.user_id="
				+ adminBean.getSIId() + whereClause;
			
			sql = "select * from (select a.id,to_char(a.create_time,'yyyy-mm-dd "
				+ "hh24:mi:ss') create_time,b.school_name,c.username,content,e.name,"
				+ "a.check_over,row_number() over (order by a.create_time desc) "
				+ "rn from dx_admin_confirmsms a "
				+ "left join xj_school b on a.school_id=b.id "
				+ "left join xj_teacher c on a.school_id=c.school_id "
				+ "left join qx_user_school d on d.school_id=b.id "
				+ "left join area e on b.area_id=e.id "
				+ "where a.sms_type=3 and a.user_id=c.id and d.user_id="
				+ adminBean.getSIId() + whereClause + ") where rn between "
				+ ((curPage - 1) * pagesize + 1) + " and " + curPage * pagesize;
			
		}else if(roleType==2){ //���ƶ���ѯ���б�������
			sqlRow="select count(*) from dx_admin_confirmsms a  "
				+"left join xj_school b on a.school_id=b.id "
				+"left join xj_teacher c on a.school_id=c.school_id "
				+"where a.sms_type=3 and a.user_id=c.id and b.area_id="+adminBean.getAreaId()
				+whereClause;
			sql="select * from ("
				+"select a.id,to_char(a.create_time,'yyyy-mm-dd hh24:mi:ss') create_time,b.school_name,c.username,content,d.name, "
				+"a.check_over,row_number() over (order by a.create_time desc) rn "
				+"from dx_admin_confirmsms a "
				+"left join xj_school b on a.school_id=b.id "
				+"left join xj_teacher c on a.school_id=c.school_id  "
				+"left join area d on b.area_id=d.id "
				+"where a.sms_type=3 and a.user_id=c.id and b.area_id="+adminBean.getAreaId()
				+whereClause
				+") where rn between "+ ((curPage - 1) * pagesize + 1) + " and " + curPage * pagesize;
			
		}else if(roleType==1){ //ʡ�ƶ���ѯ����
			sqlRow="select count(*) from dx_admin_confirmsms a  "
				+"left join xj_school b on a.school_id=b.id "
				+"left join xj_teacher c on a.school_id=c.school_id "
				+"where a.sms_type=3 and a.user_id=c.id "
				+whereClause;
			sql="select * from ("
				+"select a.id,to_char(a.create_time,'yyyy-mm-dd hh24:mi:ss') create_time,b.school_name,c.username,content,d.name, "
				+"a.check_over,row_number() over (order by a.create_time desc) rn "
				+"from dx_admin_confirmsms a "
				+"left join xj_school b on a.school_id=b.id "
				+"left join xj_teacher c on a.school_id=c.school_id  "
				+"left join area d on b.area_id=d.id "
				+"where a.sms_type=3 and a.user_id=c.id "
				+whereClause
				+") where rn between "+ ((curPage - 1) * pagesize + 1) + " and " + curPage * pagesize;
		}
		
		db.prepareDB(sqlRow);
		rsRow = db.preparedQuery();
		Paginate paginate = new Paginate(rsRow, pagesize, curPage);
		curPage = paginate.getCurPage();
		paginate.setRoll(perNum, request);
		roll = paginate.getRoll();
		br.putValue("roll", roll); // 5.��ҳ
		if (db != null) {
			db.closePrepared();
		}

		try {
			db.prepareDB(sql);
			rs = db.preparedQuery();
			int row = 0, tmp = 0;
			while (rs != null && rs.next()) {
				bd.addRow(); // 6 ���ݼ���
				bd.setField(row, "id", rs.getString("id")); // ID
				bd.setField(row, "op_time", Util.valueOf(rs
						.getString("create_time"))); // ����ʱ��
				bd.setField(row, "sender", Util.valueOf(rs.getString("name")+"/"+ rs.getString("school_name")
						+ "/" + rs.getString("username"))); // ������
				bd.setField(row, "content", rs.getString("content")); // ��������
				// bd.setField(row, "reason",
				// rs.getInt("confirmtype") == 1 ? "һ�η��Ͷ����������޶�ֵ"
				// : "���췢�Ͷ����������޶�ֵ");

				sTmp3 = "<input type=\"checkbox\" name=\"id\" value=\""
						+ rs.getString("id") + "\">";
				tmp = rs.getInt("check_over"); // ״̬
				if (tmp == 1) {
					sTmp1 = "<font color=\"red\">ȷ�Ͽ��Է���</font>";
					sTmp3 = "<input type=\"checkbox\" name=\"id\" value=\""
							+ rs.getString("id") + "\" disabled>";
				} else if (tmp == 2) {
					sTmp1 = "<font color=\"red\">ȷ�ϲ����Է���</font>";
					sTmp3 = "<input type=\"checkbox\" name=\"id\" value=\""
							+ rs.getString("id") + "\" disabled>";
				} else if (tmp == 0) {
					if(!pur){
						sTmp1="��δȷ��";
					}else{
						sTmp1 = "<a href=\"#\" onclick=\"showRoles('td"
							+ rs.getString("id")
							+ "','/oaconfirmsms.do?action=confirm&op=1&id="
							+ rs.getString("id")
							+ "','1')\" title=\"���������\"><font color='#0099FF'>����</font></a>&nbsp;&nbsp;<a href=\"#\" onclick=\"showRoles('td"
							+ rs.getString("id")
							+ "','/oaconfirmsms.do?action=confirm&op=2&id="
							+ rs.getString("id")
							+ "','2')\" title=\"�����������\"><font color='#0099FF'>������</font></a>";
					}
				} else {
					sTmp1 = "�쳣";
				}
				bd.setField(row, "status", sTmp1);
				bd.setField(row, "checkbox", sTmp3);
				row++;
			}
			br.putValue("data", bd);
		} catch (Exception e) {
			e.printStackTrace();
			br.putValue("data", bd);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
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

	/**
	 * �鿴��ϸ��Ϣ
	 *
	 * @author yuhl 2007��07��20
	 * @param request
	 * @return cn.qtone.xxt.base.share.BaseResult
	 * @throws java.lang.Exception
	 * @roseuid 44B1B0390271
	 */
	public BaseResult detail(HttpServletRequest request) throws Exception {
		BaseData bd = new BaseData();
		BaseResult br = new BaseResult();
		DBControl db = new DBControl(request);

		String id = request.getParameter("id"); // ����ID
		String sql = "select object_mobile,content,stu_sequence,family_id,stu_name,note from "
				+ "(select object_mobile,content,stu_sequence,family_id,stu_name,note,row_number() over (order by stu_sequence) rn "
				+ "from confirmTaskSub where task_id=?) where rn between ? and ?";
		String sqlRow = "select count(id) from confirmTaskSub where task_id="
				+ id;

		// ִ��ͳ���ܼ�¼����SQL��䣬�ҳ��ܼ�¼��
		int totalrow = 0;
		int curPage = Util.getPage(request); // ��ȡ��ǰҳ
		ResultSet rs = null;
		try {
			// 1����ѯ����
			totalrow = Util.getTotalRow(db, sqlRow);
			db.prepareDB(sql);
			db.setInt(1, Integer.parseInt(id)); // ����ID
			db.setInt(2, (curPage - 1) * Util.PAGE_SIZE + 1);
			db.setInt(3, (curPage) * Util.PAGE_SIZE);
			rs = db.preparedQuery();
			int row = 0;
			String note = "";
			cn.qtone.xxt.jzdx.PubFunction pub = new cn.qtone.xxt.jzdx.PubFunction();
			while (rs.next()) {
				bd.addRow();
				note = rs.getString("note"); // ��������
				if (note == null) {
					note = "";
				}
				if ("".equals(note) || note == null || "null".equals(note)) {
					bd.setField(row, "color", "#FFFFFF");
					bd.setField(row, "title", "");
				} else {
					bd.setField(row, "color", "#E1E1E1");
					bd.setField(row, "title", "�����С�" + pub.getChar(note)
							+ "�����ۣ���ע��"); // ��ʾ
				}
				bd.setField(row, "stu_name", rs.getString("stu_name")
						.substring(0, rs.getString("stu_name").length() - 1)
						+ "��" + rs.getString("object_mobile") + "��"); // ѧ��
				bd.setField(row, "content", rs.getString("content")); // ��������
				row++;
			}
			db.closePrepared();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (Exception e) {
					//
				}
			}
			if (db != null) {
				db.closePrepared();
				db = null;
			}
		}
		br.putValue("dx_confirmsms_data", bd);
		br.putValue("totalrow", String.valueOf(totalrow)); // �ܼ�¼��
		br.putValue("pagenate_url", ""); // ��ҳurl
		return br;
	}

	/**
	 * �鿴��ϸ��Ϣ
	 *
	 * @author yuhl 2007��03��23
	 * @param request
	 * @return cn.qtone.xxt.base.share.BaseResult
	 * @throws java.lang.Exception
	 * @roseuid 44B1B0390271
	 */
	public BaseResult confirm(HttpServletRequest request) throws Exception {
		SIRevertMessage(request);
		BaseResult valueMap = new BaseResult();
		String multi = request.getParameter("multi"); // �Ƿ���һ�����
		if (multi == null) { // �Ե�����¼���в���
			valueMap.setMessage(null);
		} else { // �Զ�����¼һ�����
			valueMap
					.setMessage("<script language=\"\">alert(\"�����ɹ���\");location.href=\"/oaconfirmsms.do?\";</script>");
		}
		return valueMap;
	}

	/**
	 * SIȷ����ʦ�Ķ����Ƿ���Է��� ������ԭ��
	 *
	 * @param request
	 *            HttpServletRequest
	 * @author yuhailin
	 */
	public void SIRevertMessage(HttpServletRequest request) throws SQLException {
		// ��ȡ����
		String op = request.getParameter("op"); // ���� 1�������� 2����������
		String id = ""; // dx_confirmsms���¼id
		String[] idAry = request.getParameterValues("id"); // dx_confirmsms���¼id����
		DBControl db = new DBControl(request);
		DBControl db1 = new DBControl(request);
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();

		sql1.delete(0, sql.length());
		sql1.append("insert into dx_groupsend(source_mobile,object_mobile,");
		sql1.append("create_time,content,school_id,user_id,area_abb,sp_id,");
		sql1.append("tran_code,isPreParent,stat_sn) values(?,?,");
		sql1.append("to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?,?,?)");
		db1.prepareDB(sql1.toString());
		sql1 = null;

		if (idAry != null) {
			for (int k = 0; k < idAry.length; k++) {
				sql = new StringBuffer();
				id = idAry[k];
				// �Ƿ��Ѿ�ȷ����
				int isConfirm = 0, isPreParent = 0;
				try {
					isConfirm = Integer
							.parseInt(db
									.getValue("select check_over from dx_admin_confirmsms where id="
											+ id));
				} catch (Exception e) {
				}

				if (isConfirm == 0) { // �ö���δȷ�ϲ����������
					if ("1".equals(op)) { // ȷ�Ͽ��Է���
						// ������Ż����(dx_groupsend)
						sql
								.append("select source_mobile,object_mobile,to_char(create_time,'yyyy-mm-dd hh24:mi:ss') create_time,");
						sql
								.append("content,school_id,user_id,area_abb,sp_id,stu_name,");
						sql
								.append("tran_code,isPreParent from dx_admin_confirmsms where id=?");

						String source_mobile = "";
						String sendPhones = "";
						String sendTime = "";
						String content = "";
						int schoolId = 0;
						int user_id = 0;
						String areaAbb = "";
						String sp_id = "";
						String tran_code = "";
						try {
							db.prepareDB(sql.toString());
							db.setInt(1, Integer.parseInt(id));
							rs = db.preparedQuery();
							if (rs != null && rs.next()) {
								source_mobile = rs.getString("source_mobile"); // ���Ŵ���
								sendPhones = rs.getString("object_mobile"); // �����ֻ�
								sendTime = rs.getString("create_time"); // ����ʱ��
								content = rs.getString("content"); // ��������
								schoolId = rs.getInt("school_id"); // ѧУID
								user_id = rs.getInt("user_id"); // ��ʦID
								areaAbb = rs.getString("area_abb"); // ������
								sp_id = rs.getString("sp_id"); // SI��ҵ����
								// stuName = rs.getString("stu_name"); // ѧ������
								tran_code = rs.getString("tran_code"); // ҵ�����
								isPreParent = rs.getInt("isPreParent"); // �Ƿ����ҳ�ǰ׺
							}

							String statsn = areaAbb + db.getValue("select " + areaAbb + "_dx_sms_statsn_seq.nextval from dual");
							
							System.out.println("sendTime --- " + sendTime);
							db1.setString(1, source_mobile);
							db1.setString(2, sendPhones);
							db1.setString(3, sendTime);
							db1.setString(4, content);
							db1.setInt(5, schoolId);
							db1.setInt(6, user_id);
							db1.setString(7, areaAbb);
							db1.setString(8, sp_id);
							db1.setString(9, tran_code);
							db1.setInt(10, isPreParent);
							db1.setString(11, statsn);
							
							db1.preparedAddBatch();
							
							// ����dx_confirmsms��¼�ֶ�
							sql.delete(0, sql.length());
							sql.append("update dx_admin_confirmsms set check_over=1 where id=?");
							db.prepareDB(sql.toString());
							db.setInt(1, Integer.parseInt(id));
							db.preparedExeDB();
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (rs != null) {
								try {
									rs.close();
								} catch (Exception e) {
									e.printStackTrace();
								}
								rs = null;
							}
							if (db != null) {
								db.closePrepared();
								// db = null;
							}
						}
					} else if ("2".equals(op)) { // �����Է���
						// ����dx_admin_confirmsms��¼�ֶ�
						sql.delete(0, sql.length());
						sql
								.append("update dx_admin_confirmsms set check_over=2 where id=?");
						try {
							db.prepareDB(sql.toString());
							db.setInt(1, Integer.parseInt(id));
							db.preparedExeDB();
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (db != null) {
								db.closePrepared();
							}
						}
					}
				}
			}
			db1.PreparedExeBatch();
			if (db1 != null) {
				db1.closePrepared();
				db1 = null;
			}
		}

		if (db != null) {
			db.closePrepared();
			db = null;
		}
		sql = null;
	}


	/**
	 * д����ű��¼��**_dx_sms��
	 *
	 * @param db
	 *            DBControl
	 * @param strBuf
	 *            StringBuffer
	 * @param i
	 *            int
	 * @param sendPhones
	 *            String
	 * @param familyId
	 *            String
	 * @param stuSequence
	 *            String
	 * @param util
	 *            MessageUtiltity
	 * @param schoolId
	 *            int
	 * @param user_id
	 *            int
	 * @param content
	 *            String
	 * @param sendTime
	 *            String
	 * @param source_mobile
	 *            String
	 * @param kind
	 *            int
	 */
	/*public void saveSMS(DBControl db, StringBuffer strBuf, int i,
			String sendPhones, String familyId, String stuSequence,
			cn.qtone.xxt.jzdx.MessageUtiltity util, int schoolId, int user_id,
			String content, String sendTime, String source_mobile, int kind) {
		// д����־�� add yuhailin 07-01-16 -----
		String phoneAry[] = sendPhones.split(","); // �ֻ�����
		String familyIdAry[] = familyId.split(","); // �ҳ�ID
		String stuSeqAry[] = stuSequence.split(","); // ѧ�����
		for (i = 0; i < familyIdAry.length; i++) {
			if (i % 200 == 0) { // init
				db.prepareDB(strBuf.toString());
			}
			util.saveSmsBatch(db, schoolId, user_id, phoneAry[i], stuSeqAry[i],
					content,
					sendTime == null ? new cn.qtone.xxt.jzdx.PubFunction()
							.getTimeStr()
							+ ":00" : sendTime, kind, Integer
							.parseInt(familyIdAry[i]), source_mobile, "other");
			if ((i + 1) % 200 == 0) { // execute
				db.PreparedExeBatch();
			}
		}
		if ((i + 1) % 200 != 0) { // execute
			db.PreparedExeBatch();
		}
		phoneAry = null;
		familyIdAry = null;
		stuSeqAry = null;
		// -----
	}*/

	/** --- xxt, 071026, lzm, sendfree_log����school_id�ֶ��漰���޸� --- begin --- */
	// д�������ʷ��sendfree_log�� - ֻ����շ�ѧУ
	public void saveSendFreeLog(DBControl db, int areaID, int charge,
			String familyId, int kind) {
//		if (charge == 1) {
//			int i = 0;
//			java.util.Date nDay = new java.util.Date();
//			int year = nDay.getYear() + 1900;
//			int month = nDay.getMonth() + 1;
//			StringBuffer strBuf = new StringBuffer();
//			String[] familyIdAry = familyId.split(",");
//			// strBuf
//			// .append("insert into
//			// sendfree_log(area_id,family_id,year,month,kind,school_id)
//			// values(?,?,?,?,?,?,?)");
//			strBuf
//					.append("insert into sendfree_log(area_id,family_id,year,month,kind) values(?,?,?,?,?)");
//
//			for (i = 0; i < familyIdAry.length; i++) {
//				if (i % 200 == 0) { // init
//					db.prepareDB(strBuf.toString());
//				}
//				try {
//					db.setInt(1, areaID); // ����ID
//					db.setInt(2, Integer.parseInt(familyIdAry[i])); // �ҳ�ID
//					db.setInt(3, year); // ��
//					db.setInt(4, month); // ��
//					db.setInt(5, kind); // ���ͣ�1:Ⱥ�� 0:���Ի����ͣ�
//					// db.setInt(6, schoolId);// �����ֶ�school_id
//					db.preparedAddBatch();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				if ((i + 1) % 200 == 0) { // execute
//					db.PreparedExeBatch();
//				}
//			}
//			if (i % 200 != 0) { // execute
//				db.PreparedExeBatch();
//			}
//		}
	}

	/** --- xxt, 071026, lzm, sendfree_log����school_id�ֶ��漰���޸� --- end --- */
	
	
	
	
	/**
	 * 
	 * @param request
	 * @param db
	 * @param link_mphone
	 * @param tea_mphone
	 * @param list_mphone
	 */
	public void passOASmsAuditingAndCompeleteSmsSend(HttpServletRequest request,DBControl db,String objectPhone,String linkManIds){
        
		int i = 0;
        String content="";
        int schoolid=0;
        int sendId=0;
        String sch_code = "";
        String strSenderTeacherCode ="";
        String strIsLong = "";
        String area ="";
        TeacherBean teacherbean=null;
        int cent=0;
        int sms_sum=0;
        String ip = request.getRemoteAddr();
        String tempIds="";
        String tempNames="";
        List<String> link_mphone = null;
		List<String> tea_mphone = null;
		List<String> list_mphone = null;
        
		try{
			
		    String sql = "";	
			 sql = "insert into dx_groupsend(source_mobile,object_mobile,create_time,content,school_id,"
					+ "user_id,area_abb,kind,sp_id,tran_code,flag,islong) values(?,?,sysdate,?,?,?,?,0,?,?,?,?)";
			i = 0;
			db.prepareDB(sql);
			String[] idAndMobile = null;
			boolean flag = false;
			while (i < link_mphone.size()) { // ͨѶ¼,�ɻظ�
				idAndMobile = String.valueOf(link_mphone.get(i)).split("_");
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
			
			db.PreparedExeBatch();
			db.clearBatch();
			db.closePrepared();
			
			SendSmsDAO dao = new SendSmsDAO();
//			dao.sendOA_MobileMsg(tempIds, tempNames, content,teacherbean, db, ip);
			
			
			cent = cent - sms_sum;
			if (cent < 0)
				cent = 0;
			sql = "update xj_teacher set cdata=" + cent + " where id=" + sendId;
			db.prepareDB(sql);
			db.preparedExeUpdate();
			db.closePrepared();
        }catch(Exception e){
          e.printStackTrace();
        }finally{
        	db.closePrepared();
        	db.closeStmt();
        }
	}
}
