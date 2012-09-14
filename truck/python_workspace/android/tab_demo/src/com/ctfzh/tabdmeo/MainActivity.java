package com.ctfzh.tabdmeo;

import com.ctfzh.tabdmeo.menu.MenuActivity;
import com.ctfzh.tabdmeo.others.CustomTabActivity1;
import com.ctfzh.tabdmeo.others.CustomTabActivity2;
import com.ctfzh.tabdmeo.others.CustomTabActivity3;
import com.ctfzh.tabdmeo.others.TopBottomTab;
import com.ctfzh.tabdmeo.tab.TabMainActivity;
import com.ctfzh.tabdmeo.vf.VfActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Button btn1,btn2,btn3,btn4,btn5,btn6,btn7;
	Context mContext;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = this;
        
        init();
    }
    
    private void init() {
    	btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(mContext, TabMainActivity.class));
			}
		});
        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(mContext, VfActivity.class));
			}
		});
        btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(mContext, MenuActivity.class));
			}
		});
        
    	btn4 = (Button) findViewById(R.id.button4);
    	btn4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, CustomTabActivity1.class);
				startActivity(intent);
				//overridePendingTransition(R.anim.translucent_enter, R.anim.translucent_exit);
			}
		});
    	
    	btn5 = (Button) findViewById(R.id.button5);
    	btn5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, CustomTabActivity2.class);
				startActivity(intent);
				overridePendingTransition(R.anim.translucent_enter, R.anim.translucent_exit);
			}
		});
    	
    	btn6 = (Button) findViewById(R.id.button6);
    	btn6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, CustomTabActivity3.class);
				startActivity(intent);
				overridePendingTransition(R.anim.translucent_enter, R.anim.translucent_exit);
			}
		});
    	
    	btn7 = (Button) findViewById(R.id.button7);
    	btn7.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, TopBottomTab.class);
				startActivity(intent);
				overridePendingTransition(R.anim.translucent_enter, R.anim.translucent_exit);
			}
		});
    }
    
}