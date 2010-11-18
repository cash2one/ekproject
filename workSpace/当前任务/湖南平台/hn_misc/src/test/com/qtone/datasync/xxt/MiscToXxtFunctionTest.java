package com.qtone.datasync.xxt;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qtone.datasync.xxt.dp.IMiscToXxtFunctionTestHelper;

/**
 * @author ���ڷ� 2009-9-2 ����ͬ�������Բ���
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
	 * ��ȡ�����ļ���׼����صĲ�������
	 * ***�ò�����Ҫ�ص�*_xj_family��family_phone���ϵ���������***
	 * 
	 * ��"phone,family_id,abb,salemodalid"��ʽ������(��"13800138000,1506,cs,-BYA")д�뵽���ݿ��У�
	 * ��������Ӧ�ļ�ͥ��ϵ��
	 */
//	private void prepareNewData() {
//		IDataSet dataSetLoader = new MyGroovyLoader<IDataSet>().getInstance("com/qtone/datasync/util/ITestImpl.groovy");
//		List<List<String>> dataSet = dataSetLoader.loadData("resources/db_init_dataset.txt");
//		
//		Sql sql = new DbUtils().newInstance();
//		
//	}

}
