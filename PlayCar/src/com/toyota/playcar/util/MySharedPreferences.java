package com.toyota.playcar.util;

import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 自定义SharedPreferences，文件名是包名，
 */
public final class MySharedPreferences {
	private Context context = null;
	private SharedPreferences mSharedPreferences = null;
	private static MySharedPreferences mMySharedPreferences = null;
	private boolean isSaveLog = false;

	private MySharedPreferences(Context context) {
		super();
		this.context = context;
		mSharedPreferences = context.getSharedPreferences(
				context.getPackageName(), 0);
	}

	public static MySharedPreferences getMySharedPreferences(Context context) {
		if (mMySharedPreferences == null) {
			mMySharedPreferences = new MySharedPreferences(context);
		}
		return mMySharedPreferences;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getValue(String name, String defValue) {
		String value = mSharedPreferences.getString(name, defValue);
		return value;
	}

	public String setValue(String name, String value) {
		mSharedPreferences.edit().putString(name, value).commit();
		return value;
	}

	public void deleteValue(String name) {
		mSharedPreferences.edit().remove(name).commit();
	}

	public void deleteAll() {
		Map<String, ?> map = mSharedPreferences.getAll();
		Set<String> set = map.keySet();

		// for (String string : set) {
		// if
		// (string.equals(PushReceiver.SP_KEY_GET_PUSH_INFO_DATE)||string.equals("language"))
		// {
		//
		// }else {
		// mSharedPreferences.edit().putString(string, "").commit();
		// }
		//
		// }

	}

	public String saveLog(String name, String value) {
		if (isSaveLog) {
			mSharedPreferences.edit().putString(name, value).commit();
		}
		return value;
	}
}
