package cn.elamzs.common.eimport.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * ��������,���ļ�����
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
