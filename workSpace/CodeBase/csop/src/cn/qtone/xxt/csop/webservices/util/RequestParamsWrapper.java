package cn.qtone.xxt.csop.webservices.util;

import java.lang.reflect.Field;
import java.util.Vector;

import org.dom4j.Document;

import cn.qtone.xxt.csop.util.BeanUtil;
import cn.qtone.xxt.csop.util.Checker;
import cn.qtone.xxt.csop.util.file.xml.XmlHandler;
import cn.qtone.xxt.csop.webservices.bean.RequestParams;
import cn.qtone.xxt.csop.webservices.bean.TransCustomerQueryParams;
import cn.qtone.xxt.csop.webservices.bean.anotation.ReqParam;
import cn.qtone.xxt.csop.webservices.bean.enums.ValueType;
import cn.qtone.xxt.csop.webservices.testcase.Client;

/***
 * 
 * @author 根据提供的 xml 生成对应的 请求参数模型
 *
 * @param <T> extends RequestParams
 */
public class RequestParamsWrapper<T extends RequestParams> {

	public T formParams(Document _doc, Class<T> clazz) throws Exception {
		if (_doc == null) {
			return null;
		}
		T obj = clazz.newInstance();

		Vector<Field> fields = BeanUtil.getAllDeclaredFields(clazz);
		if (fields == null || fields.size() == 0)
			throw new Exception("服务请求失败！出错的原因是，服务器端解释请求报文失败！");
		
		ReqParam paramAnotation = null;
		String nodePath = "";
		for (Field field : fields) {
			paramAnotation = field.getAnnotation(ReqParam.class);
			if (paramAnotation == null)
				continue;
			field.setAccessible(true);
			if (Checker.isNull(paramAnotation.nodeName()))
				continue;
			if (!Checker.isNull(paramAnotation.parent()))
				nodePath = paramAnotation.parent().replace(".", "/") + "/"
						+ paramAnotation.nodeName();
			else
				nodePath = paramAnotation.nodeName();
			
			try{
				if (ValueType.TEXT_VALUE.equals(paramAnotation.fetch())) {
					field.set(obj, XmlHandler.getElement(_doc, nodePath)
							.getTextTrim());
				} else if (ValueType.ATTRIBUTE.equals(paramAnotation.fetch())) {
					field.set(obj, XmlHandler.getElement(_doc, nodePath).attribute(
							paramAnotation.attribute()).getText());
				}
			}catch(Exception e){
				throw new Exception("请求报文协议中所定义的参数字段不一致，请检查字段格式！出错的原因是，找不到节点["+nodePath+"]");
			}
			field = null;
			paramAnotation = null;
		}

		_doc = null;
		fields = null;
		return obj;
	}

	public T formParams(String xml, Class<T> clazz) throws Exception {
		if(Checker.isNull(xml)){
			System.out.println("请求报文为空! ");			
		}
		xml.replace("&lt;","<").replace("&gt;",">");
		Document _doc = XmlHandler.createDocument(xml);
		if (_doc == null) {
			System.out.println("读取请求参数文件有误。 ");
			return null;
		}
		return formParams(_doc, clazz);
	}

	
	public static void main(String... s) throws Exception {
		RequestParamsWrapper test = new RequestParamsWrapper<TransCustomerQueryParams>();
//		Document xml = XmlHandler.loadXML("configs/request.xml");
//		TransCustomerQueryParams params = (TransCustomerQueryParams) test
//				.formParams(xml, TransCustomerQueryParams.class);
//		System.out.println(params.getEndDate());
		
		String content = Client.requestXMLTrans("configs/request.xml");
		System.out.println(content);
		content = content.replace("&lt;","<").replace("&gt;",">");
		TransCustomerQueryParams params = (TransCustomerQueryParams) test
				.formParams(content, TransCustomerQueryParams.class);
		System.out.println(params.getEndDate());
	}

}
