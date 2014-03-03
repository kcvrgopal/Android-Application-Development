package com.rajarena.fitnessapp;

import java.util.ArrayList;


import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnection extends SQLiteOpenHelper {
	private static final String DATABASE_NAME="USER_INFO";

	public DBConnection(Context context) {
		 
		super(context, DATABASE_NAME, null, 1);
		 
		}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL("CREATE TABLE USER_INFO (_id INTEGER PRIMARY KEY AUTOINCREMENT, height INTEGER, weight INTEGER, age INTEGER, bodyfat INTEGER, sex INTEGER)");

	}

	@Override
	 
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	 
	db.execSQL("DROP TABLE IF EXISTS MESSAGE_TABLE");
	 
	onCreate(db);
	 
	}
	//Inserting data
	public void addInfo(int ht, int wt, int age, int bodyfat,int sex)
	 
	{
	 
	ContentValues values=new ContentValues(5);
	 
	values.put("height", ht);
	values.put("weight", wt);
	values.put("age", age);
	values.put("bodyfat", bodyfat);
	values.put("sex",sex);
	 
	getWritableDatabase().insert("USER_INFO", "name", values);	 
	getWritableDatabase().close();
	}
	//Retrieving data
	public List<Integer> getInfo()

	{
		List<Integer> test=new ArrayList<Integer>();
	    String selectQuery = "SELECT  * FROM USER_INFO WHERE _id=(SELECT MAX(_id) FROM USER_INFO)";

	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	test.add(cursor.getInt(cursor.getColumnIndex("height")));
	        	test.add(cursor.getInt(cursor.getColumnIndex("weight")));
	        	test.add(cursor.getInt(cursor.getColumnIndex("age")));
	        	test.add(cursor.getInt(cursor.getColumnIndex("bodyfat")));
	        	test.add(cursor.getInt(cursor.getColumnIndex("sex")));
	        } while (cursor.moveToNext());
	        
	    }
	    cursor.close();
	    db.close();
	     return test;
	}
	 
	

}
