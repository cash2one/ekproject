package com.ctfzh.tabdmeo.others;

import com.ctfzh.tabdmeo.R;

import android.app.Activity;
import android.os.Bundle;

public class Activity2 extends Activity{

	private final static String TAG = "Activity2";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_layout);
    }

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
}