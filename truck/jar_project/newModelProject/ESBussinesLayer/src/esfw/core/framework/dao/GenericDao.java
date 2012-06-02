package esfw.core.framework.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import esfw.core.framework.controller.ViewObject;
import esfw.core.framework.exception.DaoAccessException;

/**
 * 
 * 描述：Dao 基类
 * @author Ethan.Lam
 * @dateTime 2012-5-22
 * @param <PK>  主键类型
 * @param <E>   实体类 
 *
 */
public interface GenericDao<Vo extends ViewObject,PK,E extends Serializable> {
	
	 /**
	  * 功能描述：查找接口
	  * author Ethan lam
	  * @param key
	  * @return
	  * @throws DaoAccessException
	  *
	  */
	  public  E load(PK key) throws DaoAccessException;
	
	  
	  /**
	   * 
	   * 功能描述：记录新增
	   * @param entity
	   * @return
	   * @throws DaoAccessException
	   * 
	   */
	  public boolean insert(E entity) throws DaoAccessException;
	  
	  
	  
	  /**
	   * 功能描述：记录更新
	   * @param entity
	   * @return
	   * @throws DaoAccessException
	   */
	  public  boolean update(E entity) throws DaoAccessException;
	  
	  
	  
	  /**
	   * 功能描述：记录删除
	   * @param keys
	   * @return
	   * @throws DaoAccessException
	   */
	  public  boolean delete(PK[] keys) throws DaoAccessException;
	  
	  
	  /**
	   * 功能描述：标准的查询接口
	   * @author Ethan.Lam
	   * @dateTime 2012-5-22
	   * @param entity
	   * @return
	   * @throws DaoAccessException
	   * List<E>
	   *
	   */
	  public List<E> query(Vo voBean)throws DaoAccessException;
	  
	 
	  /**
	   * 
	   * 功能描述：分页查询
	   * @author Ethan.Lam
	   * @dateTime 2012-5-22
	   * @param page
	   * @param PageSize
	   * @param entity
	   * @return
	   * @throws DaoAccessException
	   * List<E>
	   *
	   */
	  public PageBean<E> query(int page,int PageSize,Vo voBean)throws DaoAccessException;
	  
	  
	  /**
	   * 
	   * 功能描述：记录统计
	   * @author Ethan.Lam
	   * @dateTime 2012-5-22
	   * @param entity
	   * @return
	   * @throws DaoAccessException
	   * int
	   *
	   */
	  public int count(Vo voBean) throws DaoAccessException;
	  
	  
	  
	  /**
	   * 功能描述：批量新增接口
	   * @param entityList
	   * @return
	   * @throws DaoAccessException
	   */
	  public boolean batchInsert(List<E> entityList) throws DaoAccessException;
	
	  
	  
	  /**
	   * 功能描述：批量更新记录
	   * @param pKeys
	   * @param values
	   * @return
	   * @throws DaoAccessException
	   */
	  public boolean batchUpdate(PK[] pKeys,Map<String,Object> values)throws DaoAccessException;
	  
}
