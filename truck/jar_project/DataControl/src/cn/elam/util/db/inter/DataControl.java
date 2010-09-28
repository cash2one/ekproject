package cn.elam.util.db.inter;

import java.sql.ResultSet;
import java.util.List;

import cn.elam.util.db.comom.DaoException;
import cn.elam.util.db.comom.PageModel;


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
