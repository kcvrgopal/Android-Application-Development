package com.rajarena.emergencyassist;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SendService extends IntentService implements LocationListener {
	
	private boolean isLocationAvailable = false;
	private boolean isAlertSent = false;
	Location location;
	public SendService (){
		super("SendService");
	}
	
	public SendService(String name) {
		super(name);
	}
	
	LocationManager locationManager;
	static double lat,lng;
	 private String provider;
	private boolean gpsavailable;
	private String prevlocation;
	 
	 	@Override
		public void onCreate() {
			super.onCreate();
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	        System.out.println(locationManager);
	        Criteria criteria = new Criteria();
	        provider = locationManager.getBestProvider(criteria, false);
	        locationManager.requestLocationUpdates(provider, 1	, 10, this);
	        Location location = locationManager.getLastKnownLocation(provider);
	        if (location != null) {	        	
	          onLocationChanged(location);
	        	gpsavailable=true;

	        } 
	        else {	        	
	        	Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show();
	        	gpsavailable=false;
	        }
		}
	 	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
        Location location = locationManager.getLastKnownLocation(provider);
		String loc=lat+","+lng;
		
        String messageToSend = null;
			DBConnection dbc=new DBConnection(getBaseContext());
			if(location!=null)
			{
				dbc.addLocation(loc);
			}
			try
			{
				Bundle extras = intent.getExtras();
				
				if (extras != null) {
				    if (extras.containsKey("numarray") && !extras.containsKey("imageURL")) {
				    	String[] numarray=extras.getStringArray("numarray");
		    			Toast.makeText(getBaseContext(), "count"+numarray.length, Toast.LENGTH_SHORT);
		    			System.out.println(numarray.length);
				    	try {
				    		for(int i=0;i<numarray.length;i++)
				    		{
							SmsManager sms = SmsManager.getDefault();  // using android SmsManager   
							if(location!=null)
							{
							messageToSend = new String("I'm at "+loc+"\n"+"http://maps.google.com/maps?q=loc:"+loc);
							}
							else
							{
								messageToSend="Alert! I'm in trouble";
							}
							ArrayList<String> msgParts = sms.divideMessage(messageToSend);
							sms.sendMultipartTextMessage(numarray[i], null, msgParts, null, null);
							Toast.makeText(getBaseContext(), "SMS Sent to " + numarray[i], Toast.LENGTH_SHORT).show();
							isAlertSent=true;

				    		}

						} catch (Exception e) {
							Toast.makeText(getBaseContext(), "SMS could not be sent", Toast.LENGTH_SHORT).show();
							e.printStackTrace();
							isAlertSent=false;
						}
				    }
				    
				    
				    if (extras.containsKey("numarray") && extras.containsKey("imageURL")) {
				    	String[] numarray=extras.getStringArray("numarray");
				    	String url=extras.getString("imageURL");
				    	
				    	try {
				    		for(int i=0;i<numarray.length;i++)
				    		{
							SmsManager sms = SmsManager.getDefault();  // using android SmsManager     
							if(location!=null)
							{
							messageToSend = new String("I'm at "+loc+"\n"+"http://maps.google.com/maps?q=loc:"+loc+ "\nPlease see my image at: "+url);
							}
							else
							{
								messageToSend="Alert! I'm in trouble"+ "\nPlease see my image at: "+url;
							}
							ArrayList<String> msgParts = sms.divideMessage(messageToSend);
							sms.sendMultipartTextMessage(numarray[i], null, msgParts, null, null);
							//sms.sendTextMessage(numarray[i], null, "Image: "+url +"    I'm at "+loc+"\n"+"http://maps.google.com/maps?q=loc:"+loc, null, null);  // adding number and text
							Toast.makeText(getBaseContext(), "SMS Sent to " + numarray[i], Toast.LENGTH_SHORT).show();
							isAlertSent=true;

				    		}

						} catch (Exception e) {
							Toast.makeText(getBaseContext(), "SMS could not be sent", Toast.LENGTH_SHORT).show();
							e.printStackTrace();
							isAlertSent=false;
						}
				    }
				    
				    
				    if (extras.containsKey("num")) {
				    	String num=extras.getString("num");
				    	try {

							SmsManager sms = SmsManager.getDefault();  // using android SmsManager  
							List<String> val=dbc.getLocation();
							String prev = null;
							System.out.println(val.size()+val.get(0));
							if(val.size()!=0)
							{
								prev=val.get(0);
								Log.d("test", prev);
								if(!prev.contains("0.0,0.0"))
								{
									prevlocation=prev;
								}
								else
								{
									prevlocation=null;
								}
								System.out.println(prev);
							}
							else
							{
								prevlocation=null;
							}
							if(location!=null)
							{
							messageToSend = new String("I'm at "+loc+"\n"+"http://maps.google.com/maps?q=loc:"+loc);	
							}
							else
							{
								if(prevlocation!=null)
								{
									messageToSend="Your device's location service is off"+"\nLast known location is "+prevlocation;
								}
								else
								{
									messageToSend="Your device's location service is off"+"\nNo Last known location data available";
								}
							}
							ArrayList<String> msgParts = sms.divideMessage(messageToSend);
							sms.sendMultipartTextMessage(num, null, msgParts, null, null);
							isAlertSent=true;
							dbc.addAccessTime(num, getDateTime());

						} catch (Exception e) {
							Toast.makeText(getBaseContext(), "SMS could not be sent", Toast.LENGTH_SHORT).show();
							isAlertSent=false;
							e.printStackTrace();
						}
				    }
				}

			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}	
        return START_STICKY;
	}

	@Override
	protected void onHandleIntent (Intent intent){		
		Toast.makeText(getBaseContext(), "On Handle Intent", Toast.LENGTH_SHORT).show();
		//sendAlert(intent);
	}
	
	
	 public void onLocationChanged(Location location) {
	        lat =  location.getLatitude();
	        lng =  location.getLongitude();
	     }
	 
	 public String getDateTime() {
	        SimpleDateFormat dateFormat = new SimpleDateFormat(
	                "MM-dd-yy HH:mm:ss", Locale.getDefault());
	        Date date = new Date();
	        return dateFormat.format(date);
	 }
	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
}
