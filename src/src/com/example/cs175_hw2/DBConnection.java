package com.example.cs175_hw2;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBConnection extends SQLiteOpenHelper {

	public DBConnection(Context context, String name, int version) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try
 	   {
 		  db.execSQL("CREATE TABLE IF NOT EXISTS utils (id INTEGER AUTO_INCREMENT PRIMARY KEY, name TEXT," +
 		  		"high_score INTEGER, slider REAL)"); 
 	   }
 	   catch(SQLException e)
 	   {
 		  Log.e("SqliteAndroid", "DBOpenHelper", e);
 	   }

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS utils");
		this.onCreate(db);
	}

}
