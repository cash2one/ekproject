package cn.qtone.xxt.csop.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.qtone.xxt.csop.dao.comom.BaseDao;
import cn.qtone.xxt.csop.dao.comom.DBConnector;
import cn.qtone.xxt.csop.dao.inter.ResultRow;
import cn.qtone.xxt.csop.webservices.bean.RequestParams;

public abstract class AbstractTransDao<Params extends RequestParams> {

	protected AreaUtil area;
	protected static String POOL_NAME = "xxt";
	
	public AbstractTransDao() {
		area = AreaUtil.getService();
	}

	/**
	 * ���ݺ��붨λ���ֻ�������Щ����
	 * ����һ��������ڶ�����������
	 * @param phone  Ŀ���ֻ��û� ���� 
	 * @return
	 */
	protected List<String> phoneServiceInAreas(String phone){
		List<String> serviceAreas = null;
		StringBuffer familySql = new StringBuffer();
		for(String abb :area.listAreaAbbs()){
             familySql.append("select '"+abb+"' abb from "+abb+"_xj_family where phone ='").append(phone).append("'");			
             familySql.append(" union ");
		}
		familySql.delete(familySql.length()-" union ".length(), familySql.length());
		BaseDao db=null;
		ResultSet rs = null;
		try{
		   db = new BaseDao(DBConnector.getConnection(POOL_NAME));	
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
	 * �Ƿ��ײ͵���
	 * @param areaAbb
	 * @return
	 */
	protected boolean isPackageArea(String areaAbb){
		return area.isPackageArea(areaAbb);
	}
	
	
	public abstract List<ResultRow> query(Params requsetParams);
	
	
}
