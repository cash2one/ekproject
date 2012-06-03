package newm.dao.imp.test;

import java.util.*;

import org.springframework.stereotype.Component;
import esfw.core.framework.dao.MyBaticGenericDao;

import newm.dao.inter.test.XjClassDao;
import newm.dao.entity.test.XjClassEntity;
import newm.vo.test.XjClassVo;


/**
 *
 * @description 班级信息 类 对应的 Dao 实现层
 * @version v1.0.0
 * @author Ethanlam  
 * @CreateTime Sun Jun 03 11:05:31 GMT 2012
 *
 */
@Component("xjClassDao")
public class XjClassDaoImp extends MyBaticGenericDao <XjClassVo,Long,XjClassEntity> implements XjClassDao{
    
     //自动生成的方法
	 //************************************************************************************************************************

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
    	return "newm.dao.test.XjClassDao";
    }
    
    
	//自定义方法
	//************************************************************************************************************************
	
	
	
}

