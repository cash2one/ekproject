package com.qtone.datasync.xxt;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qtone.datasync.xxt.dp.IMiscToXxtFunctionTestHelper;

/**
 * @author 杨腾飞 2009-9-2 正向同步功能性测试
 */
public class MiscToXxtFunctionTest {
	IMiscToXxtFunctionTestHelper helper = null;
	
	@BeforeClass
	public void setUp() {
		helper.prepareDb();
	}

	@Test
	public void doTest() {
		helper.doSync();
		
		Assert.assertEquals(helper.checkRequestCount(), true);
		Assert.assertEquals(helper.checkNonexistsUserDealResult(), true);
		Assert.assertEquals(helper.checkExistsUserDealRequest(), true);
	}

	/**
	 * 读取测试文件，准备相关的测试数据
	 * ***该测试需要关掉*_xj_family和family_phone表上的自增序列***
	 * 
	 * 将"phone,family_id,abb,salemodalid"格式的数据(如"13800138000,1506,cs,-BYA")写入到数据库中，
	 * 并建立相应的家庭关系等
	 */
//	private void prepareNewData() {
//		IDataSet dataSetLoader = new MyGroovyLoader<IDataSet>().getInstance("com/qtone/datasync/util/ITestImpl.groovy");
//		List<List<String>> dataSet = dataSetLoader.loadData("resources/db_init_dataset.txt");
//		
//		Sql sql = new DbUtils().newInstance();
//		
//	}

}
