package cn.qtone.xxt.csop.services.impl;

import java.util.List;

import cn.qtone.xxt.csop.business.ServciceResponseParams;
import cn.qtone.xxt.csop.business.ServiceAgreement;
import cn.qtone.xxt.csop.dao.impl.TransBooklogDao;
import cn.qtone.xxt.csop.dao.model.TransBooklogRow;
import cn.qtone.xxt.csop.dao.model.TransCustomerRow;
import cn.qtone.xxt.csop.inter.AbstractQueryService;
import cn.qtone.xxt.csop.util.CsopLog;
import cn.qtone.xxt.csop.webservices.bean.ServiceResponse;
import cn.qtone.xxt.csop.webservices.bean.TransBooklogParams;

/**
 * 
 * 6.4.1.3 业务历史订购记录查询服务（B005_03）
 * @author linhansheng
 * 
 */
public class TransBooklogService extends AbstractQueryService<TransBooklogParams,TransBooklogRow>{

	@Override
	protected ServiceAgreement agreement() {
		return ServiceAgreement.TRANSCATION_BOOKLOG;
	}

	
	@Override
	public ServiceResponse query(TransBooklogParams requsetParams) {
		ServiceResponse resp = new ServiceResponse();
		if(!validator(requsetParams)){
			resp.setResult("");
			resp.setRetcode(ServciceResponseParams.SV0M95.code()); //服务调用参数错误
			resp.setRetmsg(ServciceResponseParams.SV0M95.description());
			return resp;
		}
	    TransBooklogDao dao = new TransBooklogDao();
		try {
			List<TransBooklogRow> results = dao.query(requsetParams);
			if(results!=null){
			   resp.setRetmsg(ServciceResponseParams.SUC.description()+",查询返回"+results.size()+"条记录。  ");
			   resp.setResult(formateResutlData(results));
			   results = null;
			}
			resp.setRetcode(ServciceResponseParams.SUC.code());
		} catch (Exception e) {
		    e.printStackTrace();
			resp.setRetcode(ServciceResponseParams.SV0MMM.code());
			resp.setRetmsg(ServciceResponseParams.SV0MMM.description());
			resp.setResult("");
			CsopLog.error(e.getMessage());
		}finally{
			 dao=null;
		}
		return resp;
	}
	
}
