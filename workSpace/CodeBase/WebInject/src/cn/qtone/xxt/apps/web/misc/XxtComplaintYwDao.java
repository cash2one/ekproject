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

	private final static String NOT_FIND_PHONE = "NOT_FOUND_PHONE";
	private final static String MATCH_YW_MODEL = "MATCH_YW_MODEL";
	
	
	//�˴��ļ�¼��Ϊ�˷��ʼ��������
	private List<ComplaintItem> needSendItems = new ArrayList<ComplaintItem>();
	
	
	public void insert(List<ComplaintItem> items) {
		Connection _conn = null;
		BaseDao db = null;
		List<String> sqls = new ArrayList<String>();
		try {
			 _conn = DBConnector.getConnection(SysCfg.DB_POOL_NAME);
			if (items != null && items.size() > 0)
				for (ComplaintItem item : items){
				   if(ItemsCheckHelper.getHelper().isNewItemJudgeFromDB(item)) //�Ƿ���Ҫ���˸ü�¼,�Ƿ����ظ���Ͷ��
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
            if("true".equals(infos.get(NOT_FIND_PHONE))||(infos.get(MATCH_YW_MODEL)!=null&&!"".equals(infos.get(MATCH_YW_MODEL)))){
            	int error_type = infos==null||"true".equals(infos.get(NOT_FIND_PHONE))?0:1;
            	error_type=error_type!=0?("NOT_RESULT".equals(infos.get(MATCH_YW_MODEL))?1:2):error_type;
            	
            	//������Ϊ 0���Ҳ����绰���� ��1:��ѧ����Ϣ��2:����ѧ����¼ ��3���������ݿ�ʱ��������
            	recordNotInserItem(item,error_type);
            }else{
            	//Ҫ�Դ���ʱ����� ���봦��
				StringBuffer sql =new StringBuffer(" insert into YW_COMPLAINT (PHONE,REMARK,FAMILY_ID,AREA_ID,SI_ID, "); //7
				sql.append("TOWN_NAME,SCHOOL_NAME,CLASS_NAME,STU_NAME,TRANPACKAGE_NAME,");;  //5
				sql.append("CREATE_ID,HANDLE_STATUS,FLAG,REASON_ID,REASON_OTHER,TSQD,HANDLE_TYPE,KHMYD,YHJXSY,COMPLAINT_LEVEL,CUSTOMER_TYPE,create_time,COMPLAINT_TIME,NEEDHANDLE_TIME)"); //8
				sql.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,10086,?,1,1,?,?,sysdate,");
				sql.append("to_date('"+item.getCreateTime()+"' , 'yyyy-mm-dd hh24:mi:ss'), to_date('"+YwComplaintUtil.reSetDeadline(item)+"' , 'yyyy-mm-dd hh24:mi:ss'))");
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
				stmt.setString(11, YwComplaintUtil.CREATE_ID); //������ ID
				stmt.setString(12, "0"); //������
				stmt.setString(13, "0"); //������
				stmt.setString(14, "-1");  //����ID
				stmt.setString(15, "��������"); //�������� 
				stmt.setString(16, "�˹�"); //��������
				stmt.setString(17, YwComplaintUtil.getComplaintLevelCode(item.getRank())); //Ͷ��ˮƽ
				stmt.setString(18, YwComplaintUtil.getCustomerTypeCode(item.getBrand())); //�ͻ�����
				stmt.execute();
				stmt.close();
            }
            
            if(infos!=null)
                infos.clear();
            infos = null;
            df = null;
		}catch(Exception e){
			e.printStackTrace();
			AppLoger.getSQLLogger().info(e.getMessage());
			recordNotInserItem(item,3);
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
	 * ����һ������� �����¼ 
	 * 1�� �Ҳ�����Ӧ�ĺ�����Ϣ                   type = 0
	 * 2�����ݺ����Ҳ�����Ӧ�ҳ� ѧ�� ����������Ϣ��         type = 1
	 * 3���ҵ�����ѧ����¼��Ϣ����ȷ����   type = 3
	 * 4�����뵽���ݿ�ʧ�ܵ�                            type = 4
	 * @param item
	 * @param type   
	 */
	void recordNotInserItem(ComplaintItem item,int type){
		String error_msg = "";
		switch(type){
		   case 0 : error_msg ="û�ҵ���Ӧ�ĵ绰����";break;
		   case 1 : error_msg ="û�ҵ���Ӧ��ѧ����Ϣ";break;
		   case 2 : error_msg ="�ҵ�����ѧ����¼��Ϣ����ȷ����";break;
		   case 3 : error_msg ="���ʧ�ܵļ�¼";break;
		}
		error_msg = error_msg +"  ---  ��ϸ��"+item.getUser()+","+item.getBrand()+","+item.getContent()+","+item.getCreateTime()+","+item.getDeadline()+","+item.getRank();
		
		//��¼�޷����в����ļ�¼
		AppLoger.getBusinessLogger().info(error_msg);		
		this.needSendItems.add(item);
		
	}
	
	/**
	 * �ʼ�������
	 * @return
	 */
	public String wrapperEMailMessageContent(){
		if(this.needSendItems.size()==0)
		   return null;
		
		StringBuffer message = new StringBuffer();
	    message.append("ע�⣺����Ͷ�߼�¼�޷���УѶͨͶ�߹����н����Զ�������˶����µ�Ͷ�߼�¼��");
	    message.append("<br/><br/>");
	    int index = 1;
		for(ComplaintItem item : needSendItems){
	    	message.append(index).append("��MISC��ˮ�� : ").append(item.getId());
	    	message.append("   ").append(item.getUser());
	    	message.append("      ").append(item.getBrand());
	    	message.append("      ").append(item.getCreateTime());
	    	message.append("      ").append(YwComplaintUtil.reSetDeadline(item));
	        message.append("      ").append(item.getRank());
	        message.append("<br/>      ").append(item.getContent());
	        message.append("<br/><br/>");
	        index++;
	    }
		needSendItems.clear();
		needSendItems = null;
		return message.toString();
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
		Map<String,String> infos = null;
		Statement stmt = null;
		ResultSet rs = null;
		try{
			List<String> abbList =getAreaAbbList(phone);
			int i=0;
			stmt = conn.createStatement();
			
			if(abbList==null||abbList.size()==0){
			   infos = new HashMap<String,String>();
               infos.put(NOT_FIND_PHONE, "true");
               return infos;
			}
			
			List<String[]> valuesList = new ArrayList<String[]>();
			String[] tempValue = null;
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
				while(rs!=null&&rs.next()){
					 tempValue = new String[]{
						   YwComplaintUtil.valueToString(rs.getString("school_id")),
						   YwComplaintUtil.valueToString(rs.getString("school_name")),
						   YwComplaintUtil.valueToString(rs.getString("area_id")),
						   YwComplaintUtil.valueToString(rs.getString("class_name")),
						   YwComplaintUtil.valueToString(rs.getString("student_name")),
						   YwComplaintUtil.valueToString(rs.getString("stu_sequence")),
						   YwComplaintUtil.valueToString(rs.getString("family_id")),
						   YwComplaintUtil.valueToString(rs.getString("school_id")),
						   YwComplaintUtil.valueToString(rs.getString("si_id")),
						   YwComplaintUtil.valueToString(rs.getString("town_name")),
						   YwComplaintUtil.valueToString(rs.getString("tranpackage_name")),
						   YwComplaintUtil.valueToString(rs.getString("charge")),
					 };
					 valuesList.add(tempValue);
				}
				infos = checkQueryResult(valuesList);
				rs.close();
			}
		}catch(Exception e){
			e.printStackTrace();
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
	 * ���ܲ�ѯ����Ӧ��ѧ�� ��ͥ��Ϣʱ����ֻ��һ����¼��Ӧʱ��ֱ�ӷ���
	 * ����ѯ��������¼ʱ��Ҫ�������µ���������жϣ����ʵ�����
     * ע�⣺�������һ���ҳ���Ӧ���ѧ����������Щѧ������ѧУ����ͬһ�� SI ��Ͻ�����   �µģ���ֱ�ӷ��ؿս��,֪ͨ���ֶ������Ϣ¼�����
	 * @param list
	 * @return
	 */
	Map<String,String> checkQueryResult(List<String[]> list){
		Map<String,String> infos = new HashMap<String,String>();
		//���ܲ�ѯ����Ӧ��ѧ�� ��ͥ��Ϣʱ����ֻ��һ����¼��Ӧʱ��ֱ�ӷ���
		//����ѯ��������¼ʱ��Ҫ�������µ���������жϣ����ʵ�����
        //ע�⣺�������һ���ҳ���Ӧ���ѧ����������Щѧ������ѧУ����ͬһ�� SI ��Ͻ�����   �µģ���ֱ�ӷ��ؿս��,֪ͨ���ֶ������Ϣ¼�����
		boolean isSameArea = false;
		boolean isSameSI = false;
		String lastArea ="";
		String lastSI = "";
		String[] tempValues = null; 
		if(list!=null&&list.size()>0){
			isSameArea = true;
			isSameSI = true;
			for(String[] temp:list){
				if(!"".equals(lastArea)&&!lastArea.equals(temp[2])){
					isSameArea = false;
					break;
				}else
					lastArea = temp[2];
				
				if(!"".equals(lastSI)&&!lastSI.equals(temp[8])){
					isSameSI = false;
					break;
				}else
					lastArea = temp[8];
				temp = null;
			}
		}
		
		//��ֻ��һ����¼ʱ �� ��ѯ�ļ�¼ͬ�� һ������ �� SI �ģ���ô�ͷ��ص�һ����¼
		if(list!=null&&(list.size()==1||(isSameArea&&isSameSI))){
			tempValues = list.get(0);
			infos.put("school_id", tempValues[0]);					
	        infos.put("school_name", tempValues[1]);	
	        infos.put("area_id", tempValues[2]);	
	        infos.put("class_name", tempValues[3]);	
	        infos.put("student_name", tempValues[4]);	
	        infos.put("stu_sequence", tempValues[5]);	
	        infos.put("family_id", tempValues[6]);	
	        infos.put("school_id", tempValues[7]);	
	        infos.put("si_id", tempValues[8]);	
	        infos.put("town_name", tempValues[9]);	
	        infos.put("tranpackage_name", tempValues[10]);	
	        infos.put("charge", tempValues[11]);
		}else if(list==null||list.size()==0){
			infos.put(MATCH_YW_MODEL, "NOT_RESULT");
		}else
			infos.put(MATCH_YW_MODEL, "MUITL");
		if(list!=null)
			list.clear();
		list = null;
		return infos;
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
			db = new BaseDao(DBConnector.getConnection(SysCfg.DB_POOL_NAME));
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
