package cn.qtone.xxt.csop.dao.impl;

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
 *6.4.1.1 ҵ���������ѯ�ӿڣ�B005_01��
 * 
 * ����ҵ�����(�������ҵ��ֿ�����)�� 
 * �ײ͵���(�ѻ���ҵ���װΪ�ײͣ�
 * �Զ����ײ͵���ʽ���Ʒ���)��
 * ADC�Ե����(��Ӫ�������ķ�ʽ���Ʒ���)
 * 
 * @author linhansheng
 */
public class TransCustomerQueryDao extends AbstractTransDao {

	/**
	 * �ӿڶ���ķ������ݸ�ʽ���ֶΣ� ҵ������ ҵ��˿� ҵ�����ݼ�� �ʷѣ�Ԫ�� �Ʒ�����  
	 * ��ͨ��ʽ   ����ʱ��    ҵ��ʹ��״̬  �۷�ʱ��   Ӫ��������Ϣ
	 */
	public List<TransCustomerRow> query(RequestParams reqParams) {
		StringBuffer querySql = new StringBuffer();
		String phone = reqParams.getTelNo();
		String beginDate = reqParams.getBeginDate();
		String endDate = reqParams.getEndDate();
        //һ����������ڶ�������ж�������
		List<String> serviceAreas = phoneServiceInAreas(phone);
		if (serviceAreas == null) {
			CsopLog.info("�����ڸ��û���" + phone + "����ҵ������Ϣ!");
			return null;
		}
		List<TransCustomerRow> allResults = new ArrayList<TransCustomerRow>();
		List<TransCustomerRow> subResults=null;
		for(String areaAbb:serviceAreas){
             //�жϸõ����Ƿ������ײ͵��� 
			 if(area.isPackageArea(areaAbb)){
				CsopLog.debug("��ѯ�û�["+phone+"] �� "+areaAbb+" �������Ƶ��ײ�ҵ�������");
				subResults=this.packageTransaction(areaAbb, phone, beginDate, endDate);
             }else{
                CsopLog.debug("��ѯ�û�["+phone+"] �� "+areaAbb+" �������ƵĻ���ҵ�������");
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
	 * ���ڻ���ҵ��Ĳ�ѯ
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
	         db = new BaseDao(DBConnector.getConnection(POOL_NAME));	
             rs = db.query(this.baseTransactionSql(areaAbb, phone, beginDate, endDate));
			 
             TransCustomerRow nRow = null;
             while(rs!=null&&rs.next()){
		          nRow = new TransCustomerRow();		 
				  nRow.setName(rs.getString("transaction"));
				  nRow.setDesc("ҵ������δ֪");
				  nRow.setPort(rs.getString("tran_code"));
				  nRow.setServiceState(rs.getInt("is_open")==0?"δ��ͨ":"��ͨ");
				  if(rs.getInt("is_open")!=0){
				    nRow.setOpenType(rs.getInt("book_type")==0?"��ҳ����":"�ֻ����ж���");
				    nRow.setOrderTime(rs.getString("open_date"));
				    nRow.setPayTime(rs.getString("kf_date"));
				    if(rs.getInt("is_charge")!=0){
				        if(rs.getInt("ywt_charge_type")==0)
				    	  nRow.setChargeType("���");  //�Ʒ�����	���¡��㲥
				        else if(rs.getInt("ywt_charge_type")==1){
					    	    nRow.setChargeType("����");
					    	    nRow.setCharge(rs.getInt("fee")+"Ԫ/��");
				               }
				        else if(rs.getInt("ywt_charge_type")==2)
					    	  nRow.setChargeType("�㲥");
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
	
	// ����ҵ�� ������� Sql ���
	String baseTransactionSql(String areaAbb, String phone, String beginDate,
			String endDate) {
		StringBuffer mainSql = new StringBuffer(" select base.id family,base.phone,base.stu_sequence,base.transaction,");
		mainSql.append("base.transaction_id,base.is_open,tlog.operator,tlog.reason,");
		mainSql.append("tlog.package_id,book_type,base.open_date,is_charge,");
		mainSql.append("nvl(ywt.fee,0)fee,ywt.type ywt_charge_type,kf.tran_code,kf.UPDATE_DATE kf_date from ( ");
		
		StringBuffer baseView = new StringBuffer();
		for (TransactionType type : TransactionType.values()) {
			baseView.append("select fv.id,fv.phone phone,fv.stu_sequence,'"
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
	    //ҵ����־
		mainSql.append(" )base left join ( ").append(this.lastBaseTransactionLog(areaAbb,phone,beginDate,endDate)).append(" ) tlog ");
		mainSql.append(" on tlog.family_id = base.id and tlog.stu_sequence = base.stu_sequence ");
		mainSql.append(" and  tlog.open = base.is_open and tlog.transaction = base.transaction_id ");
		//�۷Ѽ�¼
		mainSql.append(" left join ").append(areaAbb).append("_yw_kf_chargerecord kf ");
		mainSql.append(" on kf.family_id=base.id and kf.phone=base.phone and base.transaction_id=kf.transaction ");
		//��ѯ��Ӧ���ʷ�
		mainSql.append(" left join ").append(" yw_Transaction ywt");
		mainSql.append(" on ywt.TRANSACTION=base.transaction_id and ywt.AREA_ID=").append(area.getAreaIdByAbb(areaAbb));
	
		//��ѯ���� ʱ��
		
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
	 * ���µĻ���ҵ������־
	 */
	String lastBaseTransactionLog(String areaAbb,String phone,String beginDate,String endDate){
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.* from "+areaAbb+"_transaction_log a, ( ");
		sql.append(" select  b.family_id,b.stu_sequence,b.transaction,b.phone,b.open,max(b.open_date) open_date  from  "+areaAbb+"_transaction_log b");
		sql.append(" group by b.family_id,b.stu_sequence,b.transaction,b.open,b.phone  having b.open = 1 or b.open = 0 ) n ");
		sql.append(" where n.family_id=a.family_id and a.stu_sequence=n.stu_sequence and a.transaction = n.transaction and a.open = n.open and a.open_date= n.open_date ");
		return sql.toString();
	} 
	
	
	// �ײͲ�ѯ �������
	List<TransCustomerRow> packageTransaction(String areaAbb, String phone, String beginDate,
			String endDate) {
		BaseDao db = null;
		ResultSet rs = null;
		List<TransCustomerRow> rows = new ArrayList<TransCustomerRow>();
		try{
	         db = new BaseDao(DBConnector.getConnection(POOL_NAME));	
             rs = db.query(this.packageTransactionSql(areaAbb, phone, beginDate, endDate));
			 
             TransCustomerRow nRow = null;
             while(rs!=null&&rs.next()){
		          nRow = new TransCustomerRow();		 
				  nRow.setName(rs.getString("transaction"));
				  nRow.setDesc(rs.getString("remark"));
				  nRow.setPort(rs.getString("tran_code"));
				  nRow.setServiceState(rs.getInt("is_open")==0?"δ��ͨ":"��ͨ");
				  if(rs.getInt("is_open")!=0){
				    nRow.setOpenType(rs.getInt("book_type")==0?"��ҳ����":"�ֻ����ж���");
				    nRow.setOrderTime(rs.getString("open_date"));
				    nRow.setPayTime(rs.getString("kf_date"));
				    if(rs.getInt("is_charge")!=0){
				    
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
	
	// �ײͲ�ѯ ������� Sql
	
	/**
	 * ҵ������ ҵ��˿� ҵ�����ݼ�� �ʷѣ�Ԫ�� �Ʒ�����  
	 * ��ͨ��ʽ   ����ʱ��    ҵ��ʹ��״̬  �۷�ʱ��   Ӫ��������Ϣ
	 */
	String packageTransactionSql(String areaAbb, String phone, String beginDate,
			String endDate){
		 StringBuffer mainSql = new StringBuffer(" select pp.name transaction,pp.REMARK,pp.IS_FREE is_charge,pp.del is_open, ");
		 mainSql.append(" tlog.open_date,tlog.book_type ");
		 mainSql.append(" from ").append(areaAbb).append("_xj_family fa ");
		 mainSql.append(" left join ").append(areaAbb).append("_preferential_packager fp ");
		 mainSql.append(" on fa.id = fp.f_id and fp.phone = fa.kf_phone ");
		 mainSql.append(" left join preferential_package pp ").append(" on fp.pp_id=pp.id");
		 mainSql.append(" left join ( ").append(this.lastPackageTransactionLog(areaAbb)).append(" )tlog ");
		 mainSql.append(" on tlog.family_id=fa.id and tlog.phone=fa.kf_phone and tlog.open=1 ");
		 mainSql.append(" where fa.kf_phone='").append(phone).append("'");	
		 mainSql.append(" and fp.del = 1 ");//��ͨ���ײ�
		 if(!Checker.isNull(beginDate))
			 mainSql.append(" and to_char(fp.START_DATE,'YY-MM-DD')>='").append(beginDate).append("'");
		 if(!Checker.isNull(endDate))
			 mainSql.append(" and to_char(fp.END_DATE,'YY-MM-DD')<='").append(endDate).append("'");
	     return mainSql.toString();
	} 

	/*
	 * ���µ��ײ�ҵ��ı����־
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
	 * ����ҵ��
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
