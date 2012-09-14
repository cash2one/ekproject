package com.ctfzh.tabdmeo.others;

import com.ctfzh.tabdmeo.R;
import com.ctfzh.tabdmeo.others.Constant.ConValue;

import android.R.integer;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class TopBottomTab extends TabActivity {
	private TabHost m_tabHost;
	private LayoutInflater mLayoutInflater;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_layout1);

		init();
	}

	private void init() {
		m_tabHost = getTabHost();
		mLayoutInflater = LayoutInflater.from(this);

		int count = ConValue.mTabClassArray2.length;
		for (int i = 0; i < count; i++) {
			TabSpec tabSpec = m_tabHost.newTabSpec(ConValue.mTextviewArray[i])
					.setIndicator(getTabItemView(i))
					.setContent(getTabItemIntent(i));
			m_tabHost.addTab(tabSpec);
			m_tabHost.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.drawable.selector_tab_background);
		}

	}

	private View getTabItemView(int index) {

		View view = mLayoutInflater.inflate(R.layout.tab_item_view, null);

		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);

		if (imageView != null) {
			imageView.setImageResource(ConValue.mImageViewArray[index]);
		}

		TextView textView = (TextView) view.findViewById(R.id.textview);

		textView.setText(ConValue.mTextviewArray[index]);

		return view;
	}

	private Intent getTabItemIntent(int index) {
		Intent intent = new Intent(this, ConValue.mTabClassArray2[index]);

		return intent;
	}
}
