package esfw.core.framework.controller;

import java.io.Serializable;


/**
 * 
 * 描述：Vo 对象基类
 * @author Ethan.Lam
 * @dateTime 2012-6-2
 * 
 */
public interface ViewObject extends Serializable {
	
	 //分页信息
     PageVo pageVo = new PageVo();
	 
     //分页对象
     public PageVo getPageVo();
      
}
