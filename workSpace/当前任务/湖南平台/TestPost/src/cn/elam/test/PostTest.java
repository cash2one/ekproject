package cn.elam.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class PostTest {
	void testPost(String urlStr, String xmlfile) {
		try {
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(con
					.getOutputStream());
			String request = xmlfile;
			System.out.println("Exedata satart\n" + request + "\nExe end");
			out.write(new String(request.getBytes("ISO-8859-1")));

			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(con
					.getInputStream()));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				System.out.println(line);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 读取模版文件
	 * @param file
	 * @return
	 */
	private String readTemplateFile(String file) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			// 这里的raw.file内容就是 上面那个xml片段，就是读取内容，将请求的xml保存成字符串 进行post发送
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				sb.append(line + "\n");
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

	
	private String matchAndReplaceKeyInfos(String keyField,String replaceContent,String templateString){
		return templateString.replace(keyField, replaceContent);
	}
	
	
	
	
	public static void main(String[] args) {
		String url = "http://localhost:8080/test/services/xxt";
		String file = "D:/Workspaces/TestPost/src/cn/elam/test/order_msg.xml";
		new PostTest().testPost(url, file);
	}
}