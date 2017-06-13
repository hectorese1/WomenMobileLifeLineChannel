package com.ali.mirachannel.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MySharedPreference {
	
	String fileName;
	static SharedPreferences mPreferences;
	
	public static void saveStringInPreference(Context context, String fileName, String key, String value) {
		mPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = mPreferences.edit();
		editor.putString(key, value);

		editor.commit();
		System.out.println("x saveStringInPreference  "+"keys...."+key+"     "+value);
	}
	
	public static void saveBooleanInPreference(Context context, String fileName, String key, Boolean value) {
		mPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = mPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public static String getStringValue(Context context, String fileName, String key){
		
		mPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		String value = mPreferences.getString(key, "0");
		System.out.println("x getStringValue  "+"keys...."+key+"     "+value);
		return value;
		
	}
	
	public static void saveIntInPreference(Context context, String fileName, String key, int value) {
		mPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = mPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static int getIntValue(Context context, String fileName, String key){
		
		mPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		int value = mPreferences.getInt(key, 420);
		return value;
		
	}

	public static void clearPrefernce(Context context, String fileName) {

		mPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = mPreferences.edit();
		editor.clear();
		editor.commit();
	}

}
