package cn.qtone.xxt.android.teacher;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * 
 * 选卡效果
 * 
 * @author Ethan lam
 * 
 */
public class MyTabActivity extends TabActivity implements
		TabHost.TabContentFactory {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final TabHost tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("家校互动")
				.setContent(new Intent(this, MyActivity.class)));
		
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("我的动态")
				.setContent(new Intent(this, MyActivity.class)));

	}

	/** {@inheritDoc} */
	public View createTabContent(String tag) {
		final TextView tv = new TextView(this);
		tv.setText("Content for tab with tag " + tag);
		return tv;
	}

}
