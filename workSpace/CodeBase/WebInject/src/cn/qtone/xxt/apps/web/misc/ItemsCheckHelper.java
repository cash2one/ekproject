package cn.qtone.xxt.apps.web.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.elam.util.db.comom.BaseDao;
import cn.elam.util.db.comom.DBConnector;

/***
 * 
 * 判断是否出现重复的投诉，入库的判断
 * @author Ethan.Lam  2011-2-17
 *
 */
public class ItemsCheckHelper {

	public static ItemsCheckHelper checker = new ItemsCheckHelper();
	Map<String, String> itemsSet = new HashMap<String, String>();

	private ItemsCheckHelper() {

	}

	public static ItemsCheckHelper getHelper() {
		return checker;
	}

	/**
	 * 
	 * 是否是新的投诉数据（内存中）
	 * @param item
	 * @return
	 * 
	 */
	public boolean isNewItem(ComplaintItem item){
        if(itemsSet.containsKey(item.getId())){
        	if(item.getCreateTime().equals(itemsSet.get(item.getId())))
		        return false;
            else
            	itemsSet.put(item.getId(), item.getCreateTime()); //更新时间
        }else
        	itemsSet.put(item.getId(), item.getCreateTime()); //创建时间标识
        return true;
	}
	
	
	/**
	 * 
	 * 从数据库中判断是否是新的投诉记录
	 * 如果无 则需要新建一条记录，有则返回false
	 * @param item
	 * @return
	 */
	public boolean isNewItemJudgeFromDB(ComplaintItem item){
		BaseDao db = null;
		ResultSet rs=null;
		boolean isNewComplaint = true;
//		System.out.println(item.getId()+","+item.getUser()+","+item.getBrand()+","+item.getArea()+","+item.getContent());
		try{
			db = new BaseDao(DBConnector.getConnection(SysCfg.DB_POOL_NAME));
			db.preparedExeDB("select * from complaint_event_log where event_id =? and phone = ? and to_char(update_time,'yyyy-mm-dd hh24:mi:ss')=?");
			db.setString(1, item.getId());
			db.setString(2, item.getUser());
			db.setString(3, item.getCreateTime());
			rs = db.queryPreparedDB();
			if(rs!=null&&rs.next())
				isNewComplaint = false;
			rs.close();
			
			if(isNewComplaint){
				db.preparedExeDB("insert into complaint_event_log(event_id,phone,update_time,remark)values(?,?,to_date('"+item.getCreateTime()+"','yyyy-mm-dd hh24:mi:ss'),?)");
				db.setString(1, item.getId());
				db.setString(2, item.getUser());
				db.setString(3, item.getContent());
				db.excPreparedDB();
				db.close();
			}
			return isNewComplaint;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			   try {
					if(rs!=null)
					   rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			rs = null;
			db.close();
		}
		return isNewComplaint;
	}
	
	
}
