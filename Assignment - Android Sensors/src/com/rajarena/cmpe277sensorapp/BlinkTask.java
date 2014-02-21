package com.rajarena.cmpe277sensorapp;



import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.AsyncTask;

public class BlinkTask extends AsyncTask<String, Integer, Long> {
	boolean x=true;
	@Override
	protected Long doInBackground(String... params) {
		// TODO Auto-generated method stub
		Camera camera=MainActivity.camera;
		Parameters p = camera.getParameters();
		System.out.println("In blink"+MainActivity.b);
		while(MainActivity.b)
		{
			if(x)
			{
				p.setFlashMode(Parameters.FLASH_MODE_TORCH);
				camera.setParameters(p);
				camera.startPreview();
				x=false;
			}
			else
			{
				p.setFlashMode(Parameters.FLASH_MODE_OFF);
				camera.setParameters(p);
				camera.startPreview();
				x=true;
			}

		}

		return null;
	}

}
