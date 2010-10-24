package cn.qtone.xxt.csop.inter;

import java.util.List;

import cn.qtone.xxt.csop.dao.model.TransCustomerRow;
import cn.qtone.xxt.csop.webservices.bean.RequestParams;
import cn.qtone.xxt.csop.webservices.bean.ServiceResponse;

public abstract class AbstractQueryService {

	/**
	 * ��ѯ�ӿ�
	 * 
	 * @param reqParams
	 * @return
	 */
	public abstract ServiceResponse query(RequestParams reqParams);

	
	/**
	 * Ӧ�����ݸ�ʽ����Ҫ��Զ������ݵķ���
	 * @param rows
	 * @return
	 */
	protected abstract String formateResutlData(List<? extends ResultRow> rows);

	
}
