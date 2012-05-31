package esfw.core.framework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 
 * 描述：
 * 
 * @author Ethan.Lam
 * @dateTime 2012-5-22
 * 
 */
@Component("myBatisDaoSupport")
public class IBatisDaoSupport extends SqlMapClientDaoSupport {

	// 为了注入SqlMapClient所以多了一个baseDao
	@Autowired(required=true)
	public void setSqlMapClientBase(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

}
