package com.ctfzh.tabdmeo.vf;

import com.ctfzh.tabdmeo.R;

import android.view.View;

public class DView extends AbstractView {

	public DView(VfActivity va) {
		super(va);
	}

	public View makeNewView() {
		View view = View.inflate(context, R.layout.d, null);
		return view;
	}
}
