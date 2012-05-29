package model.business;

import model.dao.ModelDao;
import model.entity.ModelEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import esfw.core.framework.business.BaseBusiness;
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
public class ModelBusiness extends BaseBusiness{

	
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

	
	public void load(Long id) throws BusinessException {
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
