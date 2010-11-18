package com.qtone.datasync.db;

import groovy.sql.Sql;

import com.qtone.datasync.util.MyGroovyLoader;

/**
 * @author ÑîÌÚ·É	2009-9-2 
 *
 */
public class DbUtils implements IGroovyDb {

	public Sql newInstance() {
		IGroovyDb obj = (IGroovyDb)MyGroovyLoader.getInstance("com/qtone/datasync/db/GroovyDbImpl.groovy");
		return obj.newInstance();
	}
	
}
