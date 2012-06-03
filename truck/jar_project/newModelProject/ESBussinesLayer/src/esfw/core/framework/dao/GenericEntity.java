package esfw.core.framework.dao;

import java.io.Serializable;

/**
 * 
 * 描述：数据层的实体类
 * @author Ethan.Lam
 * @dateTime 2012-5-22
 *
 */
public abstract class GenericEntity implements Serializable {

     private String daoAbb; //实现表的物理分区所设置的字段信息

	public String getDaoAbb() {
		return daoAbb;
	}

	public void setDaoAbb(String daoAbb) {
		this.daoAbb = daoAbb;
	}
	
     
}
