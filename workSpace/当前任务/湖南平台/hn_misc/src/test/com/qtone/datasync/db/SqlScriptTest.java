package com.qtone.datasync.db;

import com.qtone.datasync.util.MyGroovyLoader;

/**
 * @author ÑîÌÚ·É	2009-9-3 
 *
 */
public class SqlScriptTest {
	public static void main(String[] args) {
		SqlScriptExecTest test =  (SqlScriptExecTest)MyGroovyLoader.getInstance("com/qtone/datasync/db/SqlScriptExecImpl.groovy");
		test.exec("resources/test.sql");
	}
}
