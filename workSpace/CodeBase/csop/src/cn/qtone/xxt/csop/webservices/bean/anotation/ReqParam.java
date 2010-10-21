package cn.qtone.xxt.csop.webservices.bean.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.qtone.xxt.csop.webservices.bean.enums.ValueType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ReqParam {

	String parent() default ""; // 根节点 到 节点的路径

	String nodeName(); // 节点名称

	ValueType fetch(); // 取值

	String attribute() default ""; // 属性指名称

}
