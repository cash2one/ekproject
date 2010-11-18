package com.qtone.datasync.dao;

import org.testng.annotations.Test;

import com.qtone.datasync.util.MyGroovyLoader;

/**
 * @author ÑîÌÚ·É	2009-9-2 
 *
 */
public class TestDB {
	@Test
	public void test(){
		IDBTest obj =(IDBTest)MyGroovyLoader.getInstance("com/qtone/datasync/dao/DBTestImpl.groovy");
		obj.query("select school_name from xj_school");
	}
}
