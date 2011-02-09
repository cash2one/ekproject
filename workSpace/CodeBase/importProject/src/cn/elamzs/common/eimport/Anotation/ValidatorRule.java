package cn.elamzs.common.eimport.Anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.poi.ss.usermodel.IndexedColors;

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
	
	int columnSeq(); //��Ӧ�����ļ��������    �� 0 ��ʼ
	
	IndexedColors color() default IndexedColors.BLACK;
	
	int width() default 50; //�п�����
	
	boolean check() default false; //���ø�ֵ���Ƿ���Ҫ��ֵ֤�ĺϷ���
	
	
}
