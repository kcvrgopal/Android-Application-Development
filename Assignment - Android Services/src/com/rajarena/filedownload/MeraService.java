package com.rajarena.filedownload;

import java.net.MalformedURLException;

import java.net.URL;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MeraService extends Service {
	
	 public class LocalBinder extends Binder {
	       MeraService getService() {
	            return MeraService.this;
	        }
	    }
	
	//private final IBinder mBinder = new MyBinder();
	private final IBinder mBinder = new LocalBinder();

	static String act=null;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
		System.out.println("in");
		boolean p=intent.getBooleanExtra("pdf", false);
		boolean i=intent.getBooleanExtra("img", false);
		boolean t=intent.getBooleanExtra("text", false);

		if(p)
		{
			act=intent.getStringExtra("act_name");
		String[] url={"http://www.sjsu.edu/registrar/docs/name_change.pdf","http://as.sjsu.edu/steinbeck/documents/BIOGRAPHY_Biography_in_Depth.pdf","http://my.sjsu.edu/docs/hr/recruiting/UG_RS_SJHR_Open_Recruitment_Attachments.pdf","http://www.engr.sjsu.edu/media/pdf/svls/f09/pres_om_nalamasu_092409.pdf","http://www.engr.sjsu.edu/gaojerry/IEEEMobileCloud2013/shuttle%20info.pdf"};
		try {DownloadThis dt=new DownloadThis();
			dt.execute(new URL(url[0]),new URL(url[1]),new URL(url[2]),new URL(url[3]),new URL(url[4]));
			dt.setAct(act);
			}
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else if(t)
		{
			act=intent.getStringExtra("act_name");
			String[] url={"http://www.sjsu.edu/towerfoundation/docs/Employment-Handbook-12.doc","http://www.engr.sjsu.edu/cme/assets/files/aluminfo.doc","http://www.sjsu.edu/publicaffairs/docs/sjsu_fax_template.doc","http://www.engr.sjsu.edu/E10/E10pdf/RobotProjectGuidelinesF13.doc","http://www.sjsu.edu/edd/Letter_of_Rec_form.doc"};
			try {
				DownloadThis dt=new DownloadThis();
				dt.execute(new URL(url[0]),new URL(url[1]),new URL(url[2]),new URL(url[3]),new URL(url[4]));
				dt.setAct(act);
			}
			catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(i)
		{
			act=intent.getStringExtra("act_name");
			String[] url={"http://blogs.sjsu.edu/today/files/2014/01/mima-mounds-1l9fs40.jpg","http://blogs.sjsu.edu/today/files/2014/01/Gragera-inpost-11xdqr8.jpg","http://blogs.sjsu.edu/today/files/2014/01/spider-inpost-285iz3l.jpg","http://www.engr.sjsu.edu/files/images/exceed-image-14-120815.thumbnail.jpg","http://www.engr.sjsu.edu/files/images/exceed-group-1-120815.thumbnail.jpg",
							"http://blogs.sjsu.edu/today/files/2014/02/0005_proam-1uawpew.jpg","http://blogs.sjsu.edu/today/files/2013/11/dance_0003_20131101-396-0045.jpg-zq3m7w.jpg","http://blogs.sjsu.edu/today/files/2013/11/dance_0009_20131101-396-0355.jpg-16qimv6.jpg",
							"http://blogs.sjsu.edu/today/files/2013/11/dance_0022_20131101-396-0822.jpg-1qyfyhk.jpg","http://blogs.sjsu.edu/today/files/2013/11/dance_0027_20131101-396-0996.jpg-2klbnkv.jpg"};
			try {
				DownloadThis dt=new DownloadThis();
				dt.execute(new URL(url[0]),new URL(url[1]),new URL(url[2]),
						new URL(url[3]),new URL(url[4]),new URL(url[5]),
						new URL(url[6]),new URL(url[7]),new URL(url[8]),new URL(url[9]));
				dt.setAct(act);
			}
			catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// We don't provide binding, so return null
		return mBinder;
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
	}
/*
	public class MyBinder extends Binder {
		MeraService getService() {
			return MeraService.this;
		}
	}*/


}