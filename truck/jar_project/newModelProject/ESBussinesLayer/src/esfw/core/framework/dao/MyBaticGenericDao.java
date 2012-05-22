package esfw.core.framework.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import esfw.core.framework.exception.DaoAccessException;

 
/**
 * 
 * 描述：MyBatic 数据访问层的数据实现
 * @author Ethan lam
 * 2012-5-22
 * @param <PK>
 * @param <E>
 */
public class MyBaticGenericDao<PK,E extends Serializable> implements GenericDao<PK, E> {
   
	static String LOAD_SQL = "loadById";
	static String INSERT_SQL = "insert";
	static String QUERY_SQL = "query";
	static String UPDATE_SQL = "update";
	static String DELETE_SQL = "delete";

	
	@Autowired
	private MyBatisDaoSupport myBatisDaoSupport;
	
	
	/**
	 * 
	 * 功能描述：返回 SqlSession 的方式
	 * @author Ethan.Lam
	 * @dateTime 2012-5-22
	 * @return
	 * SqlSession
	 *
	 */
	protected SqlMapClientTemplate getSqlMapClientTemplate(){
	    return this.myBatisDaoSupport.getSqlMapClientTemplate();
	}
	
	
	/**
	 * 
	 * 功能描述：定义命名空间
	 * @author Ethan.Lam
	 * @dateTime 2012-5-22
	 * @return
	 * String
	 *
	 */
    protected String mapNameSpace(){
    	return "";
    };
	
    /**
     * (non-Javadoc)
     * @see esfw.core.framework.dao.GenericDao#load(java.lang.Object)
     * 功能描述：
     * author Ethan lam
     * @param key
     * @return
     * @throws DaoAccessException
     * 
     */
	public E load(PK key) throws DaoAccessException {
		// TODO Auto-generated method stub
		return (E)getSqlMapClientTemplate().queryForObject(mapNameSpace()+LOAD_SQL,key);
	}

	
	/**
	 * (non-Javadoc)
	 * @see esfw.core.framework.dao.GenericDao#insert(java.io.Serializable)
	 * 功能描述：
	 * author Ethan lam
	 * @param entity
	 * @return PK
	 * @throws DaoAccessException
	 */
	public boolean insert(E entity) throws DaoAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert(mapNameSpace()+INSERT_SQL, entity);
		return true;
	}

	/**
	 * (non-Javadoc)
	 * @see esfw.core.framework.dao.GenericDao#update(java.io.Serializable)
	 * 功能描述：
	 * author Ethan lam
	 * @param entity
	 * @return
	 * @throws DaoAccessException
	 */
	public boolean update(E entity) throws DaoAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update(this.mapNameSpace()+UPDATE_SQL, entity);
		return true;
	}
	
	
	/**
	 * (non-Javadoc)
	 * @see esfw.core.framework.dao.GenericDao#delete(PK[])
	 * 功能描述：
	 * author Ethan lam
	 * @param keys
	 * @return
	 * @throws DaoAccessException
	 */
	public boolean delete(PK[] keys) throws DaoAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().delete(this.mapNameSpace()+DELETE_SQL,keys);
		return true;
	}
	
	
	/**
	 * (non-Javadoc)
	 * @see esfw.core.framework.dao.GenericDao#query(java.io.Serializable)
	 * 功能描述：标准的查询方法
	 * @author Ethan.Lam
	 * @dateTime 2012-5-22
	 * @param entity
	 * @return
	 * @throws DaoAccessException
	 *
	 */
	public List<E> query(E entity) throws DaoAccessException {
		// TODO Auto-generated method stub
		List list = getSqlMapClientTemplate().queryForList(this.mapNameSpace()+QUERY_SQL,entity);
		return list;
	}

	
	/**
	 * 
	 * (non-Javadoc)
	 * @see esfw.core.framework.dao.GenericDao#query(int, int, java.io.Serializable)
	 * 功能描述：分页查询
	 * @author Ethan.Lam
	 * @dateTime 2012-5-22
	 * @param page
	 * @param PageSize
	 * @param entity
	 * @return PageBean<E>
	 * @throws DaoAccessException
	 *
	 */
	public PageBean<E> query(int page, int pageSize, E entity) throws DaoAccessException {
        // TODO Auto-generated method stub
        int skipResults = (page-1)*pageSize+1;
        int maxResults = page*pageSize;
        int totalRows =  count(entity); //总记录项
        PageBean pageBean = new PageBean();
        pageBean.setCurPage(page);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecords(totalRows);
        List list = getSqlMapClientTemplate().queryForList(this.mapNameSpace()+QUERY_SQL, entity, skipResults, maxResults);
        pageBean.setRecords(list);
		return pageBean;
    }
	
	
	
	/**
	 * 
	 * (non-Javadoc)
	 * @see esfw.core.framework.dao.GenericDao#count(java.io.Serializable)
	 * 功能描述：记录统计
	 * @author Ethan.Lam
	 * @dateTime 2012-5-22
	 * @param entity
	 * @return
	 * @throws DaoAccessException
	 *
	 */
	public int count(E entity) throws DaoAccessException {
		// TODO Auto-generated method stub
		 int rowResult = (Integer) getSqlMapClientTemplate().queryForObject(this.mapNameSpace()+QUERY_SQL, entity);
		 return rowResult;
	}
	
	

	/**
	 * 
	 * (non-Javadoc)
	 * @see esfw.core.framework.dao.GenericDao#batchInsert(java.util.List)
	 * 功能描述：
	 * author Ethan lam
	 * @param entityList
	 * @return
	 * @throws DaoAccessException
	 * 
	 */
 	public boolean batchInsert(List<E> entityList) throws DaoAccessException {
		// TODO Auto-generated method stub
		return false;
	}

 	
 	/**
 	 * (non-Javadoc)
 	 * @see esfw.core.framework.dao.GenericDao#batchUpdate(PK[], java.util.Map)
 	 * 功能描述：
 	 * author Ethan lam
 	 * @param pKeys
 	 * @param values
 	 * @return
 	 * @throws DaoAccessException
 	 */
	public boolean batchUpdate(PK[] pKeys, Map<String, Object> values)
			throws DaoAccessException {
		// TODO Auto-generated method stub
		return false;
	}






}
