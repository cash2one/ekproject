package com.ctfzh.tabdmeo.others;

import java.util.HashMap;
import java.util.Map;

import com.ctfzh.tabdmeo.R;
import com.ctfzh.tabdmeo.menu.AActivity;
import com.ctfzh.tabdmeo.menu.BActivity;
import com.ctfzh.tabdmeo.menu.CActivity;
import com.ctfzh.tabdmeo.menu.DActivity;
import com.ctfzh.tabdmeo.menu.EActivity;
import com.ctfzh.tabdmeo.menu.FActivity;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.ViewFlipper;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout.LayoutParams;

public class TopBottomActivity extends TabActivity {

	TabHost tab;
	Context context;
	RadioGroup rg;
	public static int WIDHT = 54;
	int left = 0;
	ImageView iv;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.top_bottom_tab);

		tab = getTabHost();
		context = this;

		init();
		registListener();

	}

	private void init() {
		tab.addTab(tab.newTabSpec("A").setIndicator("A")
				.setContent(new Intent(context, AActivity.class)));
		tab.addTab(tab.newTabSpec("B").setIndicator("B")
				.setContent(new Intent(context, BActivity.class)));
		tab.addTab(tab.newTabSpec("C").setIndicator("C")
				.setContent(new Intent(context, CActivity.class)));
		tab.addTab(tab.newTabSpec("D").setIndicator("D")
				.setContent(new Intent(context, DActivity.class)));
		tab.addTab(tab.newTabSpec("E").setIndicator("E")
				.setContent(new Intent(context, EActivity.class)));
		tab.addTab(tab.newTabSpec("F").setIndicator("F")
				.setContent(new Intent(context, FActivity.class)));

		iv = (ImageView) findViewById(R.id.iv);
		rg = (RadioGroup) findViewById(R.id.rg);
		WIDHT = getWindowManager().getDefaultDisplay().getWidth() / 6; // 每个菜单的宽度
	}

	private void registListener() {
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
				} else if (checkedId == R.id.rb06) {
					idx = 5;
				} else {
					return;
				}

				switchActivity(idx);

				/* 位置移动动画 关键代码 */
				TranslateAnimation animation = new TranslateAnimation(left, idx
						* WIDHT, 0, 0);
				animation.setDuration(400);
				animation.setFillAfter(true);
				animation.setFillBefore(true);
				iv.startAnimation(animation);
				final int i = idx;
				animation.setAnimationListener(new AnimationListener() {

					public void onAnimationEnd(Animation animation) {
					}

					public void onAnimationRepeat(Animation animation) {

					}

					public void onAnimationStart(Animation animation) {
						LayoutParams lp = (LayoutParams) iv.getLayoutParams();
						lp.leftMargin = 0;
						iv.setLayoutParams(lp);
						left = i * WIDHT;
					}

				});
			}
		});
	}

	protected void switchActivity(int idx) {
		int n = tab.getCurrentTab();

		if (idx < n) {
			tab.getCurrentView().startAnimation(
					AnimationUtils.loadAnimation(this, R.anim.slide_left_out));
		} else if (idx > n) {
			tab.getCurrentView().startAnimation(
					AnimationUtils.loadAnimation(this, R.anim.slide_right_out));
		}
		tab.setCurrentTab(idx);
		if (idx < n) {
			tab.getCurrentView().startAnimation(
					AnimationUtils.loadAnimation(this, R.anim.slide_left_in));
		} else if (idx > n) {
			tab.getCurrentView().startAnimation(
					AnimationUtils.loadAnimation(this, R.anim.slide_right_in));
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
