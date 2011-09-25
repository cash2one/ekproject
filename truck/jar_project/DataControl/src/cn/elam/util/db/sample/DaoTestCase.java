package cn.elam.util.db.sample;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.elam.util.db.impl.PersistWrapperDao;
import cn.elam.util.db.impl.QueryWrapperDao;
import cn.elam.util.db.inter.DataModel;
import cn.elam.util.db.inter.PersistAction;
import cn.elam.util.db.inter.QueryAction;

/**
 * 测试例子
 * 
 * @author Ethan.Lam 2011-2-27
 * 
 */
public class DaoTestCase {

	public static void main(String... args) throws Exception {
       
	    querySample();
		
		//持久化	
//		persistSample();
	}
	
	
	/**
	 * 持久化	
	 * @throws Exception
	 */
	public static void persistSample() throws Exception{
		
		AreaModel data = new AreaModel();
		
		new PersistWrapperDao<AreaModel>("xxt").persist(new PersistAction<AreaModel>(){

			public Map<String, Object> persistParamValues(AreaModel data)
					throws Exception {
				// TODO Auto-generated method stub
				Map<String,Object> valueSet = new HashMap<String,Object>();
				valueSet.put("name", data.getName());
				return valueSet;
			}
			
		}, data, "eimport_task_status");
		
		
	}
	
	
	
	//查询接口测试
	public static void querySample() throws Exception{
 		StringBuffer sql =new StringBuffer("select * from area ");
		
 		List<AreaModel> list = new QueryWrapperDao<AreaModel>("xxt").list(new QueryAction<AreaModel>(){
			
			public AreaModel wrapperItem(ResultSet rs) throws Exception {
				// TODO Auto-generated method stub
				AreaModel model = new AreaModel();
				model.setId(rs.getInt("id"));
				model.setName(rs.getString("name"));
				model.setAbb(rs.getString("abb"));
				model.setCode(rs.getString("code"));
				return model;
			}
		}, sql.toString(), 1, 10);
		
		
		for(AreaModel area : list){
			 System.out.println(area.getName()+" "+area.getAbb());
		}
	}

}

class AreaModel implements DataModel {
 
	private int id;
	private String name;
	private String abb;
	private String code;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAbb() {
		return abb;
	}
	public void setAbb(String abb) {
		this.abb = abb;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	 

}
