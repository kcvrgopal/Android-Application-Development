package com.assignment.preftracker;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnection extends SQLiteOpenHelper{
		 
		private static final String DATABASE_NAME="MESSAGE_TABLE";
		 
		public DBConnection(Context context) {
		 
		super(context, DATABASE_NAME, null, 1);
		 
		}
		 
		@Override
		 
		public void onCreate(SQLiteDatabase database) {
		 
		database.execSQL("CREATE TABLE MESSAGE_TABLE (_id INTEGER PRIMARY KEY AUTOINCREMENT, message TEXT, time TEXT)");
		 
		}
		 
		@Override
		 
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 
		db.execSQL("DROP TABLE IF EXISTS MESSAGE_TABLE");
		 
		onCreate(db);
		 
		}
		
		public void addMessage(String msg,String time)
		 
		{
		 
		ContentValues values=new ContentValues(2);
		 
		values.put("message", msg);
		values.put("time", time);
		 
		getWritableDatabase().insert("MESSAGE_TABLE", "name", values);
		 
		}
		
		public List<String> getMessage()

		{
			List<String> test=new ArrayList<String>();
			
		    String selectQuery = "SELECT  * FROM MESSAGE_TABLE";
		    SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
		    // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
		        do {
		        	test.add(cursor.getString(1)+" at "+cursor.getString(2));
		            System.out.println("DATE"+cursor.getString(1));
		        } while (cursor.moveToNext());
		        
		    }
		     return test;
		}
		 
		}