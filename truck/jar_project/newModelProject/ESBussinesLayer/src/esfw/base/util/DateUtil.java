package esfw.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

 

/**
 * 
 * 日期转换函数
 * @author Ethan.Lam  2011-7-24
 *
 */
public class DateUtil {

	public static final String Formater_yyyy_MM_dd = "yyyy-MM-dd";
	public static final String Formater_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public static final String Formater_yyyyMMddHHmmss = "yyyyMMddHHmmss";
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str){
		  str = str!=null?str.trim():str;
          return  str==null||"".equals(str)?true:false;		
	}
	
	
	/**
	 * 
     * 获取当前的时间戳
     * 
     * @return
     */
    public static String getCurrentTimestamp() {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = new Date();
		return formater.format(today);
    }
	
    
	/**
	 * 
     * Date 对象转换成对应格式的 字符串
     * @param date
     * @param format  如："yyyy-MM-dd HH:mm:ss"，默认："yyyy-MM-dd HH:mm:ss" 
     * @return   
     * 
     */
    public static String dateToString(Date date,String format) {
    	if(date==null)
    		return "";
		SimpleDateFormat formater = new SimpleDateFormat(isNull(format)?"yyyy-MM-dd HH:mm:ss":format.trim());
		return formater.format(date);
    }
    
    
    /**
	 * 
     * Date 对象转换成对应格式的 字符串
     * @param date
     * 默认："yyyy-MM-dd HH:mm:ss" 
     * @return   
     * 
     */
    public static String dateToString(Date date) {
    	if(date==null)
    		return "";
		return dateToString(date,null);
    }
    
    /**
     * 
     * Date 对象转换成对应格式的 字符串
     * @param dateSource
     * @param format  如："yyyy-MM-dd HH:mm:ss"，默认："yyyy-MM-dd HH:mm:ss"  
     * @return
     * 
     */
    public static Date timeStrToDate(String dateSource,String format) {
    	if(isNull(dateSource))
    		return null;
		SimpleDateFormat formater = new SimpleDateFormat(isNull(format)?"yyyy-MM-dd HH:mm:ss":format.trim());
		try {
			return formater.parse(dateSource);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    /**
     * 
     * Date 对象转换成对应格式的 字符串
     * @param dateSource
     * 默认："yyyy-MM-dd HH:mm:ss"  
     * @return
     * 
     */
    public static Date timeStrToDate(String dateSource) {
    	if(isNull(dateSource))
    		return null;
		return timeStrToDate(dateSource,null) ;
    }
    
    /**
	 * 得到系统日期
	 * 
	 * @return  
	 */
	public static String getDate() {
		Calendar calendar = Calendar.getInstance();
		String year = calendar.get(Calendar.YEAR) + "";
		String month = calendar.get(Calendar.MONTH) + 1 + "";
		String day=calendar.get(Calendar.DAY_OF_MONTH)+"";
		if (month.length() == 1)
			month = "0" + month;
		
		return year+"-"+month+"-"+day;
	}
	
	/**
	 * 得到系统日期,xx月xx日 xx xx:xx
	 * 
	 * @return  
	 */
	public static String getWapDate() {
		Calendar calendar = Calendar.getInstance();
		String month = calendar.get(Calendar.MONTH) + 1 + "";
		String day = calendar.get(Calendar.DAY_OF_MONTH)+"";
		String hour = calendar.get(Calendar.HOUR_OF_DAY)+"";
		String minute = calendar.get(Calendar.MINUTE)+"";
		
		if (month.length() == 1)
			month = "0" + month;
		
		return month+"月"+day+"日 "+hour+":"+minute;
	}
	
	
	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数 
	 * @param nowdate
	 * @param delay   
	 * @return   
	 */
	public static String getNextDay(String nowdate, int delay) { 
	  try{ 
	       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
	       String mdate = ""; 
	       Date d = timeStrToDate(nowdate); 
	       long myTime = (d.getTime() / 1000) +  delay * 24 * 60 * 60; 
	       d.setTime(myTime * 1000); 
	       mdate = format.format(d); 
	       return mdate; 
	   }catch(Exception e){ 
	        return ""; 
	   } 
	} 
	
	
	/**
	 * 
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数 
	 * @param nowdate
	 * @param delay  小于 0，过去多小天，大于0 未来多小天
	 * @param dateFormat
	 * @return  
	 */
	public static String getNextDay(String nowdate,int delay,String dateFormat) { 
	  try{ 
	       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
	       String mdate = ""; 
	       Date d = timeStrToDate(nowdate,dateFormat); 
	       long myTime = (d.getTime() / 1000) + delay* 24 * 60 * 60; 
	       d.setTime(myTime * 1000); 
	       mdate = format.format(d); 
	       return mdate; 
	   }catch(Exception e){ 
	        return ""; 
	   } 
	} 
	
	
	/**
	 * 返回今天的时间段
	 * @return   yyyy-MM-dd HH:mm:ss
	 */
	public static String[] getTodayPeriods(){
		String today = dateToString(new Date(),Formater_yyyy_MM_dd);
		return new String[]{today+" 00:00:00",today+" 23:59:59"};	
	}
	
	/**
	 * 返回昨天的时间段
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String[] getYesterdayPeriods(){
		String today = dateToString(new Date(),Formater_yyyy_MM_dd);
		String beforeDay = getNextDay(today,-1,Formater_yyyy_MM_dd);
		return new String[]{beforeDay+" 00:00:00",beforeDay+" 23:59:59"};
	}
	
	/**
	 * 返回一个星期(7天前)的时间段
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String[] getWeekPeriods(){
		String today = dateToString(new Date(),Formater_yyyy_MM_dd);
		String beforeDay = getNextDay(today,-7,Formater_yyyy_MM_dd);
		return new String[]{beforeDay+" 00:00:00",today+" 23:59:59"};	
	}
    	
	
	/**
	 * 返回的一个月时间段
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String[] getMonthDayPeriods(int difMonths){
		difMonths=difMonths==0?1:difMonths;
		String today = dateToString(new Date(),Formater_yyyy_MM_dd);
		String beforeDay = getNextDay(today,-7*4*difMonths,Formater_yyyy_MM_dd);
		return new String[]{beforeDay+" 00:00:00",today+" 23:59:59"};	
	}
    	
	
	
	/**
	 * 得到系统当前的时间
	 * @return
	 */
	public static String getSysDateTime(){
		return dateToString(new Date(),DateUtil.Formater_yyyy_MM_dd_HH_mm_ss);
	}
	
	/**
	 * 将calendar的星期几转化为我们习惯的（1-星期一，7-星期日）
	 * @param dayOfWeek
	 * @return
	 */
	  public static long toChineseWeek(long dayOfWeek){
		  return dayOfWeek-1==0?7:dayOfWeek-1;
	  }
	
	  
	  /**
	   * 验证字符串是否是合法的日期
	   * @param dateStr
	   * @param format
	   * @return
	   */
	  public static boolean isValidDate(String dateStr,String format){
	       try{
	    	   SimpleDateFormat dateFormat = new SimpleDateFormat(format);
	           dateFormat.setLenient(false);
	           dateFormat.parse(dateStr);
	           return true;
	        }catch (Exception e){
	            return false;
	        }
	    }
	  
	/**
	 * 返回当月的起始 日期  2011-01-01 : 2011-01-31
	 * @return
	 */
	public static String[] getMonthPeriods(int difMonth){
		Date today = new Date();
		int year = today.getYear()+1900;
		int month = today.getMonth()+difMonth + 1;
		Calendar c = new GregorianCalendar(today.getYear(),today.getMonth()+difMonth,today.getDate());
//		System.out.println(year+" "+month+"  "+c.getActualMaximum(Calendar.DATE));
		return new String[]{year+"-"+month+"-01",year+"-"+month+"-"+c.getActualMaximum(Calendar.DATE)};
	}  
	  
	  
	public static void main(String...args){
		getMonthPeriods(-1);
	}
	
	
	
	
}
