package cn.qtone.xxt.csop.services.impl;

import java.util.List;

import cn.qtone.xxt.csop.business.ServciceResponseParams;
import cn.qtone.xxt.csop.business.ServiceAgreement;
import cn.qtone.xxt.csop.dao.impl.TransCustomerQueryDao;
import cn.qtone.xxt.csop.dao.model.TransCustomerRow;
import cn.qtone.xxt.csop.inter.AbstractQueryService;
import cn.qtone.xxt.csop.util.CsopLog;
import cn.qtone.xxt.csop.webservices.bean.ServiceResponse;
import cn.qtone.xxt.csop.webservices.bean.TransCustomerQueryParams;

/**
 * 6.4.1.1 业务定制情况查询（B005_01）
 * 
 * @author linhansheng
 * 
 */
public class TransCustomerQueryService extends AbstractQueryService<TransCustomerQueryParams,TransCustomerRow>{

	/**
	 * 
	 * @param reqParams
	 * @return
	 */
	public ServiceResponse query(TransCustomerQueryParams reqParams) {
		ServiceResponse resp = new ServiceResponse();
		if(!validator(reqParams)){
			resp.setResult("");
			resp.setRetcode(ServciceResponseParams.SV0M95.code()); //服务调用参数错误
			resp.setRetmsg(ServciceResponseParams.SV0M95.description());
			return resp;
		}
		TransCustomerQueryDao dao = new TransCustomerQueryDao();
		try {
			List<TransCustomerRow> results = dao.query(reqParams);
			if(results!=null){
			   resp.setRetmsg(ServciceResponseParams.SUC.description()+",查询返回"+results.size()+"条记录。  ");
			   resp.setResult(this.formateResutlData(results));
			   results = null;
			}
			resp.setRetcode(ServciceResponseParams.SUC.code());
		} catch (Exception e) {
			resp.setRetcode(ServciceResponseParams.SV0MMM.code());
			resp.setRetmsg(ServciceResponseParams.SV0MMM.description());
			resp.setResult("");
			CsopLog.error(e.getMessage());
		}finally{
			 dao=null;
		}
		return resp;
	}


	@Override
	protected ServiceAgreement agreement() {
		return ServiceAgreement.TRANSCATION_CUSTOMER_QUERY;
	}
    
	
}
