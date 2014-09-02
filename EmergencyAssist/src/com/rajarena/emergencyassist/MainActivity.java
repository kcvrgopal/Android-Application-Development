package com.rajarena.emergencyassist;

import java.util.List;


import android.os.Bundle;
import android.os.Debug;
import android.app.Activity;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	DBConnection dbc;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 
       // Debug.startMethodTracing("emergencyAssist");
        dbc=new DBConnection(this);
        List<Integer> val=dbc.getSecret();
        if(val.size()==0)
        {
        	startActivity(new Intent(this,PinCreate.class));
        }        
    }

    @Override
    public void onBackPressed() {
     boolean fromMain=true;
     Intent mainIntent = new Intent(this, MainActivity.class);
     Bundle bundleObj = new Bundle();
     bundleObj.putString("fromMain", Boolean.toString(fromMain));
     mainIntent.putExtras(bundleObj);
     startActivityForResult(mainIntent, 0);
    }
    
    public void editContacts (View view)
    {
    	StartActivityName.activityName = new String("EmergencyContacts");
    	//startActivity (new Intent (this,EmergencyContacts.class));
    	startActivity(new Intent(this,PinEntry.class));    	
    }
    
    public void addOrEditMedical(View view)
    {
    	StartActivityName.activityName = new String("MedicalInformation");
    	//startActivity(new Intent (this,MedicalInformation.class));
    	startActivity(new Intent(this,PinEntry.class));
    }
    
    public void sendIt(View view)
    {
    	List<String> phoneNumbers = dbc.getContacts();
    	String[] numarray = phoneNumbers.toArray(new String[phoneNumbers.size()]);
    	
    	Intent sendintent= new Intent(this, SendService.class);
		sendintent.putExtra("numarray", numarray);
		
		this.startService(sendintent);
		
    
    }
    
    public void sendWithImage(View view)
    {
    	startActivity(new Intent (this,CameraActivity.class));
    }
 
    
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		List<String> access=dbc.getLastAccess();
        if(access.size()==0)
        {
        	
        }
        else
        {
        	String Message = "You were last accessed "+access.get(0);
        	TextView tv=(TextView)findViewById(R.id.accessshow);
        	tv.setText(Message);
        }
		
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);   
        }

    public boolean onOptionsItemSelected(MenuItem item) {
    	System.out.println("ZZZ");
		 switch (item.getItemId()) {
	        case R.id.set:
	        	Intent sendIntent = new Intent(this,SettingsPage.class);
	    		startActivity(sendIntent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }	    
    	}
    
    public void editCode(View view)
    {
    	StartActivityName.activityName = new String("Register");
    	startActivity(new Intent(this,PinEntry.class));
    }

	
    @Override
    protected void onDestroy()
    {
    	super.onDestroy();
    	//Debug.stopMethodTracing();
    }
    
      
}
