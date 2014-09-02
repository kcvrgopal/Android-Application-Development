package com.rajarena.emergencyassist;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MedicalInformation extends Activity {
	
	DBConnection dbConn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medical_information);
		dbConn=new DBConnection(this);
		setupActionBar();
	}

	@Override
    public void onBackPressed() {
             boolean fromMedicalInformation=true;
     Intent mainIntent = new Intent(this, MainActivity.class);
     Bundle bundleObj = new Bundle();
     bundleObj.putString("fromMedicalInformation", Boolean.toString(fromMedicalInformation));
     mainIntent.putExtras(bundleObj);
     startActivityForResult(mainIntent, 0);
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.medical_information, menu);
		return true;
	}
	
	@Override
    protected void onResume()
    {
    	super.onResume();
    	getMedicalInfo();
    }
	
	public void saveMedicalInfo(View view)
	{
		EditText txtname = (EditText) findViewById(R.id.txtName);
		String Name = txtname.getText().toString();
		
		EditText txtBloodType = (EditText) findViewById(R.id.txtBloodType);
		String bloodType=txtBloodType.getText().toString();
		
		EditText txtAllergies = (EditText) findViewById(R.id.txtAllergy);
		String allergies=txtAllergies.getText().toString();
		
		EditText txtMedicines = (EditText) findViewById(R.id.txtMedicines);
		String medicines=txtMedicines.getText().toString();
		if(Name.isEmpty()||bloodType.isEmpty())
		{
			new AlertDialog.Builder(this).setTitle("Incomplete entries").setMessage("Did you enter all the values?").setNeutralButton("Retry",new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {

		           }
		       }).create().show();
		}
		else
		{
		dbConn.addMedicalInfo(Name, bloodType, allergies, medicines);
		Toast.makeText(this, "Medical Information Saved Successfully",Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(this,MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    	startActivity(intent);
		}
	}
	
	public void getMedicalInfo()
	{
		
		List<String> retrieveMedical=dbConn.getMedicalInfo();
		
		if(retrieveMedical.size()>0)
		{
			String name=retrieveMedical.get(0);
			Log.d("Jeena",name);
			String bloodType=retrieveMedical.get(1);
			Log.d("Jeena",bloodType);
			String allergies = retrieveMedical.get(2);
			Log.d("Jeena",allergies);
		
			EditText txtname = (EditText) findViewById(R.id.txtName);
			txtname.setText(name);
		
			EditText txtBloodType = (EditText) findViewById(R.id.txtBloodType);
			txtBloodType.setText(bloodType);
		
			EditText txtAllergies = (EditText) findViewById(R.id.txtAllergy);
			txtAllergies.setText(allergies);
		}
	}

	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}
