package com.rajarena.filedownload;

import com.rajarena.filedownload.MeraService.MyBinder;

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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.android_pdf);
	}

	public void pdown(View view){
		intent=new Intent(this, MeraService.class);
		intent.putExtra("pdf", true);
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
		intent = new Intent(this,MeraService.class);
		//bindService(intent, mConnection,Context.BIND_AUTO_CREATE);
		bindService(intent,mConnection, Context.BIND_ADJUST_WITH_ACTIVITY);
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
