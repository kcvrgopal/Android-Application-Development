package com.rajarena.emergencyassist;

import java.util.List;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity {
	DBConnection dbc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);   
        setupActionBar();
    }
    
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	@Override
    public void onBackPressed() {
     boolean fromRegister=true;
     Intent mainIntent = new Intent(this, MainActivity.class);
     Bundle bundleObj = new Bundle();
     bundleObj.putString("fromRegister", Boolean.toString(fromRegister));
     mainIntent.putExtras(bundleObj);
     startActivityForResult(mainIntent, 0);
    }
	
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parse_rest, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
    public void onStart()
    {
    	super.onStart();
    	
    }
    public void saveit(View view)
    {
    	EditText codetext=(EditText)findViewById(R.id.editCodeText);
    	DBConnection dbc=new DBConnection(this);
    	String code=codetext.getText().toString();
    	if(code.isEmpty())
    	{
    		new AlertDialog.Builder(this).setTitle("Incomplete entries").setMessage("Did you enter all the values?").setNeutralButton("Retry",new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {

		           }
		       }).create().show();
    	}
    	else
    	{
    		Log.d("register", "else"+code);
    		dbc.addCode(code);
    		Toast.makeText(this, "Information Saved", Toast.LENGTH_SHORT).show();
    		Intent intent=new Intent(this,MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        	startActivity(intent);
    	}
    }
    
    public void viewit(View view)
    {
    	DBConnection dbc=new DBConnection(this);
    	TextView tv=(TextView)findViewById(R.id.textView2);
    	List<String> val=dbc.getCode();
    	if(val.size()==0)
    	{
    		new AlertDialog.Builder(this).setTitle("No code Set").setMessage("Did you set a code").setNeutralButton("Retry",new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {

		           }
		       }).create().show();
    	}
    	else
    	{
    	tv.setText(val.get(0));
    	}
    }
    public void changemypin(View view)
    {
    	Intent intent=new Intent(this,PinCreate.class);
    	startActivity(intent);
    	
    }
}
