package com.rajarena.fitnessapp;

import android.os.Bundle;
import android.os.PowerManager;

import java.util.Calendar;
import java.util.Timer;  
import java.util.TimerTask; 
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {
	SensorManager sm;
	static double calburned;
	Sensor countsensor,as;
	PowerManager.WakeLock wl;
	PowerManager pm;
	private static double count=0.0;
	double last=0;
	long now,prev=0;
	String pmsg="";
	Timer myTimer;
	long tillMidnight;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
		wl.acquire();

		sm=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
		countsensor=sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
		if(countsensor!=null)
		{
			Toast.makeText(this, "Step detector available", Toast.LENGTH_SHORT).show();
			sm.registerListener(this, countsensor, SensorManager.SENSOR_DELAY_UI);
		}
		else
		{
			as=sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
			if(as!=null)
			{
				Toast.makeText(this, "Step detector not available. Don't worry, Accelerometer is available", Toast.LENGTH_SHORT).show();
				sm.registerListener(this, as, SensorManager.SENSOR_DELAY_FASTEST);
			}
			else
			{
				Toast.makeText(this, "Accelerometer and Step detector are not available", Toast.LENGTH_SHORT).show();
			}
		}
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

	/*	public void onStart()
	{
		super.onStart();
	}*/

	public void logit(View view)
	{
		Intent intent=new Intent(this,ManualActivity.class);
		startActivity(intent);
	}
	private void TimerMethod()
	{
		this.runOnUiThread(Timer_Tick);
	}

	private Runnable Timer_Tick = new Runnable() {
		public void run() {
			MainActivity.setCount(0.0);
			MainActivity.resetCal();
		}
	};

	private void SummaryMethod()
	{
		this.runOnUiThread(summary);
	}

	private Runnable summary = new Runnable() {
		public void run() {
			String text="You took "+Math.round(MainActivity.getCount())+" steps."+"For detailed summary open application";
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
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
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

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
			now=System.currentTimeMillis();
			float time=(float)(now-prev);
			//finding the time in seconds
			float time_s=(time/1000);
			double x=Double.parseDouble(String.valueOf(event.values[0]));
			double y=Double.parseDouble(String.valueOf(event.values[1]));
			double z=Double.parseDouble(String.valueOf(event.values[2]));
			double res=Math.sqrt(x*x+y*y+z*z);
			double dist=(res*time_s*time_s*1000);
			System.out.println(dist*100);
			if (dist>=1.75)
			{
				MainActivity.count=MainActivity.count+0.94;
			}
			prev=now;
			last=res;
			String msg="count is "+Math.round(count);
			if(Math.round(count)%25==0&&Math.round(count)!=0)
			{
				if(!pmsg.equals(msg))
					Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
				pmsg=msg;
			}
		}
		else if(event.sensor.getType()==Sensor.TYPE_STEP_COUNTER)
		{
			MainActivity.count=Double.parseDouble(String.valueOf(event.values[0]));
		}

	}

	public void onDestroy()
	{
		super.onDestroy();
		if(countsensor!=null)
		{
			sm.unregisterListener(this);
		}
		else if(as!=null)
		{
			sm.unregisterListener(this);
		}
		wl.release();
	}

	public static void setCount(double c)
	{
		count=c;
	}

	public static double getCount()
	{
		return count;
	}
	public static void resetCal()
	{
		calburned=0;
	}
	public static void addCalBurned(double c)
	{
		calburned=calburned+c;
	}
	public static double getCalBurned()
	{
		return calburned;
	}
}
