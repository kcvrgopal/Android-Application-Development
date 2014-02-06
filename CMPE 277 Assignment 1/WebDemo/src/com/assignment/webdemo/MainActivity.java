package com.assignment.webdemo;

import com.assignment.webdemo.R;


import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	private boolean checked=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void openPage(View view)
	{
		checked=true;
		TextView textview=(TextView)findViewById(R.id.textView1);
		textview.setTextColor(Color.GREEN);
		textview.setText("Click Done");
		Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sjsu.edu"));
		startActivity(intent);
	}
	
	public void checkTheDone(View view)
	{
		if(checked==true)
		{
			TextView textview=(TextView)findViewById(R.id.textView1);
			textview.setTextColor(Color.GREEN);
			textview.setText("Thank you spartan for using the app");
			checked=false;
		}
		else
		{
			TextView txt=(TextView)findViewById(R.id.textView1);
			txt.setTextColor(Color.RED);
			txt.setText("Open SJSU Home Page First");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
