package com.rajarena.emergencyassist;

import android.location.LocationManager;
import android.location.Criteria;
import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	LocationManager locationManager;
	static double lat,lng;
	 private String provider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        System.out.println(locationManager);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
          onLocationChanged(location);
        } else {
        	Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show();
        }
        //optional
        String locationProvider2 = LocationManager.NETWORK_PROVIDER;

        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void sendIt(View view)
    {
    	

        
    	 String[] phone_Num = {"05104176178","5104176108","5104088774"};
         String send_msg = "Alert Alert Alert\n My location is "+lat+","+lng;
         
         try {
        	 for(String num : phone_Num)
             {
        	 SmsManager sms = SmsManager.getDefault();  // using android SmsManager            
             sms.sendTextMessage(num, null, send_msg, null, null);  // adding number and text 
             }
         } catch (Exception e) {
             Toast.makeText(this, "Sms not Send", Toast.LENGTH_SHORT).show();
             e.printStackTrace();
         }
    
    }
    
    public void onLocationChanged(Location location) {
         lat =  location.getLatitude();
         lng =  location.getLongitude();
      }

    public static String getLocation()
    {
    	return lat+","+lng;

    }
    
}
