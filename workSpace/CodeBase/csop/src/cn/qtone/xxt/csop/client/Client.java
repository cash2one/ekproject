package cn.qtone.xxt.csop.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class Client{

	static int PORT = 8080;
	static String SERVEICE_NAME ="Test";
	
	public static void main(String...srt){
		
		try {
//			test_2();
			File file = new File(Client.class.getResource("test.xml").getPath());
			test_1(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
   public static void test_2() throws Exception{
	  
	   String reqUrl = "http://localhost:"+PORT+"/axis/services/"+SERVEICE_NAME;
	   Service service = new Service();
	   Call call =  (Call)service.createCall();
	   call.setTargetEndpointAddress(new URL(reqUrl));
	   
	   call.setOperationName(new QName(reqUrl,"test"));
	   String result  =(String)call.invoke(new Object[]{" Robert "});
	   System.out.println(result);
	   
	   
	   
   }	
	
	
	public static void test_1(File file){
		HttpClient httpClient = new HttpClient();
		BufferedReader in = null;
		PrintWriter pw = null;
		StringBuilder msg = new StringBuilder();
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			while ((line = in.readLine()) != null){
				msg.append(line);
			}
//			System.out.println(msg.toString());
			String reqUrl = "http://localhost:"+PORT+"/axis/services/"+SERVEICE_NAME;
			PostMethod postMethod = new PostMethod(reqUrl);
			try {
				RequestEntity entity = new StringRequestEntity(msg.toString(), "application/soap+xml", "utf-8");
				postMethod.setRequestHeader("SOAPAction", "http://localhost:"+PORT+"/services/"+SERVEICE_NAME);
				postMethod.setRequestEntity(entity);  
				httpClient.executeMethod(postMethod);
				System.out.println(postMethod.getResponseBodyAsString());
			}  catch (HttpException e) {
				e.printStackTrace();
				throw new RuntimeException("发送HTTP请求异常");
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("发送HTTP请求异常");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (pw != null)
				pw.close();
		}
		
	}
	
	
}
