package com.asgreywolf.teeviewer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class AsActivity extends Activity {

	Server srvs[]=null;
	Settings Settings;
	@Override
	public void onStart(){
		super.onStart();
		startRefreshAnim();
		IntentFilter filter = new IntentFilter("TeeViewerUpdate");
		Intent i=new Intent(this, AsService.class);
		startService(i);
		bindService(i,ToService,0);
		FromService = new BroadcastReceiver() 
	    {
	        @Override
	        public void onReceive(Context context, Intent intent) 
	        {
	        	int v=intent.getIntExtra("Data", -1);
	            if(v==0)
	            {
	            	updateRender();
	            }else if(v==1){
	            	startRefreshAnim();
	            }
	        }
	    };
	    registerReceiver(FromService, filter);
	    ServiceRegistred=true;
	    
	}
	boolean working;
	Message msg=new Message();
	public void updateRender(){
		msg=Message.obtain();
		msg.obj=this;
		render.sendMessage(msg);
	}
	@SuppressLint("HandlerLeak")
	Handler render=new Handler(){
		@Override 
	    public void handleMessage(Message msg) { 
			onUpdateRender(msg);
		}
	};
	public void onUpdateRender(Message msg) {
		stopRefreshAnim();
	}
	@Override 
	public void onStop(){
		onCancel();
		super.onStop();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Settings=new Settings();
		Settings.Load(this);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		working=true;
	}
	@Override
	public void onDestroy(){
		working=false;
		super.onDestroy();
	}
	public void startRefreshAnim(){
		Message msg=refreshanimstart.obtainMessage();
		msg.obj=this;
		refreshanimstart.sendMessage(msg);
	}
	public void stopRefreshAnim(){
		Message msg=refreshanimstart.obtainMessage();
		msg.obj=this;
		refreshanimstop.sendMessage(msg);
	}
	static Handler refreshanimstart=new Handler(){
		@Override 
	    public void handleMessage(Message msg) {
			if (Tools.Honeycomb()){
            	((AsActivity)msg.obj).setRefreshActionButtonState(true);            	
			}else{
				((AsActivity)msg.obj).setProgressBarIndeterminateVisibility(true);
			}
		}
	};
	static Handler refreshanimstop=new Handler(){
		@Override 
	    public void handleMessage(Message msg) {
			if (Tools.Honeycomb()){
            	((AsActivity)msg.obj).setRefreshActionButtonState(false);            	
			}else{
				((AsActivity)msg.obj).setProgressBarIndeterminateVisibility(false);
			}
		}
	};

	@SuppressLint("NewApi")
	public void setRefreshActionButtonState(final boolean refreshing) {
		if (refreshButton != null) {
            if (refreshing) {
            		refreshButton.setActionView(R.layout.actionbar_indeterminate_progress);
            		
            } else {
            	refreshButton.setActionView(null);
            }
        }
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		refreshButton = menu.findItem(R.id.action_update);
		return true;
	}
	MenuItem refreshButton;
	public void onCancel(){
		stopRefreshAnim();
		if(ServiceBound){
			unbindService(ToService);
			ServiceBound=false;
		}
			if(ServiceRegistred && FromService!=null){
				try{
					unregisterReceiver(FromService);
				}catch(java.lang.IllegalArgumentException e){
					
				}
				ServiceRegistred=false;
			}
	}
	boolean ServiceBound;
	boolean ServiceRegistred;
	BroadcastReceiver FromService;
	UpdateService Service = null;
	private ServiceConnection ToService = new ServiceConnection() 
    {
        public void onServiceDisconnected(ComponentName name)
        {
            Service = null;
            ServiceBound = false;
        }
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            Service = UpdateService.Stub.asInterface(service);
            ServiceBound = true;
            updateRender();
        }
    };
    public Server getServer(int id){
		try {
			ServerTransfer st=Service.GetServer(id);
			return st.Server;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			Tools.Exc(e);
		}
		return null;
	}
	public Server[] getServers(){
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
	public void EditServer(final int id,final String ip,final int port){
		pool.submit(new EditTask(Service,id,ip,port));
	}
	public void AddServer(String ip,int port){
		pool.submit(new AddTask(Service,ip,port));
	}
	public void RemoveServer(int id){
		pool.submit(new RemoveTask(Service,id));
	}
	public void Check(int id){
		pool.submit(new CheckTask(Service,id));
		startRefreshAnim();
	}
	public void CheckAll(){
		pool.submit(new CheckAllTask(Service));
		startRefreshAnim();
	}
	public void CheckSettings(){
		pool.submit(new CheckSettingsTask(Service));
	}
	ExecutorService pool = Executors.newFixedThreadPool(1);
}
class CheckSettingsTask implements Runnable {
	public UpdateService Service;
	CheckSettingsTask(UpdateService S){
		Service = S;
	}
	@Override
	public void run() {
		try {
			Service.CheckSettings();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			Tools.Exc(e);
		}
	}
}
class CheckAllTask implements Runnable {
	public UpdateService Service;
	CheckAllTask(UpdateService S){
		Service = S;
	}
	@Override
	public void run() {
		try {
			Service.CheckAll();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			Tools.Exc(e);
		}
	}
}
class CheckTask implements Runnable {
	public UpdateService Service;
	int id;
	CheckTask(UpdateService S,int i){
		Service = S;
		id=i;
	}
	@Override
	public void run() {
		try {
			Service.Check(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			Tools.Exc(e);
		}
	}
}
class RemoveTask implements Runnable {
	public UpdateService Service;
	int id;
	RemoveTask(UpdateService S,int i){
		Service = S;
		id=i;
	}
	@Override
	public void run() {
		try {
			Service.RemoveServer(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			Tools.Exc(e);
		}
	}
}
class AddTask implements Runnable {
	public UpdateService Service;
	String ip;
	int port;
	AddTask(UpdateService S,String ipp,int portt){
		Service = S;
		ip=ipp;
		port=portt;
	}
	@Override
	public void run() {
		try {
			Service.AddServer(ip,port);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			Tools.Exc(e);
		}
	}

}
class EditTask implements Runnable {
	public UpdateService Service;
	String ip;
	int port;
	int id;
	EditTask(UpdateService S,int i,String ipp,int portt){
		Service = S;
		ip=ipp;
		port=portt;
		id=i;
	}
	@Override
	public void run() {
		try {
			Service.EditServer(id,ip,port);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			Tools.Exc(e);
		}
	}
}

