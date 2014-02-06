package com.assignment.webViewDemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	private boolean clicked=false;
	final Context content=this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void openPage(View view)
	{
		clicked=true;
		Intent intent=new Intent(content, WebViewActivity.class);
		startActivity(intent);
	}
	
	public void checkDone(View view)
	{
		if(clicked==true)
		{
			TextView textview=(TextView)findViewById(R.id.textView1);
			textview.setText("Thank you spartan for using the app");
			clicked=false;
		}
		else
		{
			TextView textview=(TextView)findViewById(R.id.textView1);
			textview.setText("Open SJSU Page First");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
