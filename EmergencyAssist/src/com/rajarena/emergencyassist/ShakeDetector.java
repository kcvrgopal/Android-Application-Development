package com.rajarena.emergencyassist;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.widget.Toast;


/**
 * Listener that detects shake gesture.
 */
public class ShakeDetector extends Service implements SensorEventListener {
  private static final int MIN_FORCE = 10;
  private static final int MIN_DIRECTION_CHANGE = 3;
  private static final int MAX_PAUSE_BETHWEEN_DIRECTION_CHANGE = 200;
  private static final int MAX_TOTAL_DURATION_OF_SHAKE = 400;
  private long mFirstDirectionChangeTime = 0;
  private long mLastDirectionChangeTime;
  private int mDirectionChangeCount = 0;
  private float lastX = 0,lastY = 0,lastZ = 0;
  private OnShakeListener mShakeListener;
	private SensorManager mSensorManager;
	  private ShakeDetector mSensorListener;

	private int count=0;

  public interface OnShakeListener {
    void onShake();
  }

  public void setOnShakeListener(OnShakeListener listener) {
    mShakeListener = listener;
  }

  @Override
  public void onSensorChanged(SensorEvent se) {
    float x = se.values[0];
    float y = se.values[1];
    float z = se.values[2];
    float totalMovement = Math.abs(x + y + z - lastX - lastY - lastZ);
    if (totalMovement > MIN_FORCE) {
      long now = System.currentTimeMillis();
      if (mFirstDirectionChangeTime == 0) {
        mFirstDirectionChangeTime = now;
        mLastDirectionChangeTime = now;
      }
      long lastChangeWasAgo = now - mLastDirectionChangeTime;
      if (lastChangeWasAgo < MAX_PAUSE_BETHWEEN_DIRECTION_CHANGE) {
        mLastDirectionChangeTime = now;
        mDirectionChangeCount++;
        lastX = x;
        lastY = y;
        lastZ = z;
        if (mDirectionChangeCount >= MIN_DIRECTION_CHANGE) {
          long totalDuration = now - mFirstDirectionChangeTime;
          if (totalDuration < MAX_TOTAL_DURATION_OF_SHAKE) {
        	  count++;
        	  System.out.println(count+"-"+totalDuration+"="+EmergencyAssistApplication.isCameraflag());
        	  if(EmergencyAssistApplication.isCameraflag()&&count>=3)
        	  {
        		  mShakeListener.onShake();
        		  resetShakeParameters();
        	  }
          }
        }

      } else {
        resetShakeParameters();
      }
    }
  }

  public void onStart(Intent intent, int startid)
  {
	  System.out.println("inside service");
	  
	  mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
      mSensorListener = new ShakeDetector();   

      mSensorListener.setOnShakeListener(new ShakeDetector.OnShakeListener() {

        public void onShake() {
        	Intent i=new Intent (getApplicationContext(),CameraActivity.class);
        	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	startActivity(i);
        	EmergencyAssistApplication.setCameraflag(false);
        	System.out.println("Shook");
        }
      });
      mSensorManager.registerListener(mSensorListener,mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_UI);
  }
  /**
   * Resets the shake parameters to their default values.
   */
  private void resetShakeParameters() {
    mFirstDirectionChangeTime = 0;
    mDirectionChangeCount = 0;
    mLastDirectionChangeTime = 0;
    lastX = 0;
    lastY = 0;
    lastZ = 0;
    count=0;
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
  }

@Override
public IBinder onBind(Intent arg0) {
	// TODO Auto-generated method stub
	return null;
}


@Override
public void onDestroy()
{
	super.onDestroy();
	mSensorManager.unregisterListener(mSensorListener);
	Toast.makeText(getBaseContext(), "Shake service switched off", Toast.LENGTH_SHORT).show();
}
}