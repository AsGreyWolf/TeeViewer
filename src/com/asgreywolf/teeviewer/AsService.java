package com.asgreywolf.teeviewer;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.os.RemoteException;

public class AsService extends Service {
	@Override
	public void onCreate(){
		super.onCreate();
		working=true;
		Settings=new Settings();
		LoadServers();
		updateSettings();
		update();
	}
	int WidgetIds[];
	 @Override
	    public int onStartCommand(Intent intent, int flags, int startId) {
		 super.onStartCommand(intent, flags,startId);
		 if(intent!=null){
			 boolean aa=intent.getBooleanExtra("Widgets", false);
		 	 if(aa)WidgetIds=intent.getIntArrayExtra("Widget_Ids");
		 }
		 return START_STICKY;
	    }
	@Override
	public void onDestroy(){
		working=false;
	}
	private void updateSettings(){
		Settings.Load(this);
		ReStartThread();
	}
	private boolean working;
	private Settings Settings;
	Thread t=null;
	private void ReStartThread(){
		t=new Thread(){
			@Override public void run(){
				while(working){
					StartSpin();
					checkAll();
					update();
					try {
						Thread.sleep(Settings.autoupdatetime*1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						Tools.Exc(e);
					}
				}
				
			}
		};
		t.start();
	}
	private void StartSpin(){
		Intent in = new Intent("TeeViewerUpdate");
		in.putExtra("Data",1);
	    sendBroadcast(in);
	    }
	private void update(){
		Intent in = new Intent("TeeViewerUpdate");
		in.putExtra("Data",0);
	    sendBroadcast(in);
	    if(WidgetIds!=null){
	    	SharedPreferences sp = getSharedPreferences(
	           "widgets", Context.MODE_PRIVATE);
	    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
                .getApplicationContext());
	    for(int i=0;i<WidgetIds.length;i++){
	    	Widget.updateWidget(this,appWidgetManager, sp, WidgetIds[i],servers);
	    }
	    }
	    
	    
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return new UpdateService.Stub() 
        {
			@Override
			public ServerTransfer GetServer(int id) throws RemoteException {
				//Tools.Err("Get "+id);
				if(isValid(id)){
					ServerTransfer st=new ServerTransfer();
					st.Server=servers[id];
					return st;
				}
				return null;
			}

			@Override
			public ServerTransfer[] GetServers() throws RemoteException {
				//Tools.Err("Get all");
				ServerTransfer[] st=new ServerTransfer[maxvalues];
				for(int i=0;i<maxvalues;i++){
					st[i]=new ServerTransfer();
					st[i].Server=servers[i];
				}
				return st;
			}

			@Override
			public void AddServer(String ip, int port) throws RemoteException {
				// TODO Auto-generated method stub
				//Tools.Err("Add");
				int i=add(ip,port);
				check(i);
				SaveServers();
				update();
			}

			@Override
			public void EditServer(int id, String ip, int port)
					throws RemoteException {
				//Tools.Err("Edit");
				if(isValid(id)){
					edit(id,ip,port);
					SaveServers();
					update();
				}
			}

			@Override
			public void RemoveServer(int id) throws RemoteException {
				// TODO Auto-generated method stub
				//Tools.Err("Remove");
				if(isValid(id)){
					remove(id);
					SaveServers();
					update();
				}
			}

			@Override
			public void Check(int id) throws RemoteException {
				// TODO Auto-generated method stub
				//Tools.Err("Check "+id);
				if(isValid(id)){
					check(id);
					SaveServers();
					update();
				}
			}

			@Override
			public void CheckAll() throws RemoteException {
				// TODO Auto-generated method stub
				//Tools.Err("Check all");
					checkAll();
					SaveServers();
					update();
			}

			@Override
			public void CheckSettings() throws RemoteException {
				// TODO Auto-generated method stub
				//Tools.Err("Check Settings");
				updateSettings();
			}
        };
	}
	private Server servers[]=new Server[100];
	private int maxvalues=0;
	private void LoadServers(){
		Clear();
		SharedPreferences servprefs = getSharedPreferences("servers", Context.MODE_PRIVATE); 
		int max=servprefs.getInt("max", 0);
		for(int i=0;i<max;i++){
			int pp=add(servprefs.getString("ip_"+i, "127.0.0.1"), servprefs.getInt("port_"+i, 8303));// servprefs.getString("name_"+i, "noname")
			servers[pp].data.flags=servprefs.getInt("data_flags_"+i, 0);
			servers[pp].data.map=servprefs.getString("data_map_"+i, "ctf1");
			servers[pp].data.max_players_all=servprefs.getInt("data_max_players_all_"+i, 0);
			servers[pp].data.max_players_ingame=servprefs.getInt("data_max_players_ingame_"+i, 0);
			servers[pp].data.max_players_spectator=servprefs.getInt("data_max_players_spectator_"+i, 0);
			servers[pp].data.name=servprefs.getString("data_name_"+i, "error");
			servers[pp].data.player_count_all=servprefs.getInt("data_player_count_all_"+i, 0);
			servers[pp].data.player_count_ingame=servprefs.getInt("data_player_count_ingame_"+i, 0);
			servers[pp].data.player_count_spectator=servprefs.getInt("data_player_count_spectator_"+i, 0);
			servers[pp].data.type=servprefs.getString("data_type_"+i,"DM");
		}
		if(max==0){
			int i=0;
			int pp=add(servprefs.getString("ip_"+i, "tee.su"), servprefs.getInt("port_"+i, 1101));// servprefs.getString("name_"+i, "noname")
			servers[pp].data.map=servprefs.getString("data_map_"+i, "tee.su");
			servers[pp].data.name=servprefs.getString("data_name_"+i, "NEW mPanic");
			i=1;
			pp=add(servprefs.getString("ip_"+i, "tee.su"), servprefs.getInt("port_"+i, 1102));// servprefs.getString("name_"+i, "noname")
			servers[pp].data.map=servprefs.getString("data_map_"+i, "tee.su");
			servers[pp].data.name=servprefs.getString("data_name_"+i, "OLD mPanic");
		}
	}
	private void SaveServers(){
		
		Editor servprefsedit=getSharedPreferences("servers", Context.MODE_PRIVATE).edit();
		servprefsedit.clear();
		servprefsedit.putInt("max",maxvalues);
		for(int i=0;i<maxvalues;i++){
			
			servprefsedit.putString("ip_"+i, servers[i].ip);
			servprefsedit.putInt("port_"+i, servers[i].port);
			//servprefsedit.putString("name_"+i, servers[i].name);
			
			servprefsedit.putInt("data_flags_"+i, servers[i].data.flags);
			servprefsedit.putString("data_map_"+i, servers[i].data.map);
			servprefsedit.putInt("data_max_players_all_"+i, servers[i].data.max_players_all);
			servprefsedit.putInt("data_max_players_ingame_"+i, servers[i].data.max_players_ingame);
			servprefsedit.putInt("data_max_players_spectator_"+i, servers[i].data.max_players_spectator);
			servprefsedit.putString("data_name_"+i, servers[i].data.name);
			servprefsedit.putInt("data_player_count_all_"+i, servers[i].data.player_count_all);
			servprefsedit.putInt("data_player_count_ingame_"+i, servers[i].data.player_count_ingame);
			servprefsedit.putInt("data_player_count_spectator_"+i, servers[i].data.player_count_spectator);
			servprefsedit.putString("data_type_"+i, servers[i].data.type);
			
		}
		servprefsedit.commit();
	}
	private void Clear(){
		maxvalues=0;
	}
	private int add(String ip,int port){
		if(maxvalues==100) return -1;
		servers[maxvalues]=new Server();
		servers[maxvalues].ip=ip;
		servers[maxvalues].port=port;
		//servers[maxvalues].name=name;
		servers[maxvalues].data=new ServerData(this);
		maxvalues++;
		return (maxvalues-1);
	}
	private void remove(int id){
		for(int i=id;i<maxvalues-1;i++){
			servers[i]=servers[i+1];
		}
		maxvalues--;
	}
	private void checkAll(){
		for(int i=0;i<maxvalues;i++){
			check(i);
		}
	}
	private void check(int i){
		servers[i].Check(this);
		Tools.Info("Checking "+servers[i].ip);
	}
	private void edit(int id, String ip, int port) {
			servers[id].ip=ip;
			servers[id].port=port;
			check(id);
	}
	private boolean isValid(int id){
		return (0<=id && id<maxvalues);
	}

}
