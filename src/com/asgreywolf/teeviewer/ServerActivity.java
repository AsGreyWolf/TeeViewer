package com.asgreywolf.teeviewer;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ServerActivity extends AsActivity {
	TextView name;
	TextView ip;
	TextView players;
	TextView map;
	ImageView status;
	ListView lv;
	public LinearLayout layout;
    int d;
    String drawablemap="";
    Settings Settings;
	int id;
	Server srv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Tools.InitFlags();
		Settings=new Settings();
		Settings.Load(this);
		setContentView(R.layout.activity_server);
		// Show the Up button in the action bar.
		setupActionBar();
		id = getIntent().getIntExtra("id", -1);
		if(id==-1){
			finish();
			return;
		}
		name=(TextView) findViewById(R.id.rowlayout_name);
		ip=(TextView) findViewById(R.id.rowlayout_ip);
		players=(TextView) findViewById(R.id.rowlayout_players);
		map=(TextView) findViewById(R.id.rowlayout_map);
		status = (ImageView) findViewById(R.id.rowlayout_online);
		
		lv = (ListView) findViewById(R.id.activity_server_list);
		layout=(LinearLayout)findViewById(R.id.rowlayout_layout);
	}
	
	public void loadDrawable(){
		if(!drawablemap.equals(srv.data.map)){
			int resID=0;
            if(Settings.mapimage)
            	resID = getResources().getIdentifier("map_"+srv.data.map, "drawable", "com.asgreywolf.teeviewer");
         
         	if (resID == 0){
         		resID = getResources().getIdentifier("map_none", "drawable", "com.asgreywolf.teeviewer");
         	}
         	d = resID;
         	drawablemap=srv.data.map;
		}
         
	}
	
    @Override 
	public void onUpdateRender(Message msg) { 
    	super.onUpdateRender(msg);
	
			ServerActivity act=(ServerActivity) msg.obj;
			act.srv=act.getServer(act.id);
			act.setTitle("TeeViewer "+act.srv.ip+":"+act.srv.port);
			act.name.setText(act.srv.data.name);
			if(act.srv.reachable)
				act.status.setImageResource(android.R.drawable.presence_online);
			else
				act.status.setImageResource(android.R.drawable.presence_offline);
			act.ip.setText(act.srv.ip+":"+act.srv.port);
			act.map.setText(act.srv.data.map);
			act.players.setText(act.srv.data.player_count_all+"/"+act.srv.data.max_players_all);
			act.loadDrawable();
			act.layout.setBackgroundResource(act.d);
			int vals=act.srv.data.player_count_all;
			if(vals>0){
				String[] blankstr=new String[vals];
				for(int i=0;i<vals;i++){
					blankstr[i]="Loading";
				}
				PlayerAdapter adapter = new PlayerAdapter(act,act.srv.data.clients ,blankstr);
				act.lv.setAdapter(adapter);
			}
			else{
				act.lv.setAdapter(null);
			}
		}
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.server, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_update:
			Check(id);
			return true;
		case R.id.action_edit:
			srvs=new Server[id+1];
			srvs[id]=srv;
			new DialogWindow(this,DialogWindow.IDD_EDIT_SERVER,id);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
