package cn.elam.util.db.impl;

import java.sql.Connection;
import java.util.Map;

import cn.elam.util.db.comom.DBConnector;
import cn.elam.util.db.inter.DataModel;
import cn.elam.util.db.inter.PersistAction;

/**
 * 简单封装的持久化接口
 * @author Ethan.Lam   2011-2-27
 *
 * @param <Model>
 */
public class PersistWrapperDao<Model extends DataModel> {

	private String DB_CONNECTION = "";

	public PersistWrapperDao(String POOL_NAME) {
		this.DB_CONNECTION = POOL_NAME;
	}

	/**
	 * 持久化
	 * @param queryAction
	 * @param sql
	 * @returnd
	 * @throws Exception
	 */
	public void persist(PersistAction<Model> persistAction,
			Model data,String persistTableName) throws Exception {
		Connection db = null;
		BaseDao dao = null;
		try {
			
			Map<String,Object> persistValueSet = persistAction.persistParamValues(data);
			if(persistValueSet==null||persistValueSet.size()==0)
				return;
			
			db = DBConnector.getConnection(DB_CONNECTION);
			dao = new BaseDao(db);
            StringBuffer insertSql = new StringBuffer();
            StringBuffer paramStr  = new StringBuffer();
            StringBuffer valuesStr = new StringBuffer();
            
            for(String paramName : persistValueSet.keySet()){
            	paramStr.append(",").append(paramName);
            	valuesStr.append(",").append("?");
            }
            
            insertSql.append("insert into ").append(persistTableName).append("");
            insertSql.append("(").append(paramStr.substring(1)).append(")");
            insertSql.append("values(").append(valuesStr.substring(1)).append(")");
            System.out.println(insertSql.toString());
            dao.preparedExeDB(insertSql.toString());
     
            int parameterIndex = 1;
            for(Object value : persistValueSet.values()){
            	dao.setObject(parameterIndex,value);
            	parameterIndex++;
            }
            dao.excPreparedDB();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.close();
		}

	}
	
}
