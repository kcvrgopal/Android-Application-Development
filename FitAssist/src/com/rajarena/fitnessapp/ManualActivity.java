package com.rajarena.fitnessapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ManualActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manualactivity);
}
	public void msubmit(View view)
	{
		double[] met = new double[2];
		Spinner act1=(Spinner)findViewById(R.id.spinner1);
		String activity1=String.valueOf(act1.getSelectedItem());
		Spinner act2=(Spinner)findViewById(R.id.spinner2);
		String activity2=String.valueOf(act2.getSelectedItem());
		String[] act = {activity1,activity2};
		EditText tm1=(EditText) findViewById(R.id.act1);
		EditText tm2=(EditText) findViewById(R.id.act2);
		int t1=Integer.parseInt(String.valueOf(tm1.getText()));
		int t2=Integer.parseInt(String.valueOf(tm2.getText()));
		float wt=CalActivity.getWeight();
		for(int i=0;i<2;i++)
		{
			if(act[i].equalsIgnoreCase("None"))
			{
				met[i]=0;
				
			}
			else if(act[i].equalsIgnoreCase("Swimming"))
			{
				met[i]=7.0;
			}
			else if(act[i].equalsIgnoreCase("Hiking"))
			{
				met[i]=6.0;
			}
			else if(act[i].equalsIgnoreCase("Racquetball"))
			{
				met[i]=4.6;
			}
			else if(act[i].equalsIgnoreCase("Badminton"))
			{
				met[i]=4.5;
			}
			else if(act[i].equalsIgnoreCase("Basketball"))
			{
				met[i]=7.0;
			}
			System.out.println(CalActivity.getWeight());
			
		}
		double calburned=(t1*(met[0]*3.5*wt)/200)+(t2*(met[1]*3.5*wt)/200);
		System.out.println(calburned);
		MainActivity.addCalBurned(calburned);
		Toast.makeText(this, "Activities recored", Toast.LENGTH_SHORT).show();
	}
}
