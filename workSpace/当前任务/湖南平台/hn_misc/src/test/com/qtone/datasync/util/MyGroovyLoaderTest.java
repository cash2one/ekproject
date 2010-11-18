package com.qtone.datasync.util;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author ÑîÌÚ·É	2009-9-2 
 *
 */
public class MyGroovyLoaderTest {
	@Test
	public void testGetInstance(){
//		GroovyClassLoader gcl = new GroovyClassLoader(MyGroovyLoaderTest.class
//				.getClassLoader());
//		String path = gcl
//		Class clazz = gcl.parseClass()
		ITest obj = (ITest)MyGroovyLoader.getInstance("com/qtone/datasync/util/ITestImpl.groovy");
		Assert.assertEquals(obj.add(1,1), 1990);
	}
}
