package cn.qtone.xxt.apps.web.misc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class YwComplaintUtil {
	
	
	public static String HANDLER_ID = "1";  //�����˵�ID
	
	
	//�ͻ����� ��1ȫ��ͨ�𿨡�2ȫ��ͨ������3���еش���4���ڿ�����
	public static String getCustomerTypeText(String customer_type){
		String text="";
		if(customer_type.equals("1"))text= "VIP�ͻ�";
		if(customer_type.equals("2"))text= "ȫ��ͨ";
		if(customer_type.equals("3"))text= "���еش�";
		if(customer_type.equals("4"))text= "�����б���";
		return text;
	}
	
	//�ͻ����� ��1ȫ��ͨ�𿨡�2ȫ��ͨ������3���еش���4���ڿ���������
	public static String getCustomerTypeCode(String customer_type){
		String text="";
		if(customer_type.equals("VIP�ͻ�"))text= "1";
		if(customer_type.equals("ȫ��ͨ"))text= "2";
		if(customer_type.equals("���еش�"))text= "3";
		if(customer_type.equals("�����б���"))text= "4";
		return text;
	}
	
	
	//Ͷ�ߵȼ���2������1����0һ�㣩��
	public static String getComplaintLevelText(String complaint_level){
		String text="";
		if(complaint_level.equals("0"))text= "һ��";
		if(complaint_level.equals("1"))text= "��";
		if(complaint_level.equals("2"))text= "����";
		return text;
	}
	
	
	//Ͷ�ߵȼ���2������1����0һ�㣩��
	public static String getComplaintLevelCode(String complaint_level){
		String text="";
		if(complaint_level.equals("һ��"))text= "0";
		if(complaint_level.equals("��"))text= "1";
		if(complaint_level.equals("����"))text= "2";
		if(complaint_level.equals("��ʱ"))text= "2";
		return text;
	}
	
	
	//������ȣ�0������ 1������ 2�����꣩
	public static String getHandleStatusText(String handle_status){
		String text="";
		if(handle_status.equals("0"))text= "������";
		if(handle_status.equals("1"))text= "������";
		if(handle_status.equals("2"))text= "������";
		return text;
	}
	
	//�ͻ������
	public static String getKHMYDText(String khmyd){
		String text="";
		if(khmyd.equals("1"))text= "�ǳ�������";
		if(khmyd.equals("2"))text= "������";
		if(khmyd.equals("3"))text= "һ��";
		if(khmyd.equals("4"))text= "����";
		if(khmyd.equals("5"))text= "�ǳ�����";
		return text;
	}
	
	
	//�û��Ƿ��ڼ���ʹ��
	public static String getYHJXSYText(String yhjxsy){
		String text="";
		if(yhjxsy.equals("0"))text= "��";
		if(yhjxsy.equals("1"))text= "��";
		return text;
	}
	

}
