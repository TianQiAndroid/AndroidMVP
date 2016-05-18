package com.example.androidmvp.presenter;

import com.example.androidmvp.bean.request.UserRequest;
import com.example.androidmvp.model.OnResponseListener;

public interface ILoginPresenter{

	/**
	 * 登录
	 * 
	 * @param userRequest
	 */
	void login(UserRequest userRequest,OnResponseListener l);

	/**
	 * 获取请求参数对象
	 * 
	 * @return
	 */
	UserRequest getLoginRequest();

	/**
	 * 校验用户名密码
	 * 
	 * @param request
	 * @return
	 */
	boolean validateInput(UserRequest request);
}
