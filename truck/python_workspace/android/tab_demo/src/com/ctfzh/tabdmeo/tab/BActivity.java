package com.ctfzh.tabdmeo.tab;

import com.ctfzh.tabdmeo.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BActivity extends Activity{

	Button btn;
	TabMainActivity main;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.b);
		btn = (Button) findViewById(R.id.button1);
		main = (TabMainActivity) getParent();
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				main.put("test", "abc123");
				main.switchActivity(2);
			}
		});
	}
	
}
