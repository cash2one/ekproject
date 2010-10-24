package cn.qtone.xxt.csop.dao.impl;

import java.util.List;

import cn.qtone.xxt.csop.dao.model.TransBooklogRow;
import cn.qtone.xxt.csop.inter.AbstractTransDao;
import cn.qtone.xxt.csop.webservices.bean.TransBooklogParams;


/**
 *6.4.1.3 业务历史订购记录查询接口（B005_03）
 * 
 * @author Solosus
 * 
 */
public class TransBooklogDao extends
		AbstractTransDao<TransBooklogParams, TransBooklogRow> {

	@Override
	public List<TransBooklogRow> query(TransBooklogParams requsetParams) {
		// TODO Auto-generated method stub
		return null;
	}

}
