package com.rajarena.emergencyassist;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Debug;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.widget.ImageButton;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class CameraActivity extends Activity {
	
	private Camera camera;
	private SurfaceView surfaceView;
	private ParseFile photoFile;
	private ImageButton photoButton;
	public static final String TAG = "CameraActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		//Debug.startMethodTracing("emergencyAssist");
		// Show the Up button in the action bar.
		setupActionBar();
		
		
		ParseAnalytics.trackAppOpened(getIntent());

		photoButton = (ImageButton)findViewById(R.id.camera_photo_button);

		if (camera == null) {
			try {
				Log.d("TAG","No camera");
				camera = Camera.open();
				photoButton.setEnabled(true);
			} catch (Exception e) {
				Log.e(TAG, "No camera with exception: " + e.getMessage());
				photoButton.setEnabled(false);
				Toast.makeText(this, "No camera detected",
						Toast.LENGTH_LONG).show();
			}
		}

		photoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (camera == null)
					return;
				camera.takePicture(new Camera.ShutterCallback() {

					@Override
					public void onShutter() {
						
					}

				}, null, new Camera.PictureCallback() {

					@Override
					public void onPictureTaken(byte[] data, Camera camera) {
						
						saveScaledPhoto(data);
						
					}

				});

			}
		});

		surfaceView = (SurfaceView)findViewById(R.id.camera_surface_view);
		SurfaceHolder holder = surfaceView.getHolder();
		holder.addCallback(new Callback() {

			public void surfaceCreated(SurfaceHolder holder) {
				try {
					if (camera==null){
						NavUtils.navigateUpFromSameTask(CameraActivity.this);
					}
					else if (camera != null) {
						camera.setDisplayOrientation(90);
						camera.setPreviewDisplay(holder);
						camera.startPreview();
					}
					else {
						NavUtils.navigateUpFromSameTask(CameraActivity.this);
					}
					
				} catch (IOException e) {
					Log.e(TAG, "Error setting up preview", e);
				}
			}

			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				
			}

			public void surfaceDestroyed(SurfaceHolder holder) {
			}

		});

		
	}

	//Save scaled image to Parse
	private void saveScaledPhoto(byte[] data) {

		// Resize photo from camera byte array
		Log.d("TAG","In save scaled photo");
		BitmapFactory.Options options=new BitmapFactory.Options();
		
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        
		Bitmap emerImage = BitmapFactory.decodeByteArray(data, 0, data.length,options);
		Log.d("TAG","After decode byte array");
		
		Bitmap emerImageScaled = Bitmap.createScaledBitmap(emerImage, 200, 200
				* emerImage.getHeight() / emerImage.getWidth(), false);
		Log.d("TAG","After image scaled");
		if(emerImage!=null)
		   {
		     emerImage.recycle();
		     emerImage=null;
		    }

		// Override Android default landscape orientation and save portrait
		Matrix matrix = new Matrix();
		matrix.postRotate(90);
		Bitmap rotatedScaledEmerImage = Bitmap.createBitmap(emerImageScaled, 0,
				0, emerImageScaled.getWidth(), emerImageScaled.getHeight(),
				matrix, true);
		Log.d("TAG","After image rotated");
		if(emerImageScaled!=null)
		   {
			emerImageScaled.recycle();
			emerImageScaled=null;
		    }

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		rotatedScaledEmerImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);

		byte[] scaledData = bos.toByteArray();

		// Save the scaled image to Parse
		photoFile = new ParseFile("emer_photo.jpg", scaledData);
		
		
		if(rotatedScaledEmerImage!=null)
		   {
			rotatedScaledEmerImage.recycle();
			rotatedScaledEmerImage=null;
		    }
		
		final ParseObject testObject = new ParseObject("EmergencyImageClass");
		testObject.put("imagefile", photoFile);
		testObject.saveInBackground();
		
		
		testObject.saveInBackground(new SaveCallback() {

			public void done(ParseException e) {
				if (e != null) {
					Log.d("TAG","Error saving");
					goToParseRest("Error");
					e.printStackTrace();
				} else {
					String objID=testObject.getObjectId();
					Log.d("TAG","Object id is "+objID);
					
					goToParseRest(objID);
					}
					
					
				
			}
		});
	}
	
	
	public void goToParseRest(String objectID)
	{
		Intent sendintent= new Intent(this,ParseRestActivity.class);
		sendintent.putExtra("imageObjectID", objectID);
		startActivity(sendintent);
	}

	

	@Override
	public void onResume() {
		super.onResume();
		if (camera == null) {
			try {
				camera = Camera.open();
				photoButton.setEnabled(true);
			} catch (Exception e) {
				Log.i(TAG, "No camera: " + e.getMessage());
				photoButton.setEnabled(false);
				Log.d("TAG","On resume no camera");
			}
		}
	}

	@Override
	public void onPause() {
		if (camera != null) {
			camera.stopPreview();
			EmergencyAssistApplication.setCameraflag(true);
			camera.release();
		}
		super.onPause();
	}
	
//	@Override
//	public void onDestroy(){
//		if (camera != null) {
//			camera.stopPreview();
//			camera.release();
//		}
//		super.onDestroy();
//	}


	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
