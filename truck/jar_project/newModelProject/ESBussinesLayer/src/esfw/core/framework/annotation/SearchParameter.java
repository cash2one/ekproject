package esfw.core.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import esfw.core.framework.business.enumeration.MatchingType;


@Target({ElementType.PARAMETER})   //用于字段，方法，参数
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchParameter {
	/**
	 * 参数名称
	 * 
	 * @return
	 */
	String name();

	/**
	 * 默认值，调用时如果没有设置值使用的默认值
	 * 
	 * @return
	 */
	String defaultValue() default "null";
	
	
	/**
	 * 
	 * 字符串  查询条件 匹配类型，默认设置为  MatchingType.ALL_FUZZY，模糊查询       %content%
	 * @return
	 */
	MatchingType type() default MatchingType.EXACT;

}

