package com.rajarena.filedownload;

import android.os.Bundle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	private Context context=this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void pdfClicked(View view){
		Intent intent=new Intent(context, PdfActivity.class);
		startActivity(intent);
	}
	
	public void imgClicked(View view){
		Intent intent=new Intent(context, ImgActivity.class);
		startActivity(intent);
	}
	public void textClicked(View view){
		Intent intent=new Intent(context, TextActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void mclose(View view)
	{
		finish();
	}

}
