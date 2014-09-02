package com.rajarena.emergencyassist;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class SettingsPage extends Activity {

	private int initialized;
    Intent intentservice;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingspage);  
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        int storedPreference = preferences.getInt("shakeon", 0);
        int initialized=preferences.getInt("initialized", 0);
    	Switch s=(Switch)findViewById(R.id.switch1);
    	intentservice=new Intent(this, ShakeDetector.class);
        if(storedPreference==0)
        {
        	System.out.println("zero"+storedPreference);
        	s.setChecked(false);
        }
        else if(storedPreference==1)
        {
        	System.out.println("one"+storedPreference);
        	s.setChecked(true);        
        	if(initialized==0)
        	{
   			 this.startService(new Intent(this, ShakeDetector.class));
        	}
        }
	}
	
	public void shakeChanged(View view) {
	    // Is the toggle on?
		SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
	    boolean on = ((Switch) view).isChecked();
	    if (on) {
	        // Enable vibrate
			 editor.putInt("shakeon", 1);	   
			 this.startService(intentservice);
	    } else {
	        // Disable vibrate
			 editor.putInt("shakeon", 0);
			 System.out.println("offf");
			 this.stopService(intentservice);
	    }
		 editor.commit();
	}
}
