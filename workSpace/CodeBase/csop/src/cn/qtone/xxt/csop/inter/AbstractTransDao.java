package cn.qtone.xxt.csop.inter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.qtone.xxt.csop.dao.comom.BaseDao;
import cn.qtone.xxt.csop.dao.comom.DBConnector;
import cn.qtone.xxt.csop.dao.impl.AreaUtil;
import cn.qtone.xxt.csop.webservices.bean.RequestParams;

public abstract class AbstractTransDao{

	protected AreaUtil area;
	protected static String POOL_NAME = "xxt";
	
	public AbstractTransDao() {
		area = AreaUtil.getService();
	}

	/**
	 * 根据号码定位该手机处于那些地区
	 * 考虑一个号码存在多个地区的情况
	 * @param phone  目标手机用户 号码 
	 * @return
	 */
	protected List<String> phoneServiceInAreas(String phone){
		List<String> serviceAreas = null;
		StringBuffer familySql = new StringBuffer();
		for(String abb :area.listAreaAbbs()){
             familySql.append("select '"+abb+"' abb from "+abb+"_xj_family fa where phone ='").append(phone).append("'");
             familySql.append(" and exists (select 1 from "+abb+"_xj_student a,"+abb+"_xj_stu_class b,xj_class c ");
             familySql.append(" where a.stu_sequence = b.stu_sequence and b.class_id = c.id and c.class_type=1 and in_school=1 ");
             familySql.append(" and a.stu_sequence = fa.stu_sequence) ");
             familySql.append(" union ");
		}
		familySql.delete(familySql.length()-" union ".length(), familySql.length());
		BaseDao db=null;
		ResultSet rs = null;
		try{
		   db = new BaseDao(POOL_NAME,DBConnector.getConnection(POOL_NAME));	
		   rs = db.query(familySql.toString());	
		   serviceAreas = new ArrayList<String>();
		   while(rs!=null&&rs.next())			
			   serviceAreas.add(rs.getString(1).trim());
		   return serviceAreas;	   
		}catch(Exception e){
           e.printStackTrace();			
		}finally{
			try {
				if(rs!=null)
				    rs.close();
				rs=null;
				db.close();
				db = null;
			} catch (SQLException e) {
			}
		}
		return null;
	}
	
	
	/**
	 * 
	 * 是否套餐地区
	 * @param areaAbb
	 * @return
	 */
	protected boolean isPackageArea(String areaAbb){
		return area.isPackageArea(areaAbb);
	}
	
	
}
