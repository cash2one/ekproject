package cn.elamzs.common.eimport.Anotation;

/**
 * �������ݵ����а������е���������
 * @author Ethan.Lam
 *
 */
public @interface ValidatorRule {

	String name(); //��������� ����ҪΨһ
	
	boolean check() default false; //���ø�ֵ���Ƿ���Ҫ��ֵ֤�ĺϷ���
	
	
}
