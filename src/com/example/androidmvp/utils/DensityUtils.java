package com.example.androidmvp.utils;

import com.example.androidmvp.application.AppApplication;

import android.util.TypedValue;

/**
 * 常用单位转换的辅助类
 * 
 * 
 * 
 */
public class DensityUtils {
	private DensityUtils() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * dp转px
	 * 
	 * @param context
	 * @param val
	 * @return
	 */
	public static int dp2px(float dpVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVal, AppApplication.getApplication().getResources()
						.getDisplayMetrics());
	}

	/**
	 * sp转px
	 * 
	 * @param context
	 * @param val
	 * @return
	 */
	public static int sp2px(float spVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				spVal, AppApplication.getApplication().getResources()
						.getDisplayMetrics());
	}

	/**
	 * px转dp
	 * 
	 * @param context
	 * @param pxVal
	 * @return
	 */
	public static float px2dp(float pxVal) {
		final float scale = AppApplication.getApplication().getResources()
				.getDisplayMetrics().density;
		return (pxVal / scale);
	}

	/**
	 * px转sp
	 * 
	 * @param fontScale
	 * @param pxVal
	 * @return
	 */
	public static float px2sp(float pxVal) {
		return (pxVal / AppApplication.getApplication().getResources()
				.getDisplayMetrics().scaledDensity);
	}

}