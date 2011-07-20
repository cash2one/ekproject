package cn.qtone.xxt.jzdx.smsinter.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.qtone.xxt.base.db.DBControl;
import cn.qtone.xxt.base.login.TeacherBean;
import cn.qtone.xxt.base.share.BaseResult;
import cn.qtone.xxt.edu.util.Util;
import cn.qtone.xxt.jzdx.Const;
import cn.qtone.xxt.jzdx.MessageUtiltity;
import cn.qtone.xxt.jzdx.MessageUtiltity2;
import cn.qtone.xxt.jzdx.PubFunction;
import cn.qtone.xxt.jzdx.smsinter.bean.Info;
import cn.qtone.xxt.jzdx.smsinter.bean.SMSBean;
import cn.qtone.xxt.jzdx.smsinter.task.EachSendTread;
import cn.qtone.xxt.jzdx.smsinter.task.MultiSendTreadChargeType;
import cn.qtone.xxt.jzdx.smsinter.task.UniteSendTread;
import cn.qtone.xxt.jzdx.task.SmsBean;

public class SMSUtil {

	public Info parse(String message) {
		Info info = new Info();
		try {
			// ���ȶ�������,����Document
			Document xmlDoc = DocumentHelper.parseText(message);

			// ȡ��XML�ĵ��ĸ�Ԫ�ؼ�ֵ
			Element root = xmlDoc.getRootElement();// ��Ԫ��
			Element loguserEle = root.element("loguser");
			// ��ȡ���ݣ�������Bean
			// Element sendKindEle = loguserEle.element("sendKind");
			info.setSendKind(loguserEle.elementTextTrim("sendKind"));
			// Element userIdEle = loguserEle.element("userId");
			info.setUserId(loguserEle.elementTextTrim("userId"));
			// Element abbEle = loguserEle.element("abb");
			info.setAbb(loguserEle.elementTextTrim("abb"));
			// Element parentTypeEle = loguserEle.element("parentType");
			info.setParentType(loguserEle.elementTextTrim("parentType"));
			info.setStu_type(loguserEle.elementTextTrim("studentType"));
			// Element smsTypeEle = loguserEle.element("smsType");
			info.setSmsType(loguserEle.elementTextTrim("smsType"));
			info.setIsAddSchool(loguserEle.elementTextTrim("isAddSchool"));
			info.setIsAddTeacher(loguserEle.elementTextTrim("isAddTeacher"));
			info.setIsAddParent(loguserEle.elementTextTrim("isAddParent"));
			info.setIsLongSMS(loguserEle.elementTextTrim("isLongSms"));
			info.setIsSendNow(loguserEle.elementTextTrim("isSendNow"));
			info.setSendTime(loguserEle.elementTextTrim("sendTime"));
			Element sendBodyEle = loguserEle.element("sendBody");
			List list = sendBodyEle.elements("sms");
			for (int i = 0; i < list.size(); i++) {
				SMSBean bean = new SMSBean();
				Element SmsEle = (Element) list.get(i);
				bean.setSchoolId(SmsEle.elementTextTrim("schoolId"));
				bean.setClassId(SmsEle.elementTextTrim("classId"));
				bean.setStudentIds(SmsEle.elementTextTrim("studentIds"));
				bean.setContent(SmsEle.elementTextTrim("content"));
				info.getSMMList().add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return info;
	}

	public boolean validate(Info bean) {
		return true;
	}

	/**
	 * ȫУ����
	 * 
	 * @param info
	 * @return
	 */
	public BaseResult sendAll(DBControl db, Info info) {
		BaseResult br = new BaseResult();
		SMSBean bean = (SMSBean) info.getSMMList().get(0);
		int iSchoolID = Integer.parseInt(bean.getSchoolId());

		// ����ѧУ��SI��Ϣ
		String sp_code = "", sp_id = "";
		HashMap tmpMap = PubFunction.getSIInfoBySchoolId(db, iSchoolID);
		try {
			sp_code = Util.valueOf(tmpMap.get("code"));
			sp_id = Util.valueOf(tmpMap.get("sp_id"));
		} catch (Exception e) {
		}
		// �ж���û��SI����ѧУ
		if ("".equals(sp_code)) {
			br.setState(0);
			br.setMessage("��û��SI����ѧУ���ݲ��ܷ��Ͷ��ţ�");
			return br;
		}
		// ��������
		String filter = MessageUtiltity.checkSms2(db, bean.getContent());
		if (!"".equals(Util.valueOf(filter))) {
			br.setState(3);
			br.setMessage("���Ͷ����к���" + filter + "�����ۣ�������˴�������Ӱ�죬���޸ĺ��ٷ��ͣ�");
			return br;
		}
		// һ������
		filter = MessageUtiltity.checkSms1(db, bean.getContent());
		if (!"".equals(filter) && filter.startsWith("1")) {
			br.setState(3);
			br.setMessage("���Ͷ����к���" + filter.substring(1)
					+ "�����ۣ�������˴�������Ӱ�죬���޸ĺ��ٷ��ͣ�");
			return br;
		}
		// �ж��Ƿ��Ѿ����͹�
		if (haveSend(db, bean.getContent(), iSchoolID)) {
			br.setState(3);
			br
					.setMessage("�������û���Ϊ��ֹ�ҳ��ظ����յ�ͬ���Ķ���Ϣ���ڽ��С�ȫУ���͡�ʱ����ͬ���ݵĶ�����һ����ֻ�ܷ���һ�Σ��������⣡");
			return br;
		}
		// �Ƿ�����ͬ�������ͨ��
		boolean haveConfirmed = new MessageUtiltity().haveConfirmed(db,
				iSchoolID, bean.getContent());
		// ���뵽�������
		int areaId = Integer.parseInt(db
				.getValue("select id from area where abb='" + info.getAbb()
						+ "'"));
		insertIntoTemp(db, iSchoolID, areaId, bean.getContent());
		// SI���õĴ�ȷ�϶�����
		int limitNum = 70;
		try {
			limitNum = Integer
					.parseInt(db
							.getValue("select qnum from si_set a left join qx_admin_si b on a.si_id=b.id left join qx_user_school c on b.id=c.user_id where c.school_id="
									+ iSchoolID));
		} catch (Exception e) {
		}

		// �ƶ����õĴ�ȷ�϶�����
		int YDLimitNum;
		try {
			YDLimitNum = Integer.parseInt(db
					.getValue("select qnum from city_set where area_id="
							+ areaId));
		} catch (Exception e) {
			YDLimitNum = 1000;
		}

		// message_code,school_name
		List list = db
				.getValues(
						"select t.userid,t.username,t.message_code,s.school_name from xj_teacher t,xj_school s where t.school_id=s.id and t.id="
								+ info.getUserId(), 4);
		String userid = (String) list.get(0);
		String teacherName = (String) list.get(1);
		String source = (String) list.get(2);
		String schoolName = (String) list.get(3);
		// tran_code
		String tran_code = db
				.getValue("select tran_code from yw_transaction where transaction=1 and area_id="
						+ areaId);
		// �ҳ��������Ƿ��շѣ��Ƿ���Զ�����û����Ͷ��ŵ�
		int school_dxx_charge = 0, fee = 0, tyfs = 0;
		boolean canSendUni = false;
		try {
			Map map = getSchoolTranInfo(db, iSchoolID);
			school_dxx_charge = Integer.parseInt((String) map
					.get("school_dxx_charge"));
			fee = Integer.parseInt((String) map.get("free"));
			tyfs = Integer.parseInt((String) map.get("tyfs"));
			canSendUni = "1".equals(map.get("can_send_uni")) ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ��ѯĿ�������
		int objectNum = getSendAllObjectNum(db, info.getAbb(), iSchoolID,
				Integer.parseInt(info.getParentType()), school_dxx_charge, fee,
				tyfs, canSendUni);

		// ��������
		String content = ("1".equals(info.getIsAddSchool()) ? "��" + schoolName
				+ "��" : "")
				+ bean.getContent()
				+ ("1".equals(info.getIsAddTeacher()) ? " /" + teacherName : "");

		// �������۹���
		if (!haveConfirmed && !"".equals(filter)) {
			Map map = getCheckInfo(db, iSchoolID);
			String confPhone = (String) map.get("phone");
			Calendar fromCal = (Calendar) map.get("from");
			Calendar toCal = (Calendar) map.get("to");
			saveConfirmSMSSendAll(
					db,
					schoolName,
					source,
					"0".equals(info.getIsSendNow()) ? info.getSendTime() : null,
					iSchoolID,
					userid,
					Integer.parseInt(info.getUserId()),
					"",
					"",
					3,
					"0".equals(info.getIsSendNow()) ? 1 : 0,
					"wap",
					info.getAbb(),
					sp_id,
					tran_code,
					content,
					filter,
					objectNum > limitNum ? 2 : 0,
					limitNum,
					Integer.parseInt(info.getIsAddParent()),
					Integer.parseInt(info.getIsLongSMS()),
					objectNum,
					Integer.parseInt(Const.smsType.get(info.getSmsType())
							.toString()),
					Integer.parseInt(info.getParentType()),
					-1,
					(Const.NEED_CONFIRM_FILTER_SWITCH || (Const.NEED_CONFIRM_SWITCH && objectNum > YDLimitNum)),
					confPhone, fromCal, toCal);
			br.setState(2);
			if (objectNum > limitNum) {
				br
						.setMessage("���Ͷ����к���" + filter.substring(1)
								+ "�����۲���Ŀ���ֻ�������" + limitNum
								+ ",���ܻ�����˴�������Ӱ�죬��ȷ���Ƿ��ܷ��ͣ��Ժ����ڡ���ȷ�϶��š������в�ѯ��");
			} else {
				br.setMessage("���Ͷ����к���" + filter.substring(1)
						+ "������,���ܻ�����˴�������Ӱ�죬��ȷ���Ƿ��ܷ��ͣ��Ժ����ڡ���ȷ�϶��š������в�ѯ��");
			}
			return br;
		}

		// �����ȷ����
		if (!haveConfirmed && objectNum > limitNum) {
			Map map = getCheckInfo(db, iSchoolID);
			String confPhone = (String) map.get("phone");
			Calendar fromCal = (Calendar) map.get("from");
			Calendar toCal = (Calendar) map.get("to");
			saveConfirmSMSSendAll(
					db,
					schoolName,
					source,
					info.getSendTime(),
					iSchoolID,
					userid,
					Integer.parseInt(info.getUserId()),
					"",
					"",
					3,
					"0".equals(info.getIsSendNow()) ? 1 : 0,
					"wap",
					info.getAbb(),
					sp_id,
					tran_code,
					content,
					filter,
					1,
					limitNum,
					Integer.parseInt(info.getIsAddParent()),
					Integer.parseInt(info.getIsLongSMS()),
					objectNum,
					Integer.parseInt(Const.smsType.get(info.getSmsType())
							.toString()),
					Integer.parseInt(info.getParentType()),
					-1,
					(Const.NEED_CONFIRM_FILTER_SWITCH || (Const.NEED_CONFIRM_SWITCH && objectNum > YDLimitNum)),
					confPhone, fromCal, toCal);
			br.setState(2);
			br.setMessage("Ŀ���ֻ�������" + limitNum + "����ȷ���Ƿ��ܷ��ͣ��Ժ����ڡ���ȷ�϶��š������в�ѯ��");
			return br;
		}

		// һ��OK����ʼ���Ͷ���
		SmsBean smsbean = new SmsBean();
		smsbean.setSource(source);
		smsbean.setSendTime("0".equals(info.getIsSendNow()) ? info
				.getSendTime() : null);
		smsbean.setContent(content);
		smsbean.setSmsType(info.getSmsType());
		smsbean.setSchoolId(iSchoolID);
		smsbean.setUserId(Integer.parseInt(info.getUserId()));
		smsbean.setAreaAbb(info.getAbb());
		smsbean.setFlag(0);
		smsbean.setKind(5);// 5��ʾȫУ����
		smsbean.setSp_id(sp_id);
		smsbean.setArea_id(areaId);
		smsbean.setGrade(null);
		smsbean.setIp("wap");

		// Ϊ���ù�������������һ��TeacherBean
		TeacherBean teacherBean = new TeacherBean();
		teacherBean.setAdmin_id(-1);
		teacherBean.setAdmin_userid("");

		MultiSendTreadChargeType sendbean = new MultiSendTreadChargeType(db,
				smsbean, null, 1, school_dxx_charge, fee, tyfs, areaId, Integer
						.parseInt(info.getParentType()), Integer.parseInt(info
						.getIsAddParent()), Integer.parseInt(info
						.getIsLongSMS()), Integer.parseInt(info.getStu_type()), canSendUni, teacherBean);

		new Thread(sendbean).start();

		br.setState(1);
		br.setMessage("���ͳɹ���");
		return br;
	}

	private boolean haveSend(DBControl db, String content, int schoolId) {
		String sql = "select count(id) from dx_sendalltemp where school_id=? and content = ? ";
		int count = 0;
		db.prepareDB(sql);
		try {
			db.setInt(1, schoolId);
			db.setString(2, content);
			count = db.prepareCount();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closePrepared();
		}
		return count > 0 ? true : false;
	}

	private void insertIntoTemp(DBControl db, int iSchoolID, int iAreaID,
			String sContent) {
		try {
			db.prepareCallDB("{call in_dx_sendalltemp(?,?,?)}");
			db.setInt(1, iAreaID);
			db.setInt(2, iSchoolID);
			db.setString(3, sContent);
			db.preparedExeDB();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.closePrepared();
			}
		}
	}

	private void saveConfirmSMSSendAll(DBControl db, String schoolName,
			String source, String sendTime, int schoolId, String userid,
			int user_id, String stuSequence, String stu_name, int method,
			int timing, String ip, String abb, String sp_id, String tran_code,
			String content, String sTmp, int tag, int limitNum,
			int isPreParent, int isLong, int obj_num, int smstype,
			int charge_type, int stu_type, boolean needConfirm,
			String confPhone, Calendar fromCal, Calendar toCal) {
		PubFunction pub = new PubFunction();

		StringBuffer bufsql = new StringBuffer();
		bufsql
				.append("insert into dx_confirmsms(source_mobile,object_mobile,send_time,create_time,content,school_id,user_id,stu_sequence,method,timing,ip,area_abb,family_id,");
		bufsql
				.append("sp_id,tran_code,stu_name,flag,isPreParent,islong,obj_num,is_check,smstype,charge_type,stu_type) values(?,?,to_date(?,'yyyy-mm-dd,hh24-mi-ss'),");
		bufsql.append("sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		int id = 0;
		int row = 1;
		try {
			db.prepareDB(bufsql.toString());
			bufsql = null;
			db.setString(row++, source);
			db.setString(row++, "");
			db.setString(row++, sendTime);
			db.setString(row++, content);
			db.setInt(row++, schoolId);
			db.setInt(row++, user_id);
			db.setString(row++, stuSequence);
			db.setInt(row++, method); // ********************method
			db.setInt(row++, timing); // ********************timing
			db.setString(row++, ip);
			db.setString(row++, abb);
			db.setString(row++, "");
			db.setString(row++, sp_id);
			db.setString(row++, tran_code);
			db.setString(row++, stu_name);
			db.setInt(row++, tag);
			db.setInt(row++, isPreParent);
			db.setInt(row++, isLong);
			db.setInt(row++, obj_num);
			if (needConfirm) {
				db.setInt(row++, 1);
			} else {
				db.setInt(row++, 2);
			}
			db.setInt(row++, smstype);
			db.setInt(row++, charge_type);
			db.setInt(row++, stu_type);
			if (db.preparedExeUpdate() > 0) {
				db.closePrepared();
				id = PubFunction
						.parseInt(db
								.getValue("select max(id) from dx_confirmsms where user_id="
										+ user_id));
			}

			// id����7λ������7λ
			String confirmId = String.valueOf(id);
			int length = confirmId.length();
			if (length < 7) {
				for (int ii = 0; ii < 7 - length; ii++) {
					confirmId = "0" + confirmId;
				}
			}

			StringBuffer siMessage = new StringBuffer();
			siMessage.append(schoolName);
			siMessage.append("��ʦ");
			siMessage.append(userid);
			siMessage.append("��");
			siMessage.append(pub.getTimeStr());
			siMessage.append("���͵Ķ��š�");
			siMessage.append(content);
			siMessage.append("��");
			if (tag == 0 || tag == 2) { // ����������
				siMessage.append("�к���");
				siMessage.append(pub.getChar(sTmp.substring(1)));
				siMessage.append("������");
				if (tag == 2) {
					siMessage.append("����");
				}
			}
			if (tag == 1 || tag == 2) { // �����޶�����
				siMessage.append("Ŀ�����������");
				siMessage.append(limitNum);
			}

			siMessage.append("����������ظ�1������������ظ�2");
			MessageUtiltity util = new MessageUtiltity();
			// ��ǰʱ��
			Calendar curCal = Calendar.getInstance();
			curCal.set(Calendar.SECOND, 0);
			curCal.set(Calendar.MILLISECOND, 0);
			if (confPhone != null && !"".equals(confPhone)) {
				if (PubFunction.getPhoneType(confPhone) <= 1
						&& curCal.before(toCal) && curCal.after(fromCal)) {
					// �ƶ��ֻ�����ŷ���
					util.saveSingleGroupSend(db, "091" + confirmId + "2",
							confPhone, null, siMessage.toString(), schoolId,
							user_id, "0", abb, 1, 0, "0",
							cn.qtone.xxt.jzdx.Const.free_sp_id,
							cn.qtone.xxt.jzdx.Const.free_tran_code);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.closePrepared();

			}
		}
	}

	/**
	 * ��ѯ��������˶��ŵ��ֻ�����,���ʱ���
	 * 
	 * @param db
	 * @param schoolId
	 * @return
	 */
	private Map getCheckInfo(DBControl db, int schoolId) {
		Map map = new HashMap();
		String phoneSql = "select d.phone,d.from_hour,d.from_min,d.to_hour,d.to_min from qx_user_school a join si_set b on a.user_id=b.si_id and a.school_id="
				+ schoolId
				+ " join xj_school c on a.school_id=c.id and c.id="
				+ schoolId
				+ " join sub_si_set d on b.id=d.si_set_id and c.town_id=d.town_id";
		List li = db.getValues(phoneSql, 5);
		String confPhone = "";
		int from_hour, from_min, to_hour, to_min;
		Calendar fromCal = Calendar.getInstance();
		Calendar toCal = Calendar.getInstance();
		if (li.size() != 0) {
			confPhone = li.get(0) == null ? "" : li.get(0).toString();
			from_hour = Integer.parseInt(li.get(1).toString());
			from_min = Integer.parseInt(li.get(2).toString());
			to_hour = Integer.parseInt(li.get(3).toString());
			to_min = Integer.parseInt(li.get(4).toString());
			fromCal.set(Calendar.HOUR_OF_DAY, from_hour);
			fromCal.set(Calendar.MINUTE, from_min);
			fromCal.set(Calendar.SECOND, 0);
			fromCal.set(Calendar.MILLISECOND, 0);
			toCal.set(Calendar.HOUR_OF_DAY, to_hour);
			toCal.set(Calendar.MINUTE, to_min);
			toCal.set(Calendar.SECOND, 0);
			toCal.set(Calendar.MILLISECOND, 0);
		}
		map.put("from", fromCal);
		map.put("to", toCal);
		map.put("phone", confPhone);
		return map;
	}

	/**
	 * ȫУ���͵�Ŀ������
	 * 
	 * @param db
	 * @param abb
	 * @param schoolId
	 * @param school_dxx_charge
	 * @param fee
	 * @param tyfs
	 * @param canSendUni
	 * @param chargeType
	 * @return
	 */
	private int getSendAllObjectNum(DBControl db, String abb, int schoolId,
			int chargeType, int school_dxx_charge, int fee, int tyfs,
			boolean canSendUni) {
		StringBuffer sqlCnt = new StringBuffer();
		if (school_dxx_charge == 0) {// ���
			if (canSendUni) {// ���Է���������
				sqlCnt
						.append("select count(1) from "
								+ abb
								+ "_xj_family a join "
								+ abb
								+ "_xj_stu_class b on a.stu_sequence=b.stu_sequence and (a.is_dxx=1 or a.phonetype>0 )");
				if (chargeType != 2) {
					sqlCnt.append("and a.charge=" + chargeType);
				}
				sqlCnt.append(" and b.school_id=" + schoolId + " ");
				sqlCnt
						.append("join xj_class c on b.class_id=c.id and c.class_type=1 and c.in_school=1 ");
			} else {
				sqlCnt
						.append("select count(1) from "
								+ abb
								+ "_xj_family a join "
								+ abb
								+ "_xj_stu_class b on a.stu_sequence=b.stu_sequence and a.is_dxx=1 ");
				if (chargeType != 2) {
					sqlCnt.append("and a.charge=" + chargeType);
				}
				sqlCnt.append(" and b.school_id=" + schoolId + " ");
				sqlCnt
						.append("join xj_class c on b.class_id=c.id and c.class_type=1 and c.in_school=1 ");
			}
		} else {// �շ�
			if (fee == 1) {// ���Զ������û����Ͷ���
				if (canSendUni) {// ���Է���������
					sqlCnt
							.append("select count(1) from "
									+ abb
									+ "_xj_family a join "
									+ abb
									+ "_xj_stu_class b on a.stu_sequence=b.stu_sequence and (a.is_dxx=1 or a.phonetype>0 )");
					if (chargeType != 2) {
						sqlCnt.append("and a.charge=" + chargeType);
					}
					sqlCnt.append(" and (a.charge!=0 or a.ty<" + tyfs + ")");
					sqlCnt.append(" and b.school_id=" + schoolId + " ");
					sqlCnt
							.append("join xj_class c on b.class_id=c.id and c.class_type=1 and c.in_school=1 ");
				} else {
					sqlCnt
							.append("select count(1) from "
									+ abb
									+ "_xj_family a join "
									+ abb
									+ "_xj_stu_class b on a.stu_sequence=b.stu_sequence and a.is_dxx=1 ");
					if (chargeType != 2) {
						sqlCnt.append("and a.charge=" + chargeType);
					}
					sqlCnt.append(" and (a.charge!=0 or a.ty<" + tyfs + ")");
					sqlCnt.append(" and b.school_id=" + schoolId + " ");
					sqlCnt
							.append("join xj_class c on b.class_id=c.id and c.class_type=1 and c.in_school=1 ");
				}
			} else {
				if (canSendUni) {// ���Է���������
					sqlCnt
							.append("select count(1) from "
									+ abb
									+ "_xj_family a join "
									+ abb
									+ "_xj_stu_class b on a.stu_sequence=b.stu_sequence and (a.is_dxx=1 or a.phonetype>0 )");
					if (chargeType != 2) {
						sqlCnt.append("and a.charge=" + chargeType);
					}
					sqlCnt.append(" and a.charge!=0 ");
					sqlCnt.append(" and b.school_id=" + schoolId + " ");
					sqlCnt
							.append("join xj_class c on b.class_id=c.id and c.class_type=1 and c.in_school=1 ");
				} else {
					sqlCnt
							.append("select count(1) from "
									+ abb
									+ "_xj_family a join "
									+ abb
									+ "_xj_stu_class b on a.stu_sequence=b.stu_sequence and a.is_dxx=1 ");
					if (chargeType != 2) {
						sqlCnt.append("and a.charge=" + chargeType);
					}
					sqlCnt.append(" and a.charge!=0 ");
					sqlCnt.append(" and b.school_id=" + schoolId + " ");
					sqlCnt
							.append("join xj_class c on b.class_id=c.id and c.class_type=1 and c.in_school=1 ");
				}
			}
		}
		db.prepareDB(sqlCnt.toString());
		return db.prepareCount();
	}

	private Map getSchoolTranInfo(DBControl db, int schoolId) {
		Map map = new HashMap();
		String sql = "select s.is_dxx_charge,s.free,s.tyfs,s.gxhfs,s.can_send_unmob can_send_uni from xj_school s where s.id="
			+ schoolId;
		ResultSet rs = null;
		db.prepareDB(sql);
		try {
			rs = db.preparedQuery();
			if (rs.next()) {
				map.put("school_dxx_charge", rs.getString("is_dxx_charge"));
				map.put("free", rs.getString("free"));
				map.put("tyfs", rs.getString("tyfs"));
				map.put("gxhfs", rs.getString("gxhfs"));
				map.put("can_send_uni", rs.getString("can_send_uni"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db.closePrepared();
		}
		return map;

	}

	public String getResponseMessage(BaseResult br) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"gb2312\" ?>").append(
				"<logusers><loguser><result>").append(br.getState()).append(
				"</result><remark>").append(br.getMessage()).append(
				"</remark></loguser></logusers>");
		return sb.toString();
	}

	public BaseResult sendUnite(DBControl db, Info info,
			HttpServletRequest request) {
		BaseResult br = new BaseResult();
		SMSBean bean = (SMSBean) info.getSMMList().get(0);
		int iSchoolID = Integer.parseInt(bean.getSchoolId());
		// ����ѧУ��SI��Ϣ
		String sp_code = "", sp_id = "";
		HashMap tmpMap = PubFunction.getSIInfoBySchoolId(db, iSchoolID);
		try {
			sp_code = Util.valueOf(tmpMap.get("code"));
			sp_id = Util.valueOf(tmpMap.get("sp_id"));
		} catch (Exception e) {
		}
		// �ж���û��SI����ѧУ
		if ("".equals(sp_code)) {
			br.setState(0);
			br.setMessage("��û��SI����ѧУ���ݲ��ܷ��Ͷ��ţ�");
			return br;
		}
		// ��������
		String filter = MessageUtiltity.checkSms2(db, bean.getContent());
		if (!"".equals(Util.valueOf(filter))) {
			br.setState(3);
			br.setMessage("���Ͷ����к���" + filter + "�����ۣ�������˴�������Ӱ�죬���޸ĺ��ٷ��ͣ�");
			return br;
		}
		// һ������
		filter = MessageUtiltity.checkSms1(db, bean.getContent());
		if (!"".equals(filter) && filter.startsWith("1")) {
			br.setState(3);
			br.setMessage("���Ͷ����к���" + filter.substring(1)
					+ "�����ۣ�������˴�������Ӱ�죬���޸ĺ��ٷ��ͣ�");
			return br;
		}
		// �Ƿ�����ͬ�������ͨ��
		boolean haveConfirmed = new MessageUtiltity().haveConfirmed(db,
				iSchoolID, bean.getContent());
		// SI���õĴ�ȷ�϶�����
		int limitNum = 70;
		try {
			limitNum = Integer
					.parseInt(db
							.getValue("select qnum from si_set a left join qx_admin_si b on a.si_id=b.id left join qx_user_school c on b.id=c.user_id where c.school_id="
									+ iSchoolID));
		} catch (Exception e) {
		}
		// area_id
		int areaId = Integer.parseInt(db
				.getValue("select id from area where abb='" + info.getAbb()
						+ "'"));
		// �ƶ����õĴ�ȷ�϶�����
		int YDLimitNum;
		try {
			YDLimitNum = Integer.parseInt(db
					.getValue("select qnum from city_set where area_id="
							+ areaId));
		} catch (Exception e) {
			YDLimitNum = 1000;
		}
		// message_code,school_name
		List list = db
				.getValues(
						"select t.userid,t.username,t.message_code,s.school_name from xj_teacher t,xj_school s where t.school_id=s.id and t.id="
								+ info.getUserId(), 4);
		String userid = (String) list.get(0);
		String teacherName = (String) list.get(1);
		String source = (String) list.get(2);
		String schoolName = (String) list.get(3);
		// tran_code
		String tran_code = db
				.getValue("select tran_code from yw_transaction where transaction=1 and area_id="
						+ areaId);
		// �ҳ��������Ƿ��շѣ��Ƿ���Զ�����û����Ͷ��ŵ�
		int school_dxx_charge = 0, fee = 0, tyfs = 0;
		boolean canSendUni = false;
		try {
			Map map = getSchoolTranInfo(db, iSchoolID);
			school_dxx_charge = Integer.parseInt((String) map
					.get("school_dxx_charge"));
			fee = Integer.parseInt((String) map.get("free"));
			tyfs = Integer.parseInt((String) map.get("tyfs"));
			canSendUni = "1".equals(map.get("can_send_uni")) ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ��������
		String content =bean.getContent()
				+" "+ teacherName+"��ʦ[�ɻظ�] "+schoolName+"���й��ƶ�УѶͨ��";

		// ѭ�����������¼
		// int flag = 0;// �Ƿ��ж�����Ҫ��� 0:û�� 1�������޶��� 2���������� 3�����߶���
		SMSBean smsBean = (SMSBean) info.getSMMList().get(0);
		System.out.println(smsBean.getStudentIds());
		// ��ѯĿ�������
		int objectNum = getSendUniteObjectNum(db, info.getAbb(), iSchoolID,
				Integer.parseInt(smsBean.getClassId()), Integer.parseInt(info
						.getParentType()), school_dxx_charge, fee, tyfs,
				canSendUni);
		// Ϊ���ù�������������һ��TeacherBean
		TeacherBean teacherBean = new TeacherBean();
		teacherBean.setAreaAbb(info.getAbb());
		teacherBean.setAreaId(areaId);
		teacherBean.setUserName(teacherName);
		teacherBean.setSchoolId(iSchoolID);
		teacherBean.setSchoolName(schoolName);
		teacherBean.setId(Integer.parseInt(info.getUserId()));
		teacherBean.setUserId(userid);
		teacherBean.setCanSendUni(canSendUni);
		teacherBean.setMessageCode(source);
		// add 2010-8-28 yhl
		teacherBean.setAdmin_id(-1); // ��̨�û�ID
		teacherBean.setAdmin_userid(""); // ��̨�û��ʺ�
		if (!haveConfirmed && !"".equals(filter)) {
			Map map = getCheckInfo(db, iSchoolID);
			String confPhone = (String) map.get("phone");
			Calendar fromCal = (Calendar) map.get("from");
			Calendar toCal = (Calendar) map.get("to");

			saveConfirmSMSUnite(
					db,
					teacherBean,
					source,
					smsBean.getStudentIds(),
					content,
					objectNum > limitNum ? 2 : 0,
					filter,
					info.getSmsType(),
					"0".equals(info.getIsSendNow()) ? info.getSendTime() : null,
					sp_id, tran_code, 1,
					Integer.parseInt(info.getIsLongSMS()), objectNum, limitNum,
					YDLimitNum, confPhone, fromCal, toCal);
			// if (flag < 3)
			// flag = objectNum > limitNum ? 3 : 2;
			// continue;
			br.setState(2);
			if (objectNum > limitNum) {
				br
						.setMessage("���Ͷ����к���" + filter.substring(1)
								+ "�����۲���Ŀ���ֻ�������" + limitNum
								+ ",���ܻ�����˴�������Ӱ�죬��ȷ���Ƿ��ܷ��ͣ��Ժ����ڡ���ȷ�϶��š������в�ѯ��");
			} else {
				br.setMessage("���Ͷ����к���" + filter.substring(1)
						+ "������,���ܻ�����˴�������Ӱ�죬��ȷ���Ƿ��ܷ��ͣ��Ժ����ڡ���ȷ�϶��š������в�ѯ��");
			}
			return br;
		}

		if (!haveConfirmed && objectNum > limitNum) {
			Map map = getCheckInfo(db, iSchoolID);
			String confPhone = (String) map.get("phone");
			Calendar fromCal = (Calendar) map.get("from");
			Calendar toCal = (Calendar) map.get("to");
			saveConfirmSMSUnite(
					db,
					teacherBean,
					source,
					smsBean.getStudentIds(),
					content,
					1,
					filter,
					info.getSmsType(),
					"0".equals(info.getIsSendNow()) ? info.getSendTime() : null,
					sp_id, tran_code, 1,
					Integer.parseInt(info.getIsLongSMS()), objectNum, limitNum,
					YDLimitNum, confPhone, fromCal, toCal);
			// flag = 1;
			// continue;
			br.setState(2);
			br.setMessage("Ŀ���ֻ�������" + limitNum + "����ȷ���Ƿ��ܷ��ͣ��Ժ����ڡ���ȷ�϶��š������в�ѯ��");
			return br;
		}

		String isAlign = ""; // ��־��׺
		if ("0".equals(info.getIsSendNow())) {
			// ��ʱ����
			isAlign = " ����ʱ���ͣ�����ʱ��Ϊ��" + new PubFunction().getTimeStr() + "��";
		}

		// Ϊ�����̵߳�������һ��DBControl
		DBControl threadDB = new DBControl(request);
		UniteSendTread sendbean = new UniteSendTread(threadDB, info.getAbb(),
				smsBean.getStudentIds(), source, "0"
						.equals(info.getIsSendNow()) ? info.getSendTime()
						: null, 1, content, teacherName, schoolName, iSchoolID,
				Integer.parseInt(info.getUserId()), sp_id, tran_code, "wap",
				isAlign, info.getSmsType(), school_dxx_charge, fee, tyfs,
				teacherBean.getAreaId(), 1, Integer.parseInt(info
						.getIsLongSMS()), Integer
						.parseInt(smsBean.getClassId()), canSendUni,
				teacherBean);
		new Thread(sendbean).start();
		// for (int i = 0; i < info.getSMMList().size(); i++) {
		// }

		// if (flag == 0) {
		br.setState(1);
		br.setMessage("���ͳɹ���");
		// } else if (flag == 1) {
		// br.setState(2);
		// br.setMessage("Ŀ���ֻ�������" + limitNum + "����ȷ���Ƿ��ܷ��ͣ��Ժ����ڡ���ȷ�϶��š������в�ѯ��");
		// } else if (flag == 2) {
		// br.setState(2);
		// br.setMessage("���Ͷ����к���" + filter.substring(1) +
		// "������,���ܻ�����˴�������Ӱ�죬��ȷ���Ƿ��ܷ��ͣ��Ժ����ڡ���ȷ�϶��š������в�ѯ��");
		// } else if (flag == 3) {
		// br.setState(2);
		// br.setMessage("���Ͷ����к���" + filter.substring(1) + "�����۲���Ŀ���ֻ�������" +
		// limitNum + ",���ܻ�����˴�������Ӱ�죬��ȷ���Ƿ��ܷ��ͣ��Ժ����ڡ���ȷ�϶��š������в�ѯ��");
		// }

		return br;
	}

	private int getSendUniteObjectNum(DBControl db, String abb, int schoolID,
			int classId, int chargeType, int school_dxx_charge, int fee,
			int tyfs, boolean canSendUni) {
		// TODO Auto-generated method stub
		// if (i == 0)
		//
		// return 9999;
		// else
		return 1;
	}

	private void saveConfirmSMSUnite(DBControl db, TeacherBean teacherBean,
			String source, String stuIds, String content, int tag, String sTmp,
			String smstype, String sendTime, String sp_id, String tran_code,
			int isPreParent, int isLong, int objectNum, int limitNum,
			int YDLimitNum, String confPhone, Calendar fromCal, Calendar toCal) {
		MessageUtiltity util = new MessageUtiltity();
		PubFunction pub = new PubFunction();
		
		int recordId = util.saveConfirmSms7(db, teacherBean, source, sp_id,
				stuIds, content, sendTime, 1, "wap", tran_code, 1, isPreParent,
				isLong, objectNum, Integer.parseInt(Const.smsType.get(smstype)
						.toString()),
				(Const.NEED_CONFIRM_SWITCH && objectNum > YDLimitNum));
		StringBuffer siMessage = new StringBuffer();
		siMessage.append(teacherBean.getSchoolName());
		siMessage.append("��ʦ");
		siMessage.append(teacherBean.getUserId());
		siMessage.append("��");
		siMessage.append(pub.getTimeStr());
		siMessage.append("���͵Ķ��š�");
		siMessage.append(content);
		siMessage.append("��");
		if (tag == 0 || tag == 2) { // ����������
			siMessage.append("�к���");
			siMessage.append(pub.getChar(sTmp.substring(1)));
			siMessage.append("������");
			if (tag == 2) {
				siMessage.append("����");
			}
		}
		if (tag == 1 || tag == 2) { // �����޶�����
			siMessage.append("Ŀ�����������");
			siMessage.append(limitNum);
		}
		siMessage.append("����������ظ�1������������ظ�2");

		// ������Ϣ��SIȷ���Ƿ��͸ö��� - ����dx_groupsend
		// ��ǰʱ��
		Calendar curCal = Calendar.getInstance();
		curCal.set(Calendar.SECOND, 0);
		curCal.set(Calendar.MILLISECOND, 0);
		if (confPhone != null && !"".equals(confPhone)) {
			if (PubFunction.getPhoneType(confPhone) <= 1
					&& curCal.before(toCal) && curCal.after(fromCal)) {
				// id����7λ������7λ
				String confirmId = String.valueOf(recordId);
				int length = confirmId.length();
				if (length < 7) {
					for (int ii = 0; ii < 7 - length; ii++) {
						confirmId = "0" + confirmId;
					}
				}
				// �ƶ��ֻ�����ŷ���
				util.saveSingleGroupSend(db, "091" + confirmId + "2",
						confPhone, null, siMessage.toString(), teacherBean
								.getSchoolId(), teacherBean.getId(), "0",
						teacherBean.getAreaAbb(), 1, 0, "0",
						sp_id,
						cn.qtone.xxt.jzdx.Const.free_tran_code);
			}
		}
	}

	public BaseResult sendEach(DBControl db, Info info,
			HttpServletRequest request) {
		BaseResult br = new BaseResult();
		MessageUtiltity util = new MessageUtiltity();
		PubFunction pub = new PubFunction();
		SMSBean bean = (SMSBean) info.getSMMList().get(0);
		int iSchoolID = Integer.parseInt(bean.getSchoolId());
		System.out.println(iSchoolID);
		int iClassID = Integer.parseInt(bean.getClassId());
		// ����ѧУ��SI��Ϣ
		String sp_code = "", sp_id = "";
		HashMap tmpMap = PubFunction.getSIInfoBySchoolId(db, iSchoolID);
		try {
			sp_code = Util.valueOf(tmpMap.get("code"));
			sp_id = Util.valueOf(tmpMap.get("sp_id"));
		} catch (Exception e) {
		}
		// �ж���û��SI����ѧУ
		if ("".equals(sp_code)) {
			br.setState(0);
			br.setMessage("��û��SI����ѧУ���ݲ��ܷ��Ͷ��ţ�");
			return br;
		}

		// message_code,school_name
		List list2 = db
				.getValues(
						"select t.userid,t.username,t.message_code,s.school_name from xj_teacher t,xj_school s where t.school_id=s.id and t.id="
								+ info.getUserId(), 4);
		String userid = (String) list2.get(0);
		String teacherName = (String) list2.get(1);
		String source = (String) list2.get(2);
		String schoolName = (String) list2.get(3);

		String stuIds = "";// ȫ����ѧ��ID��

		for (int i = 0; i < info.getSMMList().size(); i++) {
			bean = (SMSBean) info.getSMMList().get(i);
			stuIds += bean.getStudentIds() + ",";
		}
		if (!stuIds.equals("")) {
			stuIds = stuIds.substring(0, stuIds.length() - 1);
		}
		// ��ѧ����Ϣ�Ͷ�������
		// List list = new ArrayList();
		HashMap contentMap = new HashMap();
		HashMap isLongMap = new HashMap();
		// ѧ������
		HashMap stuNameMap = PubFunction.getStudentNameById(db, info.getAbb()
				+ "_xj_student", stuIds);
		// �����������۹���
		String filter = null;
		// ����������Ϣ��ѧ�����,ѧ������
		String filterStuId = "-1", filterStuName = "";
		HashMap filterMap = new HashMap();
		//
		for (int i = 0; i < info.getSMMList().size(); i++) {
			bean = (SMSBean) info.getSMMList().get(i);
			// ��������
			filter = MessageUtiltity.checkSms2(db, bean.getContent());
			if (!"".equals(Util.valueOf(filter))) {
				br.setState(3);
				br.setMessage("���͸�" + stuNameMap.get(bean.getStudentIds())
						+ "�ҳ��Ķ����к���" + filter + "�����ۣ�������˴�������Ӱ�죬���޸ĺ��ٷ��ͣ�");
				return br;
			}
			// һ������
			filter = MessageUtiltity.checkSms1(db, bean.getContent());
			if (!"".equals(filter)) {
				if (filter.startsWith("1")) {
					br.setState(3);
					br.setMessage("���͸�" + stuNameMap.get(bean.getStudentIds())
							+ "�ҳ��Ķ����к���" + filter.substring(1)
							+ "�����ۣ�������˴�������Ӱ�죬���޸ĺ��ٷ��ͣ�");
					return br;
				} else {
					filterStuId += "," + bean.getStudentIds();
					filterStuName += stuNameMap.get(bean.getStudentIds()) + ",";
					filterMap.put(bean.getStudentIds(), filter.substring(1));
				}
			}
			contentMap.put(bean.getStudentIds(),  "�𾴵�"+stuNameMap.get(bean.getStudentIds())+"�ļҳ���"+bean.getContent()
					+" "+ teacherName+"��ʦ[�ɻظ�] "+schoolName+"���й��ƶ�УѶͨ��");
			isLongMap.put(bean.getStudentIds(), info.getIsLongSMS());
		}
		// int idx = 0;

		// ��ȷ�϶�����
		int limitNum = 100;
		try {
			limitNum = Integer
					.parseInt(db
							.getValue("select b.dnum from qx_user_school a join si_set b on a.user_id=b.si_id and a.school_id="
									+ iSchoolID));
		} catch (Exception e) {
		}
		// area_id
		int areaId = Integer.parseInt(db
				.getValue("select id from area where abb='" + info.getAbb()
						+ "'"));
		// �ƶ����õĴ�ȷ�϶�����
		int YDLimitNum;
		try {
			YDLimitNum = Integer.parseInt(db
					.getValue("select dnum from city_set where area_id="
							+ areaId));
		} catch (Exception e) {
			YDLimitNum = 1000;
		}
		// tran_code
		String tran_code = db
				.getValue("select tran_code from yw_transaction where transaction=1 and area_id="
						+ areaId);
		// �ҳ��������Ƿ��շѣ��Ƿ���Զ�����û����Ͷ��ŵ�
		int school_dxx_charge = 0, fee = 0, gxhfs = 0;
		boolean canSendUni = false;
		try {
			Map map = getSchoolTranInfo(db, iSchoolID);
			school_dxx_charge = Integer.parseInt((String) map
					.get("school_dxx_charge"));
			fee = Integer.parseInt((String) map.get("free"));
			gxhfs = Integer.parseInt((String) map.get("gxhfs"));
			canSendUni = "1".equals(map.get("can_send_uni")) ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Ϊ���ù�������������һ��TeacherBean
		TeacherBean teacherBean = new TeacherBean();
		teacherBean.setAreaAbb(info.getAbb());
		teacherBean.setAreaId(areaId);
		teacherBean.setUserName(teacherName);
		teacherBean.setSchoolId(iSchoolID);
		teacherBean.setSchoolName(schoolName);
		teacherBean.setId(Integer.parseInt(info.getUserId()));
		teacherBean.setUserId(userid);
		teacherBean.setCanSendUni(canSendUni);
		teacherBean.setMessageCode(source);
		// add 2010-8-28
		teacherBean.setAdmin_id(-1); // ��̨�û�ID
		teacherBean.setAdmin_userid(""); // ��̨�û��˺�

		Map map = getCheckInfo(db, iSchoolID);
		String confPhone = (String) map.get("phone");
		Calendar fromCal = (Calendar) map.get("from");
		Calendar toCal = (Calendar) map.get("to");
		// ��ǰʱ��
		Calendar curCal = Calendar.getInstance();
		curCal.set(Calendar.SECOND, 0);
		curCal.set(Calendar.MILLISECOND, 0);

		int flag = 0;// �Ƿ��ж��ź�����������Ҫ���
		int objectNum = info.getSMMList().size();
		if (objectNum > limitNum) { // �����޶�ֵ
			// д���ȷ�϶��ű�confirmTaskMain,confirmTaskSub��
			int mainID = handleEachSendConfirm(
					db,
					teacherBean,
					source,
					"0".equals(info.getIsSendNow()) ? info.getSendTime() : null,
					iSchoolID,
					Integer.parseInt(info.getUserId()),
					info.getAbb(),
					sp_id,
					tran_code,
					"wap",
					Integer.parseInt(Const.smsType.get(info.getSmsType()) + ""),
					school_dxx_charge, stuIds, filterStuId, areaId, fee, gxhfs,
					contentMap, filterMap, 1, Integer.parseInt(info
							.getIsLongSMS()), objectNum,
					(Const.NEED_CONFIRM_SWITCH && objectNum > YDLimitNum));
			// ���͸�SI�Ķ�������
			StringBuffer siMessage = new StringBuffer();
			siMessage.append(schoolName + "��ʦ" + userid);
			siMessage.append("��" + pub.getTimeStr());
			siMessage.append("���еĸ��Է���Ŀ���ֻ�������");
			siMessage.append(limitNum);
			siMessage.append("����������ظ�1������������ظ�2");
			// ������Ϣ��SIȷ���Ƿ��͸ö��� - ����dx_groupsend
			System.out.println("@@@@@@@@@@@" + confPhone);
			if (confPhone != null && !"".equals(confPhone)) {
				if (PubFunction.getPhoneType(confPhone) <= 1
						&& curCal.before(toCal) && curCal.after(fromCal)) {
					// mianid����7λ������7λ
					String confirmId = String.valueOf(mainID);
					int length = confirmId.length();
					if (length < 7) {
						for (int ii = 0; ii < 7 - length; ii++) {
							confirmId = "0" + confirmId;
						}
					}
					System.out.println("���Ͷ���");
					// �ƶ��ֻ�����ŷ���
					util.saveSingleGroupSend(db, teacherBean.getMessageCode()
							.substring(0, 3)
							+ confirmId + "3", confPhone, null, siMessage
							.toString(), iSchoolID, Integer.parseInt(info
							.getUserId()), "0", info.getAbb(), 1, 0, "0",
							sp_id,
							cn.qtone.xxt.jzdx.Const.free_tran_code);
				}
			}
			br.setState(2);
			br.setMessage("Ŀ���ֻ�������" + limitNum + "����ȷ���Ƿ��ܷ��ͣ��Ժ����ڡ���ȷ�϶��š������в�ѯ��");
			if (db != null) {
				db.closePrepared();
				db = null;
			}
			if (stuNameMap != null) {
				stuNameMap.clear();
				stuNameMap = null;
			}
			if (filterMap != null) {
				filterMap.clear();
				filterMap = null;
			}
			return br;
		}
		// for (int i = 0; i < list.size(); i++) {
		// eachSendBean = (EachSendBean) list.get(i);
		// String classId = eachSendBean.getClassId();
		// String studentIds = eachSendBean.getStudentIds().substring(0,
		// eachSendBean.getStudentIds().length() - 1);
		// System.out.println("stuID:" + studentIds);
		// HashMap contentMap = eachSendBean.getContentMap();
		// HashMap isLongMap = eachSendBean.getIsLongMap();
		// int objectNum = studentIds.split(",").length;
		// // ������ȷ����
		// if (objectNum > limitNum) {
		// int mainID = handleEachSendConfirm(db, teacherBean, source,
		// "0".equals(info.getIsSendNow()) ? info.getSendTime() : null,
		// iSchoolID,
		// Integer.parseInt(info
		// .getUserId()), info.getAbb(), sp_id, tran_code, "wap",
		// Integer.parseInt(Const.smsType.get(info.getSmsType()) + ""),
		// school_dxx_charge, studentIds,
		// eachSendBean.getFilterStuId(), areaId, fee, gxhfs, contentMap,
		// eachSendBean.getFilterMap(), 1,
		// Integer.parseInt(info.getIsLongSMS()), objectNum,
		// (Const.NEED_CONFIRM_SWITCH && objectNum > YDLimitNum));
		// // ���͸�SI�Ķ�������
		// StringBuffer siMessage = new StringBuffer();
		// siMessage.append(schoolName + "��ʦ" + userid);
		// siMessage.append("��" + pub.getTimeStr());
		// siMessage.append("���еĸ��Է���Ŀ���ֻ�������");
		// siMessage.append(limitNum);
		// siMessage.append("����������ظ�1������������ظ�2");
		// // ������Ϣ��SIȷ���Ƿ��͸ö��� - ����dx_groupsend
		//
		// if (confPhone != null && !"".equals(confPhone)) {
		// // ��ǰʱ��
		// Calendar curCal = Calendar.getInstance();
		// curCal.set(Calendar.SECOND, 0);
		// curCal.set(Calendar.MILLISECOND, 0);
		// if (PubFunction.getPhoneType(confPhone) <= 1 && curCal.before(toCal)
		// && curCal.after(fromCal)) {
		// String confirmId = String.valueOf(mainID);
		// int length = confirmId.length();
		// if (length < 7) {
		// for (int ii = 0; ii < 7 - length; ii++) {
		// confirmId = "0" + confirmId;
		// }
		// }
		// // �ƶ��ֻ�����ŷ���
		// util.saveSingleGroupSend(db,
		// teacherBean.getMessageCode().substring(0, 3) + confirmId + "3",
		// confPhone, null, siMessage.toString(), iSchoolID, Integer
		// .parseInt(info.getUserId()), "0", info.getAbb(), 1, 0, "0",
		// cn.qtone.xxt.jzdx.Const.free_sp_id,
		// cn.qtone.xxt.jzdx.Const.free_tran_code);
		// }
		// }
		// flag = flag == 2 ? 3 : flag == 3 ? 3 : 1;
		// continue;
		// }
		//
		// if (!eachSendBean.getFilterStuId().equals("-1")) {
		// saveConfirmSMSEach(db, teacherBean, eachSendBean.getFilterStuId(),
		// contentMap, eachSendBean.getFilterMap(), source, sp_id, tran_code,
		// info, school_dxx_charge, fee,
		// gxhfs, confPhone, fromCal, toCal);
		// flag = flag == 1 ? 3 : flag == 3 ? 3 : 2;
		// }
		//
		// db1 = new DBControl(request);
		// db2 = new DBControl(request);
		// String isAlign = "";
		// if ("0".equals(info.getIsSendNow())) {
		// // ��ʱ����
		// isAlign = " ����ʱ���ͣ�����ʱ��Ϊ��" + pub.getTimeStr() + "��";
		// }
		// System.out.println("����ID:" + eachSendBean.getFilterStuId());
		// EachSendTread sendbean = new EachSendTread(db1, db2, info.getAbb(),
		// studentIds, eachSendBean.getFilterStuId(), source,
		// "0".equals(info.getIsSendNow()) ? info
		// .getSendTime() : null, contentMap, teacherName, iSchoolID,
		// Integer.parseInt(info.getUserId()), sp_id, tran_code, "wap", isAlign,
		// 1, info.getSmsType(),
		// school_dxx_charge, fee, gxhfs, areaId, isLongMap,
		// Integer.parseInt(classId));
		// new Thread(sendbean).start();
		//
		// }

		// ���Ͷ���
		if (!filterStuId.equals("-1")) {
			saveConfirmSMSEach(db, teacherBean, filterStuId, contentMap,
					filterMap, source, sp_id, tran_code, info,
					school_dxx_charge, fee, gxhfs, confPhone, fromCal, toCal);
			flag = 1;
		}
		String isAlign = "";
		if ("0".equals(info.getIsSendNow())) {
			// ��ʱ����
			isAlign = " ����ʱ���ͣ�����ʱ��Ϊ��" + pub.getTimeStr() + "��";
		}

		DBControl db1 = new DBControl(request);
		DBControl db2 = new DBControl(request);
		EachSendTread sendbean = new EachSendTread(db1, db2, info.getAbb(),
				stuIds, filterStuId, source,
				"0".equals(info.getIsSendNow()) ? info.getSendTime() : null,
				contentMap, teacherName, iSchoolID, Integer.parseInt(info
						.getUserId()), sp_id, tran_code, "wap", isAlign, 1,
				info.getSmsType(), school_dxx_charge, fee, gxhfs, areaId,
				isLongMap, iClassID, teacherBean);
		new Thread(sendbean).start();

		if (flag == 0) {
			br.setState(1);
			br.setMessage("���ͳɹ���");
		} else if (flag == 1) {
			br.setState(2);
			br.setMessage("����"
					+ filterStuName.substring(0, filterStuName.length() - 1)
					+ "�ҳ��Ķ��������к����������ۣ������Ա���ͨ�����ͣ�");
		}

		return br;
	}

	private int saveConfirmSMSEach(DBControl db, TeacherBean teacherBean,
			String filterStuId, Map contentMap, Map filterMap, String source,
			String sp_id, String tran_code, Info info, int school_dxx_charge,
			int fee, int gxhfs, String confPhone, Calendar fromCal,
			Calendar toCal) {
		int count = 0;
		String stuTabName = teacherBean.getAreaAbb() + "_xj_student"; // ѧ����
		// String familyTabName = teacherBean.getAreaAbb() + "_xj_family"; //
		// ��ͥ�����
		MessageUtiltity util = new MessageUtiltity();
		PubFunction pub = new PubFunction();
		cn.qtone.xxt.base.share.PubFunction pubShare = new cn.qtone.xxt.base.share.PubFunction();
		StringBuffer sql = new StringBuffer();
		sql.append("select id,name from " + stuTabName + " where id in("
				+ filterStuId + ")");
		Connection con = db.getConnection();
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = con.createStatement();
			rs = statement.executeQuery(sql.toString());
			int recordId = 0;
			while (rs != null && rs.next()) {
				recordId = util.saveConfirmSms5(db, teacherBean, source, sp_id,
						rs.getString("id"), contentMap.get(rs.getString("id"))
								+ teacherBean.getUserName(), "0".equals(info
								.getIsSendNow()) ? info.getSendTime() : null,
						2, "wap", tran_code, 0, school_dxx_charge, fee, gxhfs,
						2, teacherBean.getAreaId(), info.getIsLongSMS(),
						Integer.parseInt(Const.smsType.get(info.getSmsType())
								.toString()),
						(Const.NEED_CONFIRM_FILTER_SWITCH));

				// recordId����7λ������7λ
				String confirmId = String.valueOf(recordId);
				int length = confirmId.length();
				if (length < 7) {
					for (int ii = 0; ii < 7 - length; ii++) {
						confirmId = "0" + confirmId;
					}
				}

				// ���͸�SI�Ķ�������
				StringBuffer siMessage = new StringBuffer();
				siMessage.append(teacherBean.getSchoolName() + "��ʦ"
						+ teacherBean.getUserId());
				siMessage.append("��" + pub.getTimeStr());
				siMessage.append("���͸�"
						+ rs.getString("name")
						+ "�ҳ��Ķ��š�"
						+ pubShare.sqlStr(contentMap.get(rs.getString("id"))
								.toString()) + "���к���");
				siMessage.append(pub.getChar(filterMap.get(rs.getString("id"))
						.toString())
						+ "�����ۡ�");
				siMessage.append("��������ظ�1������������ظ�2");
				// ������Ϣ��SIȷ���Ƿ��͸ö��� - ����dx_groupsend
				// ��ǰʱ��
				Calendar curCal = Calendar.getInstance();
				curCal.set(Calendar.SECOND, 0);
				curCal.set(Calendar.MILLISECOND, 0);
				if (confPhone != null && !"".equals(confPhone)) {
					if (PubFunction.getPhoneType(confPhone) <= 1
							&& curCal.before(toCal) && curCal.after(fromCal)) {
						// �ƶ��ֻ�����ŷ���
						util.saveSingleGroupSend(db, teacherBean
								.getMessageCode().substring(0, 3)
								+ confirmId + "2", confPhone, null, siMessage
								.toString(), teacherBean.getSchoolId(),
								teacherBean.getId(), "0", teacherBean
										.getAreaAbb(), 1, 0, "0",
								sp_id,
								cn.qtone.xxt.jzdx.Const.free_tran_code);
					}
				}
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {

			}
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {

			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {

			}
			db.closePrepared();
		}

		return count;

	}

	public int handleEachSendConfirm(DBControl db, TeacherBean teacherBean,
			String source, String sendTime, int schoolId, int user_id,
			String areaAbb, String sp_id, String tran_code, String ip,
			int smstype, int school_dxx_charge, String strStuId,
			String filterStuId, int area_id, int fee, int gxhfs,
			Map contentMap, Map filterMap, int confirmType, int isLong,
			int obj_num, boolean needConfirm) {
		int mainID = 0;
		StringBuffer sql = new StringBuffer();
		// ����school_id �ж��Ƿ���Է���������
		if (teacherBean.isCanSendUni()) {
			sql.append("select s.id id,s.stu_sequence stu_sequence,");
			sql.append("s.name sname,f.id fid,f.phone phone,f.");
			sql.append(Const.unSmsType.get(smstype + ""));
			sql.append(" ");
			sql.append(Const.unSmsType.get(smstype + ""));
			sql.append(",f.week week from ");
			sql.append(areaAbb);
			sql.append("_xj_student");
			sql.append(" s left join ");
			sql.append(areaAbb);
			sql.append("_xj_family");
			sql.append(" f on s.stu_sequence=f.stu_sequence where s.id in(");
			sql.append(strStuId);
			sql.append(") ");
			sql.append("and (f.is_dxx=1 or f.phonetype>0) order by s.id");
		} else {
			sql.append("select s.id id,s.stu_sequence stu_sequence,");
			sql.append("s.name sname,f.id fid,f.phone phone,f.");
			sql.append(Const.unSmsType.get(smstype + ""));
			sql.append(" ");
			sql.append(Const.unSmsType.get(smstype + ""));
			sql.append(",f.week week from ");
			sql.append(areaAbb);
			sql.append("_xj_student");
			sql.append(" s left join ");
			sql.append(areaAbb);
			sql.append("_xj_family");
			sql.append(" f on s.stu_sequence=f.stu_sequence where s.id in(");
			sql.append(strStuId);
			sql.append(") ");
			sql.append("and f.is_dxx=1 and f.phonetype=0 order by s.id");
		}
		ResultSet rs = null;
		Connection con=null;
		try {
			con = db.getConnection();
			Statement statment = con.createStatement();
			rs = statment.executeQuery(sql.toString());
			int row = 0;
			while (rs != null && rs.next()) {
				if (row == 0) {
					mainID = handleConfirm(db, source, sendTime, schoolId,
							user_id, areaAbb, 2, sp_id, tran_code, 1, ip,
							smstype, 2, sendTime == null ? 0 : 1, confirmType,
							obj_num, needConfirm);
				}
				handleConfirmSub(db, rs.getString("phone"), "��"
						+ teacherBean.getSchoolName() + "��"
						+ contentMap.get(rs.getString("id")), mainID, rs
						.getString("stu_sequence"), rs.getString("fid"),
						areaAbb, filterMap == null ? "" : (filterMap.get(rs
								.getString("id")) == null ? "" : (filterMap
								.get(rs.getString("id")) + "")), isLong);
				row++;
			}
			if (statment != null) {
				statment.close();
				statment = null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sql.delete(0, sql.length());
			sql = null;
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
				rs = null;
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				con = null;
			}
			db.closePrepared();
		}
		return mainID;
	}

	/**
	 * ������ȷ�϶��ű������޶����������ݴ���
	 * 
	 * @param db
	 *            DBControl:DBControl����
	 * @param source
	 *            String:���Ŵ���
	 * @param sendTime
	 *            String:����ʱ��
	 * @param schoolId
	 *            int:ѧУID
	 * @param userId
	 *            int:�û�ID
	 * @param areaAbb
	 *            String:�������
	 * @param kind
	 *            int:����ģʽ��1 ͳһ���� 2 ���Է��� 3 �ɼ����� 4 �꼶���� 5 ȫУ���� 7 ȫʡ���� 8 ��ʦ�ظ��ҳ����ԣ�
	 * @param sp_id
	 *            String:��ҵ����
	 * @param tran_code
	 *            String:ҵ�����
	 * @param level
	 *            int:���ȼ�
	 * @param ip
	 *            String:����IP
	 * @param smstype:��������
	 * @param method:����ģʽ��1:ͳһ����
	 *            2: ���Է��� 3:ȫУ���� 4:�꼶���� 5 �ɼ����ͣ�
	 * @param timing:����ģʽ��0:��ʱ����
	 *            1:��ʱ���ͣ�
	 * @param confirmType:��ȷ��ԭ��1:һ�η��Ͷ��ų�������ֵ
	 *            2�����췢�Ͷ����������޶�ֵ��
	 * @author yuhailin
	 * @2007-7-19
	 */
	public int handleConfirm(DBControl db, String source, String sendTime,
			int schoolId, int userId, String areaAbb, int kind, String sp_id,
			String tran_code, int level, String ip, int smstype, int method,
			int timing, int confirmType, int obj_num, boolean needConfirm) {
		StringBuffer sql = new StringBuffer();
		PubFunction pub = new PubFunction();
		String tmpDate = pub.getTimeStr() + ":00";
		sql
				.append("insert into confirmTaskMain(refertime,sendtime,source,school_id,user_id,area_abb,kind,sp_id,tran_code,");
		sql
				.append("ilevel,ip,smstype,method,timing,confirmtype,obj_num,is_check) values(to_date(?,'yyyy-mm-dd hh24:mi:ss'),to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,");
		sql.append("?,?,?,?,?,?,?,?,?,?,?)");
		int row = 1, taskId = 0; // ��ʱ����������ID
		String taskIdStr = "";
		try {
			db.prepareDB(sql.toString());
			db.setString(row++, tmpDate); // �ύʱ��
			db.setString(row++, sendTime); // ����ʱ�� nullʱΪ��ʱ����
			db.setString(row++, source); // ���Ŵ���
			db.setInt(row++, schoolId); // ѧУID
			db.setInt(row++, userId); // �û�ID
			db.setString(row++, areaAbb); // �������
			db.setInt(row++, kind); // �������� 1 ͳһ���� 2 ���Է��� 3 �ɼ����� 4 �꼶���� 5 ȫУ���� 7
			// ȫʡ���� 8 ��ʦ�ظ��ҳ�����
			db.setString(row++, sp_id); // ��ҵ����
			db.setString(row++, tran_code); // ҵ�����
			db.setInt(row++, level); // ���ȼ���1��99�� ��ֵԽ�����ȼ�Խ��
			// һ��������ȼ�Ĭ��Ϊ1�����޸ĳ�2��98��������֪ͨ���ȼ�Ϊ99
			db.setString(row++, ip); // IP��ַ
			db.setInt(row++, smstype); // ��������
			db.setInt(row++, method); // 1:ͳһ���� 2: ���Է��� 3:ȫУ���� 4:�꼶���� 5 �ɼ�����
			db.setInt(row++, timing); // 0:��ʱ���� 1:��ʱ����
			db.setInt(row++, confirmType); // 1:һ�η��Ͷ��ų�������ֵ 2�����췢�Ͷ����������޶�ֵ
			db.setInt(row++, obj_num);
			if (needConfirm) {
				db.setInt(row++, 1);
			} else {
				db.setInt(row++, 2);
			}
			db.preparedExeDB();
			taskIdStr = db
					.getValue("select max(id) from confirmTaskMain where user_id="
							+ userId
							+ " and ip='"
							+ ip
							+ "' and to_char(refertime,'yyyy-mm-dd hh24:mi:ss')='"
							+ tmpDate + "'");
			taskId = ("".equals(taskIdStr) || "null".equals(taskIdStr) || taskIdStr == null) ? 0
					: Integer.parseInt(taskIdStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closePrepared();
		}
		return taskId;
	}

	/**
	 * ��ȷ�϶��Ŵӱ���뵥����¼ ���Է���
	 * 
	 * @param db
	 *            DBControl:DBControl����
	 * @param sendPhones
	 *            String:Ŀ�����
	 * @param content
	 *            String:��������
	 * @param taskId
	 *            int:����ID
	 * @param stu_sequence
	 *            String:ѧ�����
	 * @param family_id
	 *            String:�ҳ�ID
	 * @param area_abb
	 *            String:�������
	 * @param note
	 *            String:��������
	 * @param isLong
	 *            String:�Ƿ��Գ����ŷ�ʽ���Ͷ���
	 * @return true or false
	 * @author yuhailin 07-07-19
	 */
	public boolean handleConfirmSub(DBControl db, String sendPhones,
			String content, int taskId, String stu_sequence, String family_id,
			String area_abb, String note, int isLong) {
		boolean isSuc = true;
		StringBuffer sql = new StringBuffer();
		sql
				.append("insert into confirmTaskSub(object_mobile,content,task_id,stu_sequence,family_id,area_abb,note,islong)");
		sql.append(" values(?,?,?,?,?,?,?,?)");
		try {
			int row = 1;
			db.prepareDB(sql.toString());
			db.setString(row++, sendPhones); // Ŀ�����
			db.setString(row++, content); // ����
			db.setInt(row++, taskId); // ����ID
			db.setString(row++, stu_sequence); // ѧ�����
			db.setString(row++, family_id); // �ҳ�ID
			db.setString(row++, area_abb); // �������
			db.setString(row++, note); // ��������
			db.setInt(row++, isLong); // ������
			db.preparedExeDB();
		} catch (Exception e) {
			isSuc = false;
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.closePrepared();
			}
			sql = null;
		}
		return isSuc;
	}
}
