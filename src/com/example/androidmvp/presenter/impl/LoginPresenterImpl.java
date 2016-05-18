package com.example.androidmvp.presenter.impl;

import android.text.TextUtils;
import com.example.androidmvp.R;
import com.example.androidmvp.bean.request.UserRequest;
import com.example.androidmvp.model.OnResponseListener;
import com.example.androidmvp.model.impl.LoginModelImpl;
import com.example.androidmvp.presenter.BasePresenter;
import com.example.androidmvp.presenter.ILoginPresenter;
import com.example.androidmvp.utils.ToastUtil;
import com.example.androidmvp.viedelegate.LoginViewDelegate;

public class LoginPresenterImpl extends
		BasePresenter<LoginViewDelegate, LoginModelImpl> implements
		ILoginPresenter {

	@Override
	public Class<LoginViewDelegate> getViewDelegateClass() {
		return LoginViewDelegate.class;
	}

	@Override
	public Class<LoginModelImpl> getModelClass() {

		return LoginModelImpl.class;
	}

	@Override
	public UserRequest getLoginRequest() {
		UserRequest request = new UserRequest();
		request.username = mViewDelegate.getText(R.id.et_username);
		request.password = mViewDelegate.getText(R.id.et_password);
		return request;
	}

	@Override
	public boolean validateInput(UserRequest request) {
		boolean retValue = true;
		// 空校验
		if (TextUtils.isEmpty(request.username)
				|| TextUtils.isEmpty(request.password)) {
			retValue = false;
			ToastUtil.showToast("用户名或密码不能为空！");
		}
		return retValue;
	}

	@Override
	public void login(UserRequest userRequest, OnResponseListener l) {

		if (validateInput(userRequest)) {
			mModel.login(userRequest, l);
		}

	}

}
