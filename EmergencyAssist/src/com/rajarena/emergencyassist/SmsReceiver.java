package com.rajarena.emergencyassist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;
 
public class SmsReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent) 
       {
        Bundle bundle = intent.getExtras();        
        SmsMessage[] msgs = null;
        
        if (bundle != null)
        {
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];            
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);               
                if(msgs[i].getMessageBody().toString().equals("rockerz"))
                {
                	String num=msgs[i].getOriginatingAddress().toString();
                    Toast.makeText(context, "I am monitoring", Toast.LENGTH_SHORT).show();
                    String loc=MainActivity.getLocation();
                    try {
                   	 
                   	 SmsManager sms = SmsManager.getDefault();  // using android SmsManager            
                        sms.sendTextMessage(num, null, "I'm at "+loc+"\n"+"http://maps.google.com/maps?q=loc:"+loc, null, null);  // adding number and text 
                        
                    } catch (Exception e) {
                        Toast.makeText(context, "Sms could not be Send", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        } 
	}
}