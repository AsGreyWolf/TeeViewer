package com.asgreywolf.teeviewer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends AsActivity {
	ListView lv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv=(ListView) findViewById(R.id.activity_main_lv);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) 
			{
				click(position);
			}
		});
		lv.setOnCreateContextMenuListener(this);
	}
	private void click(int id){
		Intent ServerAct=new Intent(this, ServerActivity.class);
		ServerAct.putExtra("id", id);
		onCancel();
		
		startActivity(ServerAct);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	ServerAdapter adapter=null;
	@Override 
	public void onUpdateRender(Message msg) { 
		super.onUpdateRender(msg);
		srvs=getServers();
		MainActivity act= (MainActivity)msg.obj;
		if(srvs.length>0){
			String[] blankstr=new String[srvs.length];
			for(int i=0;i<srvs.length;i++){
				blankstr[i]="Loading";
			}
			act.adapter = new ServerAdapter(act, srvs, blankstr);
			act.lv.setAdapter(act.adapter);
		}else{
			act.clearadapter();
		}
	}
	void clearadapter(){
		if(adapter!=null){
			adapter=null;
			lv.setAdapter(adapter);
		}
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		AdapterContextMenuInfo aMenuInfo = (AdapterContextMenuInfo) menuInfo;
		menuitem = aMenuInfo.position;
		//menu.setHeaderTitle(R.string.menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.server_context, menu);
	}
	int menuitem;
	@Override
	public boolean onContextItemSelected(MenuItem item) 
	{
		
		if(menuitem>=srvs.length || menuitem<0) return true;
		switch (item.getItemId()) 
		{
		    case R.id.server_context_edit:
		    	new DialogWindow(this,DialogWindow.IDD_EDIT_SERVER,menuitem);
		    	break;
		    case R.id.server_context_remove:
		    	RemoveServer(menuitem);
		    	break;	    

		    default:
			    return super.onContextItemSelected(item);
		}
		return true;
	}
	static MainActivity MainActivity;
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_add_server:
			new DialogWindow(this,DialogWindow.IDD_ADD_SERVER,0);
			break;
		case R.id.action_settings:
			Intent SAct=new Intent(this, SettingsActivity.class);
			MainActivity=this;
			startActivity(SAct);
			break;
		case R.id.action_update:
			CheckAll();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
}
