package cn.qtone.xxt.jzdx.smsinter.task;

import java.sql.ResultSet;
import java.util.ArrayList;

import cn.qtone.xxt.base.db.DBControl;
import cn.qtone.xxt.base.login.TeacherBean;
import cn.qtone.xxt.jzdx.JzdxUtil;
import cn.qtone.xxt.jzdx.MessageUtiltity;
import cn.qtone.xxt.jzdx.MessageUtiltity2;
import cn.qtone.xxt.jzdx.PubFunction;
import cn.qtone.xxt.jzdx.Utiltity;

public class UniteSendTread implements Runnable {
	private DBControl db;

	private String areaAbb = ""; // ������

	private String strStu = ""; // ѡ���͵�ѧ��ID

	private String source = ""; // ����Դ���루�ط��ţ�

	private String sendTime = ""; // ����ʱ��

	private int isPresuffix = 0; // �Ƿ��ѧУ����ǰ׺

	private String data = ""; // ��������

	private String userName = ""; // ��ʦ����

	private String schoolName = ""; // ѧУ����

	private int schoolId = 0; // ѧУID

	private int user_id = 0; // ��ʦID

	private String sp_id = ""; // ��ҵ����

	private String tran_code = ""; // ҵ�����

	private String ip = ""; // ����IP

	private String isAlign = ""; // �Ƿ�ʱ

	private String smsType = ""; // ��������

	private int school_dxx_charge = 0; // ѧУ�Ƿ�ͨ�ҳ��������շ�

	private int fee = 1; // �Ƿ���Խ�����Ѷ���

	private int tyfs = 1; // Ⱥ����������

	private int areaID = 0; // ����ID

	private int isPreParent = 0; // �Ƿ�Ӽҳ��ƺ�ǰ׺

	private int isLong = 0; // �Ƿ񳤶��ŷ�ʽ����

	private int classId = 0; // �༶ID

	private boolean canSendUni;// �Ƿ���Է�����������

	private TeacherBean teacher = null; // ��ʦsesson����

	private PubFunction pub = new PubFunction();

	public UniteSendTread(DBControl db, String areaAbb, String strStu,
			String source, String sendTime, int isPresuffix, String data,
			String userName, String schoolName, int schoolId, int user_id,
			String sp_id, String tran_code, String ip, String isAlign,
			String smsType, int school_dxx_charge, int fee, int tyfs, int areaID) {
		this.db = db;
		this.areaAbb = areaAbb;
		this.strStu = strStu;
		this.source = source;
		this.sendTime = sendTime;
		this.isPresuffix = isPresuffix;
		this.data = data;
		this.userName = userName;
		this.schoolName = schoolName;
		this.schoolId = schoolId;
		this.user_id = user_id;
		this.sp_id = sp_id;
		this.tran_code = tran_code;
		this.ip = ip;
		this.isAlign = isAlign;
		this.smsType = smsType;
		this.school_dxx_charge = school_dxx_charge;
		this.fee = fee;
		this.tyfs = tyfs;
		this.areaID = areaID;
	}

	public UniteSendTread(DBControl db, String areaAbb, String strStu,
			String source, String sendTime, int isPresuffix, String data,
			String userName, String schoolName, int schoolId, int user_id,
			String sp_id, String tran_code, String ip, String isAlign,
			String smsType, int school_dxx_charge, int fee, int tyfs,
			int areaID, int isPreParent) {
		this.db = db;
		this.areaAbb = areaAbb;
		this.strStu = strStu;
		this.source = source;
		this.sendTime = sendTime;
		this.isPresuffix = isPresuffix;
		this.data = data;
		this.userName = userName;
		this.schoolName = schoolName;
		this.schoolId = schoolId;
		this.user_id = user_id;
		this.sp_id = sp_id;
		this.tran_code = tran_code;
		this.ip = ip;
		this.isAlign = isAlign;
		this.smsType = smsType;
		this.school_dxx_charge = school_dxx_charge;
		this.fee = fee;
		this.tyfs = tyfs;
		this.areaID = areaID;
		this.isPreParent = isPreParent;
	}

