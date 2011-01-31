package cn.elamzs.common.eimport.Anotation;

/**
 *
 * 导入限制,对文件限制
 * @author Ethan.Lam
 *
 */
public @interface Restriction {

	boolean check();
	float size() default 1;
	
	
}
