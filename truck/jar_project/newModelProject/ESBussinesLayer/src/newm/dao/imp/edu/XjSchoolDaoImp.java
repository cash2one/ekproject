package newm.dao.imp.edu;

import java.util.*;

import org.springframework.stereotype.Component;

import esfw.core.framework.dao.MyBaticGenericDao;

import newm.dao.edu.XjSchoolDao;

import newm.dao.entity.edu.XjSchoolEntity;

import newm.vo.edu.XjSchoolVo;

/**
 * 
 * 
 * 
 * @description 学校信息 类 对应的 Dao 实现层
 * 
 * @version v1.0
 * 
 * @author Qtone
 * 
 * @CreateTime Sun Jun 03 02:42:48 GMT 2012
 * 
 * 
 */

@Component("xjSchoolDao")
public class XjSchoolDaoImp extends
		MyBaticGenericDao<XjSchoolVo, Long, XjSchoolEntity> implements
		XjSchoolDao {

	// 自动生成的方法

	// ************************************************************************************************************************

	/**
	 * (non-Javadoc)
	 * @see esfw.core.framework.dao.MyBaticGenericDao#mapNameSpace()
	 * 功能描述：命名空间定义
	 * @author Ethan.Lam
	 * @dateTime 2012-6-3
	 * @return
	 */
	protected String mapNameSpace() {
		return "newm.dao.edu.XjSchoolDao";
	}

	// 自定义方法

	// ************************************************************************************************************************

}
