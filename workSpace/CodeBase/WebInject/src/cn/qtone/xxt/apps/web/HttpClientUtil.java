package cn.qtone.xxt.apps.web;

import java.io.IOException;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;


/**
 * HttpClient
 * @author Ethan.Lam  2011-2-15
 *
 */
public class HttpClientUtil {

	
	/**
	 * �½�һ�� HttpClient ʵ��
	 * @param logonSite
	 * @param port
	 * @return
	 */
	public static HttpClient newHttpClient(String logonSite,int port){
		HttpClient client = new HttpClient();  
        client.getHostConfiguration().setHost(logonSite, port);  
        return client;
	}
	
	
	
	/**
	 * ����һ�� POST 
	 * @param requestUrl  ����ĵ�ַ
	 * @param userAgent   
	 * @param hrefUrl     ��ת��ַ
	 * @param NameValuePairs  �ύ����
	 * @return
	 */
	public static PostMethod newPostMethod(String requestUrl,String userAgent,String hrefUrl,String[][] NameValuePairs){
		 PostMethod post = new PostMethod(requestUrl);  
	        NameValuePair ie = new NameValuePair("User-Agent",userAgent);     
	        NameValuePair url = new NameValuePair("url", hrefUrl!=null?hrefUrl:"");  
	        NameValuePair paramValueSet = null;
	        if(NameValuePairs!=null){
	        	  NameValuePair[] paramsSet = new NameValuePair[NameValuePairs.length+2];
	        	  paramsSet[0] =  ie;
	        	  paramsSet[1] =  url;
	        	  int index = 2;
	        	  for(String[] param : NameValuePairs){
                	  paramsSet[index++] =  new NameValuePair(param[0],param[1]);
                  }
	        	  post.setRequestBody(paramsSet); 
	        }else{
	        	  post.setRequestBody(new NameValuePair[]{ie,url}); 
	        }
	        return post;
	}
	
	
	/**
	 * ���沢���� cookies
	 * @param client
	 */
	public static Cookie[] addCookies(HttpClient client){
		  Cookie[] cookies = client.getState().getCookies();  
	      if(cookies!=null)
		    client.getState().addCookies(cookies);  
	      return cookies;
	}
	
	
	/**
	 * ����һ�� GetMethod ʵ��
	 * @param requestUrl
	 * @param cookies
	 * @return
	 */
	public static GetMethod newGetMethod(String requestUrl,Cookie[] cookies){
	       GetMethod get = new GetMethod(requestUrl);  
	       get.setRequestHeader("Cookie", cookies.toString());  
	       return get;
	}
	
	
	
	public static void main(String...srt) throws HttpException, IOException{
		
	    String LOGON_SITE = "http://admin.zj.monternet.com:8080/sp/index.jsp";  
		int LOGON_PORT = 8080;
		String loginReq = "http://admin.zj.monternet.com:8080/sp/SPLogin";
		String UserAgent = "Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)";
		String hrefUrl ="/sp/index.jsp";
		
		HttpClient client = newHttpClient(LOGON_SITE,LOGON_PORT);
		PostMethod post = newPostMethod(loginReq,UserAgent,hrefUrl,new String[][]{{"selectAccount","SPPREREG"},{"USER","qtone"},{"PASSWORD","qtone2010"}});
		
		client.executeMethod(post);  
		Cookie[] cookies  = addCookies(client);
		post.releaseConnection();
		
		
		String dataUrl="http://admin.zj.monternet.com:8080/sp/indict/queryIndictICD.jsp?subsId=&userName=&status=1&queryTime=0&fromDate=2010-02-01&toDate=2011-02-15&pageNum=1&currentPageNo=1&pageSize=100&navigatePage_toPageSize=100&navigatePage_toPageNum=1";
		GetMethod get = newGetMethod(dataUrl,cookies);
		client.executeMethod(get);
		String responseString = get.getResponseBodyAsString();  
	    System.out.println(responseString);  
		get.releaseConnection();
		
	}
	
	
}
