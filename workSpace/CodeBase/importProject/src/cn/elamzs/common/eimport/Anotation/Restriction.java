package cn.elamzs.common.eimport.Anotation;

/**
 *
 * ��������,���ļ�����
 * @author Ethan.Lam
 *
 */
public @interface Restriction {

	boolean check();
	float size() default 1;
	
	
}
