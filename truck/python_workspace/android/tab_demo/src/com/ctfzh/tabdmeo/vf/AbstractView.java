package com.ctfzh.tabdmeo.vf;

import android.content.Context;
import android.view.View;

public abstract class AbstractView {

	protected Context context;
	protected VfActivity activity;

	public AbstractView(VfActivity va) {
		context = va;
		activity = va;
	}

	public abstract View makeNewView();
}