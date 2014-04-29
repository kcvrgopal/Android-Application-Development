package com.rajarena.fitnessapp;

import android.os.Bundle;

import android.os.PowerManager;
import android.preference.PreferenceManager;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;  
import java.util.TimerTask; 
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity{
	SensorManager sm;
	static double calburned;
	Sensor countsensor,as;
	PowerManager.WakeLock wl;
	private static double count;
	double last=0;
	long now,prev=0;
	String pmsg="";
	Timer myTimer;
	long tillMidnight;
	SharedPreferences.Editor editor;
	DBConnection dbc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbc=new DBConnection(this);

		dbc.addCounttodb(0.0);
		dbc.addCaltodb(0.0);
		this.startService(new Intent(this,CountService.class));
		//count=((FitAssistApplication) MainActivity.this.getApplication()).getCount();
		List<Integer> val=dbc.getCountfromdb();
		count=val.get(0);
		setContentView(R.layout.activity_main);
		//Initiating schedule services for periodic notifications and daily data reset
		Calendar midnight=Calendar.getInstance();
		midnight.set(Calendar.HOUR_OF_DAY, 0);
		midnight.set(Calendar.MINUTE, 0);
		midnight.set(Calendar.SECOND, 0);
		midnight.set(Calendar.MILLISECOND, 1);
		midnight.set(Calendar.DAY_OF_YEAR, midnight.get(Calendar.DAY_OF_YEAR)+1);
		tillMidnight = midnight.getTimeInMillis() - System.currentTimeMillis() - 1;
		myTimer = new Timer();
		myTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				TimerMethod();
			}

		}, tillMidnight, 86400000);	
		myTimer = new Timer();
		myTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				SummaryMethod();
			}

		}, 0, 10800000);
	}

	public void onStart()
	{
		super.onStart();
	}

	public void logit(View view)
	{
		Intent intent=new Intent(this,ManualActivity.class);
		startActivity(intent);
	}
	
	//To reset counter and calories burned every midnight
	private void TimerMethod()
	{
		this.runOnUiThread(Timer_Tick);
	}

	private Runnable Timer_Tick = new Runnable() {
		public void run() {
			((FitAssistApplication) MainActivity.this.getApplication()).setCalBurned(0.0);
			editor.putFloat("countvalue", (float) 0.0);
			dbc.updateCount(0.0);
			dbc.updateCal(0.0);
			((FitAssistApplication) MainActivity.this.getApplication()).setCount(0.0);
		}
	};
	//To provide summary for every 3 hours
	private void SummaryMethod()
	{
		this.runOnUiThread(summary);
	}

	private Runnable summary = new Runnable() {
		public void run() {
			List<Integer> c=dbc.getCountfromdb();
			List<Integer> cc=dbc.getCalfromdb();
			String text="You took "+c.get(0)+" steps and burned "+cc.get(0)+" Calories.";
			Intent intent = new Intent(getApplicationContext(), CalActivity.class);
			PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
			Notification notification = new Notification.Builder(getApplicationContext())
			.setContentTitle("Your Summary ")
			.setContentText(text).setSmallIcon(R.drawable.ic_launcher)
			.setContentIntent(pIntent).build();
			NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notificationManager.notify(0, notification);

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	public void onClickSaveInfo(View view)
	{
		Intent intent=new Intent(this,InfoActivity.class);
		startActivity(intent);

	}

	public void onClickCalculate(View view)
	{
		Intent intent=new Intent(this,CalActivity.class);
		startActivity(intent);
	}
	
	public void onRestart()
	{
		super.onRestart();

	}
	
	public void onResume()
	{
		super.onResume();
		
	}
	
	public void onPause()
	{
		super.onPause();
		
	}
	
	public void onDestroy()
	{
		super.onDestroy();
	}

	}
