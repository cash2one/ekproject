package cn.qtone.xxt.apps.web.misc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ����
 * @author Ethan.Lam  2011-2-16
 *
 */
public class YwComplaintUtil {
	
	public static String HANDLER_ID = "1";  //�����˵�ID
	public static String CREATE_ID = SysCfg.COMPLAINT_CREATE_USERID;  //�����˵�ID

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
	
	public static String valueToString(String value){
		return value!=null?value:"";
	}
	
	
	/**
	 * 
	 * ���¼��� �����������
	 * @param item
	 * @return
	 */
	static String reSetDeadline(ComplaintItem item){
		//����ʱ��Ҫ����
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			long difTime = df.parse(item.getDeadline()).getTime() - df.parse(item.getCreateTime()).getTime();
			String newTime = df.format(new Date(df.parse(item.getCreateTime()).getTime()+(difTime/2)));
			df = null;
			return newTime;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return item.getDeadline(); 
	}
	
}
