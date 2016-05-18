package com.example.androidmvp.model;

import com.example.androidmvp.bean.request.UserRequest;

public interface ILoginModel{
	
	/**
	 * 登录
	 * @param userRequest 请求参数
	 * @param l	响应监听
	 */
	void login(UserRequest userRequest, OnResponseListener l);

}	
