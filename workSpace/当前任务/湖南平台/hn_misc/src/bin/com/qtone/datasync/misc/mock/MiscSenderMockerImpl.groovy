import com.qtone.datasync.misc.mock.IMiscSenderMocker
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import groovy.text.*

public class MiscSenderMockerImpl implements IMiscSenderMocker{
	//XXT platform's url
	private def url = new URL('http://211.142.221.225:8080/services/xxt?wsdl')

	private def srcFile = new File('data.txt');
	private def retFile = new File('sync_return.log');
	private def tplFile = new File('order_tpl.xml')

	public String sendMsg(String msgFile,String retFile){
		srcFile = new File(FileUtil.getFilePath(msgFile))	
		retFile = new File(FileUtil.getFilePath(retFile))

		retFile.withWriter{writer->
			srcFile.eachLine(){line->
				urlconn = (HttpURLConnection)url.openConnection()
				urlconn.setRequestProperty("content-type", "text/plain")
				urlconn.setRequestMethod("POST")
				urlconn.setDoInput(true)
				urlconn.setDoOutput(true)

				OutputStream out = urlconn.getOutputStream();
				out.write(line.getBytes("UTF8"));
				out.flush();
				out.close();

				BufferedReader rd = new BufferedReader(new InputStreamReader(urlconn.getInputStream()))
				StringBuffer sb = new StringBuffer()
				int ch;
				while ((ch = rd.read()) > -1){
					sb.append((char)ch);
				}
				writer.println(sb.toString())
				writer.flush()

				rd.close();
				urlconn.disconnect();
			}
		}
	}

	public String parseAndSend(String msgFile,String tplFile,String retFile){
		srcFile = new File(FileUtil.getFilePath(msgFile))	
		retFile = new File(FileUtil.getFilePath(retFile))
		tplFile = new File(FileUtil.getFilePath(tplFile))

		def tplEngine = new SimpleTemplateEngine()
		def tpl = tplEngine.createTemplate(tplFile.getText())

		retFile.withWriter{writer->
			srcFile.eachLine(){line->
				//resolve the message template
				def args = line.split('\\s')
				def binding = ['transaction_id':"${new Date().getTime()}",'phone':"${args[0]}",'salemodalid':"${args[1]}"]
				def MSG = tpl.make(binding).toString()
				writer.println(MSG)

				//send message to xxt 
				urlconn = (HttpURLConnection)url.openConnection()   
				urlconn.setRequestProperty("content-type", "text/plain")
				urlconn.setRequestMethod("POST")
				urlconn.setDoInput(true)
				urlconn.setDoOutput(true)

				OutputStream out = urlconn.getOutputStream();
				out.write(MSG.getBytes("UTF8"));
				out.flush();
				out.close();

				//record response from xxt
				BufferedReader rd = new BufferedReader(new InputStreamReader(urlconn.getInputStream()))
				StringBuffer sb = new StringBuffer()
				int ch;
				while ((ch = rd.read()) > -1){
					sb.append((char)ch);
				}
				writer.println(sb.toString())
				writer.flush()
				rd.close();
				urlconn.disconnect();
			}
		}
	}
}
