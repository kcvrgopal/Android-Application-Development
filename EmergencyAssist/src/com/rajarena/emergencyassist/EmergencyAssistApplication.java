package com.rajarena.emergencyassist;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

import android.app.Application;

public class EmergencyAssistApplication extends Application
{
	public static boolean cameraflag=true;

	public static boolean isCameraflag() {
		return cameraflag;
	}

	public static void setCameraflag(boolean cameraflag) {
		EmergencyAssistApplication.cameraflag = cameraflag;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();

		// Parse initialization
		Parse.initialize(this, "wVIpMkCJDajqDpOlaS6RbhLbA3EDKjxZmy8AvMzB", "QaAAypfUafiBXIaFY5T496YJxTlafZIfRCJYBoxH");


		ParseUser.enableAutomaticUser();
		
		
		ParseACL postACL = new ParseACL(ParseUser.getCurrentUser());
		postACL.setPublicReadAccess(true);
	    postACL.setPublicWriteAccess(true);
	}

}