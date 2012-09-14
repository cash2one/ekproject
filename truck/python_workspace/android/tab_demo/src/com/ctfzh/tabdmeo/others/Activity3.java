package com.ctfzh.tabdmeo.others;

import com.ctfzh.tabdmeo.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Activity3 extends Activity{

	private final static String TAG = "Activity3";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3_layout);
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