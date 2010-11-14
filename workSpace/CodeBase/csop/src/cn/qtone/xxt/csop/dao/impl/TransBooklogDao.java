package cn.qtone.xxt.csop.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.qtone.xxt.csop.business.TransactionType;
import cn.qtone.xxt.csop.dao.comom.BaseDao;
import cn.qtone.xxt.csop.dao.comom.DBConnector;
import cn.qtone.xxt.csop.dao.model.TransBooklogRow;
import cn.qtone.xxt.csop.inter.AbstractTransDao;
import cn.qtone.xxt.csop.util.Checker;
import cn.qtone.xxt.csop.util.CsopLog;
import cn.qtone.xxt.csop.webservices.bean.TransBooklogParams;


/**
 *6.4.1.3 业务历史订购记录查询接口（B005_03）
 * 
 * @author linhansheng 
 * 
 */
public class TransBooklogDao extends
		AbstractTransDao<TransBooklogParams, TransBooklogRow> {

	static int record_max_limit = 200;
	
	@Override
	public List<TransBooklogRow> query(TransBooklogParams requsetParams) {
		String phone = requsetParams.getTelNo();
		String studentName = requsetParams.getChildObject();
		String beginDate = requsetParams.getBeginDate();
		String endDate =requsetParams.getEndDate();
		
		List<String> serviceAreas = this.phoneServiceInAreas(phone);
		if(serviceAreas==null||serviceAreas.size()==0){
            CsopLog.debug("找不得到[",phone,"]用户的业务历史订购记录。");			
			return null;
		}
			
		List<TransBooklogRow> allResult = new ArrayList<TransBooklogRow>();
		List<TransBooklogRow> tempSet = null;
		for(String areaAbb:serviceAreas){
	    	if(this.isPackageArea(areaAbb)){
	    	  tempSet = this.packageTransBookLog(phone,areaAbb, studentName, beginDate, endDate);
	    	}else
	    	  tempSet = this.baseTransBookLog(phone,areaAbb, studentName, beginDate, endDate);
	        
	    	if(tempSet!=null){
			    for(TransBooklogRow row :tempSet){
			    	allResult.add(row);
			    }
		    	tempSet.clear();
		    	tempSet = null;
	    	}
		}
		return allResult;
	}
	
	/**
	 * 基本业务
	 * @param areaAbb
	 * @param studentName
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<TransBooklogRow> baseTransBookLog(String phone,String areaAbb,String studentName,String beginDate,String endDate){
		List<TransBooklogRow> rows = new ArrayList<TransBooklogRow>();
		BaseDao db = null;
		ResultSet rs = null;
		try{
      		db = new BaseDao(POOL_NAME,DBConnector.getConnection(POOL_NAME));
      		rs = db.queryByPage(queryBaseTranscationLogsSql(phone,areaAbb,studentName,beginDate,endDate), 1, record_max_limit);
      		TransBooklogRow row = null;
      		while(rs!=null&&rs.next()){
      			   if(rs.getInt("transaction")>0&&rs.getInt("transaction")<5){
      				 row = new TransBooklogRow();
      				 row.setTransaction(TransactionType.values()[rs.getInt("transaction")-1].cname());
      			   }
                   else
      				   continue;
                    row.setSchool(rs.getString("school_name"));
                    row.setClassName(rs.getString("class_name"));
                    row.setStudentName(rs.getString("stu_name"));
                    row.setBookType(rs.getInt("book_type")==0?"校讯通客服":"短信");
                    row.setReason(rs.getString("reason"));
                    row.setParentName(rs.getString("parent_name"));
                    row.setOperateDate(rs.getString("open_date"));
                    row.setOperator(rs.getString("operator"));
                    row.setOperationType(rs.getInt("open")==0?"取消":(rs.getInt("open")==1?"开通":"修改收费"));
                    row.setRemark("备注信息");
                    row.setSaleRelationShip("营销关系");
                    rows.add(row);
                    row = null;
      		}
		}catch(Exception e){
			CsopLog.error("查询业务历史订购记录出错... ",e.getMessage());
		}finally{
			try {
				if(rs!=null)
				   rs.close();
      			db.close();
			} catch (SQLException e) {
			}
		}
		return rows;
	}
	
	
	/**
	 * 基本业务变更日志
	 * @param areaAbb
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	String queryBaseTranscationLogsSql(String phone,String areaAbb,String studentName,String beginDate,String endDate){
		StringBuffer mainSql = new StringBuffer();
		mainSql.append(" select xj_school.school_name,cla.class_name,stu.name stu_name,fa.name parent_name,tlog.transaction,tlog.open,book_type,reason,operator,open_date ");
		mainSql.append(" from ").append(areaAbb+"_transaction_log tlog ");
		mainSql.append(" left join "+areaAbb+"_xj_student stu ");
		mainSql.append(" on stu.stu_sequence = tlog.stu_sequence ");
		mainSql.append(" left join "+areaAbb+"_xj_family fa");
		mainSql.append(" on fa.id = tlog.family_id ");
		mainSql.append(" left join "+areaAbb+"_xj_stu_class scla ");
		mainSql.append(" on scla.stu_sequence=tlog.stu_sequence ");
		mainSql.append(" left join xj_school on xj_school.id=scla.school_id ");
		mainSql.append(" left join xj_class cla on cla.id=scla.class_id and cla.school_id=scla.school_id ");

		//查询条件
		mainSql.append(" where 1=1 ");
		if(!Checker.isNull(beginDate))
			mainSql.append(" and to_char(tlog.open_date,'YYYY-MM-DD')>='").append(beginDate).append("'");
		if(!Checker.isNull(endDate))
			mainSql.append(" and to_char(tlog.open_date,'YYYY-MM-DD')<='").append(endDate).append("'");
		if(!Checker.isNull(studentName)&&("全部".equals(studentName)||"all".equals(studentName.toLowerCase())))
			mainSql.append(" and stu.name like '%"+studentName+"%'");
		if(!Checker.isNull(phone))
			mainSql.append(" and( fa.phone like '"+phone+"'or fa.kf_phone like '"+phone+"')");
		
		//排序
		mainSql.append(" order by tlog.open_date desc ");  
		CsopLog.debug("基本业务变更日志SQL："+mainSql.toString());
		return mainSql.toString();
	}
	
	
	/**
	 * 套餐类型
	 * @param areaAbb
	 * @param studentName
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<TransBooklogRow> packageTransBookLog(String phone,String areaAbb,String studentName,String beginDate,String endDate){
		List<TransBooklogRow> rows = new ArrayList<TransBooklogRow>();
		BaseDao db = null;
		ResultSet rs = null;
		try{
      		db = new BaseDao(POOL_NAME,DBConnector.getConnection(POOL_NAME));
      		rs = db.queryByPage(queryPackageTranscationLogsSql(phone,areaAbb,studentName,beginDate,endDate), 1, record_max_limit);
      		TransBooklogRow row = null;
      		while(rs!=null&&rs.next()){
      			    
      			    row = new TransBooklogRow();
                    row.setSchool(rs.getString("school_name"));
                    row.setClassName(rs.getString("class_name"));
                    row.setStudentName(rs.getString("stu_name"));
                    row.setBookType(rs.getInt("book_type")==0?"校讯通客服":"短信");
                    row.setReason(rs.getString("reason"));
                    row.setParentName(rs.getString("parent_name"));
                    row.setOperateDate(rs.getString("open_date"));
                    row.setOperator(rs.getString("operator"));
                    row.setOperationType(rs.getInt("open")==0?"取消":(rs.getInt("open")==1?"开通":"修改收费"));
                    row.setRemark(rs.getString("remark"));
                    row.setSaleRelationShip("营销关系");
                    rows.add(row);
                    row = null;
      		}
		}catch(Exception e){
			CsopLog.error("查询业务历史订购记录出错... ",e.getMessage());
		}finally{
			try {
				if(rs!=null)
					rs.close();
      			db.close();
			} catch (SQLException e) {
			}
		}
		return rows;
	}
	
	
	/**
	 * 套餐类型的日志
	 * @param areaAbb
	 * @param studentName
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	String queryPackageTranscationLogsSql(String phone,String areaAbb,String studentName,String beginDate,String endDate){
		StringBuffer mainSql = new StringBuffer();
		mainSql.append(" select xj_school.school_name,cla.class_name,stu.name stu_name,fa.name parent_name,pp.name transaction,pp.remark,tlog.open,book_type,reason,operator,open_date ");
		mainSql.append(" from ").append(areaAbb+"_transaction_log tlog ");
		mainSql.append(" left join preferential_package pp ");
		mainSql.append(" on tlog.package_id=pp.id ");
		mainSql.append(" left join "+areaAbb+"_xj_student stu ");
		mainSql.append(" on stu.stu_sequence = tlog.stu_sequence ");
		mainSql.append(" left join "+areaAbb+"_xj_family fa");
		mainSql.append(" on fa.id = tlog.family_id ");
		mainSql.append(" left join "+areaAbb+"_xj_stu_class scla ");
		mainSql.append(" on scla.stu_sequence=tlog.stu_sequence ");
		mainSql.append(" left join xj_school on xj_school.id=scla.school_id ");
		mainSql.append(" left join xj_class cla on cla.id=scla.class_id and cla.school_id=scla.school_id ");

		//查询条件
		mainSql.append(" where 1=1 ");
		if(!Checker.isNull(beginDate))
			mainSql.append(" and to_char(tlog.open_date,'YYYY-MM-DD')>='").append(beginDate).append("'");
		if(!Checker.isNull(endDate))
			mainSql.append(" and to_char(tlog.open_date,'YYYY-MM-DD')<='").append(endDate).append("'");
		if(!Checker.isNull(studentName)&&("全部".equals(studentName)||"all".equals(studentName.toLowerCase())))
			mainSql.append(" and stu.name like '%"+studentName+"%'");
		if(!Checker.isNull(phone))
			mainSql.append(" and( fa.phone like '"+phone+"'or fa.kf_phone like '"+phone+"')");
		
		//排序
		mainSql.append(" order by tlog.open_date desc ");  
		CsopLog.debug("套餐业务变更日志SQL："+mainSql.toString());
		return mainSql.toString();
	}
	
	
	public static void main(String...r){
		TransBooklogDao d = new TransBooklogDao();
		d.queryBaseTranscationLogsSql("","zs","",null,null);
	}

}
