package com.ali.mirachannel.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ali.mirachannel.utility.MiraConstants;

public class DatabaseHelperx extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "databaseMira";
	private static final String TABLE_LOG = "table_userlog";
	private static final String KEY_ID = "id";
	private static final String NAME = "name";
	
	
	
	
	
	// Table Names
	private static final String TABLE_USERLOG = "tabuserlog";
	private static final String TABLE_HOUSE = "tabhouse";
	private static final String TABLE_WOMEN = "tabwomen";
	private static final String TABLE_CHILD = "tabchild";
	private static final String TABLE_VACCINE = "tabvaccine";

//	private static final String KEY_ID = "id";
	
	// Colums for TABLE_USERLOG;
	private static final String USER_ACTIVITY = "activity";
	private static final String USER_TIMECREATED = "created";
	
	
	private static final String CREATE_TABLE_LOG = "CREATE TABLE "+TABLE_LOG+" ("+KEY_ID+" INTEGER PRIMARY KEY,"
			+USER_ACTIVITY+" TEXT,"
			+ USER_TIMECREATED + " DATETIME" + ")";
	
//	private static final String CREATE_TABLE_USERLOG = "CREATE TABLE "
//			+ TABLE_USERLOG + " (" + KEY_ID + " INTEGER PRIMARY KEY,"
//			+ USER_ACTIVITY + " TEXT," + USER_TIMECREATED +" DATETIME" + ")";
//	
	public DatabaseHelperx(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public long insertValues(String activity) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(USER_ACTIVITY, activity);
		
		values.put(USER_TIMECREATED, MiraConstants.getDateTime());
		return db.insert(TABLE_LOG, null, values);
	}
	
//	public long insertintoUserLog() {
//		SQLiteDatabase db = this.getWritableDatabase();
//		ContentValues values = new ContentValues();
////		values.put(KEY_ID, Math.abs(new Random().nextInt()%100));
//		values.put(USER_ACTIVITY, "Table Created");
//		values.put(USER_TIMECREATED, MiraConstants.getDateTime());
//		return db.insert(CREATE_TABLE_USERLOG, null, values);
//		
//	}
	

//	private String MiraConstants.getDateTime() {
//		SimpleDateFormat dateFormat = new SimpleDateFormat(
//				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//		Date date = new Date();
//		return dateFormat.format(date);
//	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_LOG);
//		db.execSQL(CREATE_TABLE_USERLOG);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
