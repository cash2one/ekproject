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
				temp = this.query(areaAbb, phone, beginDate, endDate);
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
	
	
	List<TransUseStateRow> query(String areaAbb,String phone,String beginDate,String endDate){
		BaseDao db = null;
		ResultSet rs = null;
		List<TransUseStateRow> rows = new ArrayList<TransUseStateRow>();
		TransUseStateRow row = null;
		try{
			db = new BaseDao(DBConnector.getConnection(POOL_NAME));
			rs =  db.queryByPage(sendMsgQuerySql(areaAbb, phone, beginDate, endDate),1,max_record_lenght);
			while(rs!=null&&rs.next()){
            	row = new TransUseStateRow();
            	row.setReceviceDate("接收日期");
				row.setSendDate(rs.getString("dt"));
				row.setRecevicePhone(rs.getString("object_mobile"));
				row.setSendPhone(rs.getString("source_mobile"));
				row.setStudentName(rs.getString("name"));
				row.setSendState("发送状态");
				row.setReceviceState("接受状态");
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
	 * 家长接收
	 * @param areaAbb
	 * @param phone
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	String sendMsgQuerySql(String areaAbb, String phone, String beginDate,
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
		
		mainSql.append(" select stu.name,dx.* from ( ");
		for(String yymm: ymSet){
			mainSql.append(" select * from  ").append(areaAbb).append("_dx_sms_").append(yymm);
			mainSql.append(" where source_mobile ='").append(phone).append("'");
			mainSql.append(" or object_mobile ='").append(phone).append("'");
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
		CsopLog.debug("业务使用记录查询SQL:"+mainSql);
		return mainSql.toString();
	}
	
	
	public String getCurrentDateTime(String fomateDate) {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(fomateDate);
		return sdf.format(new Date());
	}

	
	List<String> getTimeSet(String beginDate,String endDate){
		List<String> timeSet = new ArrayList<String>();
		timeSet.add("1010");
		return timeSet;
	}
	
	/**
	 * 家长（回复）发送
	 * @param areaAbb
	 * @param phone
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	String receviceMsgQuerySql(String areaAbb, String phone, String beginTime,
			String endTime) {
		StringBuffer mainSql = new StringBuffer();
		mainSql.append("select * from  ").append(areaAbb).append("_dx_recsms");
		return null;
	}

	
	public static void main(String...rr){
		TransUseStateDao dao = new TransUseStateDao();
		dao.query("zs", "13525531006", null, null);
	}
}
