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
	public List<ResultRow> query(RequestParams reqParams) {
		StringBuffer querySql = new StringBuffer();
		String phone = reqParams.getTelNo();
		String beginTime = reqParams.getBeginDate();
		String endTime = reqParams.getEndDate();

		List<String> serviceAreas = phoneServiceInAreas(phone);
		if (serviceAreas == null) {
			CsopLog.info("�����ڸ��û���" + phone + "����ҵ������Ϣ!");
			return null;
		}
		return null;
	}
	
	/**
	 * ���ڻ���ҵ��Ĳ�ѯ
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
				  nRow.setDesc(rs.getString("ҵ������δ֪"));
				  nRow.setPort(rs.getString("ҵ�����δ֪"));
				  nRow.setServiceState(rs.getInt("is_open")==0?"δ��ͨ":"��ͨ");
				  if(rs.getInt("is_open")!=0){
				    nRow.setOpenType(rs.getInt("book_type")==0?"��ҳ����":"�ֻ����ж���");
				    nRow.setOrderTime(rs.getString("open_date"));
				    nRow.setPayTime(rs.getString("�۷�ʱ��δ֪"));
				    nRow.setChargeType(rs.getInt("is_charge")==0?"���":"�շ�");
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
	

	// �ײͲ�ѯ �������
	void packageTransaction(String areaAbb, String phone, String beginDate,
			String endDate) {

		
	}

	// ����ҵ�� ������� Sql ���
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
	 * ���µ�ҵ������־
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
     * ҵ�������Ϣ
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
