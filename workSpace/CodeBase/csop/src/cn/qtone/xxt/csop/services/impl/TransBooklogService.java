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
 * 6.4.1.1 业务定制情况查询（B005_01）
 * 
 * @author linhansheng
 * 
 */
public class TransBooklogService extends AbstractQueryService<TransBooklogParams,TransBooklogRow>{

	@Override
	protected String formateResutlData(List<TransBooklogRow> rows) {
		StringBuffer resultXml = new StringBuffer();
		String parent_start ="<column>";
		String parent_end="</column>";
		//要处理 2 7  12 的取值, 
		for(TransBooklogRow row :rows){
			resultXml.append(parent_start);
			resultXml.append("<column1>").append(row.getSchool()).append("</column1>");
			resultXml.append("<column2>").append(row.getClassName()).append("</column2>");
			resultXml.append("<column3>").append(row.getStudentName()).append("</column3>");
			resultXml.append("<column4>").append(row.getParentName()).append("</column4>");
			resultXml.append("<column5>").append(row.getTransaction()).append("</column5>");
			resultXml.append("<column6>").append(row.getBookType()).append("</column6>"); //操作方式
			resultXml.append("<column7>").append(row.getCharge()).append("</column7>");
			resultXml.append("<column8>").append(row.getOperationType()).append("</column8>");//操作类型
			resultXml.append("<column9>").append(row.getOperateDate()).append("</column9>");
			resultXml.append("<column10>").append(row.getReason()).append("</column10>");
			resultXml.append("<column11>").append(row.getOperator()).append("</column11>");
			resultXml.append("<column12>").append(row.getSaleRelationShip()).append("</column12>");
			resultXml.append("<column13>").append(row.getRemark()).append("</column13>");
			resultXml.append(parent_end);
		}
		String content = resultXml.toString();
		resultXml=null;
		return content;
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
			   resp.setResult(this.formateResutlData(results));
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

	@Override
	protected ServiceAgreement agreement() {
		return ServiceAgreement.TRANSCATION_BOOKLOG;
	}

}
