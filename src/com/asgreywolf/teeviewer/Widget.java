package com.asgreywolf.teeviewer;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;

public class Widget extends AppWidgetProvider {
	 @Override
	  public void onEnabled(Context context) {
	    super.onEnabled(context);
	   // Log.d(LOG_TAG, "onEnabled");
	  }
	  SharedPreferences sp;
	  int wids[];
	  Context c;
	  AppWidgetManager awm;
	  @Override
	  public void onUpdate(Context context, AppWidgetManager appWidgetManager,
	      int[] appWidgetIds) {
	    super.onUpdate(context, appWidgetManager, appWidgetIds);
	    //Log.d(LOG_TAG, "onUpdate " + Arrays.toString(appWidgetIds));
	    sp = context.getSharedPreferences(
	            "widgets", Context.MODE_PRIVATE);
	    wids=appWidgetIds;
	    c=context;
	    awm=appWidgetManager;
	    Intent i=new Intent(context, AsService.class);
	    i.putExtra("Widget_Ids", appWidgetIds);
	    i.putExtra("Widgets", true);
	    context.startService(i);
		//context.bindService(i,ToService,0);
		
	  }
	/*  public Server[] getServers(){
			Server[] s=null;
			try {
				ServerTransfer[] st=Service.GetServers();
				s=new Server[st.length];
				for(int i=0;i<st.length;i++){
					s[i]=st[i].Server;
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				Tools.Exc(e);
			}
			return s;
		}
	 UpdateService Service = null;
		private ServiceConnection ToService = new ServiceConnection() 
	    {
	        public void onServiceDisconnected(ComponentName name)
	        {
	            Service = null;
	        }
	        public void onServiceConnected(ComponentName name, IBinder service)
	        {
	            Service = UpdateService.Stub.asInterface(service);
	          Server[] srvs=getServers();
		        for (int id : wids) {
		          updateWidget(c, awm, sp, id,srvs);
		        }
		        c.unbindService(this);
	        }
	    };*/
	  static int serverid;
	  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	  public void onAppWidgetOptionsChanged(Context context,
	                                        AppWidgetManager mgr,
	                                        int appWidgetId,
	                                        Bundle newOptions) {
		  Editor e = context.getSharedPreferences(
		            "widgets", Context.MODE_PRIVATE).edit();
		  e.putInt("id_" + appWidgetId+"_width",newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)/80+1);
		  e.commit();
	  }
	  static void updateWidget(final Context context,
			AppWidgetManager appWidgetManager, SharedPreferences sp, int widgetID,
			Server[] srvs) {
		serverid=sp.getInt("id_" + widgetID, -1);
		if(serverid>=0 && serverid<srvs.length){
			/*Thread thread = new Thread() {
		        public void run() {
					//Tools.check(context,serverid);
		        }
		    };
		    thread.start();
		    try {
		        thread.join();
		    } catch (InterruptedException e) {
		    }*/
			int size=sp.getInt("id_" + widgetID+"_width", 110);
			RemoteViews widgetView;
			if(size<3)
			widgetView = new RemoteViews(context.getPackageName(),
			        R.layout.widget);
			else widgetView = new RemoteViews(context.getPackageName(),
			        R.layout.widget3);
			widgetView.setTextViewText(R.id.rowlayout_name,srvs[serverid].data.name);
			if(srvs[serverid].reachable)
				widgetView.setImageViewResource(R.id.rowlayout_online,android.R.drawable.presence_online);
			else
				widgetView.setImageViewResource(R.id.rowlayout_online,android.R.drawable.presence_offline);
			widgetView.setTextViewText(R.id.rowlayout_ip,srvs[serverid].ip+":"+srvs[serverid].port);
			widgetView.setTextViewText(R.id.rowlayout_map,srvs[serverid].data.map);
			widgetView.setTextViewText(R.id.rowlayout_players,srvs[serverid].data.player_count_all+"/"+srvs[serverid].data.max_players_all);
			
			Intent configIntent = new Intent(context, ServerActivity.class);
			configIntent.putExtra("id", serverid);
		    PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);

		    widgetView.setOnClickPendingIntent(R.id.rowlayout_layout, configPendingIntent);
		    // Обновляем виджет
		    appWidgetManager.updateAppWidget(widgetID, widgetView);
		}
		
	}

	@Override
	  public void onDeleted(Context context, int[] appWidgetIds) {
	    super.onDeleted(context, appWidgetIds);
	   // Log.d(LOG_TAG, "onDeleted " + Arrays.toString(appWidgetIds));
	    Editor editor = context.getSharedPreferences(
	            "widgets", Context.MODE_PRIVATE).edit();
	        for (int widgetID : appWidgetIds) {
	          editor.remove("id_" + widgetID);
	        }
	        editor.commit();
	  }

	  @Override
	  public void onDisabled(Context context) {
	    super.onDisabled(context);
	    //Log.d(LOG_TAG, "onDisabled");
	  }
}
