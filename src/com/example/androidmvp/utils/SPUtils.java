package com.example.androidmvp.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.example.androidmvp.application.AppApplication;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {
	/**
	 * 保存在手机里面的文件名
	 */
	private static final String FILE_NAME = "share_data";
	/**
	 * sp
	 */
	private static SharedPreferences sp = AppApplication.getApplication()
			.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
	/**
	 * editor
	 */
	private static SharedPreferences.Editor editor = sp.edit();

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public static void putString(String key, String value) {

		editor.putString(key, value);
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public static void putInt(String key, int value) {

		editor.putInt(key, value);
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public static void putBoolean(String key, boolean value) {

		editor.putBoolean(key, value);
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public static void putFloat(String key, float value) {

		editor.putFloat(key, value);
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public static void putLong(String key, long value) {

		editor.putLong(key, value);
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(String key, String defaultValue) {
		return sp.getString(key, defaultValue);
	}

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getInt(String key, int defaultValue) {
		return sp.getInt(key, defaultValue);
	}

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(String key, boolean defaultValue) {
		return sp.getBoolean(key, defaultValue);
	}

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static float getString(String key, float defaultValue) {
		return sp.getFloat(key, defaultValue);
	}

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static long getLong(String key, long defaultValue) {
		return sp.getLong(key, defaultValue);
	}

	/**
	 * 移除某个key值已经对应的值
	 * 
	 * @param key
	 */
	public static void remove(String key) {
		editor.remove(key);
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 清除所有数据
	 * 
	 * @param context
	 */
	public static void clear() {
		editor.clear();
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 查询某个key是否已经存在
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean contains(Context context, String key) {
		return sp.contains(key);
	}

	/**
	 * 返回所有的键值对
	 * 
	 * @param context
	 * @return
	 */
	public static Map<String, ?> getAll(Context context) {
		return sp.getAll();
	}

	/**
	 * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
	 * 
	 * @author zhy
	 * 
	 */
	private static class SharedPreferencesCompat {
		private static final Method sApplyMethod = findApplyMethod();

		/**
		 * 反射查找apply的方法
		 * 
		 * @return
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static Method findApplyMethod() {
			try {
				Class clz = SharedPreferences.Editor.class;
				return clz.getMethod("apply");
			} catch (NoSuchMethodException e) {
			}

			return null;
		}

		/**
		 * 如果找到则使用apply执行，否则使用commit
		 * 
		 * @param editor
		 */
		public static void apply(SharedPreferences.Editor editor) {
			try {
				if (sApplyMethod != null) {
					sApplyMethod.invoke(editor);
					return;
				}
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			editor.commit();
		}
	}

}