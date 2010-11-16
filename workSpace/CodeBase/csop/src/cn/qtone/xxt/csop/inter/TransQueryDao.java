package cn.qtone.xxt.csop.inter;

import java.util.List;

import cn.qtone.xxt.csop.webservices.bean.RequestParams;


public interface TransQueryDao<Params extends RequestParams,Row extends ResultRow>{
	
	public List<Row> query(Params requsetParams);
	
	
}
