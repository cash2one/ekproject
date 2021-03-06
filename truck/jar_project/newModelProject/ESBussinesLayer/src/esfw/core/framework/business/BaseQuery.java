package esfw.core.framework.business;

import java.util.List;

import esfw.core.framework.controller.ViewObject;
import esfw.core.framework.exception.BusinessException;

/**
 * 
 * 描述：查询接口
 * @author Ethan.Lam 
 * @date 2012-5-31
 *
 */
public interface BaseQuery<Bean extends BaseBusiness,PK,Vo extends ViewObject> {

	
	/**
	 * 
	 * 功能描述：主键查询单条记录
	 * @author Ethan.Lam
	 * @date 2012-5-31
	 * @param pk
	 * @return Boolean 是否存在对应的记录
	 */
	public Boolean load(PK pk) throws BusinessException ;
	
	
	/**
	 * 功能描述：列表查询
	 * @author Ethan.Lam
	 * @date 2012-5-31
	 * @param voBean
	 * @return
	 * @throws BusinessException
	 *
	 */
	public List<Bean> query(Vo voBean)throws BusinessException;

	
	
	
}
