package cn.qtone.xxt.csop.inter;

import java.util.List;

import cn.qtone.xxt.csop.dao.model.TransCustomerRow;
import cn.qtone.xxt.csop.webservices.bean.RequestParams;
import cn.qtone.xxt.csop.webservices.bean.ServiceResponse;

public abstract class AbstractQueryService<Params extends RequestParams, Row extends ResultRow> {

	/**
	 * 查询接口
	 * 
	 * @param reqParams
	 * @return
	 */
	public abstract ServiceResponse query(Params reqParams);

	/**
	 * 应答数据格式，主要针对多行数据的返回
	 * 
	 * @param rows
	 * @return
	 */
	protected abstract String formateResutlData(List<Row> rows);

}
