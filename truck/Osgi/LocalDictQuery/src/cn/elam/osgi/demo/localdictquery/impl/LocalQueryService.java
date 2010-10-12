package cn.elam.osgi.demo.localdictquery.impl;

import cn.elam.osgi.demo.dictquery.query.QueryService;

public class LocalQueryService implements QueryService {

	@Override
	public String queryWork(String mes) {
		return "Local query:result" + mes;
	}

}
