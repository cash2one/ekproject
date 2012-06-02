package esfw.core.framework.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import esfw.core.framework.controller.ViewObject;
import esfw.core.framework.exception.DaoAccessException;

 
/**
 * 
 * 描述：MyBatic 数据访问层的数据实现
 * @author Ethan lam
 * 2012-5-22
 * @param <PK>
 * @param <E>
 */
public class MyBaticGenericDao<Vo extends ViewObject,PK,E extends Serializable> implements GenericDao<Vo,PK, E> {
   
	static String LOAD_SQL = ".loadById";
	static String INSERT_SQL = ".insert";
	static String QUERY_SQL = ".query";
	static String UPDATE_SQL = ".update";
	static String DELETE_SQL = ".delete";
	static String COUNT_SQL = ".count";
	
	@Autowired
	private MyBatisDaoSupport myBatisDaoSupport;
	
	private SqlSessionTemplate template;
	
	/**
	 * 
	 * 功能描述：返回 SqlSession 的方式
	 * @author Ethan.Lam
	 * @dateTime 2012-5-22
	 * @return
	 * SqlSession
	 *
	 */
	protected SqlSession getSqlSession(){
	    return this.myBatisDaoSupport.getSqlSession();
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
		try{
		   return (E)getSqlSession().selectOne(mapNameSpace()+LOAD_SQL,key);
		}catch(Exception e){
		   throw new DaoAccessException("记录查询发生异常",e);
		}
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
		try{
			getSqlSession().insert(mapNameSpace()+INSERT_SQL, entity);
			return true;
		}catch(Exception e){
		   throw new DaoAccessException("--SQL--记录新增发生异常",e);
		}
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
		try{
			getSqlSession().update(this.mapNameSpace()+UPDATE_SQL, entity);
			return true;
		}catch(Exception e){
		   throw new DaoAccessException("--SQL--记录修改发生异常",e);
		}		
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
		try{	
			getSqlSession().delete(this.mapNameSpace()+DELETE_SQL, keys);
			return true;
		 }catch(Exception e){
			throw new DaoAccessException("--SQL--记录删除发生异常",e);
		 }		
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
	public List<E> query(Vo vo) throws DaoAccessException {
		// TODO Auto-generated method stub
	 try{	
		List list = getSqlSession().selectList(this.mapNameSpace()+QUERY_SQL,vo);
		return list;
	   }catch(Exception e){
			throw new DaoAccessException("--SQL--记录查询发生异常",e);
	   }			
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
	public PageBean<E> query(int page, int pageSize, Vo voBean) throws DaoAccessException {
        // TODO Auto-generated method stub
       try{ 
			int startIndex = (page-1)*pageSize +1;
	        int totalRows =  count(voBean); //总记录项
	        PageBean<E> pageBean = new PageBean<E>();
	        pageBean.setCurPage(page);
	        pageBean.setPageSize(pageSize);
	        pageBean.setTotalRecords(totalRows);
	        RowBounds rb = new RowBounds();
	        List<E> list =(List<E>)getSqlSession().selectList(this.mapNameSpace()+QUERY_SQL, voBean, new RowBounds(startIndex,pageSize));
	        pageBean.setBeanList(list);
			return pageBean;
	   }catch(Exception e){
			throw new DaoAccessException("--SQL--记录查询发生异常",e);
	   }			
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
	public int count(Vo voBean) throws DaoAccessException {
		// TODO Auto-generated method stub
	   try{	
		 int rowResult = (Integer) getSqlSession().selectOne(this.mapNameSpace()+COUNT_SQL, voBean);
		 return rowResult;
	   }catch(Exception e){
			throw new DaoAccessException("--SQL--记录查询发生异常",e);
	   }		 
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
