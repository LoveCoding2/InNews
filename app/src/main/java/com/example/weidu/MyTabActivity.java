package com.example.weidu;

import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.location.LocationClientOption.LocationMode;
import android.app.TabActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;



public class MyTabActivity extends TabActivity {

	private TabHost tabHost;
	private static final String NEWS = "新闻";
	private static final String LISTENER = "视听";
	private static final String ME = "我";
	
	
	private Intent newsIntent;
	
	private Intent listenerIntent;
	private Intent meIntent;

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_tab);
		tabHost = getTabHost();
		tabHost.setFocusable(true);
		prepareIntent();
		setupIntent();

	}

	 
	 
	private void setupIntent() {
		TabSpec tabSpec1 = buildTabSpec(NEWS, R.drawable.btnselector1,
				newsIntent);
		tabHost.addTab(tabSpec1);

		TabSpec tabSpec3 = buildTabSpec(LISTENER, R.drawable.btnselector3,
				listenerIntent);
		tabHost.addTab(tabSpec3);

		TabSpec tabSpec4 = buildTabSpec(ME, R.drawable.btnselector4,
				meIntent);
		tabHost.addTab(tabSpec4);
	}


	private TabSpec buildTabSpec(String tag, int icon, Intent intent) {
		View view = View.inflate(MyTabActivity.this, R.layout.activity__tabspec, null);
		((ImageView) view.findViewById(R.id.tab_iv_icon)).setImageResource(icon);
		((TextView) view.findViewById(R.id.tab_tv_text)).setText(tag);

		return tabHost.newTabSpec(tag).setIndicator(view).setContent(intent);
	}

	private void prepareIntent()
	{
		
		newsIntent = new Intent(this,MainActivity.class);
		listenerIntent = new Intent(this, ListenerActivity.class);
		meIntent = new Intent(this, MeActivity.class);
	}

}
