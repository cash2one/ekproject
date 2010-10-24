package cn.qtone.xxt.csop.webservices.bean;

import cn.qtone.xxt.csop.webservices.bean.anotation.ReqParam;
import cn.qtone.xxt.csop.webservices.bean.enums.ValueType;



/**
 * 6.4.1.3	业务历史订购记录查询 
 * @author linhansheng
 *
 */
public class TransBooklogParams extends RequestParams  {

	// 查询对象      小孩1、小孩2…全部
	@ReqParam(parent="Params",nodeName = "ChildObject", fetch =ValueType.TEXT_VALUE)
	String childObject;
	
}
