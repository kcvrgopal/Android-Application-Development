package com.rajarena.emergencyassist;

import java.util.List;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsMonitor extends BroadcastReceiver
{
		
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		Bundle bundle = intent.getExtras();        
		SmsMessage[] msgs = null;
		DBConnection dbc=new DBConnection(context);
		List<String> val=dbc.getCode();
		if(val.size()!=0)
		{
		String code=val.get(0);
		if (bundle != null)
		{
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];            
			for (int i=0; i<msgs.length; i++){
				msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);               
				if(msgs[i].getMessageBody().toString().equals(code))
				{
					String num=msgs[i].getOriginatingAddress().toString();
					Intent sendintent= new Intent(context, SendService.class);
					sendintent.putExtra("num", num);
					context.startService(sendintent);
				}
			}
		} 
		}
	}
}

