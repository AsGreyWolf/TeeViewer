package com.asgreywolf.teeviewer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {
	public boolean mapimage;
	public int autoupdatetime;
	public void Load(Context act){
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(act);
		mapimage=prefs.getBoolean("interface_mapimages", true);
		autoupdatetime=Integer.parseInt(prefs.getString("interface_autoupdatetime", "1800"));
	}
}
