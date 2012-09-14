package com.ctfzh.tabdmeo.tab;

import java.util.HashMap;
import java.util.Map;

import com.ctfzh.tabdmeo.R;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

public class TabMainActivity extends TabActivity {

	TabHost tab;
	Context context;
	RadioGroup rg;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_main);

		tab = getTabHost();
		context = this;

		tab.addTab(tab.newTabSpec("A").setIndicator("A").setContent(new Intent(context, AActivity.class)));
		tab.addTab(tab.newTabSpec("B").setIndicator("B").setContent(new Intent(context, BActivity.class)));
		tab.addTab(tab.newTabSpec("C").setIndicator("C").setContent(new Intent(context, CActivity.class)));
		tab.addTab(tab.newTabSpec("D").setIndicator("D").setContent(new Intent(context, DActivity.class)));
		tab.addTab(tab.newTabSpec("E").setIndicator("E").setContent(new Intent(context, EActivity.class)));

		rg = (RadioGroup) findViewById(R.id.rg);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int idx = -1;
				if (checkedId == R.id.rb01) {
					idx = 0;
				} else if (checkedId == R.id.rb02) {
					idx = 1;
				} else if (checkedId == R.id.rb03) {
					idx = 2;
				} else if (checkedId == R.id.rb04) {
					idx = 3;
				} else if (checkedId == R.id.rb05) {
					idx = 4;
				}
				switchActivity(idx);
			}
		});
	}

	protected void switchActivity(int idx) {
		int n = tab.getCurrentTab();

		if (idx < n) {
			tab.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_left_out));
		} else if (idx > n) {
			tab.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_right_out));
		}
		tab.setCurrentTab(idx);
		if (idx < n) {
			tab.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_left_in));
		} else if (idx > n) {
			tab.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_right_in));
		}
		
		RadioButton rb = (RadioButton) rg.getChildAt(idx);
		rb.setChecked(true);
	}

	private Map<String, Object> data = new HashMap<String, Object>();

	protected void put(String key, String value) {
		data.put(key, value);
	}

	protected Object get(String key) {
		return data.get(key);
	}
}