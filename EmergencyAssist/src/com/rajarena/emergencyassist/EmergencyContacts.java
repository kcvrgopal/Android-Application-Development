package com.rajarena.emergencyassist;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EmergencyContacts extends Activity {

	private static final int CONTACT_PICKER_RESULT = 1001;
	private static final String DEBUG_TAG = "debug";
	DBConnection dbConn;
	private boolean found;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emergency_contacts);
		dbConn=new DBConnection(this);
		setupActionBar();
	}
	
	@Override
    public void onBackPressed() {
     boolean fromEmergencyContacts=true;
     Intent mainIntent = new Intent(this, MainActivity.class);
     Bundle bundleObj = new Bundle();
     bundleObj.putString("fromEmergencyContacts", Boolean.toString(fromEmergencyContacts));
     mainIntent.putExtras(bundleObj);
     startActivityForResult(mainIntent, 0);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.emergency_contacts, menu);
		return true;
	}


	
	public void showContacts(View view){
		Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
	            Contacts.CONTENT_URI);
	    startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (resultCode == RESULT_OK) {
	        switch (requestCode) {
	        case CONTACT_PICKER_RESULT:
	            Cursor cursor = null;
	            String phoneNum = "";
	            try {
	                Uri result = data.getData();
	                Log.v(DEBUG_TAG, "Got a contact result: "
	                        + result.toString());

	                
	                String id = result.getLastPathSegment();
	                
	                cursor = getContentResolver().query(Phone.CONTENT_URI,
	                        null, Phone.CONTACT_ID + "=?", new String[] { id },
	                        null);

	                
	                int phoneNumIndex = cursor.getColumnIndex(Phone.DATA);
	                	                
	                if (cursor.moveToFirst()) {
	                    phoneNum = cursor.getString(phoneNumIndex);
	                    Log.v(DEBUG_TAG, "Got number: " + phoneNum);
	                } else {
	                    Log.w(DEBUG_TAG, "No results");
	                }
	            } catch (Exception e) {
	                Log.e(DEBUG_TAG, "Failed to get number", e);
	            } finally {
	                if (cursor != null) {
	                    cursor.close();
	                }
	                EditText phoneNumText = (EditText) findViewById(R.id.editText1);
	                phoneNumText.setText(phoneNum);
	                if (phoneNum.length() == 0) {
	                    Toast.makeText(this, "No number found for contact.",
	                            Toast.LENGTH_SHORT).show();
	                }
	            }

	            break;
	        }

	    } else {
	        Log.w(DEBUG_TAG, "Warning: activity result not ok");
	    }		
	}
	
	public void saveContact (View view){
		
		EditText phoneNumText = (EditText) findViewById(R.id.editText1);
		String phoneNumber = phoneNumText.getText().toString();
		if (phoneNumber!=null && !phoneNumber.equals(""))
		{
			List<String> phoneNumbers = dbConn.getContacts();
			for (String s : phoneNumbers){
				if(s.equals(phoneNumber))
				{
					found=true;
				}
			}
			if(found)
			{
				Toast.makeText(this, "Emergency Contact already exists",Toast.LENGTH_SHORT).show();
			}
			else
			{
			dbConn.addContacts(phoneNumber);
			Toast.makeText(this, "Number saved as Emergency Contact",Toast.LENGTH_SHORT).show();
			}
		}
		else
		{
			Toast.makeText(this, "Please choose a contact",Toast.LENGTH_SHORT).show();
		}
		
	}
	
	public void viewContacts (View view){
		List<String> phoneNumbers = dbConn.getContacts();
		String nums = new String();
		for (String s : phoneNumbers){
			nums+= s + ",\n";
		}
		//Toast.makeText(this, "Emergency Contact Numbers:"+ nums,Toast.LENGTH_SHORT).show();
		new AlertDialog.Builder(this).setTitle("Emergency Contact Numbers").setMessage(nums).setNeutralButton("OK",new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	           }
	       }).create().show();
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
