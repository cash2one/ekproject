package model.vo;

import model.entity.ModelEntity;
import esfw.core.framework.controller.PageVo;
import esfw.core.framework.controller.ViewObject;

/**
 * 
 * 描述：模版代码 （负责页面传值，查询等 值域 ）
 * @author Ethan.Lam
 * @dateTime 2012-6-2
 *
 */
public class ModelVo extends ModelEntity implements ViewObject {

	public PageVo getPageVo() {
	    return pageVo;
	}
    
	
	
}
