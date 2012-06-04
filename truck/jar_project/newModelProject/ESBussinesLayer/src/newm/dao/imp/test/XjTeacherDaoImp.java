package newm.dao.imp.test;

import java.util.*;

import org.springframework.stereotype.Component;
import esfw.core.framework.dao.MyBaticGenericDao;

import newm.dao.inter.test.XjTeacherDao;
import newm.dao.entity.test.XjTeacherEntity;
import newm.vo.test.XjTeacherVo;


/**
 *
 * @description 测试 类 对应的 Dao 实现层
 * @version v1.0.0
 * @author Ethanlam  
 * @CreateTime Mon Jun 04 13:23:17 GMT 2012
 *
 */
@Component("xjTeacherDao")
public class XjTeacherDaoImp extends MyBaticGenericDao <XjTeacherVo,Long,XjTeacherEntity> implements XjTeacherDao{
    
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
    	return "newm.dao.test.XjTeacherDao";
    }
    
    
	//自定义方法
	//************************************************************************************************************************
	
	
	
}

