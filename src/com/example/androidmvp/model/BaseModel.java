package com.example.androidmvp.model;

import com.example.androidmvp.bean.response.BaseResponse;
import com.example.androidmvp.server.Server;
import com.example.androidmvp.utils.JsonUtils;
import com.example.androidmvp.utils.ToastUtil;
import com.zhy.http.okhttp.request.RequestCall;

/**
 * model基类
 * 
 * @author Cao Mingming
 * @date 2016-5-4 下午5:50:49
 */
public abstract class BaseModel {

	private RequestCall mRequestCall;

	/**
	 * 请求服务器方法
	 * 
	 * @param service
	 * @param request
	 * @param l
	 */
	public <T> RequestCall request(String service, T request,
			final OnResponseListener l) {

		mRequestCall = new BaseRequest() {

			@Override
			public void onBefore() {
				if (l != null) {
					l.onBefore();
				}

			}

			@Override
			public void onAfter(boolean responseResult) {
				if (l != null) {
					l.onAfter(responseResult);
				}

			}

			@Override
			public void onResponse(String json) {
				BaseResponse data = JsonUtils
						.fromJsonObject(json, BaseResponse.class);
				if (Server.SUCCESS.equals(data.code)) {
					responseResult = true;
					if (l != null) {
						l.onSuccess(json);
					}
				} else {
					ToastUtil.showToast(data.code + " -> " + data.msg);
				}

			}
		}.request(service, request);
		return mRequestCall;
	}
	
	public void cancelRequest() {
		if (mRequestCall != null) {
			mRequestCall.cancel();
			mRequestCall = null;

		}
	}
}
