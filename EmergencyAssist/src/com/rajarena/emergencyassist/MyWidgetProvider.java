package com.rajarena.emergencyassist;

import java.util.List;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyWidgetProvider extends AppWidgetProvider {
	private static final String ACTION_CLICK = "ACTION_CLICK";
	private String medicalInfo = new String();
	private Boolean isSet = false;
	

	
	  @Override
	  public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {
		  

			
			
	        final int N = appWidgetIds.length;

	        for (int i=0; i<N; i++) {
	            int appWidgetId = appWidgetIds[i];
	           
	            Bundle myOptions = appWidgetManager.getAppWidgetOptions (appWidgetId);
				
				int category = myOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_HOST_CATEGORY, -1);
				boolean isKeyguard = category == AppWidgetProviderInfo.WIDGET_CATEGORY_KEYGUARD;
				int baseLayout = isKeyguard ? R.layout.widget_lock_layout : R.layout.widget_layout;
				
	            DBConnection dbc=new DBConnection(context);
	            
	            //SENDING ALERTS
	            List<String> phoneNumbers = dbc.getContacts();
	        	String[] numarray = phoneNumbers.toArray(new String[phoneNumbers.size()]);
	        	
	            Intent sendintent= new Intent(context, SendService.class);
	            sendintent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
	    		sendintent.putExtra("numarray", numarray);	    		
	    		
	    		PendingIntent pendingIntent = PendingIntent.getService(context, 0, sendintent, 0);
	            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
	            views.setOnClickPendingIntent(R.id.button1, pendingIntent);

	            appWidgetManager.updateAppWidget(appWidgetId, views);	            
	            
	            //MEDICAL INFO
	            Intent medIntent= new Intent(context, WidgetDialogActivity.class);
	            medIntent.setAction(ACTION_CLICK);
	            //PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 0, medIntent, 0);
	            PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 0, medIntent, 0);
	    		RemoteViews views2 = new RemoteViews(context.getPackageName(), baseLayout);	    		
	    		views2.setOnClickPendingIntent(R.id.button2, pendingIntent2);
	            appWidgetManager.updateAppWidget(appWidgetId, views2);            
	           
	        }
	  }
	  
  
	  @Override
	  public void onReceive(Context context, Intent intent) {
		  
		  super.onReceive(context, intent);
		  Log.d("Test","Inside on receive");
		  DBConnection dbc=new DBConnection(context);

	      if (intent.getAction().equals(ACTION_CLICK)) {
	    	//MEDICAL INFO
	            List<String> medInfo = dbc.getMedicalInfo();
	            medicalInfo = "Name: " + medInfo.get(0) + 
	            		"\nBlood Group: "+ medInfo.get(1) +
	            		"\nAllergies: " + medInfo.get(2);
	    		
	    		Log.d("Test","inside onreceive: " + medicalInfo);
	    		Toast.makeText(context, "inside receive", Toast.LENGTH_SHORT).show();
	      }
	  }

}
