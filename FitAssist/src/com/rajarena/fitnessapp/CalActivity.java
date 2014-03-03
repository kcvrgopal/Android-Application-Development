package com.rajarena.fitnessapp;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class CalActivity extends Activity {
	static float wt;
	static double calburned;
	DBConnection dbc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calactivity);
		dbc=new DBConnection(this);
		String msg="You took "+Math.round(((FitAssistApplication) this.getApplication()).getCount())+" steps";
		Toast.makeText(this, msg , Toast.LENGTH_SHORT).show();
	}
	public void onStart()
	{
		super.onStart();
		calculate();
	}
	public void onRestart()
	{
		super.onRestart();
		calculate();
	}
	
	public void onResume()
	{
		super.onResume();
		calculate();
	}
	public void onDestroy()
	{
		super.onDestroy();
		if(dbc!=null)
			dbc.close();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu2, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		System.out.println("ZZZ");
		 switch (item.getItemId()) {
	        case R.id.shareit:
	        	String msg="Yippee, I took "+Math.round(((FitAssistApplication) this.getApplication()).getCount())+" steps and burned "+Math.round(((FitAssistApplication) this.getApplication()).getCalBurned())+" calories today! - Via FitAssist";
	        	Intent sendIntent = new Intent();
	    		sendIntent.setAction(Intent.ACTION_SEND);
	    		sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
	    		sendIntent.setType("text/plain");
	    		startActivity(Intent.createChooser(sendIntent, "Share your summary to "));
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }	    
	}
	
	public void calculate()
	{
		//DBConnection dbc=new DBConnection(this);
		List<Integer> val=dbc.getInfo();
		if(!val.isEmpty())
		{
		float ht=(float)val.get(0)/100;
		wt=(float) ((float)val.get(1)/2.20462);
		((FitAssistApplication) this.getApplication()).setWeight(wt);
		float age=(float)val.get(2);
		float bf=(float)val.get(3);
		int sex=val.get(4);
		//calculating BMI
		float cbmi = wt/(ht*ht);
		float leanmass=wt-bf*wt/100;
		float cbmr;
		float x=(float) 0.0;
		//Calculating BMR
		if(sex==0)
		{
			cbmr = (float)(66+13.7*(leanmass)+5*(ht*100)-6.8*(age));
		}
		else
		{
			cbmr = (float) (655+9.6*leanmass+1.8*ht-4.7*age);
		}
		calburned = ((val.get(1))*((FitAssistApplication) this.getApplication()).getCount()/3500)+MainActivity.getCalBurned();
		((FitAssistApplication) this.getApplication()).setCalBurned(calburned);
		if(calburned<100)
		{
			x=(float) 1.2;
		}
		else if(calburned>=100&&calburned<250)
		{
			x=(float) 1.3;
		}
		else if(calburned >= 250 && calburned <425)
		{
			x=(float) 1.4;
		}
		else if(calburned>=425&&calburned <700)
		{
			x=(float) 1.5;
		}
		else if(calburned>=700)
		{
			x=(float) 1.6;	
		}
		double lcbmr=cbmr;
		if (((cbmr*x)-500)<cbmr)
		{
			lcbmr=cbmr;
		}
		else
		{
			lcbmr = (cbmr*x)-500;
		}
		
		((TextView) findViewById(R.id.bmi)).setText(String.valueOf(cbmi));
		((TextView) findViewById(R.id.bmr)).setText(String.valueOf(Math.round(cbmr)));
		((TextView) findViewById(R.id.put)).setText(String.valueOf(Math.round((cbmr*x)+500))+" calories");
		((TextView) findViewById(R.id.cut)).setText(String.valueOf(Math.round(lcbmr))+" calories");
		((TextView) findViewById(R.id.maintain)).setText(String.valueOf(Math.round((cbmr*x)))+" calories");
		((TextView) findViewById(R.id.leanmass)).setText(String.valueOf(Math.round(leanmass*2.2))+" pounds");
		((TextView) findViewById(R.id.calburned)).setText(String.valueOf(Math.round(calburned))+" calories");
		}
		else
		{
			((TextView) findViewById(R.id.bmi)).setText("0");
			((TextView) findViewById(R.id.bmr)).setText("0");
			((TextView) findViewById(R.id.put)).setText("0"+" calories");
			((TextView) findViewById(R.id.cut)).setText("0"+" calories");
			((TextView) findViewById(R.id.maintain)).setText("0"+" calories");
			((TextView) findViewById(R.id.leanmass)).setText("0"+" pounds");
			((TextView) findViewById(R.id.calburned)).setText(String.valueOf(Math.round(calburned/55))+" calories");
			Toast.makeText(this, "Did you register your data?", Toast.LENGTH_SHORT).show();
		}
	}
	
	public static float getWeight()
	{
		return wt;
	}
	
	public static double getCalBurned()
	{
		return calburned;
	}
}
