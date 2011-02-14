package cn.qtone.xxt.apps.web;

import org.apache.commons.httpclient.Cookie;  
import org.apache.commons.httpclient.HttpClient;  
import org.apache.commons.httpclient.NameValuePair;  
import org.apache.commons.httpclient.methods.GetMethod;  
import org.apache.commons.httpclient.methods.PostMethod;

public class LoginKaixin {  
    private static final String LOGON_SITE = "http://www.kaixin001.com";  
    private static final int LOGON_PORT = 80;  
    public static void main(String[] args)throws Exception {  
        HttpClient client = new HttpClient();  
        client.getHostConfiguration().setHost(LOGON_SITE, LOGON_PORT);  
          
        //登录页面  
        PostMethod post = new PostMethod("http://www.kaixin001.com/login/login.php");  
        NameValuePair ie = new NameValuePair("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");     
        NameValuePair url = new NameValuePair("url", "/home/");  
        NameValuePair username = new NameValuePair("email","xxx@163.com");  
        NameValuePair password = new NameValuePair("password", "xxxxxx");  
    post.setRequestBody(new NameValuePair[] { ie,url,username, password});  
        client.executeMethod(post);  
        System.out.println("******************************登录******************************");  
        Cookie[] cookies = client.getState().getCookies();  
        client.getState().addCookies(cookies);  
        post.releaseConnection();  
        System.out.println("******************************页面转向******************************");  
        String newUrl="http://www.kaixin001.com/home/";  
        System.out.println("==========Cookies============");  
        int i=0;  
        for(Cookie c:cookies){  
            System.out.println(++i+":   "+c);  
        }  
        client.getState().addCookies(cookies);  
        post.releaseConnection();  
        GetMethod get = new GetMethod(newUrl);  
        get.setRequestHeader("Cookie", cookies.toString());  
        client.executeMethod(get);  
        String responseString = get.getResponseBodyAsString();  
        //登录后首页的内容  
        System.out.println(responseString);  
        get.releaseConnection();  
        System.out.println("******************************组件功能******************************");  
        //"http://www.kaixin001.com/!slave/index.php", "朋友买卖"  
        //"http://www.kaixin001.com/!parking/index.php", "争车位"  
        //"http://www.kaixin001.com/!house/index.php?_lgmode=pri", "买房子"   
        //http://www.kaixin001.com/!house/index.php?_lgmode=pri&t=49  
        //"http://www.kaixin001.com/!house/garden/index.php","花园"  
        //(1)进入朋友买卖****************  
        System.out.println("******************************(1)进入朋友买卖******************************");  
        String slave="http://www.kaixin001.com/!slave/index.php";  
        get = new GetMethod(slave);  
        get.setRequestHeader("Cookie", cookies.toString());  
        client.executeMethod(get);  
        responseString = get.getResponseBodyAsString();  
        System.out.println(responseString);  
        get.releaseConnection();  
        //(2)进入争车位****************  
        System.out.println("******************************(2)进入争车位******************************");  
        String parking="http://www.kaixin001.com/!parking/index.php";  
        get = new GetMethod(parking);  
        get.setRequestHeader("Cookie", cookies.toString());  
        client.executeMethod(get);  
        responseString = get.getResponseBodyAsString();  
        System.out.println(responseString);  
        get.releaseConnection();  
        //(3)进入买房子****************  
        System.out.println("******************************(3)进入买房子*******************************");  
        String house="http://www.kaixin001.com/!house/index.php?_lgmode=pri&t=49";  
        get = new GetMethod(house);  
        get.setRequestHeader("Cookie", cookies.toString());  
        client.executeMethod(get);  
        responseString = get.getResponseBodyAsString();  
        System.out.println(responseString);  
        get.releaseConnection();  
        //(4)进入花园****************  
        System.out.println("******************************(4)进入花园*******************************");  
        String garden="http://www.kaixin001.com/!house/garden/index.php";  
        get = new GetMethod(garden);  
        get.setRequestHeader("Cookie", cookies.toString());  
        client.executeMethod(get);  
        responseString = get.getResponseBodyAsString();  
        System.out.println(responseString);  
        get.releaseConnection();  
          
          
    }  
  
}  
