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
	private SmsBean smsbean; // Ⱥ����Ϣ��������ֵ����Bean
	private PubFunction pub = new PubFunction();
	private PubFunction2 pub2 = new PubFunction2();
	private String[] GradeID = null;
	private int flag; // 0���꼶���ͣ�1��ȫУ����
	private String[] str = null; // str[0]Ϊ�绰���룬1����ͥID��2��ѧ�����
	private ArrayList str1 = null; // str.get(0)Ϊ���͵绰���룬1����ͥID��2��ѧ�����
	private int school_dxx_charge = 0; // ѧУ�Ƿ�ͨ�ҳ��������շ�
	private int fee = 0; // ѧУ�Ƿ���������Ѷ���
	private int tyfs = 1; // ���Ⱥ��������
	private int areaID = 0; // ����ID
	private int chargeType; // �ҳ����ն������� ��1:��� 0���շ� 1������
	private int isPreParent = 0;
	private int isLong = 0; // ������
	private int stuType = 0;// ѧ��ס������ 0���߶� 1��ס�� 2����ס
	private boolean canSendUni;// �Ƿ�ɷ���������
	private String[] strUni = null; // str[0]Ϊ�绰���룬1����ͥID��2��ѧ�����
	private TeacherBean teacher = null; // ��ʦsession����

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
			if ("jjtz".equals(smsbean.getSmsType())) { // ����֪ͨ���� ����
				str = pub2.getUrgentAllMobilData(db, smsbean.getSchoolId(),
						smsbean.getAreaAbb(), flag, GradeID);
			} else { // �������� ����
				str = pub.getAllMobileandFamilyIDCondChargeType(db, smsbean
						.getSchoolId(), smsbean.getAreaAbb(), flag, GradeID,
						school_dxx_charge, fee, tyfs, areaID, chargeType,
						stuType);
				if (canSendUni && chargeType == 2) {// �ɷ���������,����ѡ�����ȫ������
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
			if (smsbean.getSendTime() == null) { // ��ʱ����
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
			} else { // ��ʱ����
				int taskId = message2.handleTask(db, smsbean.getSource(),
						smsbean.getSendTime(), smsbean.getSchoolId(), smsbean
								.getUserId(), smsbean.getAreaAbb(), smsbean
								.getKind(), smsbean.getSp_id(), tran_code,
						"jjtz".equals(smsbean.getSmsType()) ? 99 : 1, (smsbean
								.getKind() == 4 ? "�꼶����" : "ȫУ����")
								+ dateStr, 1, smsbean.getIp(), Integer
								.parseInt(cn.qtone.xxt.jzdx.Const.smsType
										.get(smsbean.getSmsType())
										+ ""), smsbean.getKind() == 5 ? ""
								: pub.getGrade_name(db, smsbean.getGrade()),
						isPreParent, teacher);
				if (canSendUni && chargeType == 2) {// �ɷ���������,����ѡ�����ȫ������
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

			if (smsbean.getSendTime() == null) { // ��ʱ���ͲŴ���
				message.updateSmsStat(db, smsbean.getAreaAbb(), str[1], 1,
						smsbean.getSchoolId());

				if (smsbean.getSendTime() == null) {
					is_sendinTime = "";
				} else {
					is_sendinTime = "(��ʱ���ͣ�����ʱ�䣺" + pub.getTimeStr() + ")";
				}
				// д����־��,ȫУ���ͺ��꼶����classId����Ϊ0
				// ��ȡ��������
				// String sn=db.getValue("select
				// "+smsbean.getAreaAbb()+"_sms_sn.nextval from dual");
				if (smsbean.getKind() == 5) {
					message.saveLogs(db, smsbean.getAreaAbb(), smsbean
							.getSchoolId(), smsbean.getUserId(), smsbean
							.getIp(), "ȫУ���ͣ�" + smsbean.getContent()
							+ is_sendinTime, 5, 5, dateStr, Integer
							.parseInt(cn.qtone.xxt.jzdx.Const.smsType
									.get(smsbean.getSmsType())
									+ ""), Integer.parseInt(sn), 0, teacher);
				} else {
					String Grade_names = pub.getGrade_name(db, smsbean
							.getGrade());
					message.saveLogs(db, smsbean.getAreaAbb(), smsbean
							.getSchoolId(), smsbean.getUserId(), smsbean
							.getIp(), "�꼶���ͣ�" + smsbean.getContent() + " Ŀ���꼶��"
							+ Grade_names + is_sendinTime, 5, 4, dateStr,
							Integer.parseInt(cn.qtone.xxt.jzdx.Const.smsType
									.get(smsbean.getSmsType())
									+ ""), Integer.parseInt(sn), 0, teacher);
				}

				// д����ű� mdf by yuhailin 10-08-26 -----
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
						.isRangeArea(smsbean.getAreaAbb()); // �Ƿ��豣����ƶ����������ϸ�ĵ���
//				System.out.println("strUni......." + strUni);
				String phoneAry[] = (str[0] + ((isSaveUniMobSmsArea && strUni != null) ? ("," + strUni[0])
						: "")).split(","); // �ֻ�����
				String familyIdAry[] = (str[1] + ((isSaveUniMobSmsArea && strUni != null) ? ("," + strUni[1])
						: "")).split(","); // �ҳ�ID
				String stuSeqAry[] = (str[2] + ((isSaveUniMobSmsArea && strUni != null) ? ("," + strUni[2])
						: "")).split(","); // ѧ�����
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
					"MultiSendTreadChargeType������ʱ�����쳣��" + e.toString()
							+ " ѧУID=" + smsbean.getSchoolId() + ",��ʦID="
							+ smsbean.getUserId());
		}
	}

}
