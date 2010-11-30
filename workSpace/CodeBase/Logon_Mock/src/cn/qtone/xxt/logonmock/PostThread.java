package cn.qtone.xxt.logonmock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cn.qtone.xxt.base.dao.comom.BaseDao;
import cn.qtone.xxt.base.util.Checker;
import cn.qtone.xxt.logonmock.util.KetangLoginEncoder;

class PostThread extends Thread {
	
	int start,end;
	long sleepTime = 100;
	PostUtil util;
	int threadName;
	String url ="";
	long allSpendTime;
	long startTime;
	int totalTime =0;
	int postTimes = 0;
	int exceptionTimes = 0;
	int remarkStart=0;
	
	String sql = null;
	Map<String,String> exceptions = new HashMap<String,String>();
	
	
	public PostThread(int name,int start,int end,String tempFile,String url,long sleepTime){
		this.start = start;
		this.end = end;
		this.util = new PostUtil();
		this.threadName = name;
		totalTime = end-start;
		this.url = url;
		remarkStart = start;
		this.sleepTime = sleepTime;
		
		sql ="select * from "+Config.TEMP_TABLE+" where id>="+start+" and id <"+end;
	}
	
	
	public void run(){
		String temp = "";
		String returnMsg = "";
		System.out.println("INFO:Start run [ Post-TesT-"+threadName+" at "+new Date().toLocaleString()+" ] ");
		startTime = System.currentTimeMillis();	
		int allRecords = this.count(sql);
		
		if(allRecords>Config.RECORDS_BATCH_LOADNUM){
			//分批次执行
			int batchNum =(allRecords%Config.RECORDS_BATCH_LOADNUM>0)?(allRecords/Config.RECORDS_BATCH_LOADNUM+1):(allRecords/Config.RECORDS_BATCH_LOADNUM); 
			for(int batchSqeunce = 1;batchSqeunce<=batchNum;batchSqeunce++){
				exceptions.clear();	
				this.loadAndProcesNeedLoginStus(batchSqeunce);
				//记录登录失败的用户
				recordLoginException();
			}
		}else{
			exceptions.clear();	
			this.loadAndProcesNeedLoginStus(1);
			//记录登录失败的用户
			recordLoginException();
		}
		
		this.allSpendTime = System.currentTimeMillis()-startTime;
		System.out.println("INFO:End run [ Post-TesT-"+threadName+" at "+new Date().toLocaleString()+" ] ");
		printPostResult();		
	}    

    /**
     * 加载需要进行模拟登录的学生
     * 表  synlesson_logon_temp
     * id | stu_sequence | user_id | pwd | syn_tran
     * 
     */
    public void loadAndProcesNeedLoginStus(int batchSqeunce){
    	BaseDao dao = null;
		ResultSet rs = null;
		String syn_tran="",userId="",password="";
		try{
			dao = new BaseDao(Config.POOL_NAME);
			rs = dao.queryByPage(sql, batchSqeunce, Config.RECORDS_BATCH_LOADNUM);
			while(rs!=null&&rs.next()){
				syn_tran = rs.getString("syn_tran");
				if(Checker.isNull(syn_tran))
					continue;
				
				userId= rs.getString("");
				password =rs.getString("");
				tryToLoginSite(syn_tran,userId,password);
				
			}
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		try {
				rs.close();
			} catch (SQLException e) {
			}
    		dao.close();
    	}
    }
	
    
    /**
     * 模拟登录
     * @param syn_tran
     * @param userId
     * @param password
     */
    void tryToLoginSite(String syn_tran,String userId,String password){
    	String exceptionCodes = "";
    	String info ="";
    	String userAgent ="";
    	String requestIp ="";
    	String returnMsg ="";
    	try{
    		exceptionCodes = "";
	    	if(syn_tran.toLowerCase().indexOf("ht")>=0){
	    		info = KetangLoginEncoder.encodeKetangLoginParam("QuanTong","2", userId, requestIp, userAgent);
	    		returnMsg =util.post(Config.SYN_LESSON_URL_HT,Locale.CHINA, Config.SYN_LESSON_URL_HT_PARAMSFORMAT, info);
	    		if(util.findKeyWord(returnMsg, Config.SYN_LESSON_URL_HT_REQ_ERROR))
	    			exceptionCodes+="ht,";
	    	}
	    	
	        if(syn_tran.toLowerCase().indexOf("kl")>=0){
	        	returnMsg =util.post(Config.SYN_LESSON_URL_KL,Locale.CHINA, Config.SYN_LESSON_URL_KL_PARAMSFORMAT, new String[]{userId,password});
	        	if(util.findKeyWord(returnMsg, Config.SYN_LESSON_URL_KL_REQ_ERROR))
	    			exceptionCodes+="kl,";
	        }
	        
			if(syn_tran.toLowerCase().indexOf("ww")>=0){
			    info = KetangLoginEncoder.encodeKetangLoginParam("QuanTong","2", userId, requestIp, userAgent);
			    returnMsg =util.post(Config.SYN_LESSON_URL_WW,Locale.CHINA, Config.SYN_LESSON_URL_WW_PARAMSFORMAT, info);
			    if(util.findKeyWord(returnMsg, Config.SYN_LESSON_URL_WW_REQ_ERROR))
	    			exceptionCodes+="ww,";
			}
			
			if(syn_tran.toLowerCase().indexOf("zx")>=0){
				info = KetangLoginEncoder.encodeKetangLoginParam("QuanTong","2", userId, requestIp, userAgent);
				returnMsg =util.post(Config.SYN_LESSON_URL_ZX,Locale.CHINA, Config.SYN_LESSON_URL_ZX_PARAMSFORMAT, info);
				if(util.findKeyWord(returnMsg, Config.SYN_LESSON_URL_ZX_REQ_ERROR))
	    			exceptionCodes+="zx,";
			}
    	}catch(Exception e){
    	   e.printStackTrace();     	
    	}finally{
    	    if(!Checker.isNull(exceptionCodes)) 
    		   exceptions.put(userId, exceptionCodes);
    	}
    }
    
    /**
     *  记录登录失败的用户
     */
    void recordLoginException(){
        if(this.exceptions.size()>0){
                      	
        }
    }
    
	public int count(String sql){
		BaseDao dao = null;
		ResultSet rs = null;
		try {
			dao = new BaseDao(Config.POOL_NAME);
			rs = dao.query(" select count(*) from ( " + sql + " ) a");
			int records = 0;
			while (rs != null && rs.next()) {
				records = rs.getInt(1);
			}
			return records;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
			}
			dao.close();
		}
	}
    
    
    
    
	void printPostResult(){
		StringBuffer result = new StringBuffer();
		result.append("---------RESULT-INFOS-START:[ Post-Test-"+threadName+"]------------ "+" \n");
		result.append("---------RESULT-INFOS-START:[ Post-Test-"+threadName+" at "+new Date().toLocaleString()+" ]------------ "+" \n");
		System.out.println(result.toString());
	}
	
}