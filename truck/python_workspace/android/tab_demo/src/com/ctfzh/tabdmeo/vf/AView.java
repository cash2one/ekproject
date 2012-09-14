package com.ctfzh.tabdmeo.vf;

import com.ctfzh.tabdmeo.R;

import android.view.View;

public class AView extends AbstractView{

	public AView(VfActivity va) {
		super(va);
	}

	public View makeNewView() {
		View view = View.inflate(context, R.layout.a, null);
		return view;
	}
}