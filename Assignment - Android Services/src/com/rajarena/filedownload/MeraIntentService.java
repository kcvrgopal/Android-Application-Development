package com.rajarena.filedownload;

import java.net.URL;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

public class MeraIntentService extends IntentService {

	public MeraIntentService() {
		super("MeraIntentService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println(intent);
		
		
	}

}
