package com.ctfzh.tabdmeo.others;

import com.ctfzh.tabdmeo.R;
import com.ctfzh.tabdmeo.others.Constant.ConValue;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost.TabSpec;

public class CustomTabActivity2 extends TabActivity {
	private TabHost m_tabHost;
	private RadioGroup m_radioGroup;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_layout2);

		init();
	}

	private void init() {
		m_tabHost = getTabHost();

		int count = ConValue.mTabClassArray.length;
		for (int i = 0; i < count; i++) {
			TabSpec tabSpec = m_tabHost.newTabSpec(ConValue.mTextviewArray[i])
					.setIndicator(ConValue.mTextviewArray[i])
					.setContent(getTabItemIntent(i));
			m_tabHost.addTab(tabSpec);
		}

		m_radioGroup = (RadioGroup) findViewById(R.id.main_radiogroup);
		m_radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.RadioButton0:

					m_tabHost.setCurrentTabByTag(ConValue.mTextviewArray[0]);
					break;
				case R.id.RadioButton1:
					m_tabHost.setCurrentTabByTag(ConValue.mTextviewArray[1]);
					break;
				case R.id.RadioButton2:
					m_tabHost.setCurrentTabByTag(ConValue.mTextviewArray[2]);
					break;
				case R.id.RadioButton3:
					m_tabHost.setCurrentTabByTag(ConValue.mTextviewArray[3]);
					break;
				case R.id.RadioButton4:
					m_tabHost.setCurrentTabByTag(ConValue.mTextviewArray[4]);
					break;
				}
			}
		});

		((RadioButton) m_radioGroup.getChildAt(0)).toggle();
	}

	private Intent getTabItemIntent(int index) {
		Intent intent = new Intent(this, ConValue.mTabClassArray[index]);

		return intent;
	}
}
