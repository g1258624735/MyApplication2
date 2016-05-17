package com.example.administrator.myapplication.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;

@SuppressWarnings("static-access")
/**
 * @author fengyh
 *	保存数据的工具类--SharedPreferences
 */
public class SPUtils {

	/**
	 * @param context
	 * @param name
	 *            SharedPreferences的name
	 * @param key
	 *            保存的key
	 * @param value
	 *            保存的value
	 */
	public static void putBoolean(Context context, String name, String key,
								  boolean value) {
		
		SharedPreferences preferences = context.getSharedPreferences(name,
				context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static boolean getBoolean(Context context, String name, String key) {
		SharedPreferences preferences = context.getSharedPreferences(name,
				context.MODE_PRIVATE);
		return preferences.getBoolean(key, false);
	}

	/**
	 * @param context
	 * @param name
	 *            SharedPreferences的name
	 * @param key
	 *            保存的key
	 * @param value
	 *            保存的value
	 */
	public static void putString(Context context, String name, String key,
								 String value) {
		SharedPreferences preferences = context.getSharedPreferences(name,
				context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getString(Context context, String name, String key) {
		SharedPreferences preferences;
		preferences = context.getSharedPreferences(name,
				context.MODE_PRIVATE);
		return preferences.getString(key, "");
	}

	/**
	 * @param context
	 * @param name
	 *            SharedPreferences的name
	 * @param key
	 *            保存的key
	 * @param value
	 *            保存的value
	 */
	public static void putFloat(Context context, String name, String key,
								Float value) {
		SharedPreferences preferences = context.getSharedPreferences(name,
				context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putFloat(key, value);
		editor.commit();
	}

	public static Float getFloat(Context context, String name, String key) {
		SharedPreferences preferences = context.getSharedPreferences(name,
				context.MODE_PRIVATE);
		return preferences.getFloat(key, 0f);
	}

	/**
	 * @param context
	 * @param name
	 *            SharedPreferences的name
	 * @param key
	 *            保存的key
	 * @param value
	 *            保存的value
	 */
	public static void putInt(Context context, String name, String key,
							  int value) {
		SharedPreferences preferences = context.getSharedPreferences(name,
				context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static int getInt(Context context, String name, String key) {
		SharedPreferences preferences = context.getSharedPreferences(name,
				context.MODE_PRIVATE);
		return preferences.getInt(key, 0);
	}
	public static int getInt(Context context, String name, String key, int defalut) {
		SharedPreferences preferences = context.getSharedPreferences(name,
				context.MODE_PRIVATE);
		return preferences.getInt(key, defalut);
	}

	/**
	 * @param context
	 * @param name
	 *            SharedPreferences的name
	 * @param key
	 *            保存的key
	 * @param value
	 *            保存的value
	 */
	public static void putLong(Context context, String name, String key,
							   Long value) {
		SharedPreferences preferences = context.getSharedPreferences(name,
				context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public static Long getLong(Context context, String name, String key) {
		SharedPreferences preferences = context.getSharedPreferences(name,
				context.MODE_PRIVATE);
		return preferences.getLong(key, 0);
	}

	/**
	 * @param context
	 * @param name
	 *            SharedPreferences的name
	 * @param key
	 *            保存的key
	 * @param
	 */
	@SuppressLint("NewApi")
	public static void putStringSet(Context context, String name, String key,
									Set<String> values) {
		SharedPreferences preferences = context.getSharedPreferences(name,
				context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putStringSet(key, values);
		editor.commit();
	}

	@SuppressLint("NewApi")
	public static Set<String> getStringSet(Context context, String name,
										   String key) {
		SharedPreferences preferences = context.getSharedPreferences(name,
				context.MODE_PRIVATE);
		return preferences.getStringSet(key, null);
	}

	 
 
 
	public static void putObject(Context context, String filename, Object object) {
		SharedPreferences preferences = context.getSharedPreferences(filename,
				context.MODE_PRIVATE);
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			String base = Base64.encodeToString(baos.toByteArray(), 0);
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString(filename, base);
			editor.commit();
			oos.close();
			baos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void putObjectKey(Context context, String filename, String key, Object object) {
		SharedPreferences preferences = context.getSharedPreferences(filename,
				context.MODE_PRIVATE);
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			String base = Base64.encodeToString(baos.toByteArray(), 0);
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString(key, base);
			editor.commit();
			oos.close();
			baos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void cleanObject(Context context, String filename) {
		SharedPreferences preferences = context.getSharedPreferences(filename,
				context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.clear();
		editor.commit();
	}

	public static Object getObject(Context context, String filename) {
		try {
			SharedPreferences mSharedPreferences = context
					.getSharedPreferences(filename, context.MODE_PRIVATE);
			String base = mSharedPreferences.getString(filename, "");
			if (base != null && base.length() > 0) {
				byte[] base64Bytes = Base64.decode(base.getBytes(), 0);
				ByteArrayInputStream bais = new ByteArrayInputStream(
						base64Bytes);
				ObjectInputStream ois = new ObjectInputStream(bais);
				Object object = ois.readObject();
				ois.close();
				bais.close();
				return object;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Object getObjectKey(Context context, String filename, String key) {
		try {
			SharedPreferences mSharedPreferences = context
					.getSharedPreferences(filename, context.MODE_PRIVATE);
			String base = mSharedPreferences.getString(key, "");
			if (base != null && base.length() > 0) {
				byte[] base64Bytes = Base64.decode(base.getBytes(), 0);
				ByteArrayInputStream bais = new ByteArrayInputStream(
						base64Bytes);
				ObjectInputStream ois = new ObjectInputStream(bais);
				Object object = ois.readObject();
				ois.close();
				bais.close();
				return object;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除
	 * @param context
	 * @param fileName
	 */
	public static void deleteSharedPreferences(Context context, String fileName){
		File file= new File("/data/data/" + context.getPackageName().toString() + "/shared_prefs", fileName + ".xml");
		if(file.exists()){
		file.delete();
		}
	}
}
