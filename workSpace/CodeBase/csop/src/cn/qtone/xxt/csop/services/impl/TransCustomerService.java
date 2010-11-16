package cn.qtone.xxt.csop.services.impl;

import cn.qtone.xxt.csop.business.ServciceResponseParams;
import cn.qtone.xxt.csop.business.ServiceAgreement;
import cn.qtone.xxt.csop.dao.impl.TransCustomerDao;
import cn.qtone.xxt.csop.dao.model.TransCustomerRow;
import cn.qtone.xxt.csop.inter.AbstractCustomerService;
import cn.qtone.xxt.csop.util.CsopLog;
import cn.qtone.xxt.csop.webservices.bean.ServiceResponse;
import cn.qtone.xxt.csop.webservices.bean.TransCustomerParams;

/**
 * 6.4.1.1 业务受理服务（B005_02）
 * 
 * @author linhansheng
 * 
 */
public class TransCustomerService extends
		AbstractCustomerService<TransCustomerParams, TransCustomerRow> {

	@Override
	protected ServiceAgreement agreement() {
		return ServiceAgreement.TRANSCATION_CUSTOMER_BOOK;
	}

	@Override
	public ServiceResponse customer(TransCustomerParams requsetParams) {
		ServiceResponse resp = new ServiceResponse();
		if (!validator(requsetParams)) {
			resp.setResult("");
			resp.setRetcode(ServciceResponseParams.SV0M95.code()); // 服务调用参数错误
			resp.setRetmsg(ServciceResponseParams.SV0M95.description());
			return resp;
		}
		TransCustomerDao dao = new TransCustomerDao();
		try {
			String result = dao.customer(requsetParams);
			resp.setResult("");
			resp.setRetcode(ServciceResponseParams.SUC.code());
			resp.setRetmsg(ServciceResponseParams.SUC.description());
		} catch (Exception e) {
			resp.setRetcode(ServciceResponseParams.SV0MMM.code());
			resp.setRetmsg(ServciceResponseParams.SV0MMM.description());
			resp.setResult("");
			CsopLog.error(e.getMessage());
		}
		return resp;
	}

}
