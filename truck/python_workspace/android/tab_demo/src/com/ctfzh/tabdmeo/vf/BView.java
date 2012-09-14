package com.ctfzh.tabdmeo.vf;

import com.ctfzh.tabdmeo.R;

import android.view.View;

public class BView extends AbstractView {

	public BView(VfActivity va) {
		super(va);
	}

	public View makeNewView() {
		View view = View.inflate(context, R.layout.b, null);
		return view;
	}
}
