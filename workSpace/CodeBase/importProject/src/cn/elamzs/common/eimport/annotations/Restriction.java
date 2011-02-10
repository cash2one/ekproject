package cn.elamzs.common.eimport.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 导入限制,对文件限制
 * @author Ethan.Lam
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Restriction {
    
	String fileName();
	boolean check();
	float size() default 1;
	
	
}
