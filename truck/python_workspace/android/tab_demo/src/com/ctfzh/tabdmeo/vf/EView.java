package com.ctfzh.tabdmeo.vf;

import com.ctfzh.tabdmeo.R;

import android.view.View;

public class EView extends AbstractView {

	public EView(VfActivity va) {
		super(va);
	}

	public View makeNewView() {
		View view = View.inflate(context, R.layout.e, null);
		return view;
	}
}
