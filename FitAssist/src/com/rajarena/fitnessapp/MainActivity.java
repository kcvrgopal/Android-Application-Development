package com.rajarena.fitnessapp;

import android.os.Bundle;

import java.util.Calendar;
import java.util.Timer;  
import java.util.TimerTask; 
import android.app.Activity;
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
		sm=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
		System.out.println("HEEEYYY");
		countsensor=sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
		if(countsensor!=null)
		{
			Toast.makeText(this, "Step detector available", Toast.LENGTH_SHORT).show();
			sm.registerListener(this, countsensor, SensorManager.SENSOR_DELAY_UI);
		}
		else
		{
			Toast.makeText(this, "Step detector not available", Toast.LENGTH_SHORT).show();
			as=sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
			if(as!=null)
			{
				Toast.makeText(this, "Don't worry, Accelerometer available", Toast.LENGTH_SHORT).show();
				sm.registerListener(this, as, SensorManager.SENSOR_DELAY_NORMAL);
			}
			else
			{
				Toast.makeText(this, "Accelerometer not available", Toast.LENGTH_SHORT).show();
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
	}
	
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
			//System.out.println(dist);
			if(dist>50)
			{
				this.count=this.count+0.39;
				//System.out.println(count);
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
			this.count=Double.parseDouble(String.valueOf(event.values[0]));
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
