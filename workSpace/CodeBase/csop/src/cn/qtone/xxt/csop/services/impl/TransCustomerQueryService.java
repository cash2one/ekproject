package cn.qtone.xxt.csop.services.impl;

import java.util.List;

import cn.qtone.xxt.csop.dao.impl.TransCustomerQueryDao;
import cn.qtone.xxt.csop.dao.impl.TransCustomerRow;
import cn.qtone.xxt.csop.dao.inter.ResultRow;
import cn.qtone.xxt.csop.util.CsopLog;
import cn.qtone.xxt.csop.webservices.bean.RequestParams;
import cn.qtone.xxt.csop.webservices.bean.ServiceResponse;

/**
 * 6.4.1.1 业务定制情况查询接口（B005_01）
 * 
 * @author linhansheng
 * 
 */
public class TransCustomerQueryService {

	/**
	 * 
	 * @param reqParams
	 * @return
	 */
	public ServiceResponse query(RequestParams reqParams) {
		TransCustomerQueryDao dao = new TransCustomerQueryDao();
		ServiceResponse resp = new ServiceResponse();
		try {
			List<TransCustomerRow> results = dao.query(reqParams);
			if(results!=null){
			   resp.setRetmsg("query suc "+results.size()+" records ");
			   resp.setResult(this.formateResutlData(results));
			}
			resp.setRetcode("000");
		} catch (Exception e) {
			resp.setRetcode("");
			resp.setRetmsg("查询失败");
			CsopLog.error(e.getMessage());
		}finally{
			 dao=null;
		}
		return resp;
	}

	/**
	 * 
	 * @param rows
	 * @return
	 */
	String formateResutlData(List<TransCustomerRow> rows){
		StringBuffer resultXml = new StringBuffer();
		String parent_start ="<column>";
		String parent_end="</column>";
		for(TransCustomerRow row : rows){
			resultXml.append(parent_start);
			resultXml.append("<column1>").append(row.getName()).append("</column1>");
			resultXml.append("<column2>").append(row.getPort()).append("</column2>");
			resultXml.append("<column3>").append(row.getDesc()).append("</column3>");
			resultXml.append("<column4>").append(row.getCharge()).append("</column4>");
			resultXml.append("<column5>").append(row.getChargeType()).append("</column5>");
			resultXml.append("<column6>").append(row.getOpenType()).append("</column6>");
			resultXml.append("<column7>").append(row.getOrderTime()).append("</column7>");
			resultXml.append("<column8>").append(row.getServiceState()).append("</column8>");
			resultXml.append("<column9>").append(row.getPayTime()).append("</column9>");
			resultXml.append("<column10>").append(row.getSaleRelationShip()).append("</column10>");
			resultXml.append(parent_end);
		}
		String content = resultXml.toString();
		resultXml=null;
		return content;
	}
    
	
}
