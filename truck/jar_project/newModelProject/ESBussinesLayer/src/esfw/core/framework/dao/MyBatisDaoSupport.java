package esfw.core.framework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 
 * 描述：
 * 
 * @author Ethan.Lam
 * @dateTime 2012-5-22
 * 
 */
public class MyBatisDaoSupport extends SqlMapClientDaoSupport {

	// 为了注入SqlMapClient所以多了一个baseDao
	@Autowired
	public void setSqlMapClientBase(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

}
