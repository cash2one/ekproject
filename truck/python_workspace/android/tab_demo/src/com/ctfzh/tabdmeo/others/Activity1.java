package com.ctfzh.tabdmeo.others;

import com.ctfzh.tabdmeo.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Activity1 extends Activity{

	private final static String TAG = "Activity1";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1_layout);
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
