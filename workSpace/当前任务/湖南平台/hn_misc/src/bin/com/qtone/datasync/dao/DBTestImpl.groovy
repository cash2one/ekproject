import com.qtone.datasync.dao.IDBTest
import com.qtone.datasync.db.*

import groovy.sql.*

public class DBTestImpl implements IDBTest{
	public void query(String str){
		Sql sql = new DbUtils().newInstance();
		sql.eachRow(str){row->
			println row.school_name
		}
	}
}