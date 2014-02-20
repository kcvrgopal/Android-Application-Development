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

import android.os.AsyncTask;
import android.os.Environment;

public class DownloadThis extends AsyncTask<URL, Integer, Long> {

	@Override
	protected Long doInBackground(URL... urls) {
		// TODO Auto-generated method stub
		URL[] url = urls;
		String name;
		for(int i=0;i<url.length;i++)
		{
			try {
				name=url[i].toString().substring((url[i].toString().lastIndexOf('/'))+1);
				File file=new File(Environment.getExternalStorageDirectory()+"/Download/"+name);
				URLConnection connection = url[i].openConnection();
				connection.connect();
				int fileLength = connection.getContentLength();
				InputStream input = new BufferedInputStream(url[i].openStream());
				OutputStream output = new FileOutputStream(file);
				byte data[] = new byte[1024];
				long total = 0;
				int count;
				while ((count = input.read(data)) != -1) {
					total += count;
					//Bundle resultData = new Bundle();
					//resultData.putInt("progress" ,(int) (total * 100 / fileLength));
					int x= (int) (total * 100 / fileLength);
					if(x==100)
					{
						System.out.println(name+" Downloaded ");
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
		}
		return null;
	}
	



}
