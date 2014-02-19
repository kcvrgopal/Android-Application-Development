package com.rajarena.filedownload;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PdfActivity extends Activity {
	Context context=this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.android_pdf);
	}
	
	public void pdown(View view){
		Intent intent=new Intent(this, MeraIntentService.class);
		String[] url={"http://www.sjsu.edu/registrar/docs/name_change.pdf","http://as.sjsu.edu/steinbeck/documents/BIOGRAPHY_Biography_in_Depth.pdf","http://my.sjsu.edu/docs/hr/recruiting/UG_RS_SJHR_Open_Recruitment_Attachments.pdf","http://www.engr.sjsu.edu/media/pdf/svls/f09/pres_om_nalamasu_092409.pdf","http://www.engr.sjsu.edu/gaojerry/IEEEMobileCloud2013/shuttle%20info.pdf"};
		intent.putExtra("URL1", url);
		startService(intent);
		
	}
}
