package com.ctfzh.tabdmeo.vf;

import com.ctfzh.tabdmeo.R;

import android.view.View;

public class CView extends AbstractView {

	public CView(VfActivity va) {
		super(va);
	}

	public View makeNewView() {
		View view = View.inflate(context, R.layout.c, null);
		return view;
	}
}
