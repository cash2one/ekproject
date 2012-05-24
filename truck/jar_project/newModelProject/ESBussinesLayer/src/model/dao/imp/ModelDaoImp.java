package model.dao.imp;

import org.springframework.stereotype.Component;

import model.dao.ModelDao;
import model.entity.ModelEntity;
import esfw.core.framework.dao.MyBaticGenericDao;

/**
 * 
 * 描述：模版类 Dao 实现层
 * @author Ethan.Lam 
 * @date 2012-5-24
 *
 */
@Component("modelDaoImp")
public class ModelDaoImp  extends MyBaticGenericDao<Long,ModelEntity> implements ModelDao{

	
	
}
