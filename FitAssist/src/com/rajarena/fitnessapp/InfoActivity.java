package com.rajarena.fitnessapp;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class InfoActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.infoactivitiy);
	}
	
	public void saveInfo(View view)
	{
		EditText height=(EditText)findViewById(R.id.height);
		EditText weight=(EditText)findViewById(R.id.weight);
		EditText age=(EditText)findViewById(R.id.age);
		EditText bodyfat=(EditText)findViewById(R.id.bodyfat);
		Spinner spinner=(Spinner)findViewById(R.id.sex);
		String val=String.valueOf(spinner.getSelectedItem());
		int sex;
		if(val.equalsIgnoreCase("male"))
		{
			sex=0;
		}
		else
		{
			sex=1;
		}
		DBConnection dbc=new DBConnection(this);
		String ht=height.getText().toString();
		String wt=weight.getText().toString();
		String ag=age.getText().toString();
		String bf=bodyfat.getText().toString();
		if(ht.isEmpty()||wt.isEmpty()||ag.isEmpty())
		{
			//System.out.println("IN");
			new AlertDialog.Builder(this).setTitle("Incomplete entries").setMessage("Did you enter all the values?").setNeutralButton("Retry",new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {

		           }
		       }).create().show();
		}
		else
		{
			
		dbc.addInfo(Integer.parseInt(ht),Integer.parseInt(wt) ,Integer.parseInt(ag) ,Integer.parseInt(bf) ,sex);
		Toast.makeText(this, "Information Saved", Toast.LENGTH_SHORT).show();
		Log.d("saveinfo", "Saved");
		startActivity(new Intent(this,MainActivity.class));
		}
	}
}
