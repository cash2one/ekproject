package cn.qtone.xxt.apps.web.misc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.elam.util.db.comom.BaseDao;
import cn.elam.util.db.comom.DBConnector;
import cn.qtone.xxt.apps.web.AppLoger;

/**
 * 
 * ��Ӧ�� XXT �� YW_COMPLAINT ��
 * 
 * @author Ethan.Lam 2011-2-15
 * 
 */
public class XxtComplaintYwDao {

	private final static String POOL_NAME = "zjxxt";
	private final static String NOT_FIND_PHONE = "NOT_FOUND_PHONE";
	private final static String NOT_FIND_YW = "NOT_FOUND_YW";
	
	public void insert(List<ComplaintItem> items) {
		Connection _conn = null;
		BaseDao db = null;
		List<String> sqls = new ArrayList<String>();
		try {
			 _conn = DBConnector.getConnection(POOL_NAME);
			if (items != null && items.size() > 0)
				for (ComplaintItem item : items){
					insertSqlWrapper(_conn,item);
				}
			_conn.close();
		} catch (Exception e) {
			AppLoger.getSQLLogger().info(e.getMessage());
		}finally{
			try {
				if(!_conn.isClosed())
				  _conn.close();
				_conn = null;
			} catch (SQLException e) {
			}
		}
	}

	
	/**
	 * ��ɲ������
	 * @param conn
	 * @param item
	 * @return
	 */
	void insertSqlWrapper(Connection conn,ComplaintItem item){
		//�����������������Ͳ��뵽���ݿ��У����벻���� �ͼ�¼����Ͷ�߼�¼
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PreparedStatement stmt = null;
		try{
			Map<String,String> infos = matchFamilyAndStudentInfosByPhone(conn,item.getUser());
            if("true".equals(infos.get(NOT_FIND_PHONE))||"true".equals(infos.get(NOT_FIND_YW))){
            	int error_type = "true".equals(infos.get(NOT_FIND_PHONE))?0:1;
            	recordNotInserItem(item,error_type);
            }else{
            	//Ҫ�Դ���ʱ����� ���봦��
				StringBuffer sql =new StringBuffer(" insert into YW_COMPLAINT (PHONE,REMARK,FAMILY_ID,AREA_ID,SI_ID, "); //7
				sql.append("TOWN_NAME,SCHOOL_NAME,CLASS_NAME,STU_NAME,TRANPACKAGE_NAME,");;  //5
				sql.append("HANDLER_ID,HANDLE_STATUS,HANDLE_RESULT,REASON_ID,REASON_OTHER,TSQD,HANDLE_TYPE,KHMYD,YHJXSY,COMPLAINT_LEVEL,CUSTOMER_TYPE,create_time,HANDLE_TIME,COMPLAINT_TIME,NEEDHANDLE_TIME)"); //8
				sql.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,10086,?,1,1,?,?,sysdate,sysdate,");
				sql.append("to_date('"+item.getCreateTime()+"' , 'yyyy-mm-dd hh24:mi:ss'), to_date('"+reSetDeadline(item)+"' , 'yyyy-mm-dd hh24:mi:ss'))");
				stmt = conn.prepareStatement(sql.toString());
				stmt.setString(1, item.getUser());
				stmt.setString(2, item.getContent());
				stmt.setString(3, infos.get("family_id"));
				stmt.setString(4, infos.get("area_id"));
				stmt.setString(5, infos.get("si_id"));
				stmt.setString(6, infos.get("town_name"));
				stmt.setString(7, infos.get("school_name"));
				stmt.setString(8, infos.get("class_name"));
				stmt.setString(9, infos.get("student_name"));
				stmt.setString(10,infos.get("tranpackage_name"));
				stmt.setString(11, YwComplaintUtil.HANDLER_ID); //������ ID
				stmt.setString(12, "2"); //������
				stmt.setString(13, "�������"); //������
				stmt.setString(14, "-1");  //����ID
				stmt.setString(15, "��������"); //�������� 
				stmt.setString(16, "�˹�"); //��������
				stmt.setString(17, YwComplaintUtil.getComplaintLevelCode(item.getRank())); //Ͷ��ˮƽ
				stmt.setString(18, YwComplaintUtil.getCustomerTypeCode(item.getBrand())); //�ͻ�����
				stmt.execute();
				stmt.close();
            }
            infos.clear();
            infos = null;
            df = null;
		}catch(Exception e){
			e.printStackTrace();
			AppLoger.getSQLLogger().info(e.getMessage());
			recordNotInserItem(item,2);
		}finally{
		    try {
			      if(stmt!=null)	
				     stmt.close();
			      stmt = null;
			 } catch (SQLException e) {
				  e.printStackTrace();
			 }
		}
	}
	
	
	/**
	 * 
	 * ���¼��� �����������
	 * @param item
	 * @return
	 */
	String reSetDeadline(ComplaintItem item){
		//����ʱ��Ҫ����
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			long difTime = df.parse(item.getDeadline()).getTime() - df.parse(item.getCreateTime()).getTime();
			String newTime = df.format(new Date(df.parse(item.getCreateTime()).getTime()+(difTime/2)));
			df = null;
			return newTime;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return item.getDeadline(); 
	}
	
