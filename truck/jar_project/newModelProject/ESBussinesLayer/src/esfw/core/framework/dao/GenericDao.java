package esfw.core.framework.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import esfw.core.framework.exception.DaoAccessException;

/**
 * Dao 基类
 * @author Ethan.Lam  2012-5-21
 * PK 主键的类型
 * E  实体类
 */
public interface GenericDao<PK,E extends Serializable> {

	 /**
	  * 查找接口
	  * @param key
	  * @return
	  * @throws DaoAccessException
	  */
	  public  E fineOne(PK key) throws DaoAccessException;
	
	  
	  /**
	   * 记录新增
	   * @param entry
	   * @return
	   * @throws DaoAccessException
	   */
	  public  boolean insert(E entry) throws DaoAccessException;
	  
	  
	  
	  /**
	   * 记录更新
	   * @param entry
	   * @return
	   * @throws DaoAccessException
	   */
	  public  boolean update(E entry) throws DaoAccessException;
	  
	  
	  
	  /**
	   * 记录删除
	   * @param keys
	   * @return
	   * @throws DaoAccessException
	   */
	  public  boolean delete(PK[] keys) throws DaoAccessException;
	  
	 
	  /**
	   * 批量新增接口
	   * @param entryList
	   * @return
	   * @throws DaoAccessException
	   */
	  public boolean batchInsert(List<E> entryList) throws DaoAccessException;
	
	  
	  
	  /**
	   * 批量更新记录
	   * @param pKeys
	   * @param values
	   * @return
	   * @throws DaoAccessException
	   */
	  public boolean batchUpdate(PK[] pKeys,Map<String,Object> values)throws DaoAccessException;
	  
}
