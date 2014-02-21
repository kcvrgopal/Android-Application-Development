package com.rajarena.cmpe277sensorapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Context context=this;
	BlinkTask bt;
	static Camera camera = Camera.open();
	static boolean b=false;
	boolean x=true;
	PackageManager pm;
	Button c;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		pm=context.getPackageManager();
		if(pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH))
		{
			Toast.makeText(context, "Your device works with Flash", Toast.LENGTH_SHORT).show();
			c=(Button) findViewById(R.id.button1);
			c.setClickable(false);


		}
		else
		{
			AlertDialog ad=new AlertDialog.Builder(context).create();
			ad.setTitle("Flash not found");
			ad.setMessage("Sorry, Your device doesn't have Flash light equipped");
			ad.show();
		}
		return true;
	}

	public void startTimer(View view)
	{
		EditText ed=(EditText)findViewById(R.id.value);
		int n=Integer.parseInt(ed.getText().toString());
		if(!b)
		{
			startTorch(n);
		}

	}

	public void stop(View view)
	{
		stopFlash();
	}
	//Method to stop lights
	public void stopFlash()
	{
		bt.cancel(true);
		MainActivity.b=false;
		Parameters p = camera.getParameters();
		p.setFlashMode(Parameters.FLASH_MODE_OFF);
		camera.setParameters(p);
		camera.startPreview();
		c.setClickable(false);
		Toast.makeText(context, "Flash Off", Toast.LENGTH_SHORT).show();
	}
	//Method to start the torch(First normal light for 10s then blinking lights until stopped
	public void startTorch(int n)
	{
		//For first 10 s
		new CountDownTimer(n*1000, 1000) {

			TextView tv=(TextView) findViewById(R.id.textView1);
			public void onTick(long millisUntilFinished) {
				b=true;
				tv.setText("seconds remaining for Flash light: " + millisUntilFinished / 1000);
			}

			public void onFinish() {
				Toast.makeText(context, "Flash Mode on", Toast.LENGTH_SHORT).show();
				Parameters p = camera.getParameters();
				p.setFlashMode(Parameters.FLASH_MODE_TORCH);
				camera.setParameters(p);
				camera.startPreview();

				//logic for blinking lights
				new CountDownTimer(10000, 1000) {

					public void onTick(long millisUntilFinished) {
						tv.setText("seconds remaining for Disco mode: " + millisUntilFinished / 1000);
					}

					public void onFinish() {
						Parameters p = camera.getParameters();
						p.setFlashMode(Parameters.FLASH_MODE_OFF);
						camera.setParameters(p);
						camera.startPreview();
						bt = new BlinkTask();
						Toast.makeText(context, "Disco mode on", Toast.LENGTH_SHORT).show();
						//Executes the AsyncTask
						bt.execute();
						c.setClickable(true);
					}
				}.start();
			}
		}.start();
	}
	public void close(View view)
	{
		finish();
	}
}
