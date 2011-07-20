package cn.qtone.xxt.jzdx.smsinter.task;

import java.util.ArrayList;

import cn.qtone.xxt.base.db.DBControl;
import cn.qtone.xxt.base.login.TeacherBean;
import cn.qtone.xxt.jzdx.MessageUtiltity;
import cn.qtone.xxt.jzdx.MessageUtiltity2;
import cn.qtone.xxt.jzdx.PubFunction;
import cn.qtone.xxt.jzdx.PubFunction2;
import cn.qtone.xxt.jzdx.Utiltity;
import cn.qtone.xxt.jzdx.task.SmsBean;

public class MultiSendTreadChargeType implements Runnable {
	private DBControl db;
	private SmsBean smsbean; // 群发信息的条件和值设置Bean
	private PubFunction pub = new PubFunction();
	private PubFunction2 pub2 = new PubFunction2();
	private String[] GradeID = null;
	private int flag; // 0：年级发送，1：全校发送
	private String[] str = null; // str[0]为电话号码，1：家庭ID，2：学生编号
	private ArrayList str1 = null; // str.get(0)为发送电话号码，1：家庭ID，2：学生编号
	private int school_dxx_charge = 0; // 学校是否开通家长短信箱收费
	private int fee = 0; // 学校是否允许发送免费短信
	private int tyfs = 1; // 免费群发短信数
	private int areaID = 0; // 地区ID
	private int chargeType; // 家长接收短信类型 －1:免费 0：收费 1：体验
	private int isPreParent = 0;
	private int isLong = 0; // 长短信
	private int stuType = 0;// 学生住宿类型 0：走读 1、住读 2：长住
	private boolean canSendUni;// 是否可发外网短信
	private String[] strUni = null; // str[0]为电话号码，1：家庭ID，2：学生编号
	private TeacherBean teacher = null; // 老师session对象

	public MultiSendTreadChargeType(DBControl db, SmsBean smsbean,
			String[] GradeID, int flag, int school_dxx_charge, int fee,
			int tyfs, int areaID, int chargeType) {
		this.db = db;
		this.smsbean = smsbean;
		this.GradeID = GradeID;
		this.flag = flag;
		this.school_dxx_charge = school_dxx_charge;
		this.fee = fee;
		this.tyfs = tyfs;
		this.areaID = areaID;
		this.chargeType = chargeType;
	}

	public MultiSendTreadChargeType(DBControl db, SmsBean smsbean,
			String[] GradeID, int flag, int school_dxx_charge, int fee,
			int tyfs, int areaID, int chargeType, int isPreParent) {
		this.db = db;
		this.smsbean = smsbean;
		this.GradeID = GradeID;
		this.flag = flag;
		this.school_dxx_charge = school_dxx_charge;
		this.fee = fee;
		this.tyfs = tyfs;
		this.areaID = areaID;
		this.chargeType = chargeType;
		this.isPreParent = isPreParent;
	}

	/*
	 * public MultiSendTreadChargeType(DBControl db, SmsBean smsbean, String[]
	 * GradeID, int flag, int school_dxx_charge, int fee, int tyfs, int areaID,
	 * int chargeType, int isPreParent, int isLong) { this.db = db; this.smsbean =
	 * smsbean; this.GradeID = GradeID; this.flag = flag; this.school_dxx_charge =
	 * school_dxx_charge; this.fee = fee; this.tyfs = tyfs; this.areaID =
	 * areaID; this.chargeType = chargeType; this.isPreParent = isPreParent;
	 * this.isLong = isLong; }
	 */

	public MultiSendTreadChargeType(DBControl db, SmsBean smsbean,
			String[] GradeID, int flag, int school_dxx_charge, int fee,
			int tyfs, int areaID, int chargeType, int isPreParent, int isLong,
			int stuType, boolean canSendUni, TeacherBean teacher) {
		this.db = db;
		this.smsbean = smsbean;
		this.GradeID = GradeID;
		this.flag = flag;
		this.school_dxx_charge = school_dxx_charge;
		this.fee = fee;
		this.tyfs = tyfs;
		this.areaID = areaID;
		this.chargeType = chargeType;
		this.isPreParent = isPreParent;
		this.isLong = isLong;
		this.stuType = stuType;
		this.canSendUni = canSendUni;
		this.teacher = teacher;
	}

