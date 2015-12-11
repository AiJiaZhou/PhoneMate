package com.phonemate.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * SharedPreferences 工具类
 * 
 * @author rancq
 *
 * @time  2014年3月20日
 *
 */
public class PreferenceUtils {

	private static final String PREFERENCE_NAME = "USER_INFO";

	private static PreferenceUtils preferenceUtil;

	private SharedPreferences sp;

	private Editor ed;

	private PreferenceUtils(Context context) {
		init(context);
	}

	public void init(Context context) {
		if (sp == null || ed == null) {
			try {//MODE_PRIVATE
				sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
				ed = sp.edit();
			} catch (Exception e) {
			}
		}
	}

	public static PreferenceUtils getInstance(Context context) {
		if (preferenceUtil == null) {
			preferenceUtil = new PreferenceUtils(context);
		}
		return preferenceUtil;
	}

	public void saveLong(String key, long l) {
		ed.putLong(key, l);
		ed.commit();
	}

	public long getLong(String key, long defaultlong) {
		return sp.getLong(key, defaultlong);
	}

	public void saveBoolean(String key, boolean value) {
		ed.putBoolean(key, value);
		ed.commit();
	}
	
	public void savefloat(String key, float value) {
		ed.putFloat(key, value);
		ed.commit();
	}
	
	public float getfloat(String key, float defaultValue) {
		return sp.getFloat(key, defaultValue);
	}

	public boolean getBoolean(String key, boolean defaultboolean) {
		return sp.getBoolean(key, defaultboolean);
	}

	public void saveInt(String key, int value) {
		if (ed != null) {
			ed.putInt(key, value);
			ed.commit();
		}
	}

	public int getInt(String key, int defaultInt) {
		return sp.getInt(key, defaultInt);
	}

	public String getString(String key, String defaultString) {
		return sp.getString(key, defaultString);
	}

	/**error*/
	public String getString(Context context, String key, String defaultValue) {
		if (sp == null || ed == null) {
			sp = context.getSharedPreferences(PREFERENCE_NAME, 0);
			ed = sp.edit();
		}
		if (sp != null) {
			return sp.getString(key, defaultValue);
		}
		return defaultValue;
	}

	public void saveString(String key, String value) {
		ed.putString(key, value);
		ed.commit();
	}

	public void remove(String key) {
		ed.remove(key);
		ed.commit();
	}

	public void destroy() {
		sp = null;
		ed = null;
		preferenceUtil = null;
	}
}
