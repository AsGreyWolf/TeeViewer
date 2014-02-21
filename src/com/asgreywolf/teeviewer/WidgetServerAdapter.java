package com.asgreywolf.teeviewer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
public class WidgetServerAdapter extends ArrayAdapter<String> {
    private final AsActivity context;
    private final Server[] servers;
    //private final int maxvalues;
    public WidgetServerAdapter(AsActivity widgetActivity, Server[] servers,String[] str) {
        super(widgetActivity, R.layout.rowlayout, str);
        this.context = widgetActivity;
        this.servers= servers;
        //this.maxvalues=maxvalues;
    }

    // Класс для сохранения во внешний класс и для ограничения доступа
    // из потомков класса
    static class ViewHolder {
        public TextView name;
        public TextView ip;
        public TextView players;
        public TextView map;
        public ImageView status;
        public LinearLayout layout;
        int d;
        int none;
    }

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // ViewHolder буферизирует оценку различных полей шаблона элемента

        ViewHolder holder;
        // Очищает сущетсвующий шаблон, если параметр задан
        // Работает только если базовый шаблон для всех классов один и тот же
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.rowlayout, null, true);
            holder = new ViewHolder();
            holder.name = (TextView) rowView.findViewById(R.id.rowlayout_name);
            holder.ip = (TextView) rowView.findViewById(R.id.rowlayout_ip);
            holder.players= (TextView) rowView.findViewById(R.id.rowlayout_players);
            holder.map= (TextView) rowView.findViewById(R.id.rowlayout_map);
            holder.status=(ImageView)rowView.findViewById(R.id.rowlayout_online);
            holder.layout=(LinearLayout)rowView.findViewById(R.id.rowlayout_layout);
            rowView.setTag(holder);

            int resID=0;
            	resID = context.getResources().getIdentifier("map_"+servers[position].data.map, "drawable", "com.asgreywolf.teeviewer");
            holder.none=context.getResources().getIdentifier("map_none", "drawable", "com.asgreywolf.teeviewer");
            if (resID == 0){
            	resID = holder.none;
            }
            holder.d =  resID; 
            
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        
        holder.name.setText(servers[position].data.name);
        if(context.srvs[position].reachable)
        	holder.status.setImageResource(android.R.drawable.presence_online);
		else
			holder.status.setImageResource(android.R.drawable.presence_offline);
        holder.ip.setText(servers[position].ip+":"+servers[position].port);
        holder.players.setText(servers[position].data.player_count_all+"/"+servers[position].data.max_players_all);
        holder.map.setText(servers[position].data.map);
        if(context.Settings.mapimage)
        	holder.layout.setBackgroundResource(holder.d);
        else holder.layout.setBackgroundResource(holder.none);
        

        return rowView;
    }
}