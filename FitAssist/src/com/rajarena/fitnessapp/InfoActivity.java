package com.rajarena.fitnessapp;


import android.app.Activity;
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
		dbc.addInfo(Integer.parseInt(height.getText().toString()), Integer.parseInt(weight.getText().toString()), Integer.parseInt(age.getText().toString()), Integer.parseInt(bodyfat.getText().toString()),sex);
		Toast.makeText(this, "Information Saved", Toast.LENGTH_SHORT).show();
		Log.d("saveinfo", "Saved");
	}
}
