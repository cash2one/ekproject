package cn.qtone.xxt.apps.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class LoginFormDemo {
	public static void DemoLoginTwo() throws Exception {

	        DefaultHttpClient httpclient = new DefaultHttpClient();

	        HttpGet httpget = new HttpGet("http://xxxx.com/xx.htm");

	        HttpResponse response = httpclient.execute(httpget);
	        HttpEntity entity = response.getEntity();

	        System.out.println("Login form get: " + response.getStatusLine());
	        if (entity != null) {
	            entity.consumeContent();
	        }
	        System.out.println("Initial set of cookies:");
	        List<Cookie> cookies = httpclient.getCookieStore().getCookies();
	        if (cookies.isEmpty()) {
	            System.out.println("None");
	        } else {
	            for (int i = 0; i < cookies.size(); i++) {
	                System.out.println("- " + cookies.get(i).toString());
	            }
	        }

	        // action=xxx&Password=xxxx
	        HttpPost httpost = new HttpPost(
	                "http://localhost/sss");


	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add(new BasicNameValuePair("LoginId", "xxxx"));
	        nvps.add(new BasicNameValuePair("Password", "xxxxx"));
	        nvps.add(new BasicNameValuePair("action", "xxxx"));
	        nvps.add(new BasicNameValuePair("eventSubmit_doPost", "xxxx"));

	        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

	        response = httpclient.execute(httpost);
	        entity = response.getEntity();

	        System.out.println("Login form get: " + response.getStatusLine());
	        if (entity != null) {
	            entity.consumeContent();
	        }

	        
	        System.out.println("Time now: " + new Date().toString());
	        System.out.println("-------------------1---------------------");
	        httpget = new HttpGet("http://localhost/test");

	        System.out.println("executing request " + httpget.getURI());

	        // Create a response handler
	        ResponseHandler<String> responseHandler = new BasicResponseHandler();
	        String responseBody = httpclient.execute(httpget, responseHandler);
	        // System.out.println(responseBody);

	        System.out.println("Time now: " + new Date().toString());
	        System.out.println("-------------------2---------------------");
//	        // 这里是为了获取页面上的hidden，用来提交的时候继续使用
//	        hidden= ParseTest.testTest(responseBody);
//
//	        System.out.println(csrfToken);

	        System.out.println("Time now: " + new Date().toString());
	        System.out.println("---------------3-post offer begin---------------");
	        // post offer

	        httpost = new HttpPost("http://local/test");

	        String keywords = "001autop" + randomString(4);
	        nvps = new ArrayList<NameValuePair>();
	        // post时，提交的form信息

	        nvps.add(new BasicNameValuePair("action", "xxxxx"));
	        nvps.add(new BasicNameValuePair("event_submit_do_process", "submit"));nvps.add(new BasicNameValuePair("topCatFormKey", "58"));
	        nvps.add(new BasicNameValuePair("names1", "xxxx"));

	        nvps.add(new BasicNameValuePair("names2", "9.00"));


	        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

	        responseHandler = new BasicResponseHandler();
	        responseBody = httpclient.execute(httpost, responseHandler);
	        // System.out.println(responseBody);
	        writeFile("E:\\result.html", responseBody);

	        response = httpclient.execute(httpost);
	        entity = response.getEntity();

	        System.out.println("Login form get: " + response.getStatusLine());
	        if (entity != null) {
	            entity.consumeContent();
	        }
	        System.out.println("Time now: " + new Date().toString());
	        System.out.println("---------------3-post offer end----------------");
	    }

	/**
	 * 产生随机字符串
	 * */
	private static Random randGen = null;
	private static char[] numbersAndLetters = null;
	private static Object initLock = new Object();

	public static final String randomString(int length) {

		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			synchronized (initLock) {
				if (randGen == null) {
					randGen = new Random();
					numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
							+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ")
							.toCharArray();
					// numbersAndLetters =
					// ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
				}
			}
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
			// randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
		}
		return new String(randBuffer);
	}

	public static void writeFile(String path, String content) {
		String s = new String();
		String s1 = new String();
		try {
			File f = new File(path);
			if (f.exists()) {
				System.out.println("文件存在");
				f.delete();
			} else {

			}
			System.out.println("文件不存在，正在创建...");
			if (f.createNewFile()) {
				System.out.println("文件创建成功！");
			} else {
				System.out.println("文件创建失败！");
			}
			BufferedReader input = new BufferedReader(new FileReader(f));

			while ((s = input.readLine()) != null) {
				s1 += s + "\n";
			}
			// System.out.println("文件内容：" + s1);
			input.close();
			s1 += content;

			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			output.write(s1);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
