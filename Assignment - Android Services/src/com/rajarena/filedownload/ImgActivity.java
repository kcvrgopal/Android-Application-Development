package com.rajarena.filedownload;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ImgActivity extends Activity {
	Context context=this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_img);
	}
	
	public void idown(View view){
		Intent intent=new Intent(this, MeraIntentService.class);
		String[] url={"http://blogs.sjsu.edu/today/files/2014/01/mima-mounds-1l9fs40.jpg","http://blogs.sjsu.edu/today/files/2014/01/Gragera-inpost-11xdqr8.jpg","http://blogs.sjsu.edu/today/files/2014/01/spider-inpost-285iz3l.jpg"};
		intent.putExtra("URL2", url);
		startService(intent);
	}
}
