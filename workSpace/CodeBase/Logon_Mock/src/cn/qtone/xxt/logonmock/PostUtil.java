package cn.qtone.xxt.logonmock;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cn.qtone.xxt.base.util.Checker;
import cn.qtone.xxt.logonmock.util.KetangLoginEncoder;

public class PostUtil {
	
	/**
	 * 向目的URL提交 XML 格式的请求报文，并读取
	 * @param urlStr
	 * @param requestXml  XML格式的请求报文
	 * @return
	 */
    public String post(String urlStr, String requestXml) {
		URLConnection con = null;
		BufferedReader br = null;
		try {
			URL url = new URL(urlStr);
			con = url.openConnection();
			con.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(con
					.getOutputStream());
//			System.out.println("Exedata satart\n" + request + "\nExe end");
			out.write(new String(requestXml.getBytes("ISO-8859-1")));
			
			out.flush();
			out.close();
			br = new BufferedReader(new InputStreamReader(con
					.getInputStream()));
			String line = "";
			StringBuffer returnMsg = new StringBuffer();
			for (line = br.readLine(); line != null; line = br.readLine()) {
				returnMsg.append(line);
			}
			return returnMsg.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally{
			try {
				br.close();
			} catch (IOException e) {
			}
		}
	}
	
    /**
     * 
     * @param urlStr  目标URL
     * @param locale  字符编码，用来格式转换对应的请求参数
     * @param requestParamsFormat  请求参数字符串的格式   如: name=%s&&pwd=%s
     * @param requestParamsValue   请求参数对应的值
     * @return  返回POST后的结果
     */
    public String post(String urlStr,Locale locale,String requestParamsFormat,String... requestParamsValue) {
		URLConnection conn = null;
		BufferedReader br = null;
		try {
			URL url = new URL(urlStr);
			conn = url.openConnection();
			
			 //这里是关键，表示我们要向链接里输出内容
            conn.setDoOutput(true);
            //获得连接输出流
            OutputStreamWriter out=new OutputStreamWriter(conn.getOutputStream());
            //这里是我定义了一组账号信息，字段+数据
            
            String str="";
            if(Config.URL_FORMATE_STATE)
               str=String.format(locale,requestParamsFormat,requestParamsValue);
            else
               str = requestParamsFormat;
            
            if(Config.ISDEUG)
            	AppLoger.getRuningLogger().debug("TARGET-URL: "+urlStr+str);
            //把数据写入
            
            out.write(new String(str.getBytes("ISO-8859-1")));
            out.flush();
			out.close();
			
			br = new BufferedReader(new InputStreamReader(conn
					.getInputStream()));
			String line = "";
			StringBuffer returnMsg = new StringBuffer();
			for (line = br.readLine(); line != null; line = br.readLine()) {
				returnMsg.append(line);
			}
			return returnMsg.toString();
		} catch (MalformedURLException e) {
			return null;
		} catch (IOException e) {
			AppLoger.getSimpleErrorLogger().info("[INFO-POST:]目标地址不存在! POST URL:"+urlStr+"    "+e.getMessage());;
			return null;
		}finally{
			try {
				if(br!=null)
				  br.close();
			} catch (IOException e) {
			}
		}
	}
    
	/**
	 * 
	 * @param returnContent
	 * @return
	 */
	public String getDealStatuCodeFromXml(String returnContent,String nodeName){
		if(returnContent==null||"".equals(returnContent))
			return "no-response";
		int sop = returnContent.indexOf("<"+nodeName+">");
		int eop = returnContent.indexOf("</"+nodeName+">");
		try{
		    return returnContent.substring(sop+("<"+nodeName+">").length(),eop);
		}catch(Exception e){
			return "exception";
		}
	}
	
	public String readTemplateFile(String file) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				sb.append(line + "\n");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public String matchAndReplaceKeyInfos(String keyField,
			String replaceContent, String templateString) {
		return templateString.replace(keyField, replaceContent);
	}

	
	public boolean findKeyWord(String content,String keyword){
		if(!Checker.isNull(content))
		    return content.indexOf(keyword)>=0?true:false;
		else
			return false;
	}
	
	public boolean requestError(String content){
		if(content==null)
			return true;
		if(content.indexOf("400")>0)
			return true;
		if(content.indexOf("405")>0)
			return true;
		if(content.indexOf("错误")>0)
			return true;
		if(content.indexOf("出错")>0)
			return true;
		if(content.indexOf("失败")>0)
			return true;
		return false;
	}
	
	
	public static void main(String[] args) {
	    
		String userId ="test";
		String requestIp ="127.0.0.1";
		String userAgent ="Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)";
		PostUtil util = new PostUtil();
		String info = KetangLoginEncoder.encodeKetangLoginParam("QuanTong","2", userId, requestIp, userAgent);
	
//		String returnMsg = util.post("http://www.baidu.com/baidu", Locale.ENGLISH,"cl=3&tn=baidutop10&wd=%B6%B9%B0%EA", "");
		String returnMsg =util.post("http://ketang.ldcstudy.com/login/synlogin.form",Locale.CHINA, "info=%s", info);
		System.out.println(returnMsg);
		
		returnMsg =util.post("http://family.chinaeduonline.cn/login/synlogin.form",Locale.CHINA, "info=%s", info);
		System.out.println(returnMsg);
	
		returnMsg =util.post("http://wawa.chinaeduonline.cn/login/synlogin.form",Locale.CHINA, "info=%s", info);
		System.out.println(returnMsg);
		
	}
	
}

