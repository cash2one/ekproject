import com.qtone.datasync.xxt.dp.IMiscToXxtFunctionTestHelper

public class MiscToXxtFunctionTestHelperImpl implements IMiscToXxtFunctionTestHelper{
	def PREPARE_DB_FILE = ''//数据库初始数据
	def MESSAGE_TPL = ''//报文模板
	def DATA_REQ = '' //用于初始模板中的数据
	def DATA_RSP = '' //返回报文

	/**
	 * 准备DB，包括清理和新建数据记录
	 */
	public void prepareDb(){
		clearDb();
		addDataToDb()
	}

	/**
	 * 向模拟程序发起同步请求
	 */
	public void doSync(){
		dataTpl = new SimpleTemplateEngine(new File('MESSAGE_TPL').getText())
		srcFile = new File(DATA_REQ)
		retFile = new File(DATA_RSP);

		cnt = 0 

		url = new URL('http://211.142.221.225:8080/services/xxt?wsdl')
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


	/**
	 * 检查发起请求的报文数目与接收到的记录数目
	 * 如果相同就返回true
	 * 
	 * @return 
	 */
	public boolean checkRequestCount(){
		
	}

	/**
	 * 检查对不存在于平台上的记录的处理结果
	 * 
	 * @return
	 */
	public boolean checkNonexistsUserDealResult(){
		
	}

	/**
	 * 检查对存在于平台的用户的处理结果
	 * 包括了对定购收费业务和免费业务的分别处理
	 * @return
	 */
	public boolean checkExistsUserDealRequest(){
		
	}
	
	/*
	 *Delete relate data from db
	 */
	private void clearDb(){
		Sql sql = new DbUtils().newInstance()
		try {
			sql.execute("truncate table cs_tranpackage_customer;");
			sql.execute("truncate table zz_tranpackage_customer;");
			sql.execute("truncate table qtone_errors;");
			sql.execute("truncate table misc_order_relation;");
			sql.execute("truncate table misc_order_log;");
			sql.execute("truncate table cs_transaction_log;");
		} finally {
			sql.close();
		}
	}	

	private void addDataToDb(){
		Sql sql = new DbUtils().newInstance()
		
		def dataFile = new File('');
		dataFile.eachLine{line->
			def arr = line.split(',')
			sql.execute("insert into ${arr[2]}_xj_family(id,phone) values('${arr[0]}',${arr[1]})")
			sql.execute("insert into family_phone(phone,family_id,abb) values('${arr[0]}',${arr[1]},${2})")
			sql.execute("insert into ${arr[2]}_tranpackage_customer(family_id,phone,boss_salemodalid,xxt_salemodalid,del) values(${arr[1]},'${arr[0]}','${arr[3]}','${arr[3]}',0)")
		}

		sql.close()
	}
}
