package newm.dao.imp.test;

import java.util.*;

import org.springframework.stereotype.Component;
import esfw.core.framework.dao.MyBaticGenericDao;

import newm.dao.inter.test.AdcSyncLogDao;
import newm.dao.entity.test.AdcSyncLogEntity;
import newm.vo.test.AdcSyncLogVo;


/**
 *
 * @description fsdf 类 对应的 Dao 实现层
 * @version v1.0.0
 * @author Ethanlam  
 * @CreateTime Mon Jun 04 13:00:20 GMT 2012
 *
 */
@Component("adcSyncLogDao")
public class AdcSyncLogDaoImp extends MyBaticGenericDao <AdcSyncLogVo,Long,AdcSyncLogEntity> implements AdcSyncLogDao{
    
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
    	return "newm.dao.test.AdcSyncLogDao";
    }
    
    
	//自定义方法
	//************************************************************************************************************************
	
	
	
}

