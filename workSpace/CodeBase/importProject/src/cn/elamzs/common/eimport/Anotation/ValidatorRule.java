package cn.elamzs.common.eimport.Anotation;

/**
 * �������ݵ����а������е���������
 * @author Ethan.Lam
 *
 */
public @interface ValidatorRule {

	String ename(); //��������� ����ҪΨһ
	
	int column(); //��Ӧ�����ļ��������
	
	boolean check() default false; //���ø�ֵ���Ƿ���Ҫ��ֵ֤�ĺϷ���
	
	
}
