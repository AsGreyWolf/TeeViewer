package com.asgreywolf.teeviewer;

import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class DialogWindow {
    
    public static final int IDD_ADD_SERVER = 0;
    public static final int IDD_EDIT_SERVER = 1;
    public int id;
    EditText ip;
    //EditText name;
    EditText port;
    AlertDialog dialog;
    AsActivity ac;
    private static final Pattern PARTIAl_IP_ADDRESS =
            Pattern.compile("^(((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])\\.){0,3}((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])){0,1})|" +
          //  		"(([a-zA-Z0-9\\-]{0,63}\\.){0,10}[a-zA-Z]{0,6})|"+
           // 		"[a-zA-Z0-9]{0,62}$");
    		"[a-zA-Z]([a-zA-Z0-9\\-\\.]{0,70})|[a-zA-Z]$");
    //private static final Pattern PARTIAl_IPv6_ADDRESS =Pattern.compile("((^|:)([0-9a-fA-F]{0,4})){1,8}$");
    //private static final Pattern PARTIAl_DOMIAN_ADDRESS =Pattern.compile("^([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}$");
    DialogWindow(AsActivity activity, int ID,int servid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // ������ ��������
            View view = activity.getLayoutInflater().inflate(R.layout.dialog_add_server, null); // �������� layout �� ��� ID
            builder.setView(view);
            id=servid;
            ac=activity;
            ip=(EditText)view.findViewById(R.id.dialog_ip);
           // name=(EditText)view.findViewById(R.id.dialog_name);
            port=(EditText)view.findViewById(R.id.dialog_port);
            if(ID==IDD_ADD_SERVER){
            	builder.setTitle(R.string.add_server);
            	builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // ������ ��
                	@Override
            		public void onClick(DialogInterface dialog, int whichButton) {
                		ac.AddServer(ip.getText().toString(),Integer.parseInt(port.getText().toString()));//,name.getText().toString()
                		dialog.dismiss();
                	}
            	});
            	builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // ������ ������ 
            		@Override
            		public void onClick(DialogInterface dialog, int which) {
                    	dialog.dismiss();
                	}
            	});
            }
            else if(ID==IDD_EDIT_SERVER){
            	builder.setTitle(R.string.edit_server);
            	builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // ������ ��
                	@Override
            		public void onClick(DialogInterface dialog, int whichButton) {
                		ac.EditServer(id,ip.getText().toString(),Integer.parseInt(port.getText().toString()));//,name.getText().toString()
                		dialog.dismiss();
                	}
            	});
            	builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // ������ ������ 
            		@Override
            		public void onClick(DialogInterface dialog, int which) {
                    	dialog.dismiss();
                	}
            	});
            	ip.setText(ac.srvs[id].ip);
            	port.setText(""+ac.srvs[id].port);
            	//name.setText(Tools.servers[id].name);
            }
            builder.setCancelable(true);
            dialog= builder.create();
            ip.addTextChangedListener(new TextWatcher() {                       
              @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}            
              @Override public void beforeTextChanged(CharSequence s,int start,int count,int after) {}            

              private String mPreviousText = "";          
              @Override
  			public void afterTextChanged(Editable s) {          
                  if(PARTIAl_IP_ADDRESS.matcher(s).matches()) {
                      mPreviousText = s.toString();
                  }
                  else {
                      s.replace(0, s.length(), mPreviousText);
                  }
              }
          });
            port.addTextChangedListener(new TextWatcher() {                       
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}            
                @Override public void beforeTextChanged(CharSequence s,int start,int count,int after) {}            

                private int mPrevious=0;          
                @Override
    			public void afterTextChanged(Editable s) { 
                	int cur;
                	try {
                		cur=Integer.parseInt(s.toString());
                		
                	}catch (NumberFormatException e) {
                        cur=0;
                    } 
                	if(cur<65536) {
                        mPrevious = cur;
                    } else {
                        s.replace(0, s.length(), ""+mPrevious);
                    }
                }
            });
            dialog.show();
            return;
        	
    }
}
