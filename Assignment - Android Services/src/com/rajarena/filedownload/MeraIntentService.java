package com.rajarena.filedownload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MeraIntentService extends IntentService {

	public MeraIntentService() {
		super("MeraIntentService");
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("SdCardPath")
	@Override
	protected void onHandleIntent(Intent intent) {
		if(intent.getStringArrayExtra("URL1")!=null)
		{
			String[] url=intent.getStringArrayExtra("URL1");
			System.out.println(url.length);
			for(int i=0;i<url.length;i++)
			{
				try {
					File file=new File("/sdcard/Download/file"+(i+1)+".pdf");
					System.out.println("Hey"+url[i]);
					URL url1=new URL(url[i]);
					URLConnection connection = url1.openConnection();
					connection.connect();
					int fileLength = connection.getContentLength();
					InputStream input = new BufferedInputStream(url1.openStream());
					OutputStream output = new FileOutputStream(file);
					byte data[] = new byte[1024];
					long total = 0;
					int count;
					while ((count = input.read(data)) != -1) {
						total += count;
						// publishing the progress....
						Bundle resultData = new Bundle();
						Intent intent1=new Intent(this, Receiver.class);
						//intent1.setAction(Receiver.class);
						resultData.putInt("progress" ,(int) (total * 100 / fileLength));
						//receiver.send(UPDATE_PROGRESS, resultData);
						int x= (int) (total * 100 / fileLength);
						if(x%25==0&&x!=0)
						{
							System.out.println(x);
						intent1.putExtra("progress", x);
						sendBroadcast(intent);
						}	
						output.write(data, 0, count);
					}

					output.flush();
					output.close();
					input.close();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("in "+i);
			}
		}

		
		if(intent.getStringArrayExtra("URL2")!=null)
		{
			String[] url_2=intent.getStringArrayExtra("URL2");
			for(int j=0;j<url_2.length-1;j++)
				System.out.println("Hey"+url_2[j]);
			for(int i=0;i<url_2.length;i++)
			{
				try {
					File file=new File("/sdcard/Pictures/file"+(i+1)+".jpg");
					URL url2=new URL(url_2[i]);
					URLConnection connection = url2.openConnection();
					connection.connect();
					int fileLength = connection.getContentLength();
					InputStream input = new BufferedInputStream(url2.openStream());
					OutputStream output = new FileOutputStream(file);
					byte data[] = new byte[1024];
					long total = 0;
					int count;
					while ((count = input.read(data)) != -1) {
						total += count;
						// publishing the progress....
						Bundle resultData = new Bundle();
						resultData.putInt("progress" ,(int) (total * 100 / fileLength));
						if((int) (total * 100 / fileLength)%25==0&&(int) (total * 100 / fileLength)!=0)
						{
							Toast toast=Toast.makeText(getApplicationContext(), "Downloaded"+(total * 100 / fileLength) , Toast.LENGTH_SHORT);
							toast.show();
							System.out.println((total * 100 / fileLength));
						}
						//receiver.send(UPDATE_PROGRESS, resultData);
						output.write(data, 0, count);
					}

					output.flush();
					output.close();
					input.close();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}

		if(intent.getStringArrayExtra("URL3")!=null)
		{
			String[] url_3=intent.getStringArrayExtra("URL3");
			for(int j=0;j<url_3.length-1;j++)
				System.out.println("Hey"+url_3[j]);
			for(int i=0;i<url_3.length;i++)
			{
				try {
					File file=new File("/sdcard/Download/file"+(i+1)+".doc");
					URL url3=new URL(url_3[i]);
					URLConnection connection = url3.openConnection();
					connection.connect();
					int fileLength = connection.getContentLength();
					InputStream input = new BufferedInputStream(url3.openStream());
					OutputStream output = new FileOutputStream(file);
					byte data[] = new byte[1024];
					long total = 0;
					int count;
					while ((count = input.read(data)) != -1) {
						total += count;
						// publishing the progress....
						Bundle resultData = new Bundle();
						Intent intent1=new Intent("com.rajarena.filedownload");
						resultData.putInt("progress" ,(int) (total * 100 / fileLength));
						int x= (int) (total * 100 / fileLength);
						if(x%25==0)
						{
						intent1.putExtra("progress", x);
						sendBroadcast(intent);
						}						
						//receiver.send(UPDATE_PROGRESS, resultData);
						output.write(data, 0, count);
					}

					output.flush();
					output.close();
					input.close();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
		System.out.println(intent);


	}

}
