package esfw.core.framework.business;

import java.io.Serializable;
import java.util.List;

import esfw.core.framework.exception.BusinessException;

/**
 * 
 * 描述：查询接口
 * @author Ethan.Lam 
 * @date 2012-5-31
 *
 */
public interface BaseQuery<Bean extends BaseBusiness,PK,E extends Serializable> {

	
	/**
	 * 
	 * 功能描述：主键查询单条记录
	 * @author Ethan.Lam
	 * @date 2012-5-31
	 * @param pk
	 *
	 */
	public void load(PK pk);
	
	
	/**
	 * 
	 * 功能描述：列表查询
	 * @author Ethan.Lam
	 * @date 2012-5-31
	 * @param entity
	 * @return
	 * @throws BusinessException
	 *
	 */
	public List<Bean> query(E entity)throws BusinessException;

	
	
	
}
