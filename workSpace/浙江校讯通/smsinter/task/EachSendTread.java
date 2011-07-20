package cn.qtone.xxt.jzdx.smsinter.task;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import cn.qtone.xxt.base.db.DBControl;
import cn.qtone.xxt.base.login.TeacherBean;
import cn.qtone.xxt.jzdx.JzdxUtil;
import cn.qtone.xxt.jzdx.MessageUtiltity;
import cn.qtone.xxt.jzdx.MessageUtiltity2;
import cn.qtone.xxt.jzdx.PubFunction;
import cn.qtone.xxt.jzdx.Utiltity;

public class EachSendTread implements Runnable {
	private DBControl db;
	private DBControl db2;
	private String areaAbb = ""; // ������
	private String strStuId = ""; // ѡ���͵�ѧ��ID
	private String filterStuId = ""; // �����ѧ��ID
	private String source = ""; // ����Դ���루�ط��ţ�
	private String sendTime = ""; // ����ʱ��
	private HashMap dataMap = null; // ��������
	private String userName = ""; // ��ʦ����
	private int schoolId = 0; // ѧУID
	private int user_id = 0; // ��ʦID
	private String sp_id = ""; // ��ҵ����
	private String tran_code = ""; // ҵ�����
	private String ip = ""; // ����IP
	private String isAlign = ""; // �Ƿ�ʱ
	private int kind = 1; // �������� 1�����Է��� 2���ɼ����ͣ���ʽһ��
	private String smstype = ""; // ��������
	private int school_dxx_charge = 0; // ѧУ�Ƿ�ͨ�ҳ��������շ�
	private int fee = 1; // ѧУ�Ƿ񿪽�����Ѷ���
	private int gxhfs = 0; // ���Ի����Ͷ�����
	private int areaID = 0; // ����ID
	private HashMap isLongMap = null; // ������
	private int classId = 0; // �༶ID
	private TeacherBean teacher = null; // ��ʦsession����bean

	private PubFunction pub = new PubFunction();

	public EachSendTread(DBControl db, DBControl db2, String areaAbb,
			String strStuId, String filterStuId, String source,
			String sendTime, HashMap dataMap, String userName, int schoolId,
			int user_id, String sp_id, String tran_code, String ip,
			String isAlign, int kind, String smstype, int school_dxx_charge,
			int fee, int gxhfs, int areaID, HashMap isLongMap, int classId,
			TeacherBean teacher) {
		this.db = db;
		this.db2 = db2;
		this.areaAbb = areaAbb;
		this.strStuId = strStuId;
		this.filterStuId = filterStuId;
		this.source = source;
		this.sendTime = sendTime;
		this.dataMap = dataMap;
		this.userName = userName;
		this.schoolId = schoolId;
		this.user_id = user_id;
		this.sp_id = sp_id;
		this.tran_code = tran_code;
		this.ip = ip;
		this.isAlign = isAlign;
		this.kind = kind;
		this.smstype = smstype;
		this.school_dxx_charge = school_dxx_charge;
		this.fee = fee;
		this.gxhfs = gxhfs;
		this.areaID = areaID;
		this.isLongMap = isLongMap;
		this.classId = classId;
		this.teacher = teacher;
		// System.out.println("size:"+isLongMap.size());
	}

