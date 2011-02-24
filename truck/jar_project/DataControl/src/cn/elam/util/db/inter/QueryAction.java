package cn.elam.util.db.inter;

import java.sql.ResultSet;

/**
 * 
 * @author Ethan.Lam 2011-2-23
 * 
 * @param <DataModel>
 */
public interface QueryAction<Model extends DataModel> {

	public Model wrapperItem(ResultSet rs) throws Exception;

}
