package model.dao.imp;

import model.dao.ModelDao;
import model.entity.ModelEntity;
import model.vo.ModelVo;

import org.springframework.stereotype.Component;

import esfw.core.framework.dao.MyBaticGenericDao;

/**
 * 
 * 描述：模版类 Dao 实现层
 * @author Ethan.Lam 
 * @date 2012-5-24
 *
 */
@Component("modelDao")
public class ModelDaoImp  extends MyBaticGenericDao<ModelVo,Long,ModelEntity> implements ModelDao{

	/**
	 * (non-Javadoc)
	 * @see esfw.core.framework.dao.MyBaticGenericDao#mapNameSpace()
	 * 功能描述：命名空间定义
	 * @author Ethan.Lam
	 * @dateTime 2012-6-3
	 * @return
	 *
	 */
    protected String mapNameSpace(){
    	return "Model";
    }
	
}
