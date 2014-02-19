package com.rajarena.filedownload;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TextActivity extends Activity {
	Context context=this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.android_text);
	}
	
	public void tdown(View view){
		Intent intent=new Intent(this, MeraIntentService.class);
		String[] url={"http://www.sjsu.edu/towerfoundation/docs/Employment-Handbook-12.doc","http://www.engr.sjsu.edu/cme/assets/files/aluminfo.doc","http://www.sjsu.edu/publicaffairs/docs/sjsu_fax_template.doc","http://www.engr.sjsu.edu/E10/E10pdf/RobotProjectGuidelinesF13.doc","http://www.sjsu.edu/edd/Letter_of_Rec_form.doc"};
		intent.putExtra("URL3", url);
		startService(intent);
		for(int j=0;j<url.length;j++)
			System.out.println("Hey"+url[j]);
	}
}
