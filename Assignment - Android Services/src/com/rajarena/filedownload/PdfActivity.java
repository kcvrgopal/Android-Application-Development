package com.rajarena.filedownload;

//import com.rajarena.filedownload.MeraService.MyBinder;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

public class PdfActivity extends Activity {
	Context context=this;
	MeraService ms;
	boolean b=false;
	Intent intent;
	private BroadcastReceiver receiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				int x=intent.getIntExtra("progress", 0);
				System.out.println("XYZ"+x);
			}
		}
	}; 
	/*
	//For Bound service
	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className,
				IBinder service) {
			// We've bound to LocalService, cast the IBinder and get LocalService instance
			MyBinder binder = (MyBinder) service;
			ms = binder.getService();
			b = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			b = false;
		}
	};	
	*/
	
//For Local service
	private ServiceConnection mConnection = new ServiceConnection() {
		private MeraService mBoundService;
	    public void onServiceConnected(ComponentName className, IBinder service) {
	        
	        mBoundService = ((MeraService.LocalBinder)service).getService();

	    }

	    public void onServiceDisconnected(ComponentName className) {
	       
	        mBoundService = null;
	      
	    }
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.android_pdf);
	}

	public void pdown(View view){
		intent=new Intent(this, MeraService.class);
		intent.putExtra("pdf", true);
		intent.putExtra("act_name", this.getClass().getSimpleName());
		bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
		//bindService(intent,mConnection, Context.BIND_ADJUST_WITH_ACTIVITY);
		System.out.println("Out");
		startService(intent);

	}

	public void onResume()
	{
		super.onResume();
		registerReceiver(receiver, new IntentFilter("com.rajarena.filedownload"));
	}

	public void onPause()
	{
		super.onResume();
		unregisterReceiver(receiver);	
	}

	@Override
	protected void onStart()
	{
		super.onStart();
	}
	@Override
	protected void onStop()
	{
		super.onStop();
		if(b)
		{
			unbindService(mConnection);
			b=false;
		}
	}


}
