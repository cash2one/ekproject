package cn.qtone.xxt.apps.web.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import cn.elam.util.db.comom.BaseDao;
import cn.elam.util.db.comom.DBConnector;
import cn.elam.util.db.comom.DaoException;

/**
 * 
 * 对应于 XXT 的 YW_COMPLAINT 表
 * 
 * @author Ethan.Lam 2011-2-15
 * 
 */
public class XxtComplaintYwDao {

	private final static String POOL_NAME = "zjxxt";

	public void insert(List<ComplaintItem> items) {
		BaseDao db = null;
		List<String> sqls = new ArrayList<String>();
		try {
			db = new BaseDao(DBConnector.getConnection(POOL_NAME));
			if (items != null && items.size() > 0)
				for (ComplaintItem item : items)
				   sqls.add("");
			db.executeBatch(sqls);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	String insertSqlWrapper(ComplaintItem item){
		return " insert into YW_COMPLAINT_TYPE (COMPLAINT_TIME,PHONE,)";
	}
	
	
	public void addinit(){
		try{
			String phone="";
			List<String> abbList =getAreaAbbList(request,phone);
			int i=0;
			for(String abb : abbList){
				StringBuffer sql=new StringBuffer("");
				String stu_table = abb+"_xj_student";
				String fam_table = abb+"_xj_family";
				String class_table = abb+"_xj_stu_class";
				String customer_table = abb+"_tranpackage_customer";
				
				sql.append("select sch.id school_id,sch.school_name,sch.area_id,c.class_name,stu.name student_name,");
				sql.append("f.stu_sequence,f.id family_id,f.name family_name,f.relationship,f.phone,f.bind_phone,qas.company,tw.name town_name,dd.name tranpackage_name,cc.charge ");
				sql.append("from ").append(fam_table).append(" f ");
				sql.append("left join ").append(class_table).append(" sc on f.stu_sequence=sc.stu_sequence ");
				sql.append("left join ").append(stu_table).append(" stu on f.stu_sequence=stu.stu_sequence ");
				sql.append("left join xj_school sch on sc.school_id=sch.id left join qx_user_school qus on qus.school_id=sch.id left join qx_admin_si qas on qus.user_id=qas.id ");
				sql.append("left join xj_class c on sc.class_id=c.id ");
				sql.append("left join "+customer_table+" cc on cc.family_id=f.id ");
				sql.append("left join tranpackage_define dd on DD.SALEMODALID = cc.xxt_salemodalid ");
				sql.append("left join town tw on tw.id=sch.town_id ");
				sql.append("where f.phone=?");
				System.out.println(sql.toString());
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		}
	}

	
}
