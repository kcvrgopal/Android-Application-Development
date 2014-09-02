package com.rajarena.emergencyassist;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class WidgetDialogActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_widget_dialog);
		
		String medicalInfo = new String();
		DBConnection dbc=new DBConnection(getApplicationContext());
		
		
		List<String> medInfo = dbc.getMedicalInfo();
		
		if (medInfo.size()>0){
        medicalInfo = "Name: " + medInfo.get(0) + 
        		"\nBlood Group: "+ medInfo.get(1) +
        		"\nAllergic to: " + medInfo.get(2) +
        		"\nCurrenty taking medicines: " + medInfo.get(3);
		}
		else
		{
			medicalInfo = "No medical information saved for this user!";
		}
		
		TextView textView = (TextView)findViewById(R.id.dialogTextView1);
		textView.setText(medicalInfo);
		
		Button okButton = (Button)findViewById(R.id.okButton1);
		okButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				WidgetDialogActivity.this.finish();
				
			}
		});
 	  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.widget_dialog, menu);
		return true;
	}

}
