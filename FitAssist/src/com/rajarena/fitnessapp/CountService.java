package com.rajarena.fitnessapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;
import android.content.SharedPreferences;


public class CountService extends Service implements SensorEventListener {
	private static double count=0.0;
	double last=0;
	long now,prev=0;
	String pmsg="";
	SensorManager sm;
	static double calburned;
	Sensor countsensor,as;
	DBConnection dbc;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
		if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
			now=System.currentTimeMillis();
			float time=(float)(now-prev);
			//finding the time in seconds
			float time_s=(time/1000);
			//finding the acceleration 
			double x=Double.parseDouble(String.valueOf(event.values[0]));
			double y=Double.parseDouble(String.valueOf(event.values[1]));
			double z=Double.parseDouble(String.valueOf(event.values[2]));
			double res=Math.sqrt(x*x+y*y+z*z);
			//finding the distance
			double dist=(res*time_s*time_s*1000);
			if (dist>=1.85)
			{
				count=count+0.8;
				((FitAssistApplication) this.getApplication()).setCount(count);

			}
			prev=now;
			String msg="count is "+Math.round(count);
			if(Math.round(count)%100==0&&Math.round(count)!=0)
			{
				if(!pmsg.equals(msg))
					Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
				pmsg=msg;
			}
			editor.putFloat("countvalue", (float) count);
			editor.commit();
		}
		else if(event.sensor.getType()==Sensor.TYPE_STEP_COUNTER)
		{
			count=Double.parseDouble(String.valueOf(event.values[0]));
			((FitAssistApplication) this.getApplication()).setCount(count);
			editor.putFloat("countvalue", (float) count);
			editor.commit();
		}
		dbc.updateCount(count);
		System.out.println(dbc.getCountfromdb().get(0));

	}
	
	public void onStart(Intent intent, int startid)
	  {
		dbc=new DBConnection(this);
		dbc.addCounttodb(0.0);
		  System.out.println("inside service");
		//Getting count and accelerometer sensors
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
					Toast.makeText(this, "Accelerometer and Step detectors are not available", Toast.LENGTH_SHORT).show();
				}
			}
	  }
	
	@Override
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

}
