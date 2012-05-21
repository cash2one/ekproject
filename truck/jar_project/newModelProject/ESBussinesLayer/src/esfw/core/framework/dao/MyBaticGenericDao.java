package esfw.core.framework.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import esfw.core.framework.exception.DaoAccessException;

/**
 * 
 * MyBatic 数据访问层的数据实现
 * @author Ethan.Lam  2012-5-21
 * @param <PK>
 * @param <E>
 * 
 */
public class MyBaticGenericDao<PK,E extends Serializable> implements GenericDao<PK, E> {
    
	public E fineOne(PK key) throws DaoAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean insert(E entry) throws DaoAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean update(E entry) throws DaoAccessException {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean delete(PK[] keys) throws DaoAccessException {
		// TODO Auto-generated method stub
		return false;
	}
	
 	public boolean batchInsert(List<E> entryList) throws DaoAccessException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean batchUpdate(PK[] pKeys, Map<String, Object> values)
			throws DaoAccessException {
		// TODO Auto-generated method stub
		return false;
	}

}
