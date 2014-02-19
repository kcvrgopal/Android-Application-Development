package com.rajarena.filedownload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {

	 public Receiver(Handler handler) {
	        super();
	    }

	    protected void onReceiveResult(int resultCode, Bundle resultData) {
	        //super.onReceive(resultCode, resultData);
	   //     if (resultCode == MeraIntentService.UPDATE_PROGRESS) {
	            int progress = resultData.getInt("progress");
	            if(((int) progress)%25==0)
				{
				//	Toast toast=Toast.makeText(MeraIntentService.class, "Downloaded"+(progress), Toast.LENGTH_SHORT);
					//toast.show();
					System.out.println(progress);
				}
	        }
	  //  }

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			
		}
}
