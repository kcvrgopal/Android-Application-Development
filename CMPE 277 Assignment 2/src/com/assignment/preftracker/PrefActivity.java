package com.assignment.preftracker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PrefActivity extends Activity {
	public Context context=this;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prefview);
    }
	@SuppressWarnings("unused")
	public void psave(View view)
	{
		EditText name = (EditText) findViewById(R.id.name);
		EditText author = (EditText) findViewById(R.id.author);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor edit=prefs.edit();
		System.out.println(getDateTime());
		String prev=prefs.getString("book", "");
		edit.putString("book", name.getText().toString()+" "+author.getText().toString()+" at "+getDateTime()+prev);
		edit.commit();	
		System.out.println("lol-Preference saved as "+name.getText().toString()+author.getText().toString());
	}

	public void pcancel(View view)
	{
		//startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		finish();
	}
	
	public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "MM-dd-yy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
}
	
}
