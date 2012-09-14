package com.ctfzh.tabdmeo.vf;

import com.ctfzh.tabdmeo.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ViewFlipper;

public class VfActivity extends Activity {

	ViewFlipper vf;
	RadioGroup rg;
	Context context;

	AView a;
	BView b;
	CView c;
	DView d;
	EView e;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vf);

		vf = (ViewFlipper) findViewById(R.id.vf);

		rg = (RadioGroup) findViewById(R.id.rg);
		context = this;
		a = new AView(this);
		b = new BView(this);
		c = new CView(this);
		d = new DView(this);
		e = new EView(this);
		
		vf.addView(a.makeNewView());
		vf.addView(b.makeNewView());
		vf.addView(c.makeNewView());
		vf.addView(d.makeNewView());
		vf.addView(e.makeNewView());
		
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
				changeViewByHorizontalnAnim(vf, idx);
			}
		});
	}

	public void changeViewByHorizontalnAnim(ViewFlipper vf, int idx) {

		int currentIdx = vf.getDisplayedChild();
		if (idx == currentIdx) {
			return;
		}
		Animation leftIn = AnimationUtils.loadAnimation(context, R.anim.left_in);
		Animation leftOut = AnimationUtils.loadAnimation(context, R.anim.left_out);
		Animation rightIn = AnimationUtils.loadAnimation(context, R.anim.right_in);
		Animation rightOut = AnimationUtils.loadAnimation(context, R.anim.right_out);
		Animation in = null;
		Animation out = null;
		if (idx > currentIdx) {
			in = leftIn;
			out = leftOut;
		} else if (idx < currentIdx) {
			in = rightIn;
			out = rightOut;
		} else {
			return;
		}
		vf.setInAnimation(in);
		vf.setOutAnimation(out);
		vf.setDisplayedChild(idx);
	}
}