	public void run() {
		String is_sendinTime = null;
		try {
//			System.out.println(",,,,,canSendUni=" + canSendUni
//					+ ",,,chargeType=" + chargeType);
			String tran_code = db
					.getValue("select tran_code from yw_transaction where transaction=1 and area_id="
							+ smsbean.getArea_id());
			// System.out.println("---"+smsbean.getSmsType());
			if ("jjtz".equals(smsbean.getSmsType())) { // 紧急通知类型 发送
				str = pub2.getUrgentAllMobilData(db, smsbean.getSchoolId(),
						smsbean.getAreaAbb(), flag, GradeID);
			} else { // 其他类型 发送
				str = pub.getAllMobileandFamilyIDCondChargeType(db, smsbean
						.getSchoolId(), smsbean.getAreaAbb(), flag, GradeID,
						school_dxx_charge, fee, tyfs, areaID, chargeType,
						stuType);
				if (canSendUni && chargeType == 2) {// 可发外网短信,并且选择的是全部类型
					strUni = pub.getAllMobileandFamilyIDUni(db, smsbean
							.getSchoolId(), smsbean.getAreaAbb(), flag,
							GradeID, school_dxx_charge, fee, tyfs, areaID,
							stuType);
				}
			}
			smsbean.setSendPhones(str[0]);
			smsbean.setFamilyId(str[1]);
			smsbean.setStuSequence(str[2]);
			str1 = pub
					.getFinalSendInfo(db, smsbean.getAreaAbb(), ""
							.equals(smsbean.getFamilyId()) ? "-1" : smsbean
							.getFamilyId(), smsbean.getSmsType(), smsbean
							.getSendTime());
			MessageUtiltity message = new MessageUtiltity();
			MessageUtiltity2 message2 = new MessageUtiltity2();
			String dateStr = message2.getDateStr();
			String sn = null;
			if (smsbean.getSendTime() == null) { // 即时发送
				sn = db.getValue("select " + smsbean.getAreaAbb()
						+ "_sms_sn.nextval from dual");
				Utiltity message3 = new Utiltity();
				message3.handleMulitMessage(db, smsbean.getSource(), str1
						.get(0)
						+ "", smsbean.getSendTime(), smsbean.getContent(),
						smsbean.getSchoolId(), smsbean.getUserId(), str1.get(2)
								+ "", smsbean.getAreaAbb(), smsbean.getFlag(),
						smsbean.getKind(), str1.get(1) + "",
						smsbean.getSp_id(), tran_code, Integer
								.parseInt(cn.qtone.xxt.jzdx.Const.smsType
										.get(smsbean.getSmsType())
										+ ""), dateStr, isPreParent, isLong,
						Integer.parseInt(sn));
				if (canSendUni && chargeType == 2) {
					message3.handleMulitMessageUni(db, smsbean.getSource(),
							strUni[0], smsbean.getSendTime(), smsbean
									.getContent(), smsbean.getSchoolId(),
							smsbean.getUserId(), strUni[1], smsbean
									.getAreaAbb(), smsbean.getFlag(), smsbean
									.getKind(), strUni[1], smsbean.getSp_id(),
							tran_code, Integer
									.parseInt(cn.qtone.xxt.jzdx.Const.smsType
											.get(smsbean.getSmsType())
											+ ""), dateStr, isPreParent,
							isLong, Integer.parseInt(sn));
				}
			} else { // 定时发送
				int taskId = message2.handleTask(db, smsbean.getSource(),
						smsbean.getSendTime(), smsbean.getSchoolId(), smsbean
								.getUserId(), smsbean.getAreaAbb(), smsbean
								.getKind(), smsbean.getSp_id(), tran_code,
						"jjtz".equals(smsbean.getSmsType()) ? 99 : 1, (smsbean
								.getKind() == 4 ? "年级发送" : "全校发送")
								+ dateStr, 1, smsbean.getIp(), Integer
								.parseInt(cn.qtone.xxt.jzdx.Const.smsType
										.get(smsbean.getSmsType())
										+ ""), smsbean.getKind() == 5 ? ""
								: pub.getGrade_name(db, smsbean.getGrade()),
						isPreParent, teacher);
				if (canSendUni && chargeType == 2) {// 可发外网短信,并且选择的是全部类型
					message2.handleMulitMessage(db,
							strUni[0].equals("") ? (str1.get(0).toString())
									: (strUni[0] + "," + str1.get(0)), smsbean
									.getContent(), taskId, smsbean
									.getSchoolId(), smsbean.getUserId(),
							strUni[2].equals("") ? (str1.get(2).toString())
									: (strUni[2] + "," + str1.get(2)),
							strUni[1].equals("") ? (str1.get(1).toString())
									: (strUni[1] + "," + str1.get(1)), smsbean
									.getAreaAbb(), isLong);
				} else {
					message2.handleMulitMessage(db, str1.get(0) + "", smsbean
							.getContent(), taskId, smsbean.getSchoolId(),
							smsbean.getUserId(), str1.get(2) + "", str1.get(1)
									+ "", smsbean.getAreaAbb(), isLong);
				}
			}
			pub.transferRMI(smsbean.getAreaAbb());

			if (smsbean.getSendTime() == null) { // 即时发送才处理
				message.updateSmsStat(db, smsbean.getAreaAbb(), str[1], 1,
						smsbean.getSchoolId());

				if (smsbean.getSendTime() == null) {
					is_sendinTime = "";
				} else {
					is_sendinTime = "(定时发送，操作时间：" + pub.getTimeStr() + ")";
				}
				// 写入日志表,全校发送和年级发送classId参数为0
				// 获取操作序列
				// String sn=db.getValue("select
				// "+smsbean.getAreaAbb()+"_sms_sn.nextval from dual");
				if (smsbean.getKind() == 5) {
					message.saveLogs(db, smsbean.getAreaAbb(), smsbean
							.getSchoolId(), smsbean.getUserId(), smsbean
							.getIp(), "全校发送：" + smsbean.getContent()
							+ is_sendinTime, 5, 5, dateStr, Integer
							.parseInt(cn.qtone.xxt.jzdx.Const.smsType
									.get(smsbean.getSmsType())
									+ ""), Integer.parseInt(sn), 0, teacher);
				} else {
					String Grade_names = pub.getGrade_name(db, smsbean
							.getGrade());
					message.saveLogs(db, smsbean.getAreaAbb(), smsbean
							.getSchoolId(), smsbean.getUserId(), smsbean
							.getIp(), "年级发送：" + smsbean.getContent() + " 目标年级："
							+ Grade_names + is_sendinTime, 5, 4, dateStr,
							Integer.parseInt(cn.qtone.xxt.jzdx.Const.smsType
									.get(smsbean.getSmsType())
									+ ""), Integer.parseInt(sn), 0, teacher);
				}

				// 写入短信表 mdf by yuhailin 10-08-26 -----
				StringBuffer strBuf = new StringBuffer();
				strBuf.append("insert into ");
				strBuf.append(smsbean.getAreaAbb());
				strBuf
						.append("_dx_sms (sn,school_id,user_id,object_mobile,stu_sequence,content,");
				strBuf
						.append("dt,kind,family_id,source_mobile,smstype,admin_id,admin_userid,send_mode) values(?,?,?,?,?,?,");
				strBuf
						.append("to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?,3)");

				String Tsql = cn.qtone.xxt.base.share.SmsBean.GetSql(strBuf
						.toString(), smsbean.getAreaAbb(), "toDay");
				strBuf.delete(0, strBuf.length());
				strBuf.append(Tsql);

				boolean isSaveUniMobSmsArea = cn.qtone.xxt.jzdx.JzdxUtil
						.isRangeArea(smsbean.getAreaAbb()); // 是否需保存非移动号码短信明细的地区
//				System.out.println("strUni......." + strUni);
				String phoneAry[] = (str[0] + ((isSaveUniMobSmsArea && strUni != null) ? ("," + strUni[0])
						: "")).split(","); // 手机号码
				String familyIdAry[] = (str[1] + ((isSaveUniMobSmsArea && strUni != null) ? ("," + strUni[1])
						: "")).split(","); // 家长ID
				String stuSeqAry[] = (str[2] + ((isSaveUniMobSmsArea && strUni != null) ? ("," + strUni[2])
						: "")).split(","); // 学生编号
				int i = 0;
				String curTime = new PubFunction().getTimeStr() + ":00";
				for (i = 0; i < familyIdAry.length
						&& !familyIdAry[i].equals(""); i++) {
					if (i % 200 == 0) { // init
						db.prepareDB(strBuf.toString());
					}
					message.saveSmsBatch(db, smsbean.getSchoolId(), smsbean
							.getUserId(), phoneAry[i], stuSeqAry[i], smsbean
							.getContent(),
							smsbean.getSendTime() == null ? curTime : smsbean
									.getSendTime(), smsbean.getKind(), Integer
									.parseInt(familyIdAry[i]), smsbean
									.getSource(), smsbean.getSmsType(), Integer
									.parseInt(sn), teacher);
					if ((i + 1) % 200 == 0) { // execute
						db.PreparedExeBatch();
					}
				}
				if (i % 200 != 0) { // execute
					db.PreparedExeBatch();
				}
				strBuf = null;
				phoneAry = null;
				familyIdAry = null;
				stuSeqAry = null;
			}
			// -----
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error:: schoolId=" + smsbean.getSchoolId()
					+ "  userid=" + smsbean.getUserId() + " time="
					+ PubFunction.getDateStr() + " " + PubFunction.getHour()
					+ ":" + PubFunction.getMinute());
			cn.qtone.xxt.jzdx.JzdxUtil.saveException(db,
					"MultiSendTreadChargeType类运行时出现异常：" + e.toString()
							+ " 学校ID=" + smsbean.getSchoolId() + ",老师ID="
							+ smsbean.getUserId());
		}
	}

}
