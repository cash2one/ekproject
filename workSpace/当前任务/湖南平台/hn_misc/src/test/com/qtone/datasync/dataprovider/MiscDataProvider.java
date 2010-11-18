package com.qtone.datasync.dataprovider;

import java.util.Iterator;

import org.testng.annotations.DataProvider;


public class MiscDataProvider {
	@DataProvider(name="miscDataProvider")
	public static Iterator<String[]> getMiscData(){
		return new MiscMsgIterator(10);
	}
}
