package newm.dao.imp.test;

import java.util.*;

import org.springframework.stereotype.Component;
import esfw.core.framework.dao.MyBaticGenericDao;

import newm.dao.inter.test.XjStudentDao;
import newm.dao.entity.test.XjStudentEntity;
import newm.vo.test.XjStudentVo;


/**
 *
 * @description 测试 类 对应的 Dao 实现层
 * @version v1.0.0
 * @author Ethanlam  
 * @CreateTime Sun Jun 03 10:54:30 GMT 2012
 *
 */
@Component("xjStudentDao")
public class XjStudentDaoImp extends MyBaticGenericDao <XjStudentVo,Long,XjStudentEntity> implements XjStudentDao{
    
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
    	return "newm.dao.test.XjStudentDao";
    }
    
    
	//自定义方法
	//************************************************************************************************************************
	
	
	
}