	public UniteSendTread(DBControl db, String areaAbb, String strStu,
			String source, String sendTime, int isPresuffix, String data,
			String userName, String schoolName, int schoolId, int user_id,
			String sp_id, String tran_code, String ip, String isAlign,
			String smsType, int school_dxx_charge, int fee, int tyfs,
			int areaID, int isPreParent, int isLong, int classId,
			boolean canSendUni, TeacherBean teacher) {
		this.db = db;
		this.areaAbb = areaAbb;
		this.strStu = strStu;
		this.source = source;
		this.sendTime = sendTime;
		this.isPresuffix = isPresuffix;
		this.data = data;
		this.userName = userName;
		this.schoolName = schoolName;
		this.schoolId = schoolId;
		this.user_id = user_id;
		this.sp_id = sp_id;
		this.tran_code = tran_code;
		this.ip = ip;
		this.isAlign = isAlign;
		this.smsType = smsType;
		this.school_dxx_charge = school_dxx_charge;
		this.fee = fee;
		this.tyfs = tyfs;
		this.areaID = areaID;
		this.isPreParent = isPreParent;
		this.isLong = isLong;
		this.classId = classId;
		this.canSendUni = canSendUni;
		this.teacher = teacher;
	}

	/**
	 * run
	 */
	public void run() {
		StringBuffer sql = new StringBuffer();
		ResultSet rs = null;
		// ȡ�����͵��ֻ����룬ѧ�����
		// ѧ����ͼ�ͥ�����
		String stuTabName = areaAbb + "_xj_student";
		String familyTabName = areaAbb + "_xj_family";
		String sendPhones = "", stuSequence = "", familyId = ""; // Ŀ���ֻ����룬ѧ�����,family
		// ID
		String sendPhonesUni = "", stuSequenceUni = "", familyIdUni = "";// ����Ŀ���ֻ����롢ѧ����š�family_id
		String targetStudent = "";

		// modify by yuhailin 2011-05-20 ��ѯѧУ���can_send_unmob�ֶ�ֵ
		int canSendUniNumber = 1;
		try {
			canSendUniNumber = Integer.parseInt(db
					.getValue("select can_send_unmob from xj_school where id="
							+ schoolId));
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (canSendUni) {// �ɷ���������
			if (school_dxx_charge == 0) {// ѧУ���
				sql
						.append("select s.stu_sequence stu_sequence,f.id fid,f.phone phone,s.name sname,f.name pname from ");
				sql.append(stuTabName);
				sql.append(" s join ");
				sql.append(familyTabName);
				sql
						.append(" f on s.stu_sequence=f.stu_sequence where s.id in(");
				sql.append(strStu);
				sql.append(") and (f.is_dxx=1 or (f.phonetype>0 "
						+ (JzdxUtil.getUnMobQuerySQL(canSendUniNumber, areaAbb,
								"f", true)) + ")) order by f.phone");
			} else {// ѧУ�շ�
				if (fee == 1) {// ���ÿ����������û����Ͷ���
					sql
							.append("select s.stu_sequence stu_sequence,f.id fid,f.phone phone,s.name sname,f.name pname from ");
					sql.append(stuTabName);
					sql.append(" s join ");
					sql.append(familyTabName);
					sql
							.append(" f on s.stu_sequence=f.stu_sequence where s.id in(");
					sql.append(strStu);
					sql
							.append(") and ((f.is_dxx=1 and (f.charge!=0 or (f.charge=0 and f.ty<"
									+ tyfs
									+ "))) or (f.phonetype>0 "
									+ (JzdxUtil.getUnMobQuerySQL(
											canSendUniNumber, areaAbb, "f",
											true)) + ")) order by f.phone");
				} else {// ���ò����������û����Ͷ���
					sql
							.append("select s.stu_sequence stu_sequence,f.id fid,f.phone phone,s.name sname,f.name pname from ");
					sql.append(stuTabName);
					sql.append(" s join ");
					sql.append(familyTabName);
					sql
							.append(" f on s.stu_sequence=f.stu_sequence where s.id in(");
					sql.append(strStu);
					sql
							.append(") and ((f.is_dxx=1 and f.charge!=0) or (f.phonetype>0 "
									+ (JzdxUtil.getUnMobQuerySQL(
											canSendUniNumber, areaAbb, "f",
											true)) + ")) order by f.phone");
				}
			}
		} else {
			if (school_dxx_charge == 0) {// ѧУ��ѣ�ֻҪ�ǿ�ͨ��ҵ��Ķ����Խ��ն���
				sql
						.append("select s.stu_sequence stu_sequence,f.id fid,f.phone phone,s.name sname,f.name pname from ");
				sql.append(stuTabName);
				sql.append(" s join ");
				sql.append(familyTabName);
				sql
						.append(" f on s.stu_sequence=f.stu_sequence where s.id in(");
				sql.append(strStu);
				sql.append(") and f.is_dxx=1 order by f.phone");
			} else {// ѧУ�շ�
				if (fee == 1) {// ���ÿ����������û����Ͷ���
					sql
							.append("select s.stu_sequence stu_sequence,f.id fid,f.phone phone,s.name sname,f.name pname from ");
					sql.append(stuTabName);
					sql.append(" s join ");
					sql.append(familyTabName);
					sql
							.append(" f on s.stu_sequence=f.stu_sequence where s.id in(");
					sql.append(strStu);
					sql
							.append(") and f.is_dxx=1 and (f.charge!=0 or (f.charge=0 and f.ty<"
									+ tyfs + ")) order by f.phone");
				} else {// ���ò����������û����Ͷ���
					sql
							.append("select s.stu_sequence stu_sequence,f.id fid,f.phone phone,s.name sname,f.name pname from ");
					sql.append(stuTabName);
					sql.append(" s join ");
					sql.append(familyTabName);
					sql
							.append(" f on s.stu_sequence=f.stu_sequence where s.id in(");
					sql.append(strStu);
					sql
							.append(") and f.is_dxx=1 and f.charge!=0 order by f.phone");
				}
			}
		}

		try {
			// System.out.println(sql);
			db.prepareDB(sql.toString());
			rs = db.preparedQuery();
			String phone;
			while (rs != null && rs.next()) {
				phone = rs.getString("phone");
				// System.out.println(phone);
				if (PubFunction.getPhoneType(phone) == 0) {
					sendPhones += phone + ","; // �ֻ�����
					stuSequence += rs.getString("stu_sequence") + ","; // ѧ�����
					familyId += rs.getString("fid") + ","; // family ID
				} else {
					sendPhonesUni += phone + ",";// �����ֻ�����
					stuSequenceUni += rs.getString("stu_sequence") + ","; // ѧ�����
					familyIdUni += rs.getString("fid") + ","; // family ID
				}
				targetStudent += rs.getString("sname") + "("
						+ rs.getString("pname") + ")" + ",";
			}
			// System.out.println("��ͨ����:"+sendPhonesUni);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error:: schoolId=" + schoolId + "  userid="
					+ user_id + " time=" + PubFunction.getDateStr() + " "
					+ PubFunction.getHour() + ":" + PubFunction.getMinute());
			cn.qtone.xxt.jzdx.JzdxUtil.saveException(db,
					"UniteSendTread���ѯĿ�����ʱ�����쳣��" + e.toString() + " ѧУID="
							+ schoolId + ",��ʦID=" + user_id + ",sql="
							+ sql.toString());
		} finally {
			// close Result
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				rs = null;
			}
			db.closePrepared();
			sql = null;
		}
		ArrayList str = pub.getFinalSendInfo(db, areaAbb,
				"".equals(familyId) ? "-1" : familyId.substring(0, familyId
						.length() - 1), smsType, sendTime);
		MessageUtiltity util = new MessageUtiltity();
		MessageUtiltity2 util2 = new MessageUtiltity2();
		String dateStr = util2.getDateStr();
		// ��������
		String sn = null;
		try {
			if (sendTime == null) { // ��ʱ����
				sn = db.getValue("select " + areaAbb
						+ "_sms_sn.nextval from dual");
				// ����Ҫ������Ϣ���Ż����(dx_groupsend)
				Utiltity util3 = new Utiltity();
				if (!"".equals(str.get(0).toString())) {
					util3.handleMulitMessage(db, source, str.get(0) + "",
							sendTime, data, schoolId, user_id, str.get(2) + "",
							areaAbb, 0, 1, str.get(1) + "", sp_id, tran_code,
							Integer.parseInt(cn.qtone.xxt.jzdx.Const.smsType
									.get(smsType)
									+ ""), dateStr, isPreParent, isLong,
							Integer.parseInt(sn));
				}
				if (!"".equals(sendPhonesUni)) {
					util3.handleMulitMessageUni(db, source, sendPhonesUni
							.substring(0, sendPhonesUni.length() - 1),
							sendTime, data, schoolId, user_id, stuSequenceUni,
							areaAbb, 0, 1, familyIdUni, sp_id, tran_code,
							Integer.parseInt(cn.qtone.xxt.jzdx.Const.smsType
									.get(smsType)
									+ ""), dateStr, isPreParent, isLong,
							Integer.parseInt(sn));
				}
			} else { // ��ʱ����
				// ����Ҫ������Ϣ���������(msgTaskMain/msgTaskSub)
				if (!"".equals(str.get(0))) {
					int taskId = util2.handleTask(db, source, sendTime,
							schoolId, user_id, areaAbb, 1, sp_id, tran_code, 1,
							"ͳһ����" + dateStr, 1, ip, Integer
									.parseInt(cn.qtone.xxt.jzdx.Const.smsType
											.get(smsType)
											+ ""), "", isPreParent, teacher);
					util2.handleMulitMessage(db, sendPhonesUni + str.get(0),
							data, taskId, schoolId, user_id, stuSequenceUni
									+ str.get(2), familyIdUni + str.get(1),
							areaAbb, isLong);
				}
			}
			// ����RMI���Ͷ���
			pub.transferRMI(areaAbb);

			if (sendTime == null && !(sendPhones + sendPhonesUni).equals("")) { // ��ʱ���ͲŴ���
				// д����־��(dx_logs)
				if (!"".equals(targetStudent)) {
					targetStudent = targetStudent.substring(0, targetStudent
							.length() - 1); // ���͵�Ŀ��ѧ��
				}
				System.out.println("д��־");
				util.saveLogs(db, areaAbb, schoolId, user_id, ip, "ͳһ���ͣ�"
						+ data + " Ŀ��ѧ����" + targetStudent + isAlign, 5, 1,
						dateStr, Integer
								.parseInt(cn.qtone.xxt.jzdx.Const.smsType
										.get(smsType)
										+ ""), Integer.parseInt(sn), classId,
						teacher);
				// д����ű� add by yuhailin 07-01-16 -----
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

				String phoneAry[] = sendPhones.split(","); // �ֻ�����
				String familyIdAry[] = familyId.split(","); // �ҳ�ID
				String stuSeqAry[] = stuSequence.split(","); // ѧ�����
				int i = 0;

				String curTime = new PubFunction().getTimeStr() + ":00";
				for (i = 0; i < familyIdAry.length
						&& !familyIdAry[i].equals(""); i++) {
					if (i % 200 == 0) { // init
						db.prepareDB(strBuf.toString());
					}
					util.saveSmsBatch(db, schoolId, user_id, phoneAry[i],
							stuSeqAry[i], data, sendTime == null ? curTime
									: sendTime, 1, Integer
									.parseInt(familyIdAry[i]), source, smsType,
							Integer.parseInt(sn), teacher);
					if ((i + 1) % 200 == 0) { // execute
						db.PreparedExeBatch();
					}
				}
				if (i % 200 != 0) { // execute
					db.PreparedExeBatch();
				}
				// ���ڵ��������������¼��������
				if (cn.qtone.xxt.jzdx.JzdxUtil.isRangeArea(areaAbb)) { // areaAbb.equals("sz")
					// ָ���ĵ����ᱣ����ƶ����������ϸ
					phoneAry = sendPhonesUni.split(","); // �ֻ�����
					familyIdAry = familyIdUni.split(","); // �ҳ�ID
					stuSeqAry = stuSequenceUni.split(","); // ѧ�����
					for (i = 0; i < familyIdAry.length
							&& !familyIdAry[i].equals(""); i++) {
						if (i % 200 == 0) { // init
							db.prepareDB(strBuf.toString());
						}
						util.saveSmsBatch(db, schoolId, user_id, phoneAry[i],
								stuSeqAry[i], data, sendTime == null ? curTime
										: sendTime, 1, Integer
										.parseInt(familyIdAry[i]), source,
								smsType, Integer.parseInt(sn), teacher);
						if ((i + 1) % 200 == 0) { // execute
							db.PreparedExeBatch();
						}
					}
					if (i % 200 != 0) { // execute
						db.PreparedExeBatch();
					}
				}

				strBuf = null;
				phoneAry = null;
				familyIdAry = null;
				stuSeqAry = null;
				// -----

				// ���¶��Ÿ�����(**_dx_smsstat)
				util.updateSmsStat(db, areaAbb, familyId, schoolId);

			}
			// 2008-10-07 ���������û�����ͳһ���Ͷ��ŵ�������¼
			util.updateTYFSSum(db, areaAbb, strStu, school_dxx_charge, fee,
					tyfs);
		} catch (Exception e) {
			e.printStackTrace();
			cn.qtone.xxt.jzdx.JzdxUtil.saveException(db,
					"UniteSendTread�ౣ����־����ϸʱ�����쳣��" + e.toString() + " ѧУID="
							+ schoolId + ",��ʦID=" + user_id);
		}
	}
}
