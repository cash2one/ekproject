import com.qtone.datasync.db.IGroovyDb
import groovy.sql.*

public class GroovyDbImpl implements IGroovyDb{
	public Sql newInstance(){
		return Sql.newInstance("jdbc:oracle:thin:@61.142.80.178:1521:orcl","hnxxt","hnxxt","oracle.jdbc.driver.OracleDriver")
	}
}