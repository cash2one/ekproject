import com.qtone.datasync.db.SqlScriptExecTest
import com.qtone.datasync.util.FileUtil
import com.qtone.datasync.db.DbUtils

import groovy.sql.*

public class SqlScriptExecImpl implements SqlScriptExecTest{
	public void exec(String sqlFile){
		String sqlFilePath = FileUtil.getFilePath(sqlFile)
		Sql sql = new DbUtils().newInstance()

		def scriptContent = new File(sqlFilePath).getText()
		sql.execute(scriptContent)

		sql.close()
	}	
}
