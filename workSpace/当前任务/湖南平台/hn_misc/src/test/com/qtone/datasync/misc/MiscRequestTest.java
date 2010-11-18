package com.qtone.datasync.misc;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qtone.datasync.dataprovider.MiscDataProvider;
import com.qtone.datasync.xxt.server.DefaultMiscRequestHandler;

/**
 * 测试MISC正向同步的处理情况
 * 
 * @author 杨腾飞 2009-05-19
 */
@Test(groups = { "miscToXxt" })
public class MiscRequestTest {

	@Test(testName = "testMiscRequest", dataProvider = "miscDataProvider", dataProviderClass = MiscDataProvider.class)
	public void testSync(String msg) {
		DefaultMiscRequestHandler handler = new DefaultMiscRequestHandler();
		String retMsg = handler.SyncOrderRelationReq(msg);
		
		Assert.assertNotNull(retMsg);
		
		retMsg = handler.SyncOrderRelationReq(null);
	}
}
