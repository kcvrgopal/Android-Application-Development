package com.rajarena.emergencyassist;

import java.util.List;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PinCreate extends Activity {
	DBConnection dbc;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pincreate);     
        dbc=new DBConnection(this);
    }
	
	public void pincreate(View view)
	{
		EditText pa1=(EditText)findViewById(R.id.p1);
		EditText pa2=(EditText)findViewById(R.id.p2);
		if(pa1.getText().toString().equals("")||pa2.getText().toString().equals(""))
		{
			new AlertDialog.Builder(this).setTitle("PIN Empty").setMessage("PIN cannot be blank").setNeutralButton("Retry",new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {

		           }
		       }).create().show();
		}
		else
		{
			int p1=Integer.parseInt(pa1.getText().toString());
			int p2=Integer.parseInt(pa2.getText().toString());
			if(p1!=p2)
			{
				new AlertDialog.Builder(this).setTitle("PIN mismatch").setMessage("PINS do not match!").setNeutralButton("Retry",new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {

			           }
			       }).create().show();
			}
			else
			{
				dbc.addSecret(p2);
				Toast.makeText(this, "PIN Created", Toast.LENGTH_SHORT).show();
				List<String> val=dbc.getCode();
				if(val.size()==0)
				{
					Intent intent=new Intent(this,Register.class);
		        	startActivity(intent);
				}
				else
				{
		        	Intent intent=new Intent(this,MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		        	startActivity(intent);
				}
			}
		}

	}
	
}

