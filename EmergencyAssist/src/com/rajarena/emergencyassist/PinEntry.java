package com.rajarena.emergencyassist;

import java.util.List;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PinEntry extends Activity {
	DBConnection dbc;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pinentry);     
        dbc=new DBConnection(this);
    }
	
	public void checkpin(View view)
	{
		EditText ep=(EditText)findViewById(R.id.pinenter);
		if(ep.getText().toString().equals(""))
		{
			new AlertDialog.Builder(this).setTitle("No PIN Entered").setMessage("Did you enter your PIN?").setNeutralButton("Retry",new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {

		           }
		       }).create().show();
		}
		else
		{
		int p=Integer.parseInt(ep.getText().toString());
		List<Integer> val=dbc.getSecret();
		if(p==val.get(0))
		{
			startNewActivity();
			
		}
		else
		{
			new AlertDialog.Builder(this).setTitle("PIN Mismatch").setMessage("PIN does not match").setNeutralButton("Retry",new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {

		           }
		       }).create().show();
		}
		}
	}
	
	public void startNewActivity(){
		if (StartActivityName.activityName.equals("Register")){
			Intent intent=new Intent(this,Register.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        	startActivity(intent);
		}
		else if (StartActivityName.activityName.equals("EmergencyContacts")){
			
			Intent intent=new Intent(this,EmergencyContacts.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        	startActivity(intent);
		}
		else if (StartActivityName.activityName.equals("MedicalInformation")){
			
			Intent intent=new Intent(this,MedicalInformation.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        	startActivity(intent);
		}
			
	}
}
