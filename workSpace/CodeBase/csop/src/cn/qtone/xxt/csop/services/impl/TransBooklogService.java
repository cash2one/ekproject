package cn.qtone.xxt.csop.services.impl;

import java.util.List;

import cn.qtone.xxt.csop.dao.impl.TransBooklogDao;
import cn.qtone.xxt.csop.dao.model.TransBooklogRow;
import cn.qtone.xxt.csop.inter.AbstractQueryService;
import cn.qtone.xxt.csop.webservices.bean.ServiceResponse;
import cn.qtone.xxt.csop.webservices.bean.TransBooklogParams;

/**
 * 6.4.1.1 业务定制情况查询（B005_01）
 * 
 * @author linhansheng
 * 
 */
public class TransBooklogService extends AbstractQueryService<TransBooklogParams,TransBooklogRow>{

	@Override
	protected String formateResutlData(List<TransBooklogRow> rows) {
		
		
		
		
		return null;
	}

	@Override
	public ServiceResponse query(TransBooklogParams requsetParams) {
		TransBooklogDao dao = new TransBooklogDao();
		ServiceResponse resp = new ServiceResponse();
		List<TransBooklogRow> results = dao.query(requsetParams);
	
		if(results!=null&&results.size()>0){
			
		} 
		 
		return null;
	}

	
}
