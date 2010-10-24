package cn.qtone.xxt.csop.dao.comom;

import java.sql.ResultSet;
import java.util.List;



public interface DataControl {

	public ResultSet query(String sql) throws DaoException;

	public boolean update(String sql) throws DaoException;

	public boolean executeBatch(List<String> sqls) throws DaoException;

	public boolean execute(String sql) throws DaoException;
	
	public void preparedExeDB(String sql) throws DaoException;
	
	public ResultSet queryPreparedDB() throws DaoException;
	
	public boolean excPreparedDB() throws DaoException;
	
    public ResultSet queryByPage(String sql,int pageIndex,int pagePerNum) throws DaoException;

    public PageModel getPage() throws DaoException;
	
}
