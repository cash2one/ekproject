package cn.elamzs.common.eimport.Anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * �������ݵ����а������е���������
 * @author Ethan.Lam
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidatorRule {

	String ename(); //��������� ����ҪΨһ
	
	String showName(); //�ı�����ʾ������
	
	int columnSeq(); //��Ӧ�����ļ��������
	
	boolean check() default false; //���ø�ֵ���Ƿ���Ҫ��ֵ֤�ĺϷ���
	
	
}
