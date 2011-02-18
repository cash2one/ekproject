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
 * 对应于 XXT 的 YW_COMPLAINT 表
 * 
 * @author Ethan.Lam 2011-2-15
 * 
 */
public class XxtComplaintYwDao {

	private final static String NOT_FIND_PHONE = "NOT_FOUND_PHONE";
	private final static String MATCH_YW_MODEL = "MATCH_YW_MODEL";
	
	
	//此处的记录是为了发邮件做缓存的
	private List<ComplaintItem> needSendItems = new ArrayList<ComplaintItem>();
	
	
	public void insert(List<ComplaintItem> items) {
		Connection _conn = null;
		BaseDao db = null;
		List<String> sqls = new ArrayList<String>();
		try {
			 _conn = DBConnector.getConnection(SysCfg.DB_POOL_NAME);
			if (items != null && items.size() > 0)
				for (ComplaintItem item : items){
				   if(ItemsCheckHelper.getHelper().isNewItemJudgeFromDB(item)) //是否需要过滤该记录,是否是重复的投诉
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
	 * 组成插入语句
	 * @param conn
	 * @param item
	 * @return
	 */
	void insertSqlWrapper(Connection conn,ComplaintItem item){
		//加入号码符合条件，就插入到数据库中，加入不符合 就记录该条投诉记录
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PreparedStatement stmt = null;
		try{
			Map<String,String> infos = matchFamilyAndStudentInfosByPhone(conn,item.getUser());
            if("true".equals(infos.get(NOT_FIND_PHONE))||(infos.get(MATCH_YW_MODEL)!=null&&!"".equals(infos.get(MATCH_YW_MODEL)))){
            	int error_type = infos==null||"true".equals(infos.get(NOT_FIND_PHONE))?0:1;
            	error_type=error_type!=0?("NOT_RESULT".equals(infos.get(MATCH_YW_MODEL))?1:2):error_type;
            	
            	//出错定义为 0：找不到电话号码 ；1:无学生信息；2:多条学生记录 ；3：插入数据库时发生错误
            	recordNotInserItem(item,error_type);
            }else{
            	//要对处理时间进行 减半处理
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
				stmt.setString(11, YwComplaintUtil.CREATE_ID); //处理人 ID
				stmt.setString(12, "0"); //待处理
				stmt.setString(13, "0"); //处理结果
				stmt.setString(14, "-1");  //理由ID
				stmt.setString(15, "其他理由"); //其他理由 
				stmt.setString(16, "人工"); //处理类型
				stmt.setString(17, YwComplaintUtil.getComplaintLevelCode(item.getRank())); //投诉水平
				stmt.setString(18, YwComplaintUtil.getCustomerTypeCode(item.getBrand())); //客户类型
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
	 * 出现一下情况的 必须记录 
	 * 1‘ 找不到对应的号码信息                   type = 0
	 * 2、根据号码找不到对应家长 学生 （订购）信息的         type = 1
	 * 3、找到多条学生记录信息，不确定性   type = 3
	 * 4、插入到数据库失败的                            type = 4
	 * @param item
	 * @param type   
	 */
	void recordNotInserItem(ComplaintItem item,int type){
		String error_msg = "";
		switch(type){
		   case 0 : error_msg ="没找到对应的电话号码";break;
		   case 1 : error_msg ="没找到对应的学生信息";break;
		   case 2 : error_msg ="找到多条学生记录信息，不确定性";break;
		   case 3 : error_msg ="入库失败的记录";break;
		}
		error_msg = error_msg +"  ---  详细："+item.getUser()+","+item.getBrand()+","+item.getContent()+","+item.getCreateTime()+","+item.getDeadline()+","+item.getRank();
		
		//记录无法进行操作的记录
		AppLoger.getBusinessLogger().info(error_msg);		
		this.needSendItems.add(item);
		
	}
	
	/**
	 * 邮件的内容
	 * @return
	 */
	public String wrapperEMailMessageContent(){
		if(this.needSendItems.size()==0)
		   return null;
		
		StringBuffer message = new StringBuffer();
	    message.append("注意：以下投诉记录无法在校讯通投诉管理中进行自动处理，请核对以下的投诉记录！");
	    message.append("<br/><br/>");
	    int index = 1;
		for(ComplaintItem item : needSendItems){
	    	message.append(index).append("：MISC流水号 : ").append(item.getId());
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
	 * 根据号码 找到对应的学生与家长信息
	 * 当面对一个家长对应多个学生时的情况，自动处理程序无法按照实际来匹配到对应的学生信息，次数默认关联到
	 * 第一个查询得到的学生信息
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
	 * 当能查询到对应的学生 家庭信息时并且只有一条记录对应时，直接返回
	 * 当查询到有条记录时，要根据如下的情况做出判断，并适当返回
     * 注意：如果出现一个家长对应多个学生，并且这些学生所在学校不是同一个 SI 管辖或地区   下的，就直接返回空结果,通知其手动完成信息录入操作
	 * @param list
	 * @return
	 */
	Map<String,String> checkQueryResult(List<String[]> list){
		Map<String,String> infos = new HashMap<String,String>();
		//当能查询到对应的学生 家庭信息时并且只有一条记录对应时，直接返回
		//当查询到有条记录时，要根据如下的情况做出判断，并适当返回
        //注意：如果出现一个家长对应多个学生，并且这些学生所在学校不是同一个 SI 管辖或地区   下的，就直接返回空结果,通知其手动完成信息录入操作
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
		
		//当只有一条纪录时 或 查询的记录同属 一个地区 和 SI 的，那么就返回第一条记录
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
	  * 全局表中查询该手机号码所属的地区 abb 
	  * @param phone
	  * @return
	  */
	 List<String> getAreaAbbList(String phone){
		BaseDao db = null;
		List<String> abbList = null;//在全局表中查询该手机号码所属的地区ID
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
