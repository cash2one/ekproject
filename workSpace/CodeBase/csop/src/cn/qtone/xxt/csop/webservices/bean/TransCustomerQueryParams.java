package cn.qtone.xxt.csop.webservices.bean;

import cn.qtone.xxt.csop.webservices.bean.anotation.ReqParam;
import cn.qtone.xxt.csop.webservices.bean.enums.ValueType;



/**
 * 业务定制情况查询接口     B005_01
 * @author linhansheng
 *
 */
public class TransCustomerQueryParams extends TransRequestParams  {

	// 开始时间
	@ReqParam(parent="Params",nodeName = "BeginDate", fetch =ValueType.TEXT_VALUE)
	String beginDate;

	// 结束时间
	@ReqParam(parent="Params",nodeName = "EndDate", fetch =ValueType.TEXT_VALUE)
	String endDate;

	
	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
