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
 * @author �����ṩ�� xml ���ɶ�Ӧ�� �������ģ��
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
			throw new Exception("��������ʧ�ܣ������ԭ���ǣ��������˽���������ʧ�ܣ�");
		
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
				throw new Exception("������Э����������Ĳ����ֶβ�һ�£������ֶθ�ʽ�������ԭ���ǣ��Ҳ����ڵ�["+nodePath+"]");
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
			System.out.println("������Ϊ��! ");			
		}
		xml.replace("&lt;","<").replace("&gt;",">");
		Document _doc = XmlHandler.createDocument(xml);
		if (_doc == null) {
			System.out.println("��ȡ��������ļ����� ");
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
