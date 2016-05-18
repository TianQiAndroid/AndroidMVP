package com.example.androidmvp.model;

import okhttp3.Call;
import okhttp3.Request;
import com.example.androidmvp.server.Server;
import com.example.androidmvp.utils.JsonUtils;
import com.example.androidmvp.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

/**
 * 网络请求基类
 * @author Cao Mingming
 * @date 2016-5-4 11:51:21
 * 
 */
public abstract class BaseRequest{

	/**
	 * 网络请求结果标记
	 */
	public boolean responseResult = false;

	/**
	 * 请求服务器
	 * 
	 * @param <T>
	 * 
	 * @param serviceName
	 * @param params
	 * @return
	 */
	public <T> RequestCall request(String serviceName, T request) {
		RequestCall call = null;
		PostFormBuilder postFormBuilder = OkHttpUtils.post().url(Server.URL);
		postFormBuilder.addParams(Server.SERVICE, serviceName);
		if (request == null) {
			call = postFormBuilder.build();

		} else {
			postFormBuilder.addParams(Server.PARAMS, JsonUtils.toJson(request));
			call = postFormBuilder.build();
		}
		call.execute(new NetCallBack());
		return call;
	}

	/**
	 * 网络请求回调类
	 * 
	 * @author Administrator
	 * 
	 */
	private class NetCallBack extends StringCallback {

		@Override
		public void onBefore(Request request) {
			BaseRequest.this.onBefore();
		}

		@Override
		public void onAfter() {
			BaseRequest.this.onAfter(responseResult);
		}

		@Override
		public void onError(Call arg0, Exception arg1) {
			ToastUtil.showToast("请求网络失败，请检查！");
		}

		@Override
		public void onResponse(String arg0) {
			BaseRequest.this.onResponse(arg0);
		}
	}

	/**
	 * 发送网络请求之前调用的方法
	 */
	public abstract void onBefore();

	/**
	 * 网络请求后，不论请求成功还是失败，都会调用的方法
	 * 
	 * @param responseResult
	 */
	public abstract void onAfter(boolean responseResult);

	/**
	 * 发送网络请求成功的回调方法
	 * 
	 * @param data
	 *            响应的数据
	 */
	public abstract void onResponse(String json);

}
