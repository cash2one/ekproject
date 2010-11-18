import com.qtone.datasync.xxt.dp.IMiscToXxtFunctionTestHelper

public class MiscToXxtFunctionTestHelperImpl implements IMiscToXxtFunctionTestHelper{
	def PREPARE_DB_FILE = ''//���ݿ��ʼ����
	def MESSAGE_TPL = ''//����ģ��
	def DATA_REQ = '' //���ڳ�ʼģ���е�����
	def DATA_RSP = '' //���ر���

	/**
	 * ׼��DB������������½����ݼ�¼
	 */
	public void prepareDb(){
		clearDb();
		addDataToDb()
	}

	/**
	 * ��ģ�������ͬ������
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
	 * ��鷢������ı�����Ŀ����յ��ļ�¼��Ŀ
	 * �����ͬ�ͷ���true
	 * 
	 * @return 
	 */
	public boolean checkRequestCount(){
		
	}

	/**
	 * ���Բ�������ƽ̨�ϵļ�¼�Ĵ�����
	 * 
	 * @return
	 */
	public boolean checkNonexistsUserDealResult(){
		
	}

	/**
	 * ���Դ�����ƽ̨���û��Ĵ�����
	 * �����˶Զ����շ�ҵ������ҵ��ķֱ���
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
