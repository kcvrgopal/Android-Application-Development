package com.assignment.preftracker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SqlActivity extends Activity {
	public Context context=this;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlview);
    }
public void ssave(View view){
	EditText msg = (EditText) findViewById(R.id.message);
	DBConnection dbc=new DBConnection(this);
	dbc.addMessage(msg.getText().toString(),getDateTime());
	System.out.println(dbc.getMessage());

}

public void sclose(View view){
	Intent intent=new Intent(context,MainActivity.class);
	startActivity(intent);
}

public String getDateTime() {
    SimpleDateFormat dateFormat = new SimpleDateFormat(
            "MM-dd-yy HH:mm:ss", Locale.getDefault());
    Date date = new Date();
    return dateFormat.format(date);
}
}
