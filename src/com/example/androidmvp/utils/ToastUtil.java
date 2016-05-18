package com.example.androidmvp.utils;

import com.example.androidmvp.application.AppApplication;

import android.widget.Toast;

public class ToastUtil {

	private static String oldMsg;
	protected static Toast toast = null;
	private static long oneTime = 0;
	private static long twoTime = 0;

	public static void showToast(String msg) {
		if (toast == null) {
			toast = Toast.makeText(AppApplication.getApplication(), msg, Toast.LENGTH_SHORT);
			toast.show();
			oneTime = System.currentTimeMillis();
		} else {
			twoTime = System.currentTimeMillis();
			if (msg.equals(oldMsg)) {
				if (twoTime - oneTime > 2000) {
					toast.show();
				}
			} else {
				oldMsg = msg;
				toast.setText(msg);
				toast.show();
			}
		}
		oneTime = twoTime;
	}

	public static void showToast(int resId) {
		showToast(AppApplication.getApplication().getResources().getString(resId));
	}

}