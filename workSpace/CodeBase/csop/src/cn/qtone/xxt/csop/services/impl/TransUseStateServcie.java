package cn.qtone.xxt.csop.services.impl;

import java.util.List;

import cn.qtone.xxt.csop.business.ServciceResponseParams;
import cn.qtone.xxt.csop.business.ServiceAgreement;
import cn.qtone.xxt.csop.dao.impl.TransUseStateDao;
import cn.qtone.xxt.csop.dao.model.TransUseStateRow;
import cn.qtone.xxt.csop.inter.AbstractQueryService;
import cn.qtone.xxt.csop.util.CsopLog;
import cn.qtone.xxt.csop.webservices.bean.ServiceResponse;
import cn.qtone.xxt.csop.webservices.bean.TransUseStateParams;


/**
 * 
 * 6.4.1.4 业务使用记录查询服务（B005_04）
 * @author LINHANSHENG 
 *
 */
public class TransUseStateServcie extends
		AbstractQueryService<TransUseStateParams, TransUseStateRow> {

	@Override
	protected ServiceAgreement agreement() {
		return ServiceAgreement.TRANSCATION_USESTATE;
	}

	@Override
	public ServiceResponse query(TransUseStateParams requsetParams) {
		ServiceResponse resp = new ServiceResponse();
		if(!validator(requsetParams)){
			resp.setResult("");
			resp.setRetcode(ServciceResponseParams.SV0M95.code()); //服务调用参数错误
			resp.setRetmsg(ServciceResponseParams.SV0M95.description());
			return resp;
		}
		TransUseStateDao dao = new TransUseStateDao();
		try {
			List<TransUseStateRow> results = dao.query(requsetParams);
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