	/**
	 * 
	 * ����һ������� �����¼ 
	 * 1�� �Ҳ�����Ӧ�ĺ�����Ϣ                   type = 0
	 * 2�����ݺ����Ҳ�����Ӧ�ҳ� ѧ�� ����������Ϣ��         type = 1
	 * 3�����뵽���ݿ�ʧ�ܵ�                            type = 2
	 * @param item
	 * @param type   
	 */
	void recordNotInserItem(ComplaintItem item,int type){
		String error_msg = (type==0?"û�ҵ���Ӧ�ĵ绰����":(type==1?"û�ҵ���Ӧ��ѧ����Ϣ":"��¼���ʧ��"));
		error_msg = error_msg +"  ---  ��ϸ��"+item.getUser()+","+item.getBrand()+","+item.getContent()+","+item.getCreateTime()+","+item.getDeadline()+","+item.getRank();
		AppLoger.getBusinessLogger().info(error_msg);		
		
	}
	
	
	/**
	 * 
	 * ���ݺ��� �ҵ���Ӧ��ѧ����ҳ���Ϣ
	 * �����һ���ҳ���Ӧ���ѧ��ʱ��������Զ���������޷�����ʵ����ƥ�䵽��Ӧ��ѧ����Ϣ������Ĭ�Ϲ�����
	 * ��һ����ѯ�õ���ѧ����Ϣ
	 * 
	 * @param conn
	 * @param phone
	 */
	Map<String,String> matchFamilyAndStudentInfosByPhone(Connection conn,String phone){
		Map<String,String> infos = new HashMap<String,String>();
		Statement stmt = null;
		ResultSet rs = null;
		try{
			List<String> abbList =getAreaAbbList(phone);
			int i=0;
			stmt = conn.createStatement();
			
			if(abbList==null||abbList.size()==0){
               infos.put(NOT_FIND_PHONE, "true");
               return infos;
			}else
			   infos.put(NOT_FIND_PHONE, "false");
			
			for(String abb : abbList){
				StringBuffer sql=new StringBuffer("");
				String stu_table = abb+"_xj_student";
				String fam_table = abb+"_xj_family";
				String class_table = abb+"_xj_stu_class";
				String customer_table = abb+"_tranpackage_customer";
				
				sql.append("select sch.id school_id,sch.school_name,sch.area_id,c.class_name,stu.name student_name,");
				sql.append("f.stu_sequence,f.id family_id,f.name family_name,f.relationship,f.phone,f.bind_phone,qas.id si_id,tw.name town_name,dd.name tranpackage_name,cc.charge ");
				sql.append("from ").append(fam_table).append(" f ");
				sql.append("left join ").append(class_table).append(" sc on f.stu_sequence=sc.stu_sequence ");
				sql.append("left join ").append(stu_table).append(" stu on f.stu_sequence=stu.stu_sequence ");
				sql.append("left join xj_school sch on sc.school_id=sch.id left join qx_user_school qus on qus.school_id=sch.id left join qx_admin_si qas on qus.user_id=qas.id ");
				sql.append("left join xj_class c on sc.class_id=c.id ");
				sql.append("left join "+customer_table+" cc on cc.family_id=f.id ");
				sql.append("left join tranpackage_define dd on DD.SALEMODALID = cc.xxt_salemodalid ");
				sql.append("left join town tw on tw.id=sch.town_id ");
				sql.append("where f.phone='").append(phone).append("'");
//				System.out.println(sql.toString());
				
				rs = stmt.executeQuery(sql.toString());
				if(rs!=null&&rs.next()){
                     infos.put("school_id", rs.getString("school_id"));					
                     infos.put("school_name", rs.getString("school_name"));	
                     infos.put("area_id", rs.getString("area_id"));	
                     infos.put("class_name", rs.getString("class_name"));	
                     infos.put("student_name", rs.getString("student_name"));	
                     infos.put("stu_sequence", rs.getString("stu_sequence"));	
                     infos.put("family_id", rs.getString("family_id"));	
                     infos.put("school_id", rs.getString("school_id"));	
                     infos.put("si_id", rs.getString("si_id"));	
                     infos.put("town_name", rs.getString("town_name"));	
                     infos.put("tranpackage_name", rs.getString("tranpackage_name"));	
                     infos.put("charge", rs.getString("charge"));
                     infos.put(NOT_FIND_YW, "false");
					 break;
				}
				rs.close();
			}
			
		}catch(Exception e){
			AppLoger.getSQLLogger().info(e.getMessage());
		}finally{
				try {
					if(rs!=null)
					    rs.close();
					rs= null;
					if(stmt!=null)
						stmt.close();
					stmt = null;
				} catch (SQLException e) {
				}
			return infos;	
		}
	}
	
	 /**
	  * 
	  * ȫ�ֱ��в�ѯ���ֻ����������ĵ��� abb 
	  * @param phone
	  * @return
	  */
	 List<String> getAreaAbbList(String phone){
		BaseDao db = null;
		List<String> abbList = null;//��ȫ�ֱ��в�ѯ���ֻ����������ĵ���ID
		ResultSet rs=null;
		try {
			db = new BaseDao(DBConnector.getConnection(POOL_NAME));
			abbList =new ArrayList<String>();
			db.preparedExeDB("select distinct abb from family_phone where phone = ?");
			db.setString(1, phone);
			rs = db.queryPreparedDB();
			while(rs.next())
			    abbList.add(rs.getString(1));
			rs.close();
		} catch (Exception e) {			
			AppLoger.getSQLLogger().info(e.getMessage());
		}finally{
			try {
				if(rs!=null)
				   rs.close();
				db.close();
				db = null;
				rs = null;
			} catch (SQLException e) {
			}
		}
		return abbList;
	}
	 
	
}
