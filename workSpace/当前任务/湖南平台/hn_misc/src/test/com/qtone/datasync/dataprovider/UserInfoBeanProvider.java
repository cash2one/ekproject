package com.qtone.datasync.dataprovider;

import java.util.Iterator;

import org.testng.annotations.DataProvider;

import com.qtone.datasync.xxt.server.bean.MiscRequestBean;

public class UserInfoBeanProvider {
	@DataProvider(name = "userInfoBeanProvider")
	public static Iterator<Object[]> getUserInfoBean() {
		return new UserInfoBeanIterator();
	}

	private static class UserInfoBeanIterator implements Iterator<Object[]> {
		private final int times;
		private int curr = 0;

		public UserInfoBeanIterator(int times) {
			this.times = times;
		}

		public UserInfoBeanIterator() {
			this(3);
		}

		public boolean hasNext() {
			return curr < times;
		}

		public Object[] next() {
			Object[] retArr = new Object[] { generateUserInfoBean() };
			curr++;
			return retArr;
		}

		private Object generateUserInfoBean() {
			MiscRequestBean bean = new MiscRequestBean();
			bean.setAction(1);
			bean.setPhoneFee("1380013800" + curr);
			bean.setPhoneUse("1380013800" + curr);
			bean.setSpServiceId("52840086");
			bean.setSpId("123456");

			return bean;
		}

		public void remove() {
		}

	}
}
