package cn.qtone.xxt.csop.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.qtone.xxt.csop.dao.comom.BaseDao;
import cn.qtone.xxt.csop.dao.comom.DBConnector;
import cn.qtone.xxt.csop.dao.model.TransUseStateRow;
import cn.qtone.xxt.csop.inter.AbstractTransDao;
import cn.qtone.xxt.csop.util.Checker;
import cn.qtone.xxt.csop.util.CsopLog;
import cn.qtone.xxt.csop.webservices.bean.TransUseStateParams;

/**
 * 6.4.1.4 业务使用记录查询接口（B005_04）
 * 
 * @author Solosus
 * 
 */
public class TransUseStateDao extends
		AbstractTransDao<TransUseStateParams, TransUseStateRow> {

	static int max_record_lenght = 200;

	@Override
	public List<TransUseStateRow> query(TransUseStateParams requestParams) {
		String phone = requestParams.getTelNo();
		String beginDate = requestParams.getBeginDate();
		String endDate = requestParams.getEndDate();
        List<String> serviceAreas = this.phoneServiceInAreas(phone);		
        
        if(serviceAreas!=null&&serviceAreas.size()>0){
		    List<TransUseStateRow> allRows = new ArrayList<TransUseStateRow>();;
		    List<TransUseStateRow> temp = null;
			for(String areaAbb:serviceAreas){
				
				//短信下行
				temp = this.querySmsSend(areaAbb, phone, beginDate, endDate);
				if(temp!=null){
					for(TransUseStateRow row:temp){
                         allRows.add(row);
                         row = null;
					}
					temp = null;
				}
				
				//短信上行
				temp = this.querySmsRecevice(areaAbb, phone, beginDate, endDate);
				if(temp!=null){
					for(TransUseStateRow row:temp){
	                     allRows.add(row);
	                     row = null;
					}
					temp = null;
				}
			}
		   return allRows;
		}
		return null;
	}
	
	/**
	 * 
	 * 家长接收短信
	 * @param areaAbb
	 * @param phone
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<TransUseStateRow> querySmsSend(String areaAbb,String phone,String beginDate,String endDate){
		BaseDao db = null;
		ResultSet rs = null;
		List<TransUseStateRow> rows = new ArrayList<TransUseStateRow>();
		TransUseStateRow row = null;
		try{
			db = new BaseDao(POOL_NAME,DBConnector.getConnection(POOL_NAME));
			rs =  db.queryByPage(sendSmsMsgQuerySql(areaAbb, phone, beginDate, endDate),1,max_record_lenght);
			while(rs!=null&&rs.next()){
            	row = new TransUseStateRow();
            	row.setReceviceDate(rs.getString("dt"));
				row.setSendDate(rs.getString("dt"));
				row.setRecevicePhone(rs.getString("object_mobile"));
				row.setSendPhone(rs.getString("source_mobile"));
				row.setStudentName(rs.getString("name"));
				row.setSendState("成功");
				row.setReceviceState("成功");
				row.setDxType("家长下行");
				rows.add(row);
				row = null;
            }
        }catch(Exception e){
        	CsopLog.debug("业务使用记录查询SQL:"+e.getMessage());
        }finally{
				try {
					if(rs!=null)
					  rs.close();
					db.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        }
        return rows;
	}
	
	
	/**
	 * 家长接收短信
	 * @param areaAbb
	 * @param phone
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	String sendSmsMsgQuerySql(String areaAbb, String phone, String beginDate,
			String endDate) {
		StringBuffer mainSql = new StringBuffer();
		//时间为空时，默认查询当月的信息
		List<String> ymSet = null;
		if(Checker.isNull(beginDate)||Checker.isNull(endDate)){
            new Date(System.currentTimeMillis());
            ymSet = new ArrayList<String>();
            ymSet.add(getCurrentDateTime("yyMM"));
		}else
			ymSet=getTimeSet(beginDate,endDate);
		
		mainSql.append(" select stu.name,dx.object_mobile,dx.source_mobile,to_char(dx.dt,'YYYY-MM-DD HH:MM:SS') DT from ( ");
		for(String yymm: ymSet){
			mainSql.append(" select * from  ").append(areaAbb).append("_dx_sms_").append(yymm);
			mainSql.append(" where object_mobile ='").append(phone).append("'");
			mainSql.append(" union all ");
		}
		mainSql.delete(mainSql.length()-" union all ".length(), mainSql.length());
		mainSql.append(" )dx left join ").append(areaAbb).append("_xj_student stu "); 
		mainSql.append("on stu.stu_sequence = dx.stu_sequence ");
		
		mainSql.append(" where 1=1 ");
		if(!Checker.isNull(beginDate))
			mainSql.append(" and to_char(dt,'YYYY-MM-DD')>='").append(beginDate).append("'");
		if(!Checker.isNull(endDate))
			mainSql.append(" and to_char(dt,'YYYY-MM-DD')<='").append(endDate).append("'");
		ymSet = null;
		CsopLog.debug("业务使用记录查询【家长下行短信】SQL:"+mainSql);
		return mainSql.toString();
	}
	
	
	public String getCurrentDateTime(String fomateDate) {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(fomateDate);
		return sdf.format(new Date());
	}

	
	
	/**
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<String> getTimeSet(String beginDate,String endDate){
		List<String> timeSet = new ArrayList<String>();
		String start_year = beginDate.substring(0, 4);
		String end_year = endDate.substring(0, 4);
		int start_month = Integer.parseInt(beginDate.substring(5, 7));
		int end_month = Integer.parseInt(endDate.substring(5, 7));
		
		if(start_year.equals(end_year)){
            for(;end_month>=start_month;end_month--){
            	timeSet.add(start_year.substring(2, 4)+(end_month<10?"0"+end_month:end_month));          	
            }
		}else{
		    int sy =  Integer.parseInt(start_year);
		    int ey =  Integer.parseInt(end_year);
		    int em = 12;
		    int sm = 1;
		    for(int ty=ey;ty>=sy;ty--){
		    	if(ty<ey)
		    	   em =12;
		    	else
		    	   em = end_month;
		    	if(ty==sy)
		    	   sm= start_month;
		    	else
		    	   sm=1;	
		    	for(;em>=sm;em--){
		    	   timeSet.add(String.valueOf(ty).substring(2, 4)+(em<10?"0"+em:em));    
		    	}
		    }
		}
		return timeSet;
	}
	
	
	
	
	/**
	 * 
	 * 家长回复短信（上行）
	 * @param areaAbb
	 * @param phone
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<TransUseStateRow> querySmsRecevice(String areaAbb,String phone,String beginDate,String endDate){
		BaseDao db = null;
		ResultSet rs = null;
		List<TransUseStateRow> rows = new ArrayList<TransUseStateRow>();
		TransUseStateRow row = null;
		try{
			db = new BaseDao(POOL_NAME,DBConnector.getConnection(POOL_NAME));
			rs =  db.queryByPage(receviceMsgQuerySql(areaAbb, phone, beginDate, endDate),1,max_record_lenght);
			while(rs!=null&&rs.next()){
            	row = new TransUseStateRow();
            	row.setReceviceDate(rs.getString("dt"));
				row.setSendDate(rs.getString("dt"));
				row.setRecevicePhone(rs.getString("object_mobile"));
				row.setSendPhone(rs.getString("source_mobile"));
				row.setStudentName(rs.getString("name"));
				row.setSendState("成功");
				row.setReceviceState("成功");
				row.setDxType("家长上行");
				rows.add(row);
				row = null;
            }
        }catch(Exception e){
        	CsopLog.debug("业务使用记录查询SQL:"+e.getMessage());
        }finally{
				try {
					if(rs!=null)
					  rs.close();
					db.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        }
        return rows;
	}
	
	
	
	
	
	
	/**
	 * 家长（回复）发送
	 * @param areaAbb
	 * @param phone
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	String receviceMsgQuerySql(String areaAbb, String phone, String beginDate,
			String endDate) {
		
		StringBuffer mainSql = new StringBuffer();
		mainSql.append("select stu.name,dx.object_mobile,dx.source_mobile,to_char(dx.dt,'YYYY-MM-DD HH:MM:SS') DT from  ").append(areaAbb).append("_dx_recsms dx ");
		mainSql.append(" left join ").append(areaAbb).append("_xj_student stu "); 
		mainSql.append(" on stu.stu_sequence = dx.stu_sequence ");
		
		mainSql.append(" where 1=1 ");
		if(!Checker.isNull(beginDate))
			mainSql.append(" and to_char(dt,'YYYY-MM-DD')>='").append(beginDate).append("'");
		if(!Checker.isNull(endDate))
			mainSql.append(" and to_char(dt,'YYYY-MM-DD')<='").append(endDate).append("'");
		
		CsopLog.debug("业务使用记录查询【家长上行短信】SQL:"+mainSql);
		return mainSql.toString();
	}

	
	public static void main(String...rr){
		TransUseStateDao dao = new TransUseStateDao();
		dao.querySmsSend("zs", "13525531006", null, null);
	}
}
