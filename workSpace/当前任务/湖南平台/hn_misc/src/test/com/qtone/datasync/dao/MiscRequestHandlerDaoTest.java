package com.qtone.datasync.dao;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qtone.datasync.dataprovider.UserInfoBeanProvider;
import com.qtone.datasync.xxt.server.bean.MiscRequestBean;
import com.qtone.datasync.xxt.server.dao.MiscRequestDao;

public class MiscRequestHandlerDaoTest {
	private MiscRequestDao dao;

	@BeforeClass
	public void setUp() {
		dao = new MiscRequestDao();
	}

	@Test(dataProvider = "userInfoBeanProvider", dataProviderClass = UserInfoBeanProvider.class)
	public void testSave(MiscRequestBean bean) {
		Assert.assertEquals(dao.save(bean), true);
	}
}
