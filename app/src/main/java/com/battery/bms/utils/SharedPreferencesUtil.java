package com.battery.bms.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.File;

public class SharedPreferencesUtil {
	private static final String TAG = "SharedPreferencesUtil";
	public static void setSharedPreferences(Context ctx, String key, String value) {
		if (ctx == null || key == null || value == null)
			return;
		SharedPreferences sp = (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(ctx);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(key, value);
		Log.d(TAG,"key = "+key+",value"+value);
		editor.commit();
	}

	public static String getSharedPreferencesValue(Context ctx, String key, String defValue) {
		if (ctx == null || key == null || defValue == null)
			return "";
		SharedPreferences sp = (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(ctx);
		Log.d(TAG,"key = "+key+",defValue"+defValue);
		return sp.getString(key, defValue);
	}

	public static void setSharedPreferences(Context ctx, String key, boolean value) {
		if (ctx == null || key == null)
			return;
		SharedPreferences sp = (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(ctx);
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static boolean getSharedPreferencesValue(Context ctx, String key, boolean defValue) {
		if (ctx == null || key == null)
			return false;
		SharedPreferences sp = (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(ctx);
		return sp.getBoolean(key, defValue);
	}

    public static void setSharedPreferences(Context ctx, String key, long value) {
        if (ctx == null || key == null)
            return;
        SharedPreferences sp = (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static long getSharedPreferencesValue(Context ctx, String key, long defValue) {
        if (ctx == null || key == null)
            return 0;
        SharedPreferences sp = (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(ctx);
        return sp.getLong(key, defValue);
    }

	public static void setSharedPreferences(Context ctx, String key, int value) {
		if (ctx == null || key == null)
			return;
		SharedPreferences sp = (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(ctx);
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static int getSharedPreferencesValue(Context ctx, String key, int defValue) {
		if (ctx == null || key == null)
			return 0;
		SharedPreferences sp = (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(ctx);
		return sp.getInt(key, defValue);
	}

	public static void clearSharedPreferencesValue(Context ctx){
		if (ctx == null)
			return;
		SharedPreferences sp = (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(ctx);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		editor.commit();
	}
}
