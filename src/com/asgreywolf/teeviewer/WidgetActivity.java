package com.asgreywolf.teeviewer;

import android.os.Bundle;
import android.os.Message;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class WidgetActivity extends AsActivity {
Settings sets;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	    
	}
	@Override
	public void onUpdateRender(Message msg){
		super.onUpdateRender(msg);
		ListView lv=(ListView) findViewById(R.id.activity_main_lv);
		lv=(ListView) findViewById(R.id.activity_main_lv);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) 
			{
				click(position);
			}
		});
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
	    if (extras != null) {
	      widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
	          AppWidgetManager.INVALID_APPWIDGET_ID);
	    }
	    // и проверяем его корректность
	    if (widgetID == AppWidgetManager.INVALID_APPWIDGET_ID) {
	      finish();
	    }
	    resultValue = new Intent();
	    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
	    sets=new Settings();
	    sets.Load(this);
		srvs=getServers();

		String[] blankstr=new String[srvs.length];
		for(int i=0;i<srvs.length;i++){
			blankstr[i]="Loading";
		}
		lv.setAdapter(new WidgetServerAdapter(this, srvs, blankstr));
	}
	Intent resultValue;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.widget, menu);
		return true;
	}
	int widgetID;
	public void click(int v){
		SharedPreferences sp = getSharedPreferences("widgets", MODE_PRIVATE);
	    Editor editor = sp.edit();
	    editor.putInt("id_" + widgetID, v);
	    editor.commit();
	    
	    setResult(RESULT_OK, resultValue);
	    
	    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
	    Widget.updateWidget(this, appWidgetManager, sp, widgetID,srvs);
	    finish();
	}

}
