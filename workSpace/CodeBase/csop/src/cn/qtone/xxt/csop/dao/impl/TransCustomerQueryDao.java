package cn.qtone.xxt.csop.dao.impl;

import java.awt.geom.Area;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.qtone.xxt.csop.business.TransactionType;
import cn.qtone.xxt.csop.dao.comom.BaseDao;
import cn.qtone.xxt.csop.dao.comom.DBConnector;
import cn.qtone.xxt.csop.dao.inter.ResultRow;
import cn.qtone.xxt.csop.util.Checker;
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
		String beginDate = reqParams.getBeginDate();
		String endDate = reqParams.getEndDate();
        //一个号码可能在多个地区中订购服务
		List<String> serviceAreas = phoneServiceInAreas(phone);
		if (serviceAreas == null) {
			CsopLog.info("不存在该用户【" + phone + "】的业务定制信息!");
			return null;
		}
		
		for(String areaAbb:serviceAreas){
             //判断该地区是否属于套餐地区 
			 if(area.isPackageArea(areaAbb)){
				CsopLog.debug("查询用户["+phone+"] 在 "+area+" 地区定制的套餐业务情况。");
             }else{
                CsopLog.debug("查询用户["+phone+"] 在 "+area+" 地区定制的基本业务情况。");
            	return this.baseTransaction(areaAbb, phone, beginDate, endDate); 
             } 
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
				  nRow.setDesc("业务描述未知");
				  nRow.setPort(rs.getString("tran_code"));
				  nRow.setServiceState(rs.getInt("is_open")==0?"未开通":"开通");
				  if(rs.getInt("is_open")!=0){
				    nRow.setOpenType(rs.getInt("book_type")==0?"网页定制":"手机上行定制");
				    nRow.setOrderTime(rs.getString("open_date"));
				    nRow.setPayTime(rs.getString("kf_date"));
				    if(rs.getInt("is_charge")!=0){
				        if(rs.getInt("ywt_charge_type")==0)
				    	  nRow.setChargeType("免费");  //计费类型	包月、点播
				        else if(rs.getInt("ywt_charge_type")==1){
					    	    nRow.setChargeType("包月");
					    	    nRow.setCharge(rs.getInt("fee")+"元/月");
				               }
				        else if(rs.getInt("ywt_charge_type")==2)
					    	  nRow.setChargeType("点播");
				    }
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
	List<ResultRow> packageTransaction(String areaAbb, String phone, String beginDate,
			String endDate) {
          
       		
		
        return null;		
	}

	// 基本业务 定制情况 Sql 语句
	String baseTransactionSql(String areaAbb, String phone, String beginDate,
			String endDate) {
		StringBuffer mainSql = new StringBuffer(" select base.id family,base.phone,base.stu_sequence,base.transaction,");
		mainSql.append("base.transaction_id,base.is_open,tlog.operator,tlog.reason,");
		mainSql.append("tlog.package_id,book_type,base.open_date,is_charge,");
		mainSql.append("nvl(ywt.fee,0)fee,ywt.type ywt_charge_type,kf.tran_code,kf.UPDATE_DATE kf_date from ( ");
		
		StringBuffer baseView = new StringBuffer();
		for (TransactionType type : TransactionType.values()) {
			baseView.append("select fv.id,fv.phone,fv.stu_sequence,'"
					+ type.cname() + "' transaction ,'" + type.code()
					+ "' transaction_id," + type.familyField() + " is_open,xj_school."+type.schoolField()+" is_charge,"+type.openDate()+" open_date ");
			baseView.append(" from "+areaAbb+"_xj_family fv ");
			baseView.append(" left join "+areaAbb+"_xj_stu_class on "+areaAbb+"_xj_stu_class.stu_sequence = fv.stu_sequence  ");
			baseView.append(" left join xj_school on "+areaAbb+"_xj_stu_class.school_id = xj_school.id ");
			baseView.append(" where fv.phone ='"+ phone +"' ");
			baseView.append(" union ");
		}
		baseView.delete(baseView.length()-" union ".length(), baseView.length());
		mainSql.append(baseView);
	    //业务日志
		mainSql.append(" )base left join ( ").append(lastTransactionLog(areaAbb,phone,beginDate,endDate)).append(" ) tlog ");
		mainSql.append(" on tlog.family_id = base.id and tlog.stu_sequence = base.stu_sequence ");
		mainSql.append(" and  tlog.open = base.is_open and tlog.transaction = base.transaction_id ");
		//扣费记录
		mainSql.append(" left join ").append(areaAbb).append("_yw_kf_chargerecord kf ");
		mainSql.append(" on kf.family_id=base.id and kf.phone=base.phone and base.transaction_id=kf.transaction ");
		//查询对应的资费
		mainSql.append(" left join ").append(" yw_Transaction ywt");
		mainSql.append(" on ywt.TRANSACTION=base.transaction_id and ywt.AREA_ID=").append(area.getAreaIdByAbb(areaAbb));
	
		//查询条件 时间
		
		mainSql.append(" where 1=1 ");
		if(!Checker.isNull(beginDate))
			mainSql.append(" and to_char(base.open_date,'YY-MM-DD')>='").append(beginDate).append("'");
		if(!Checker.isNull(endDate))
			mainSql.append(" and to_char(base.open_date,'YY-MM-DD')<='").append(endDate).append("'");
		
		baseView = null;
		System.out.println(mainSql.toString());
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
	
   
	public static void main(String...srt){
		TransCustomerQueryDao test = new TransCustomerQueryDao();
	     test.baseTransaction("zs","13770536428", null, null);	
	}
	
}
