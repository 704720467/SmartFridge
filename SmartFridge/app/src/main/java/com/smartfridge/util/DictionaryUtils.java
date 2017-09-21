package com.smartfridge.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.smartfridge.R;
import com.smartfridge.application.MyApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;



public class DictionaryUtils {
	private static DictionaryUtils du;
	public static SharedPreferences sp;
	private static Editor e;

	public static DictionaryUtils getInstance() {
		if (du == null) {
			synchronized (DictionaryUtils.class) {
				if (du == null) {
					du = new DictionaryUtils();
				}
			}
		}
		return du;
	}

	private DictionaryUtils() {
		if (sp == null) {
			sp = MyApplication.getAppContext().getSharedPreferences(MyApplication.getAppContext().getResources().getString(R.string.prefs_name),
					Context.MODE_PRIVATE);
		}
		if (e == null) {
			e = sp.edit();
		}
	}

	public boolean contains(String key) {
		if (sp.contains(key)) {
			return true;
		} else {
			return false;
		}
	}

	public void remove(String key) {
		e.remove(key);
		e.commit();
	}

	public void putBoolean(String key, boolean value) {
		e.putBoolean(key, value);
		e.commit();
	}

	public void putFloat(String key, float value) {
		e.putFloat(key, value);
		e.commit();
	}

	public void putInt(String key, int value) {
		e.putInt(key, value);
		e.commit();
	}

	public boolean putLong(String key, long value) {
		e.putLong(key, value);
		return  e.commit();
	}

	public boolean putString(String key, String value) {
		e.putString(key, value);
		return e.commit();
	}

	public boolean getBoolean(String key) {
		return sp.getBoolean(key, false);
	}

	public boolean getBoolean(String key, boolean defValue) {
		return sp.getBoolean(key, defValue);
	}

	public float getFloat(String key) {
		return sp.getFloat(key, 0.0f);
	}

	public int getInt(String key) {
		return sp.getInt(key, 0);
	}

	public long getLong(String key) {
		return sp.getLong(key, -1);
	}

	public String getString(String key) {
		return sp.getString(key, "");// 默认值为空字符串
	}

	/**根据前缀字符获取数据*/
	public static ArrayList<String> getKeyByChart(String head){
		ArrayList<String> all=new ArrayList<String>();
		HashMap<String, Object> hashMap = (HashMap<String, Object>) sp.getAll();
		for (Map.Entry<String, Object> item : hashMap.entrySet()) {
			// 所有value首字母包含h的都是历史记录，取出并存放
			if (item.getKey().indexOf(head) == 0) {
				all.add(item.getKey());
			}
		}
		// 对数据进行排序
		Collections.sort(all);
		return all;
	}
}