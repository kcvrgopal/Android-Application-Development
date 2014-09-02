package com.rajarena.emergencyassist;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBConnection extends SQLiteOpenHelper{
		 
		private static final String DATABASE_NAME="USER_INFO";
		public static final String TAG = "DBConnection";
		 
		public DBConnection(Context context) {
		 
		super(context, DATABASE_NAME, null, 1);
		 
		}
		 
		@Override
		 
		public void onCreate(SQLiteDatabase database) {
		 
		database.execSQL("CREATE TABLE USER_TABLE (_id INTEGER PRIMARY KEY AUTOINCREMENT, code TEXT)");
		database.execSQL("CREATE TABLE ACCESS_TABLE (_id INTEGER PRIMARY KEY AUTOINCREMENT, num TEXT, time TEXT)");
		database.execSQL("CREATE TABLE SECRET_TABLE (_id INTEGER PRIMARY KEY AUTOINCREMENT, pin INTEGER)");
		database.execSQL("CREATE TABLE CONTACTS_TABLE (_id INTEGER PRIMARY KEY AUTOINCREMENT, phoneNumber TEXT)");
		database.execSQL("CREATE TABLE MEDICAL_INFO_TABLE (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, bloodtype TEXT, allergic_to TEXT, medicines TEXT)");
		database.execSQL("CREATE TABLE LOCATION_INFO_TABLE (_id INTEGER PRIMARY KEY AUTOINCREMENT, loc TEXT)");
		}
		 
		@Override
		 
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 
		db.execSQL("DROP TABLE IF EXISTS USER_TABLE");
		db.execSQL("DROP TABLE IF EXISTS ACCESS_TABLE");
		db.execSQL("DROP TABLE IF EXISTS SECRET_TABLE");
		db.execSQL("DROP TABLE IF EXISTS CONTACTS_TABLE");
		db.execSQL("DROP TABLE IF EXISTS MEDICAL_INFO_TABLE");
		db.execSQL("DROP TABLE IF EXISTS LOCATION_INFO_TABLE");
		onCreate(db);
		 
		}
		
		public void addLocation(String loca)
		 
		{
		 System.out.println(loca+"jaf");
		ContentValues values=new ContentValues(1);
		 
		values.put("loc", loca);
		getWritableDatabase().insert("LOCATION_INFO_TABLE", "name", values);
		 
		}
		
		public void addCode(String code)
		 
		{
		 
		ContentValues values=new ContentValues(1);
		 
		values.put("code", code);		 
		getWritableDatabase().insert("USER_TABLE", "name", values);
		 
		}
		public void addSecret(int pin)
		{
			ContentValues values=new ContentValues(1);
			 
			values.put("pin", pin);		 
			getWritableDatabase().insert("SECRET_TABLE", "name", values);
		}
		
		public void addAccessTime(String number,String time)
		{
			ContentValues values=new ContentValues(1);
			values.put("num", number);
			values.put("time", time);
			getWritableDatabase().insert("ACCESS_TABLE", "name", values);

		}
		
		public List<String> getCode()

		{
			List<String> test=new ArrayList<String>();
			
			String selectQuery = "SELECT  * FROM USER_TABLE WHERE _id=(SELECT MAX(_id) FROM USER_TABLE)";
			SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
		    // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
		        do {
		        	test.add(cursor.getString(1));
		        } while (cursor.moveToNext());
		        
		    }
		     return test;
		}
		
		public List<Integer> getSecret()
		{
			List<Integer> test=new ArrayList<Integer>();
			
			String selectQuery = "SELECT  * FROM SECRET_TABLE WHERE _id=(SELECT MAX(_id) FROM SECRET_TABLE)";
			SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
		    // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
		        do {
		        	test.add(cursor.getInt(1));
		        } while (cursor.moveToNext());
		        
		    }
		     return test;
		}
		public List<String> getLastAccess()

		{
			List<String> test=new ArrayList<String>();
			
			String selectQuery = "SELECT  * FROM ACCESS_TABLE WHERE _id=(SELECT MAX(_id) FROM ACCESS_TABLE)";
			SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
		    // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
		        do {
		        	test.add("from "+cursor.getString(1)+" on "+cursor.getString(2));
		        } while (cursor.moveToNext());
		        
		    }
		     return test;
		}

		public void addContacts(String phoneNumber)
		{
			ContentValues values=new ContentValues(1);
			 
			values.put("phoneNumber", phoneNumber);		 
			getWritableDatabase().insert("CONTACTS_TABLE", "name", values);
		}
		
		public List<String> getContacts()
		{
			List<String> contacts=new ArrayList<String>();
			
			String selectQuery = "SELECT * FROM CONTACTS_TABLE";
			SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
		    // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
		        do {
		        	contacts.add(cursor.getString(1));
		        } while (cursor.moveToNext());		        
		    }
		     return contacts;
		 }
		
		
		public void addMedicalInfo(String name,String bloodType, String allergies, String medicines)
		{
			ContentValues values=new ContentValues(1);
			values.put("name", name);
			values.put("bloodtype", bloodType);
			values.put("allergic_to", allergies);
			values.put("medicines", medicines);
			getWritableDatabase().insert("MEDICAL_INFO_TABLE", "allergic_to", values);
		}
		
		public List<String> getLocation()
		{
			List<String> locationInfo=new ArrayList<String>();
			
			String selectQuery = "SELECT  * FROM LOCATION_INFO_TABLE WHERE _id=(SELECT MAX(_id) FROM LOCATION_INFO_TABLE)";
			SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
		    // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
		        do {
		        	locationInfo.add(cursor.getString(1));
		        	Log.d("TAG",cursor.getString(1));
		        } while (cursor.moveToNext());
		        
		    }
			
			return locationInfo;
		}
		
		public List<String> getMedicalInfo()
		{
			List<String> medicalInfo=new ArrayList<String>();
			
			String selectQuery = "SELECT  * FROM MEDICAL_INFO_TABLE WHERE _id=(SELECT MAX(_id) FROM MEDICAL_INFO_TABLE)";
			SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
		    // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
		        do {
		        	medicalInfo.add(cursor.getString(1));
		        	medicalInfo.add(cursor.getString(2));
		        	medicalInfo.add(cursor.getString(3));
		        	medicalInfo.add(cursor.getString(4));
		        	Log.d("TAG",cursor.getString(1) + " ,"+cursor.getString(2)+" ,"+cursor.getString(3));
		        } while (cursor.moveToNext());
		        
		    }
			
			return medicalInfo;
		}
}