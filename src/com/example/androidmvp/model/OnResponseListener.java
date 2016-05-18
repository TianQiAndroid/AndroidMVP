package com.example.androidmvp.model;

public interface OnResponseListener{

	/**
	 * 请求网络之前
	 */
	void onBefore();
	
	/**
	 * 请求网络之后
	 * @param responseResult
	 */
	void onAfter(boolean responseResult);
	
	/**
	 * 请求网络成功
	 * @param json
	 */
	void onSuccess(String json);
}
