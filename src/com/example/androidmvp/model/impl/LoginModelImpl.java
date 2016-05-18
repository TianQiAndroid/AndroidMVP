package com.example.androidmvp.model.impl;

import com.example.androidmvp.bean.request.UserRequest;
import com.example.androidmvp.model.BaseModel;
import com.example.androidmvp.model.ILoginModel;
import com.example.androidmvp.model.OnResponseListener;

public class LoginModelImpl extends BaseModel implements
		ILoginModel {

	@Override
	public void login(UserRequest userRequest, OnResponseListener l) {
		request("login", userRequest, l);
	}

}
