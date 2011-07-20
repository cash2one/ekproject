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

	private String areaAbb = ""; // 区域简称

	private String strStu = ""; // 选择发送的学生ID

	private String source = ""; // 发送源号码（特服号）

	private String sendTime = ""; // 发送时间

	private int isPresuffix = 0; // 是否加学校名称前缀

	private String data = ""; // 发送内容

	private String userName = ""; // 老师姓名

	private String schoolName = ""; // 学校名称

	private int schoolId = 0; // 学校ID

	private int user_id = 0; // 老师ID

	private String sp_id = ""; // 企业代码

	private String tran_code = ""; // 业务代码

	private String ip = ""; // 操作IP

	private String isAlign = ""; // 是否定时

	private String smsType = ""; // 短信类型

	private int school_dxx_charge = 0; // 学校是否开通家长短信箱收费

	private int fee = 1; // 是否可以接收免费短信

	private int tyfs = 1; // 群发短信上限

	private int areaID = 0; // 地区ID

	private int isPreParent = 0; // 是否加家长称呼前缀

	private int isLong = 0; // 是否长短信方式发送

	private int classId = 0; // 班级ID

	private boolean canSendUni;// 是否可以发送外网短信

	private TeacherBean teacher = null; // 老师sesson对象

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
		// 取待发送的手机号码，学生编号
		// 学生表和家庭情况表
		String stuTabName = areaAbb + "_xj_student";
		String familyTabName = areaAbb + "_xj_family";
		String sendPhones = "", stuSequence = "", familyId = ""; // 目标手机号码，学生编号,family
		// ID
		String sendPhonesUni = "", stuSequenceUni = "", familyIdUni = "";// 外网目标手机号码、学生编号、family_id
		String targetStudent = "";

		// modify by yuhailin 2011-05-20 查询学校表的can_send_unmob字段值
		int canSendUniNumber = 1;
		try {
			canSendUniNumber = Integer.parseInt(db
					.getValue("select can_send_unmob from xj_school where id="
							+ schoolId));
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (canSendUni) {// 可发外网短信
			if (school_dxx_charge == 0) {// 学校免费
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
			} else {// 学校收费
				if (fee == 1) {// 设置可以向体验用户发送短信
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
				} else {// 设置不能向体验用户发送短信
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
			if (school_dxx_charge == 0) {// 学校免费，只要是开通了业务的都可以接收短信
				sql
						.append("select s.stu_sequence stu_sequence,f.id fid,f.phone phone,s.name sname,f.name pname from ");
				sql.append(stuTabName);
				sql.append(" s join ");
				sql.append(familyTabName);
				sql
						.append(" f on s.stu_sequence=f.stu_sequence where s.id in(");
				sql.append(strStu);
				sql.append(") and f.is_dxx=1 order by f.phone");
			} else {// 学校收费
				if (fee == 1) {// 设置可以向体验用户发送短信
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
				} else {// 设置不能向体验用户发送短信
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
					sendPhones += phone + ","; // 手机号码
					stuSequence += rs.getString("stu_sequence") + ","; // 学生编号
					familyId += rs.getString("fid") + ","; // family ID
				} else {
					sendPhonesUni += phone + ",";// 外网手机号码
					stuSequenceUni += rs.getString("stu_sequence") + ","; // 学生编号
					familyIdUni += rs.getString("fid") + ","; // family ID
				}
				targetStudent += rs.getString("sname") + "("
						+ rs.getString("pname") + ")" + ",";
			}
			// System.out.println("联通号码:"+sendPhonesUni);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error:: schoolId=" + schoolId + "  userid="
					+ user_id + " time=" + PubFunction.getDateStr() + " "
					+ PubFunction.getHour() + ":" + PubFunction.getMinute());
			cn.qtone.xxt.jzdx.JzdxUtil.saveException(db,
					"UniteSendTread类查询目标号码时出现异常：" + e.toString() + " 学校ID="
							+ schoolId + ",老师ID=" + user_id + ",sql="
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
		// 操作序列
		String sn = null;
		try {
			if (sendTime == null) { // 即时发送
				sn = db.getValue("select " + areaAbb
						+ "_sms_sn.nextval from dual");
				// 保存要发送信息短信缓存表(dx_groupsend)
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
			} else { // 定时发送
				// 保存要发送信息短信任务表(msgTaskMain/msgTaskSub)
				if (!"".equals(str.get(0))) {
					int taskId = util2.handleTask(db, source, sendTime,
							schoolId, user_id, areaAbb, 1, sp_id, tran_code, 1,
							"统一发送" + dateStr, 1, ip, Integer
									.parseInt(cn.qtone.xxt.jzdx.Const.smsType
											.get(smsType)
											+ ""), "", isPreParent, teacher);
					util2.handleMulitMessage(db, sendPhonesUni + str.get(0),
							data, taskId, schoolId, user_id, stuSequenceUni
									+ str.get(2), familyIdUni + str.get(1),
							areaAbb, isLong);
				}
			}
			// 调用RMI发送短信
			pub.transferRMI(areaAbb);

			if (sendTime == null && !(sendPhones + sendPhonesUni).equals("")) { // 即时发送才处理
				// 写入日志表(dx_logs)
				if (!"".equals(targetStudent)) {
					targetStudent = targetStudent.substring(0, targetStudent
							.length() - 1); // 发送的目标学生
				}
				System.out.println("写日志");
				util.saveLogs(db, areaAbb, schoolId, user_id, ip, "统一发送："
						+ data + " 目标学生：" + targetStudent + isAlign, 5, 1,
						dateStr, Integer
								.parseInt(cn.qtone.xxt.jzdx.Const.smsType
										.get(smsType)
										+ ""), Integer.parseInt(sn), classId,
						teacher);
				// 写入短信表 add by yuhailin 07-01-16 -----
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

				String phoneAry[] = sendPhones.split(","); // 手机号码
				String familyIdAry[] = familyId.split(","); // 家长ID
				String stuSeqAry[] = stuSequence.split(","); // 学生编号
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
				// 深圳地区对外网号码记录短信内容
				if (cn.qtone.xxt.jzdx.JzdxUtil.isRangeArea(areaAbb)) { // areaAbb.equals("sz")
					// 指定的地区会保存非移动号码短信明细
					phoneAry = sendPhonesUni.split(","); // 手机号码
					familyIdAry = familyIdUni.split(","); // 家长ID
					stuSeqAry = stuSequenceUni.split(","); // 学生编号
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

				// 更新短信辅助表(**_dx_smsstat)
				util.updateSmsStat(db, areaAbb, familyId, schoolId);

			}
			// 2008-10-07 更新体验用户接收统一发送短信的条数记录
			util.updateTYFSSum(db, areaAbb, strStu, school_dxx_charge, fee,
					tyfs);
		} catch (Exception e) {
			e.printStackTrace();
			cn.qtone.xxt.jzdx.JzdxUtil.saveException(db,
					"UniteSendTread类保存日志、明细时出现异常：" + e.toString() + " 学校ID="
							+ schoolId + ",老师ID=" + user_id);
		}
	}
}
