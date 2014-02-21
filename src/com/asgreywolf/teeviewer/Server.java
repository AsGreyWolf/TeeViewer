package com.asgreywolf.teeviewer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

import com.asgreywolf.teeviewer.R;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
class ServerTransfer implements Parcelable{
	Server Server;
	public static final Parcelable.Creator<ServerTransfer> CREATOR
    = new Parcelable.Creator<ServerTransfer>() {

@Override
public ServerTransfer createFromParcel(Parcel source) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public ServerTransfer[] newArray(int size) {
	// TODO Auto-generated method stub
	return null;
}
};
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
}
class Server{
	ServerData data;
	//String name="Error";
	String ip="127.0.0.1";
	int port=8303;
	boolean reachable=false;
	public void Check(Context con){
		reachable=true;
		ServerData newdata=getdata(con,ip,port);
		if(newdata==null){
			Tools.Info("Can't get data from "+ip+":"+port);
			newdata=data;
			reachable=false;
		}
		data=newdata;
	}
	@SuppressWarnings("deprecation")
	public ServerData getdata(Context con,String ip,int port){
 		ServerData sd=null;
		byte[] lMsg = new byte[2048];
		int v=0;
		//String data="";
	
		try {
			int server_port = port;
			DatagramSocket s = new DatagramSocket(); 
			InetAddress local = InetAddress.getByName(ip);
			int msg_length = 15; 
			byte[] message = {(byte)255,(byte)255,(byte)255,(byte)255,(byte)255,(byte)255,(byte)255,(byte)255,(byte)255,(byte)255,
				(byte)103,(byte)105,(byte)101,(byte)51,(byte)5};
			DatagramPacket p = new DatagramPacket(message, msg_length, local, server_port);
			s.send(p);
		
			DatagramPacket dp = new DatagramPacket(lMsg, lMsg.length);
			s.setSoTimeout(1000);   // set the timeout in millisecounds.
			boolean res=true;
	        while(res){ 
	        	try {
	                s.receive(dp);
	                res=false;
	                v=dp.getLength();
	            }
	            catch (SocketTimeoutException e) {
	                s.close();
	                res=false;
	            }
	        }
			//data= new String(lMsg, 0, v);
		} catch (Exception e) { 
			Tools.Exc(e);
			return sd;
		}
		if(v>0){
		String[] s = new String[30];
		int i=0;
		int ii=0;
		while(ii<v){
		s[i]="";
		while(lMsg[ii]!=0){
			byte c[]={lMsg[ii]};
			s[i]=s[i]+new String(c,0);
			ii++;
		}
		i++;
		ii++;
		}
		sd=new ServerData(con);
		int specslots;
		try{
			if(Integer.parseInt(s[9]) == Integer.parseInt(s[7])){
				specslots = Integer.parseInt(s[9]);
			}else{ 
				specslots = Integer.parseInt(s[9]) - Integer.parseInt(s[7]);
			}
			sd.flags=Integer.parseInt(s[5]);
			sd.player_count_ingame=Integer.parseInt(s[6]);
			sd.max_players_ingame=Integer.parseInt(s[7]);
			sd.player_count_spectator=Integer.parseInt(s[8]) - Integer.parseInt(s[6]);
			sd.max_players_spectator=specslots;
			sd.player_count_all=Integer.parseInt(s[8]);
			sd.max_players_all=Integer.parseInt(s[9]);
		}catch(NumberFormatException e){
			Tools.Exc(e);
		}
		
		sd.name=s[2];
		sd.map=s[3];
		sd.type=s[4];
		
		sd.clients=new ClientData[16];
		ii=0;
		for(i=0;i<16;i++){
			sd.clients[i]=null;
		}
		try{
		for (i = 0; i <= Integer.parseInt(s[8])*5-5 ; i += 5) {
			sd.clients[ii]=new ClientData();
			
			sd.clients[ii].team=Integer.parseInt(s[i+14]);
			sd.clients[ii].flag=Integer.parseInt(s[i+12]);
			if(sd.clients[ii].flag==-1){
				sd.clients[ii].flag=0;
			}
			sd.clients[ii].score=Integer.parseInt(s[i+13]);
			
			sd.clients[ii].name=s[i+10];
			sd.clients[ii].clan=s[i+11];
			ii++;
		}
		}catch(NumberFormatException e){
			Tools.Exc(e);
		}
			
		}
		return sd;
	}
}


class ClientData{
	public String name;
	public String clan;
	public int flag;
	public int score;
	public int team;
	ClientData(){
		name="";
		clan="";
		flag=0;
		score=0;
		team=0;
	}
}
class ServerData{
	public String name;
	public String map;
	public String type;
	public int flags;
	public int player_count_ingame;
	public int max_players_ingame;
	public int player_count_spectator;
	public int max_players_spectator;
	public int player_count_all;
	public int max_players_all;
	ClientData clients[];
	ServerData(Context c){
		map="none";
		max_players_all=0;
		max_players_ingame=0;
		max_players_spectator=0;
		name=c.getString(R.string.cant_reach);
		player_count_all=0;
		player_count_ingame=0;
		player_count_spectator=0;
		type="none";
		flags=0;
		clients=new ClientData[16];
		for(int i=0;i<16;i++){
			clients[i]=new ClientData();
		}
	}
}
