package com.assignment.preftracker;

import java.util.List;
import java.util.Set;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	public final Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void onPrefUpdate()
    {
    	TextView display = (TextView) findViewById(R.id.pdata);
    	String prev=(String) display.getText();
		SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyPreference",Context.MODE_PRIVATE);
    	Set<String> keySet = prefs.getAll().keySet();
    	for(String key : keySet){
    	   if(key.contains("book")){
    	       System.out.println(prefs.getString(key, null));
    	       display.setText(prefs.getString(key, null)+prev);
    	       
    	   }
    	}
    }
    @Override
    public void onRestart()
    {
    	super.onRestart();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    	TextView display = (TextView) findViewById(R.id.pdata);
    	String previous=(String) display.getText();
    	System.out.println(previous.length());
    	String prev="";
    	TextView display2 = (TextView) findViewById(R.id.sdata);
		System.out.println("previous is "+prev);
		if(previous.length()!=0) {
			prev+=previous.substring(19);
		}
		System.out.println("previous is "+prev);
    	System.out.println(prefs.getString("book", ""));
    	DBConnection dbc=new DBConnection(this);
    	List<String> list=dbc.getMessage();
    	String mesg="";
    	for(int i=list.size()-1;i>=0;i--)
    	{
    		mesg+=list.get(i)+",";
    	}
    	if(prefs.getString("book", "").length()!=0)
    	{
    	display.setText("Preferences saved as "+prefs.getString("book", ""));
    	}
    	if(mesg.length()!=0)
    	{
    	display2.setText("Messages saved as "+mesg);
    	}
    }
    
    public void sqlClicked(View view)
    {
    	Intent intent=new Intent(context,SqlActivity.class);
		startActivity(intent);
    }
    
    public void prefclicked(View view)
    {
    	Intent intent=new Intent(context,PrefActivity.class);
		startActivity(intent);
    }
    
    public void mClose(View view)
    {
    	finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
