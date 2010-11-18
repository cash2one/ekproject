package com.qtone.datasync.xxt.server;

import com.qtone.datasync.misc.Constraints;
import com.qtone.datasync.misc.ErrorCode;
import com.qtone.datasync.misc.client.bean.XxtRespBean;
import com.qtone.datasync.util.StringUtil;
import com.qtone.datasync.xxt.server.bean.MiscRequestBean;
import com.qtone.datasync.xxt.server.dao.MiscRequestDao;

/**
 * Misc数据请求的业务处理类,它负责根据Misc请求数据的内容，对数据进行相应的处理
 * 
 * @author 杨腾飞 2009-05-31
 */
public class MiscRequestBusiness {
	private MiscRequestDao dao = new MiscRequestDao();

	public XxtRespBean handle(final MiscRequestBean bean) {
		String retCode = "";
		// 验证Bean携带的信息是否完成正常
		if (validate(bean)) {
			// 进行数据库操作，包含业务判断、数据存储等
			retCode = dao.save(bean);
//			retCode = this.doBusiness(bean);
			if (StringUtil.isEmpty(retCode)) {// 操作成功
				retCode = "0";
			}
			
			//如果Bean中没有包含异常代码，将给它设置一个新值。否则不做修改
			if(StringUtil.isEmpty(bean.getErrorCode()))
				bean.setErrorCode(retCode);
		}

		// 构造返回报文
		XxtRespBean respBean = new XxtRespBean();
		respBean.setMsgType(Constraints.MSG_TYPE_RESPONSE);
		respBean.setTransactionID(bean.getTransactionID());
		respBean.setHRet(bean.getErrorCode());

		return respBean;
	}

	/**
	 * 验证Misc同步过来的信息是否完整
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

		// 由于SPId为一个可选字段，所以如果该字段为空，它仍然是有效的
		if (!StringUtil.isEmpty(bean.getSpId())
				&& !Constraints.SP_ID.equals(bean.getSpId())) {
			bean.setErrorCode(ErrorCode.INVALID_SP_ID);
			return false;
		}

		// 由于业务代码的验证涉及到数据库操作，这里只验证它是否为空
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
