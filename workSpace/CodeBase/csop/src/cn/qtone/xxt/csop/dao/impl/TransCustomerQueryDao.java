package cn.qtone.xxt.csop.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.qtone.xxt.csop.business.TransactionType;
import cn.qtone.xxt.csop.dao.comom.BaseDao;
import cn.qtone.xxt.csop.dao.comom.DBConnector;
import cn.qtone.xxt.csop.dao.inter.ResultRow;
import cn.qtone.xxt.csop.util.CsopLog;
import cn.qtone.xxt.csop.webservices.bean.RequestParams;

/**
 *6.4.1.1 业务定制情况查询接口（B005_01）
 * 
 * 基本业务地区(四项基本业务分开定制)、 
 * 套餐地区(把基本业务封装为套餐，
 * 以定制套餐的形式定制服务)、
 * ADC试点地区(以营销方案的方式定制服务)
 * 
 * @author linhansheng
 */
public class TransCustomerQueryDao extends AbstractTransDao {

	/**
	 * 接口定义的返回数据格式（字段） 业务名称 业务端口 业务内容简介 资费（元） 计费类型  
	 * 开通方式   订购时间    业务使用状态  扣费时间   营销关联信息
	 */
	public List<ResultRow> query(RequestParams reqParams) {
		StringBuffer querySql = new StringBuffer();
		String phone = reqParams.getTelNo();
		String beginTime = reqParams.getBeginDate();
		String endTime = reqParams.getEndDate();

		List<String> serviceAreas = phoneServiceInAreas(phone);
		if (serviceAreas == null) {
			CsopLog.info("不存在该用户【" + phone + "】的业务定制信息!");
			return null;
		}
		return null;
	}
	
	/**
	 * 属于基本业务的查询
	 * @param areaAbb
	 * @param phone
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<ResultRow> baseTransaction(String areaAbb, String phone, String beginDate,String endDate){
		BaseDao db = null;
		ResultSet rs = null;
		List<ResultRow> rows = new ArrayList<ResultRow>();
		try{
	         db = new BaseDao(DBConnector.getConnection(POOL_NAME));	
             rs = db.query(this.baseTransactionSql(areaAbb, phone, beginDate, endDate));
			 
             TransCustomerRow nRow = null;
             while(rs!=null&&rs.next()){
		          nRow = new TransCustomerRow();		 
				  nRow.setName(rs.getString("transaction"));
				  nRow.setDesc(rs.getString("业务描述未知"));
				  nRow.setPort(rs.getString("业务代码未知"));
				  nRow.setServiceState(rs.getInt("is_open")==0?"未开通":"开通");
				  if(rs.getInt("is_open")!=0){
				    nRow.setOpenType(rs.getInt("book_type")==0?"网页定制":"手机上行定制");
				    nRow.setOrderTime(rs.getString("open_date"));
				    nRow.setPayTime(rs.getString("扣费时间未知"));
				    nRow.setChargeType(rs.getInt("is_charge")==0?"免费":"收费");
				  }  
				  nRow.setSaleRelationShip(rs.getString("transaction"));
				  rows.add(nRow);
				  nRow = null;
			 }
		}catch(Exception e){
		   CsopLog.error(e.getMessage());
		   return null;
		}finally{
	   		try {
				rs.close();
				db.close();
			} catch (SQLException e) {
			}
		}
		return rows;
	}
	

	// 套餐查询 定制情况
	void packageTransaction(String areaAbb, String phone, String beginDate,
			String endDate) {

		
	}

	// 基本业务 定制情况 Sql 语句
	String baseTransactionSql(String areaAbb, String phone, String beginDate,
			String endDate) {
		StringBuffer mainSql = new StringBuffer(" select base.id family,base.phone,base.stu_sequence,base.transaction,");
		mainSql.append("base.transaction_id,base.is_open,tlog.operator,tlog.reason,");
		mainSql.append("tlog.package_id,book_type,open_date,is_charge from ( ");
		
		StringBuffer baseView = new StringBuffer();
		for (TransactionType type : TransactionType.values()) {
			baseView.append("select fv.id,fv.phone,fv.stu_sequence,'"
					+ type.cname() + "' transaction ,'" + type.code()
					+ "' transaction_id," + type.familyField() + " is_open,xj_school."+type.schoolField()+" is_charge ");
			baseView.append(" from "+areaAbb+"_xj_family_view fv ");
			baseView.append(" left join zs_xj_stu_class on zs_xj_stu_class.stu_sequence = fv.stu_sequence  ");
			baseView.append(" left join xj_school on zs_xj_stu_class.school_id = xj_school.id ");
			baseView.append(" where fv.phone ='"+ phone +"' ");
			baseView.append(" union ");
		}
		baseView.delete(baseView.length()-" union ".length(), baseView.length());
		
		mainSql.append(baseView);
		mainSql.append(" )base left join ( ").append(lastTransactionLog(areaAbb,phone,beginDate,endDate)).append(" ) tlog ");
		mainSql.append(" on tlog.family_id = base.id and tlog.stu_sequence = base.stu_sequence ");
		mainSql.append(" and  tlog.open = base.is_open and tlog.transaction = base.transaction_id ");
		return mainSql.toString();	
	}
	
	
	/*
	 * 最新的业务变更日志
	 */
	String lastTransactionLog(String areaAbb,String phone,String beginDate,String endDate){
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.* from zs_transaction_log a, ( ");
		sql.append(" select  b.family_id,b.stu_sequence,b.transaction,b.phone,b.open,max(b.open_date) open_date  from  zs_transaction_log b");
		sql.append(" group by b.family_id,b.stu_sequence,b.transaction,b.open,b.phone  having b.open = 1 or b.open = 0 ) n ");
		sql.append(" where n.family_id=a.family_id and a.stu_sequence=n.stu_sequence and a.transaction = n.transaction and a.open = n.open and a.open_date= n.open_date ");
		return sql.toString();
	} 
	
	
	/**
	 * 
	 * @return
	 */
    String otherTransaction(){
    	
    	return null;
    }
	
    
    /**
     * 业务费用信息
     * @return
     */
    String ywTransactionInfos(){
       //yw_Transaction
    	return "";
    }
    
	
	public static void main(String...srt){
		TransCustomerQueryDao test = new TransCustomerQueryDao();
	    System.out.println(test.baseTransaction("zs","13800138000", null, null));	
	}
	
}
