package cn.qtone.xxt.csop.inter;

import java.util.List;

import cn.qtone.xxt.csop.business.ServiceAgreement;
import cn.qtone.xxt.csop.dao.model.TransCustomerRow;
import cn.qtone.xxt.csop.webservices.bean.RequestParams;
import cn.qtone.xxt.csop.webservices.bean.ServiceResponse;
import cn.qtone.xxt.csop.webservices.bean.TransRequestParams;

public abstract class AbstractQueryService<Params extends TransRequestParams, Row extends ResultRow> {

	/**
	 * 查询接口
	 * 
	 * @param reqParams
	 * @return
	 */
	public abstract ServiceResponse query(Params reqParams);

	/**
	 * 
	 * 应答数据格式，主要针对多行数据的返回
	 * @param rows
	 * @return
	 */
	protected String formateResutlData(List<Row> rows){
		StringBuffer resultXml = new StringBuffer();
		for(Row row :rows){
			resultXml.append(row.formColumnData());
		    row = null;
		}
		rows = null;
		String content = resultXml.toString();
		resultXml=null;
		return content;
	};

	
	/**
	 * 
	 * 校验请求服务的有效性，设置拒绝访问的条件
	 * @param reqParams
	 * @return
	 */
    protected boolean validator(Params reqParams){
    	return  agreement().validator(reqParams);
    }
 
    
    /**
     * 设置对应的 服务使用协议
     * @return
     */
    protected abstract ServiceAgreement agreement(); 
    
}
