package com.qtone.datasync.xxt.server;

import com.qtone.datasync.misc.Constraints;
import com.qtone.datasync.misc.ErrorCode;
import com.qtone.datasync.misc.client.bean.XxtRespBean;
import com.qtone.datasync.util.StringUtil;
import com.qtone.datasync.xxt.server.bean.MiscRequestBean;
import com.qtone.datasync.xxt.server.dao.MiscRequestDao;

/**
 * Misc���������ҵ������,���������Misc�������ݵ����ݣ������ݽ�����Ӧ�Ĵ���
 * 
 * @author ���ڷ� 2009-05-31
 */
public class MiscRequestBusiness {
	private MiscRequestDao dao = new MiscRequestDao();

	public XxtRespBean handle(final MiscRequestBean bean) {
		String retCode = "";
		// ��֤BeanЯ������Ϣ�Ƿ��������
		if (validate(bean)) {
			// �������ݿ����������ҵ���жϡ����ݴ洢��
			retCode = dao.save(bean);
//			retCode = this.doBusiness(bean);
			if (StringUtil.isEmpty(retCode)) {// �����ɹ�
				retCode = "0";
			}
			
			//���Bean��û�а����쳣���룬����������һ����ֵ���������޸�
			if(StringUtil.isEmpty(bean.getErrorCode()))
				bean.setErrorCode(retCode);
		}

		// ���췵�ر���
		XxtRespBean respBean = new XxtRespBean();
		respBean.setMsgType(Constraints.MSG_TYPE_RESPONSE);
		respBean.setTransactionID(bean.getTransactionID());
		respBean.setHRet(bean.getErrorCode());

		return respBean;
	}

	/**
	 * ��֤Miscͬ����������Ϣ�Ƿ�����
	 * 
	 * @param bean
	 * @return
	 */
	private boolean validate(final MiscRequestBean bean) {
		if (!StringUtil.isEmpty(bean.getErrorCode())) {
			return false;
		}

		if (!Constraints.MSG_TYPE_REQUEST.equals(bean.getMsgType())) {
			bean.setErrorCode(ErrorCode.INVALID_MSG_TYPE);
			return false;
		}

		int action = bean.getAction();
		if (action < 1 || action > 4) {
			bean.setErrorCode(ErrorCode.INVALID_ACTION_ID);
			return false;
		}

		int actionReason = bean.getActionReason();
		if (actionReason < 1 || actionReason > 9) {
			bean.setErrorCode(ErrorCode.INVALID_ACTION_REASON_ID);
			return false;
		}

		// ����SPIdΪһ����ѡ�ֶΣ�����������ֶ�Ϊ�գ�����Ȼ����Ч��
		if (!StringUtil.isEmpty(bean.getSpId())
				&& !Constraints.SP_ID.equals(bean.getSpId())) {
			bean.setErrorCode(ErrorCode.INVALID_SP_ID);
			return false;
		}

		// ����ҵ��������֤�漰�����ݿ����������ֻ��֤���Ƿ�Ϊ��
		if (StringUtil.isEmpty(bean.getSpServiceId())) {
			bean.setErrorCode(ErrorCode.INVALID_SERVICE_ID);
			return false;
		}

		if (StringUtil.isEmpty(bean.getPhoneFee())
				|| StringUtil.isEmpty(bean.getPhoneUse())) {
			bean.setErrorCode(ErrorCode.INVALID_OTHER);
			return false;
		}

		return true;
	}
}
