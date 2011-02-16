package cn.qtone.xxt.apps.web.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.elam.util.db.comom.BaseDao;
import cn.elam.util.db.comom.DBConnector;

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
	 * �Ƿ����µ�Ͷ�����ݣ��ڴ��У�
	 * @param item
	 * @return
	 * 
	 */
	public boolean isNewItem(ComplaintItem item){
        if(itemsSet.containsKey(item.getId())){
        	if(item.getCreateTime().equals(itemsSet.get(item.getId())))
		        return false;
            else
            	itemsSet.put(item.getId(), item.getCreateTime()); //����ʱ��
        }else
        	itemsSet.put(item.getId(), item.getCreateTime()); //����ʱ���ʶ
        return true;
	}
	
	
	/**
	 * 
	 * �����ݿ����ж��Ƿ����µ�Ͷ�߼�¼
	 * ����� ����Ҫ�½�һ����¼�����򷵻�false
	 * @param item
	 * @return
	 */
	public boolean isNewItemJudgeFromDB(ComplaintItem item){
		BaseDao db = null;
		ResultSet rs=null;
		boolean isNewComplaint = true;
		try{
			db = new BaseDao(DBConnector.getConnection("zjxxt"));
			db.preparedExeDB("select * from complaint_event_log where event_id =? and phone = ? and to_char(update_time,'yyyy-mm-dd hh24:mi:ss')=?");
			db.setString(1, item.getId());
			db.setString(2, item.getUser());
			db.setString(3, item.getCreateTime());
			rs = db.queryPreparedDB();
			if(rs!=null&&rs.next())
				isNewComplaint = false;
			rs.close();
			
			if(isNewComplaint){
				db.preparedExeDB("insert into complaint_event_log(event_id,phone,update_time)values(?,?,?)");
				db.setString(1, item.getId());
				db.setString(2, item.getUser());
				db.setString(3, item.getCreateTime());
				db.excPreparedDB();
				db.close();
			}
			return isNewComplaint;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
				try {
					if(rs!=null&&!rs.isClosed())
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
