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
		try {/*
			URL[] urls=new URL[]{
					new URL("http://www.sjsu.edu/registrar/docs/name_change.pdf"),
					new URL("http://as.sjsu.edu/steinbeck/documents/BIOGRAPHY_Biography_in_Depth.pdf"),
					new URL("http://my.sjsu.edu/docs/hr/recruiting/UG_RS_SJHR_Open_Recruitment_Attachments.pdf"),
					new URL("http://www.engr.sjsu.edu/media/pdf/svls/f09/pres_om_nalamasu_092409.pdf"),
					new URL("http://www.engr.sjsu.edu/gaojerry/IEEEMobileCloud2013/shuttle%20info.pdf")
			};*/
			intent.putExtra("URL1", new URL("http://blogs.sjsu.edu/today/files/2014/01/mima-mounds-1l9fs40.jpg"));
			intent.putExtra("URL2", new URL("http://blogs.sjsu.edu/today/files/2014/01/Gragera-inpost-11xdqr8.jpg"));
			intent.putExtra("URL3", new URL("http://blogs.sjsu.edu/today/files/2014/01/spider-inpost-285iz3l.jpg"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Hero");
		startService(intent);
	}
}
