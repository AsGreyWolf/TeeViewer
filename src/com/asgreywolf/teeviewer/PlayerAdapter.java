package com.asgreywolf.teeviewer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class PlayerAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final ClientData[] clients;
    public PlayerAdapter(Activity context, ClientData[] clients,String[] str) {
        super(context, R.layout.rowlayout, str);
        this.context = context;
        this.clients= clients;
    }

    // Класс для сохранения во внешний класс и для ограничения доступа
    // из потомков класса
    static class ViewHolder {
        public TextView name;
        public TextView clan;
        public TextView score;
        public ImageView flag;
        public int resID;
        //public LinearLayout layout;
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
            rowView = inflater.inflate(R.layout.playerlayout, null, true);
            holder = new ViewHolder();
            holder.name = (TextView) rowView.findViewById(R.id.playerlayout_name);
            holder.clan= (TextView) rowView.findViewById(R.id.playerlayout_clan);
            holder.flag= (ImageView) rowView.findViewById(R.id.playerlayout_flag);
            holder.score= (TextView) rowView.findViewById(R.id.playerlayout_score);
            rowView.setTag(holder);
            holder.resID = context.getResources().getIdentifier("flag_"+clients[position].flag, "drawable", "com.asgreywolf.teeviewer");
            
            if (holder.resID == 0){
            	holder.resID = context.getResources().getIdentifier("flag_0", "drawable", "com.asgreywolf.teeviewer");
            }
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        
        holder.name.setText(clients[position].name);
        holder.clan.setText(clients[position].clan);
        holder.score.setText(""+clients[position].score);
        holder.flag.setContentDescription(Tools.flags[clients[position].flag]);
            
        holder.flag.setImageResource(holder.resID);
        
        // holder.layout.setBackgroundResource(R.drawable.ctf1);
        // Изменение иконки для Windows и iPhone
        

        return rowView;
    }
}
