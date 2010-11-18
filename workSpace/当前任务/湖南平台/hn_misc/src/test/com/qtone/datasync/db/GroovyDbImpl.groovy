import com.qtone.datasync.db.IGroovyDb
import groovy.sql.*

public class GroovyDbImpl implements IGroovyDb{
	public Sql newInstance(){
	System.out.println("11111");
		return Sql.newInstance("jdbc:oracle:thin:@211.142.221.221:15210:hndb1","hnxxt","hnxxt090625","oracle.jdbc.driver.OracleDriver")
	}
}