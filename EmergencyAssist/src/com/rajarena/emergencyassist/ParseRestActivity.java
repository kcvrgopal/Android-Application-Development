package com.rajarena.emergencyassist;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class ParseRestActivity extends Activity {
	DBConnection dbc;
	public static final String TAG = "ParseRestActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parse_rest);
		dbc=new DBConnection(this);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent=getIntent();
		String objectID = intent.getStringExtra("imageObjectID");
		
		if(!(objectID.equals("Error")))
		{
			Log.d("TAG","Not error message");
			new LongOperation().execute(objectID);
		}

	}
	
	public void onBackPressed() {
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startMain);

		}
	
	private class LongOperation extends AsyncTask<String, Void, String> {

        @SuppressWarnings("deprecation")
		@Override
        protected String doInBackground(String... params) {
        	InputStream in;
        	try{
        	Log.d("TAG","Inside try block");
        	String objectID=params[0];
        	Log.d("TAG","Object ID is "+objectID);
    		URL	url = new URL("https://api.parse.com/1/classes/EmergencyImageClass/"+objectID);
    		
    		HttpsURLConnection  conn = (HttpsURLConnection)url.openConnection();
    		conn.setRequestMethod("GET");
    		conn.setRequestProperty("X-Parse-Application-Id", "wVIpMkCJDajqDpOlaS6RbhLbA3EDKjxZmy8AvMzB");
    		conn.setRequestProperty("X-Parse-REST-API-Key", "jajMDu2cch7ILGWDR22tm0By9g1pIMLfAyqESUqK");
    		
    		String encoding = conn.getHeaderField("Content-Encoding");
    		boolean gzipped = encoding!=null && encoding.toLowerCase().contains("gzip");
    		InputStream responseStream=null;
    		    InputStream inputStream = conn.getInputStream();
    		    if(gzipped){ 
    		    	Log.d("TAG","here");
    		    	responseStream = new BufferedInputStream(new GZIPInputStream(inputStream));
    		    }
    		    else 
    		    	responseStream = new BufferedInputStream(inputStream);
    		
    		//in = conn.getInputStream();
    		Log.d("TAG","Input stream is "+responseStream.toString());
    		
    		BufferedReader d
            = new BufferedReader(new InputStreamReader(responseStream));
    		
    		
    			String r=d.readLine();
    			
    			return r;
    		
        	}
        	catch(Exception e)
        	{
        		Log.d("TAG","inside exception");
        		e.printStackTrace();
        	}
    		
    		
        	return "";
        	
        }

        @Override
        protected void onPostExecute(String response) {
           String imageURL="";
        	Log.d("TAG","Completed execution");
           try
           {
        	   JSONObject jsonResponse=new JSONObject(response);
        	   Log.d("TAG","JSON response is "+jsonResponse);
        	   if(jsonResponse.has("ImageFile")){
        	   imageURL=jsonResponse.getJSONObject("ImageFile").getString("url");
        	   }
        	   else
        	   {
        		  imageURL=jsonResponse.getJSONObject("imagefile").getString("url");
        	   }
        	   Log.d("TAG","Url is "+imageURL);
        	   
        	   sendAlertMessage(imageURL);
        	   
        	   
           }
           catch(Exception e)
           {
        	   Log.d("TAG","Inside onpost exception");
        	   e.printStackTrace();
           }
        }
	}
	
	
	public void sendAlertMessage(String imageURL)
	{
		List<String> phoneNumbers = dbc.getContacts();
       	String[] numarray = phoneNumbers.toArray(new String[phoneNumbers.size()]);
    	
    	Intent sendintent= new Intent(this, SendService.class);
    	sendintent.putExtra("numarray", numarray);
    	sendintent.putExtra("imageURL", imageURL);
    	
    	TextView textView=(TextView) findViewById(R.id.textView1);
    	textView.setText("Sending alert message with your image and location...");
		
    	this.startService(sendintent);
    	EmergencyAssistApplication.setCameraflag(true);
    	Intent intent=new Intent(this,MainActivity.class);
    	startActivity(intent);	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parse_rest, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		//Debug.stopMethodTracing();
	}

}
