package cn.qtone.xxt.apps.web.misc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 工具
 * @author Ethan.Lam  2011-2-16
 *
 */
public class YwComplaintUtil {
	
	public static String HANDLER_ID = "1";  //处理人的ID
	public static String CREATE_ID = SysCfg.COMPLAINT_CREATE_USERID;  //创建人的ID

	//客户类型 （1全球通金卡、2全球通银卡、3动感地带、4大众卡）、
	public static String getCustomerTypeText(String customer_type){
		String text="";
		if(customer_type.equals("1"))text= "VIP客户";
		if(customer_type.equals("2"))text= "全球通";
		if(customer_type.equals("3"))text= "动感地带";
		if(customer_type.equals("4"))text= "神州行本地";
		return text;
	}
	
	//客户类型 （1全球通金卡、2全球通银卡、3动感地带、4大众卡）、代码
	public static String getCustomerTypeCode(String customer_type){
		String text="";
		if(customer_type.equals("VIP客户"))text= "1";
		if(customer_type.equals("全球通"))text= "2";
		if(customer_type.equals("动感地带"))text= "3";
		if(customer_type.equals("神州行本地"))text= "4";
		return text;
	}
	
	
	//投诉等级（2紧急、1急、0一般）、
	public static String getComplaintLevelText(String complaint_level){
		String text="";
		if(complaint_level.equals("0"))text= "一般";
		if(complaint_level.equals("1"))text= "急";
		if(complaint_level.equals("2"))text= "紧急";
		return text;
	}
	
	
	//投诉等级（2紧急、1急、0一般）、
	public static String getComplaintLevelCode(String complaint_level){
		String text="";
		if(complaint_level.equals("一般"))text= "0";
		if(complaint_level.equals("急"))text= "1";
		if(complaint_level.equals("紧急"))text= "2";
		if(complaint_level.equals("超时"))text= "2";
		return text;
	}
	
	
	//处理进度（0待处理 1处理中 2处理完）
	public static String getHandleStatusText(String handle_status){
		String text="";
		if(handle_status.equals("0"))text= "待处理";
		if(handle_status.equals("1"))text= "处理中";
		if(handle_status.equals("2"))text= "处理完";
		return text;
	}
	
	//客户满意度
	public static String getKHMYDText(String khmyd){
		String text="";
		if(khmyd.equals("1"))text= "非常不满意";
		if(khmyd.equals("2"))text= "不满意";
		if(khmyd.equals("3"))text= "一般";
		if(khmyd.equals("4"))text= "满意";
		if(khmyd.equals("5"))text= "非常满意";
		return text;
	}
	
	
	//用户是否还在继续使用
	public static String getYHJXSYText(String yhjxsy){
		String text="";
		if(yhjxsy.equals("0"))text= "否";
		if(yhjxsy.equals("1"))text= "是";
		return text;
	}
	
	public static String valueToString(String value){
		return value!=null?value:"";
	}
	
	
	/**
	 * 
	 * 重新计算 处理截至日期
	 * @param item
	 * @return
	 */
	static String reSetDeadline(ComplaintItem item){
		//处理时间要减半
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
