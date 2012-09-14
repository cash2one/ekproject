package com.ctfzh.tabdmeo.others;

import com.ctfzh.tabdmeo.R;

public class Constant {

	public static final class ConValue {

		public static int mImageViewArray[] = { R.drawable.tab_icon1,
				R.drawable.tab_icon2, R.drawable.tab_icon3,
				R.drawable.tab_icon4, R.drawable.tab_icon5 };

		public static String mTextviewArray[] = { "主页", "关于", "设置", "搜索", "更多" };

		public static Class mTabClassArray[] = { Activity1.class,
				Activity2.class, Activity3.class, Activity4.class,
				Activity5.class };
		
		//TopBottomActivity是上下tab综合实例
		public static Class mTabClassArray2[] = { TopBottomActivity.class,
			Activity2.class, Activity3.class, Activity4.class,
			Activity5.class };
	}
}
