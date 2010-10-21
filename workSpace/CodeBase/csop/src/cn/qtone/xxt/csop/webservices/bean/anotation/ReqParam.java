package cn.qtone.xxt.csop.webservices.bean.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.qtone.xxt.csop.webservices.bean.enums.ValueType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ReqParam {

	String parent() default ""; // ���ڵ� �� �ڵ��·��

	String nodeName(); // �ڵ�����

	ValueType fetch(); // ȡֵ

	String attribute() default ""; // ����ָ����

}