	/**
	 * run
	 */
	public void run() {
		Connection conn = null;
		CallableStatement stmt = null;
		StringBuffer sql = new StringBuffer();
		ResultSet rs = null;
		ArrayList stuNameAL = new ArrayList(); // ѧ������,д����־���õ�
		ArrayList dataAL = new ArrayList(); // ��������,д����־���õ�
		ArrayList mobileAL = new ArrayList(); // �ֻ�����,д����ű��õ�
		ArrayList stuSequenceAL = new ArrayList(); // ѧ�����,д����ű��õ�
		ArrayList contentAL = new ArrayList(); // ��������,д����ű��õ�
		ArrayList familyIDAL = new ArrayList(); // ���Ŵ���,д����ű��õ�
		String stuTabName = areaAbb + "_xj_student";
		String familyTabName = areaAbb + "_xj_family";

		// modify by yuhailin 2011-05-20 ��ѯѧУ���can_send_unmob�ֶ�ֵ
		int canSendUniNumber = 1;
		try {
			canSendUniNumber = Integer.parseInt(db
					.getValue("select can_send_unmob from xj_school where id="
							+ schoolId));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String strFid = "";
			// if (school_dxx_charge == 0) { //ѧУδ�շ�
			// ����ѧУID�ж����Ƿ���Է�����������
			if (pub.isCanSendUni(db, schoolId)) {
				sql.append("select s.id id,s.stu_sequence stu_sequence,");
				sql.append("s.name sname,f.id fid,f.phone phone,f.");
				sql.append(smstype);
				sql.append(" ");
				sql.append(smstype);
				sql.append(",f.week week from ");
				sql.append(stuTabName);
				sql.append(" s join ");
				sql.append(familyTabName);
				sql
						.append(" f on s.stu_sequence=f.stu_sequence where s.id in(");
				sql.append(strStuId);
				sql.append(") and s.id not in(");
				sql.append(filterStuId);
				sql
						.append(") and ((f.phonetype=0 and f.is_dxx=1) or (f.phonetype>0 "
								+ (JzdxUtil.getUnMobQuerySQL(canSendUniNumber,
										areaAbb, "f", true))
								+ ")) order by s.id");
			} else {
				sql.append("select s.id id,s.stu_sequence stu_sequence,");
				sql.append("s.name sname,f.id fid,f.phone phone,f.");
				sql.append(smstype);
				sql.append(" ");
				sql.append(smstype);
				sql.append(",f.week week from ");
				sql.append(stuTabName);
				sql.append(" s join ");
				sql.append(familyTabName);
				sql
						.append(" f on s.stu_sequence=f.stu_sequence where s.id in(");
				sql.append(strStuId);
				sql.append(") and s.id not in(");
				sql.append(filterStuId);
				sql.append(") and f.is_dxx=1 order by s.id");
			}
			// ---
			Connection con = db.getConnection();
			Statement statement = con.createStatement();
			rs = statement.executeQuery(sql.toString());
			MessageUtiltity util = new MessageUtiltity();
			MessageUtiltity2 util2 = new MessageUtiltity2();
			Utiltity util3 = new Utiltity();
			String dateStr = util2.getDateStr();
			int taskId = 0;
			String sn = null;
			if (sendTime == null) { // ��ʱ����
				sn = db.getValue("select " + areaAbb
						+ "_sms_sn.nextval from dual");
				util3.setGroupSendBatch2(db); // set db
				util3.setGroupSendBatchUni2(db2);
			} else { // ��ʱ����
				taskId = util2.handleTask(db, source, sendTime, schoolId,
						user_id, areaAbb, kind == 1 ? 2 : 3, sp_id, tran_code,
						1, (kind == 1 ? "���Է���" : "�ɼ�����") + dateStr, 1, ip,
						Integer.parseInt(cn.qtone.xxt.jzdx.Const.smsType
								.get(smstype)
								+ ""), "", teacher);
				util2.setTaskSubBatch2(db); // set db
			}
			int stuIdTmp = 0;
			while (rs != null && rs.next()) {
				if (rs.getInt(smstype) == 1
						|| (rs.getInt(smstype) == 0 && rs.getInt("week") > 0 && rs
								.getInt("week") != (sendTime == null ? new java.util.Date()
								.getDay() + 1
								: new java.text.SimpleDateFormat("yyyy-MM-dd")
										.parse(sendTime).getDay() + 1))) { // �����ղŷ���
					if (sendTime == null) { // ��ʱ����
						if (PubFunction.getPhoneType(rs.getString("phone")) == 0) {
							util3
									.saveSingleGroupSendBatch2(
											db,
											source,
											rs.getString("phone"),
											sendTime,
											dataMap.get(rs.getString("id"))
													+ "",
											schoolId,
											user_id,
											rs.getString("stu_sequence"),
											areaAbb,
											0,
											kind == 1 ? 2 : 3,
											rs.getString("fid"),
											sp_id,
											tran_code,
											Integer
													.parseInt(cn.qtone.xxt.jzdx.Const.smsType
															.get(smstype)
															+ ""), dateStr,
											Integer.parseInt(isLongMap.get(rs
													.getString("id"))
													+ ""), Integer.parseInt(sn));
							// add by yuhailin 07-01-15д����ű�
							mobileAL.add(rs.getString("phone"));
							stuSequenceAL.add(rs.getString("stu_sequence"));
							contentAL.add(dataMap.get(rs.getString("id")));
							familyIDAL.add(rs.getString("fid"));
						} else {
							util3
									.saveSingleGroupSendBatch2(
											db2,
											source,
											rs.getString("phone"),
											sendTime,
											dataMap.get(rs.getString("id"))
													+ "",
											schoolId,
											user_id,
											rs.getString("stu_sequence"),
											areaAbb,
											0,
											kind == 1 ? 2 : 3,
											rs.getString("fid"),
											sp_id,
											tran_code,
											Integer
													.parseInt(cn.qtone.xxt.jzdx.Const.smsType
															.get(smstype)
															+ ""), dateStr,
											Integer.parseInt(isLongMap.get(rs
													.getString("id"))
													+ ""), Integer.parseInt(sn));
							if (cn.qtone.xxt.jzdx.JzdxUtil.isRangeArea(areaAbb)) {
								mobileAL.add(rs.getString("phone"));
								stuSequenceAL.add(rs.getString("stu_sequence"));
								contentAL.add(dataMap.get(rs.getString("id")));
								familyIDAL.add(rs.getString("fid"));
							}
						}
					} else { // ��ʱ����
						util2.saveSingleTaskSubBatch(db, rs.getString("phone"),
								dataMap.get(rs.getString("id")) + "", taskId,
								schoolId, user_id,
								rs.getString("stu_sequence"), rs
										.getString("fid"), areaAbb, Integer
										.parseInt(isLongMap.get(rs
												.getString("id"))
												+ ""));
					}
				}
				strFid += rs.getString("fid") + ",";
				// add by yuhailin 07-01-15д����ű�
				// mobileAL.add(rs.getString("phone"));
				// stuSequenceAL.add(rs.getString("stu_sequence"));
				// contentAL.add(dataMap.get(rs.getString("id")));
				// familyIDAL.add(rs.getString("fid"));
				// һ��ѧ��(�����Ӧ����ҳ�)һ����־,�����ű��ж��ټҳ��Ͷ�Ӧ���ټ�¼
				if (rs.getInt("id") != stuIdTmp) {
					stuIdTmp = rs.getInt("id");
					dataAL.add(dataMap.get(rs.getString("id")));
					stuNameAL.add(rs.getString("sname"));
				}
			}
			if (statement != null) {
				statement.close();
				statement = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
			db.PreparedExeBatch(); // execute Batch
			if (sendTime == null) {
				db2.PreparedExeBatch();
			}
			if (rs != null) {
				rs.close();
			}
			rs = null;
			sql = null;
			util3 = null;

			// ����RMI���Ͷ���
			pub.transferRMI(areaAbb);
			if (sendTime == null) { // ��ʱ���ͲŴ���
				// ��ȡ��������
				// String sn=db.getValue("select "+areaAbb+"_sms_sn.nextval from
				// dual");
				// д����־��
				conn = db.getConnection();
				stmt = conn.prepareCall("{call in_" + areaAbb
						+ "_logs(?,?,?,?,?,?,?,?,?,?,?,?)}");
				for (int i = 0; i < dataAL.size(); i++) {
					util.saveLogsBatch(stmt, areaAbb, schoolId, user_id, ip,
							(kind == 1 ? "���Է��ͣ�" : "�ɼ����ͣ�") + dataAL.get(i)
									+ " Ŀ��ѧ����" + stuNameAL.get(i) + isAlign, 5,
							kind == 1 ? 2 : 3, dateStr, Integer
									.parseInt(cn.qtone.xxt.jzdx.Const.smsType
											.get(smstype).toString()), Integer
									.parseInt(sn), classId, teacher);
				}
				stmt.executeBatch(); // execute Batch
				// д����ű�
				StringBuffer strBuf = new StringBuffer();
				strBuf.append("insert into ");
				strBuf.append(areaAbb);
				strBuf
						.append("_dx_sms (sn,school_id,user_id,object_mobile,stu_sequence,content,");
				strBuf
						.append("dt,kind,family_id,source_mobile,smstype,admin_id,admin_userid,send_mode) values(?,?,?,?,?,?,");
				strBuf
						.append("to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?,3)");

				String Tsql = cn.qtone.xxt.base.share.SmsBean.GetSql(strBuf
						.toString(), areaAbb, "toDay");
				strBuf.delete(0, strBuf.length());
				strBuf.append(Tsql);

				db.prepareDB(strBuf.toString());
				strBuf = null;
				String curTime = new PubFunction().getTimeStr() + ":00";
				for (int i = 0; i < contentAL.size(); i++) {
					util.saveSmsBatch(db, schoolId, user_id, mobileAL.get(i)
							+ "", stuSequenceAL.get(i) + "", contentAL.get(i)
							+ "", sendTime == null ? curTime : sendTime,
							kind == 1 ? 2 : 3, Integer.parseInt(familyIDAL
									.get(i)
									+ ""), source, smstype, Integer
									.parseInt(sn), teacher);
				}
				db.PreparedExeBatch();
				System.out.println("���������ϸ�����");
				// ���¶��Ÿ�����(**_dx_smsstat)
				util.updateSmsStat(db, areaAbb, strFid, schoolId);
			}
			// 2008-10-08���������û��ҳ����ո��Ի����ŵ�������¼
			util.updateGXHFSSum(db, areaAbb, strStuId, filterStuId,
					school_dxx_charge, fee, gxhfs);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error:: schoolId=" + schoolId + "  userid="
					+ user_id + " time=" + PubFunction.getDateStr() + " "
					+ PubFunction.getHour() + ":" + PubFunction.getMinute());
			cn.qtone.xxt.jzdx.JzdxUtil.saveException(db,
					"EachSendTread������ʱ�����쳣��" + e.toString() + " ѧУID="
							+ schoolId + ",��ʦID=" + user_id);
		} finally {
			dataAL.clear();
			dataAL = null;
			stuNameAL.clear();
			stuNameAL = null;
			mobileAL.clear();
			mobileAL = null;
			stuSequenceAL.clear();
			stuSequenceAL = null;
			contentAL.clear();
			contentAL = null;
			familyIDAL.clear();
			familyIDAL = null;
			if (rs != null) {
				try {
					rs.close();
					if (conn != null) {
						conn.close();
						conn = null;
					}
					if (stmt != null) {
						stmt.close();
						stmt = null;
					}
				} catch (Exception e) {
				}
				rs = null;
			}
			sql = null;
			if (db != null) {
				db.closePrepared();
			}
			if (dataMap != null) {
				dataMap.clear();
				dataMap = null;
			}
			db2.closePrepared();
		}
	}
}
