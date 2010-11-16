package cn.qtone.xxt.csop.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.qtone.xxt.csop.business.TransactionType;
import cn.qtone.xxt.csop.dao.comom.BaseDao;
import cn.qtone.xxt.csop.dao.comom.DBConnector;
import cn.qtone.xxt.csop.dao.model.TransCustomerRow;
import cn.qtone.xxt.csop.inter.AbstractTransDao;
import cn.qtone.xxt.csop.inter.TransQueryDao;
import cn.qtone.xxt.csop.util.Checker;
import cn.qtone.xxt.csop.util.CsopLog;
import cn.qtone.xxt.csop.webservices.bean.TransCustomerQueryParams;

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
public class TransCustomerQueryDao extends AbstractTransDao 
implements TransQueryDao<TransCustomerQueryParams,TransCustomerRow> {

	/**
	 * 接口定义的返回数据格式（字段） 业务名称 业务端口 业务内容简介 资费（元） 计费类型  
	 * 开通方式   订购时间    业务使用状态  扣费时间   营销关联信息
	 */
	public List<TransCustomerRow> query(TransCustomerQueryParams reqParams) {
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
		List<TransCustomerRow> allResults = new ArrayList<TransCustomerRow>();
		List<TransCustomerRow> subResults=null;
		for(String areaAbb:serviceAreas){
             //判断该地区是否属于套餐地区 
			 if(area.isPackageArea(areaAbb)){
				CsopLog.debug("查询用户["+phone+"] 在 "+areaAbb+" 地区定制的套餐业务情况。");
				subResults=this.packageTransaction(areaAbb, phone, beginDate, endDate);
             }else{
                CsopLog.debug("查询用户["+phone+"] 在 "+areaAbb+" 地区定制的基本业务情况。");
                subResults=this.baseTransaction(areaAbb, phone, beginDate, endDate); 
             }
			 if(subResults!=null){
                 for(TransCustomerRow row : subResults){ 			   
                	 allResults.add(row);
                     row = null;
                 } 
				 subResults.clear();
			 }   
			 subResults=null;
		}
		return allResults;
	}
	
	/**
	 * 属于基本业务的查询
	 * @param areaAbb
	 * @param phone
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<TransCustomerRow> baseTransaction(String areaAbb, String phone, String beginDate,String endDate){
		BaseDao db = null;
		ResultSet rs = null;
		List<TransCustomerRow> rows = new ArrayList<TransCustomerRow>();
		try{
	         db = new BaseDao(POOL_NAME,DBConnector.getConnection(POOL_NAME));	
             rs = db.query(this.baseTransactionSql(areaAbb, phone, beginDate, endDate));
			 
             TransCustomerRow nRow = null;
             while(rs!=null&&rs.next()){
		          nRow = new TransCustomerRow();		 
				  
		         if(rs.getInt("transaction_id")<=4)
		            nRow.setName(TransactionType.values()[rs.getInt("transaction_id")-1].cname());
		          
		          nRow.setDesc("业务描述未知");
				  nRow.setPort(rs.getString("port"));
				  nRow.setServiceState(rs.getInt("is_open")==0?"暂停":"正常");
				  if(rs.getInt("is_open")!=0){
				    nRow.setOpenType(rs.getInt("book_type")==0?"校讯通合作商操作":"短信");
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
				  nRow.setSaleRelationShip("营销关联信息");
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
	
	// 基本业务 定制情况 Sql 语句
	String baseTransactionSql(String areaAbb, String phone, String beginDate,
			String endDate) {
		StringBuffer mainSql = new StringBuffer(" select base.id family,base.phone,base.stu_sequence,base.transaction,");
		mainSql.append("base.transaction_id,base.is_open,tlog.operator,tlog.reason,");
		mainSql.append("tlog.package_id,book_type,base.open_date,is_charge,");
		mainSql.append("nvl(ywt.fee,0)fee,ywt.type ywt_charge_type,ywt.tran_code,kf.UPDATE_DATE kf_date,ywt.port from ( ");
		
		StringBuffer baseView = new StringBuffer();
		for (TransactionType type : TransactionType.values()) {
			baseView.append("select fv.id,fv.phone phone,fv.stu_sequence,'"
					+ type.cname() + "' transaction ,'" + type.code()
					+ "' transaction_id," + type.familyField() + " is_open,xj_school."+type.schoolField()+" is_charge,"+type.openDate()+" open_date ");
			baseView.append(" from "+areaAbb+"_xj_family fv ");
			baseView.append(" left join xj_school on fv.school_id = xj_school.id ");
			baseView.append(" where fv.phone ='"+ phone +"' ");
			baseView.append(" union ");
		}
		
		baseView.delete(baseView.length()-" union ".length(), baseView.length());
		mainSql.append(baseView);
		
	    //业务日志
		mainSql.append(" )base left join ( ").append(this.lastBaseTransactionLog(areaAbb,phone,beginDate,endDate)).append(" ) tlog ");
		mainSql.append(" on tlog.family_id = base.id and tlog.stu_sequence = base.stu_sequence ");
		mainSql.append(" and  tlog.open = base.is_open and tlog.transaction = base.transaction_id ");
		
		//扣费记录
		mainSql.append(" left join ").append(areaAbb).append("_yw_kf_chargerecord kf ");
		mainSql.append(" on kf.family_id=base.id and kf.phone=base.phone and base.transaction_id=kf.transaction ");
		
		//查询对应的资费
//		mainSql.append(" left join ").append(" yw_Transaction ywt");
//		mainSql.append(" on ywt.tran_code = kf.tran_code and ywt.TRANSACTION=base.transaction_id and ywt.AREA_ID=").append(area.getAreaIdByAbb(areaAbb));
	
		mainSql.append(" LEFT JOIN  ( select ao.family_id,ADC.TRAN_ID,ADC.FEE,ADC.TRAN_CODE,ADC.SERVICE_CODE,ADC.PORT,ADC.TYPE from  zs_adc_member_order ao ");
		mainSql.append(" left join adc_member_order_service adc on ao.tran_id = ADC.TRAN_ID and ao.tran_code = ADC.TRAN_CODE and ao.type =1 and adc.type=1 ) ywt");
		mainSql.append(" ON YWT.TRAN_ID = base.transaction_id and ywt.family_id = base.id ");
		
		//查询条件 时间
		mainSql.append(" where 1=1 ");
		if(!Checker.isNull(beginDate))
			mainSql.append(" and to_char(base.open_date,'YYYY-MM-DD')>='").append(beginDate).append("'");
		if(!Checker.isNull(endDate))
			mainSql.append(" and to_char(base.open_date,'YYYY-MM-DD')<='").append(endDate).append("'");
		
		baseView = null;
		CsopLog.debug("基本业务:"+mainSql.toString());
		return mainSql.toString();	
	}
	
	
	/*
	 * 最新的基本业务变更日志
	 */
	String lastBaseTransactionLog(String areaAbb,String phone,String beginDate,String endDate){
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.* from "+areaAbb+"_transaction_log a, ( ");
		sql.append(" select  b.family_id,b.stu_sequence,b.transaction,b.phone,b.open,max(b.open_date) open_date  from  "+areaAbb+"_transaction_log b");
		sql.append(" group by b.family_id,b.stu_sequence,b.transaction,b.open,b.phone  having b.open = 1 or b.open = 0 ) n ");
		sql.append(" where n.family_id=a.family_id and a.stu_sequence=n.stu_sequence and a.transaction = n.transaction and a.open = n.open and a.open_date= n.open_date ");
		return sql.toString();
	} 
	
	
	// 套餐查询 定制情况
	List<TransCustomerRow> packageTransaction(String areaAbb, String phone, String beginDate,
			String endDate) {
		BaseDao db = null;
		ResultSet rs = null;
		List<TransCustomerRow> rows = new ArrayList<TransCustomerRow>();
		try{
	         db = new BaseDao(POOL_NAME,DBConnector.getConnection(POOL_NAME));	
             rs = db.query(this.packageTransactionSql(areaAbb, phone, beginDate, endDate));
			 
             TransCustomerRow nRow = null;
             while(rs!=null&&rs.next()){
		          nRow = new TransCustomerRow();		 
				  nRow.setName(rs.getString("transaction"));
				  nRow.setDesc(rs.getString("remark"));
 				  nRow.setPort(rs.getString("port"));
				  nRow.setServiceState(rs.getInt("is_open")==0?"激活":"正常");
				  if(rs.getInt("is_open")!=0){
					    nRow.setOpenType(rs.getInt("book_type")==0?"校讯通合作商操作":"短信");
					    nRow.setOrderTime(rs.getString("open_date"));
//					    nRow.setPayTime(rs.getString("kf_date"));
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
				  
				  
				  nRow.setSaleRelationShip("营销关联信息");
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
	
	// 套餐查询 定制情况 Sql
	// (当前订购的)
	/**
	 * 业务名称 业务端口 业务内容简介 资费（元） 计费类型  
	 * 开通方式   订购时间    业务使用状态  扣费时间   营销关联信息
	 */
	String packageTransactionSql(String areaAbb, String phone, String beginDate,
			String endDate){
		 StringBuffer mainSql = new StringBuffer(" select fp.name transaction,fp.REMARK,fp.IS_FREE is_charge,fp.del is_open, ");
		 mainSql.append(" tlog.open_date,tlog.book_type,nvl(ywt.fee,0)fee,ywt.type ywt_charge_type,ywt.tran_code,ywt.port ");
		 mainSql.append(" from ").append(areaAbb).append("_xj_family fa ");
		 
		 mainSql.append(" left join ( select a.* ,pp.name,pp.remark,pp.is_free from ").append(areaAbb).append("_preferential_packager a ");
		 mainSql.append(" left join preferential_package pp on pp.id =a.pp_id ");
		 mainSql.append(" )fp on fa.id = fp.f_id and fp.phone = fa.phone ");
		 
		 
		 //套餐日志
		 mainSql.append(" left join ( ").append(this.lastPackageTransactionLog(areaAbb)).append(" )tlog ");
		 mainSql.append(" on tlog.family_id=fa.id and tlog.phone=fa.phone and tlog.open=1 and tlog.package_id = fp.pp_id ");
		 
		 
		 //对应的资费
		 mainSql.append(" left join ( select a.family_id,b.TRAN_ID,b.FEE,b.TRAN_CODE,b.SERVICE_CODE,b.PORT,b.TYPE from fs_adc_member_order a,adc_member_order_service b ");
		 mainSql.append("  where a.tran_code = b.tran_code  and b.type =2 and a.type=2 ");
		 mainSql.append("  and exists( select * from "+areaAbb+"_xj_family c where c.phone ='"+phone+"' and a.family_id = c.id )  ");
		 mainSql.append(" ) ywt  on ywt.TRAN_ID = fp.pp_id  ");
		 
		 
		 mainSql.append(" where fa.phone='").append(phone).append("'");	
		 mainSql.append(" and fp.del = 1 ");//开通的套餐
		 
		 if(!Checker.isNull(beginDate))
			 mainSql.append(" and to_char(fp.START_DATE,'YYYY-MM-DD')>='").append(beginDate).append("'");
		 if(!Checker.isNull(endDate))
			 mainSql.append(" and to_char(fp.END_DATE,'YYYY-MM-DD')<='").append(endDate).append("'");
	     
		 CsopLog.debug("套餐业务:"+mainSql.toString());
		 return mainSql.toString();
	} 
	
	
	/**
	 * 列出该用户在该地区所有套餐状态的
	 * @param areaAbb
	 * @param phone
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	String allPackageTransactionSql(String areaAbb, String phone, String beginDate,
			String endDate){
		 StringBuffer mainSql = new StringBuffer(" select fp.name transaction,fp.REMARK,fp.IS_FREE is_charge,fp.del is_open, ");
		 mainSql.append(" tlog.open_date,tlog.book_type,nvl(ywt.fee,0)fee,ywt.type ywt_charge_type,ywt.tran_code,ywt.port ");
		 mainSql.append(" from ").append(areaAbb).append("_xj_family fa ,preferential_package a ");
		 
		 mainSql.append(" left join( select * from (  select * from  "+areaAbb+"_preferential_packager  b");
		 mainSql.append(" where exists( select * from "+areaAbb+"_xj_family c where c.phone ='"+phone+"' and b.f_id = c.id )) ");
		 mainSql.append(" ) e  on e.pp_id = a.id ");
		
		 //查询对应的日志
		 mainSql.append(" left join ( ").append(this.lastPackageTransactionLog(areaAbb)).append(" )tlog ");
		 mainSql.append(" on tlog.family_id =  e.f_id and tlog.package_id = a.id ");
		
		 
		 //对应的资费信息
		 mainSql.append(" left join ( ");
		 mainSql.append(" select a.family_id,b.TRAN_ID,b.FEE,b.TRAN_CODE,b.SERVICE_CODE,b.PORT,b.TYPE from "+areaAbb+"_adc_member_order a,adc_member_order_service b ");
		 mainSql.append(" where a.tran_code = b.tran_code  and b.type =2 ");
		 mainSql.append(" and exists( select * from "+areaAbb+"_xj_family c where c.phone ='"+phone+"' and a.family_id = c.id ) ");
		 mainSql.append(" ) adc  on adc.TRAN_ID = a.id ");
		 
		 
		 mainSql.append(" where 1=1");
		 mainSql.append(" and fa.phone ='"+phone+"' ");
		 mainSql.append(" and exists (select * from area where area.id = a.area_id and area.abb='"+areaAbb+"') ");
		 mainSql.append("");
		 
		 if(!Checker.isNull(beginDate))
			 mainSql.append(" and to_char(fp.START_DATE,'YYYY-MM-DD')>='").append(beginDate).append("'");
		 if(!Checker.isNull(endDate))
			 mainSql.append(" and to_char(fp.END_DATE,'YYYY-MM-DD')<='").append(endDate).append("'");
	     
		 CsopLog.debug("套餐业务:"+mainSql.toString());
		 return mainSql.toString();
	} 
	
	
	/*
	 * 最新的套餐业务的变更日志
	 */
	String lastPackageTransactionLog(String areaAbb){
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.* from "+areaAbb+"_transaction_log a, ( ");
		sql.append(" select  b.family_id,b.stu_sequence,b.package_id,b.phone,b.open,max(b.open_date) open_date  from  "+areaAbb+"_transaction_log b");
		sql.append(" group by b.family_id,b.stu_sequence,b.package_id,b.open,b.phone  having b.open = 1 or b.open = 2 ) n ");
		sql.append(" where n.family_id=a.family_id and a.stu_sequence=n.stu_sequence and a.package_id = n.package_id and a.open = n.open and a.open_date= n.open_date ");
		return sql.toString();
	} 
	
	/**
	 * 其他业务
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
