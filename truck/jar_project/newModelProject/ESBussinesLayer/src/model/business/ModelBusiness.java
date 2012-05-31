package model.business;

import java.util.List;

import model.dao.ModelDao;
import model.entity.ModelEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import esfw.core.framework.business.BaseBusiness;
import esfw.core.framework.business.BaseQuery;
import esfw.core.framework.business.enumeration.ActionType;
import esfw.core.framework.exception.BusinessException;
import esfw.core.framework.exception.DaoAccessException;


/**
 * 
 * 描述：业务逻辑层的 模版
 * @author Ethan.Lam 
 * @date 2012-5-24
 *
 */
@Scope("prototype") 
@Service("modelBusiness")
public class ModelBusiness extends BaseBusiness implements BaseQuery<ModelBusiness,Long,ModelEntity>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3705365983032530911L;
	
	@Autowired
	ModelDao modelDao;
	
	
	
	@Override
	protected void checkAndFilter(ActionType type) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getBusinessName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFunctionFlag() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public String getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * 
	 * 功能描述：列表查询
	 * @author Ethan.Lam
	 * @date 2012-5-31
	 * (non-Javadoc)
	 * @see esfw.core.framework.business.BaseQuery#query(java.io.Serializable)
	 */
	public List<ModelBusiness> query(ModelEntity entity) throws BusinessException {
           // TODO Auto-generated method stub
		 try {
			    int t = modelDao.count(new ModelEntity());
			    System.out.println("查询结构的记录数有:"+ t ); 
			    List<ModelEntity> entityList = modelDao.query(new ModelEntity());
				for(ModelEntity e:entityList){
					System.out.println("query:"+e.getSchoolName());
				} 		
			} catch (DaoAccessException e) {
				e.printStackTrace();
			}
		
		return null;
	}


	/**
	 * 
	 * 功能描述：FindOne 查询
	 * @author Ethan.Lam
	 * @date 2012-5-31
	 * (non-Javadoc)
	 * @see esfw.core.framework.business.BaseQuery#load(java.lang.Object)
	 */
	public void load(Long id) {
	// TODO Auto-generated method stub
	 try {
			ModelEntity entity =  modelDao.load(id);
			System.out.println(entity.getSchoolName());
		} catch (DaoAccessException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected void onAdd() throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onDelete(long[] ids) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onModify() throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	
}
