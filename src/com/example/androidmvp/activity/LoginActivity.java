package com.example.androidmvp.activity;

import android.view.View;
import com.example.androidmvp.R;
import com.example.androidmvp.model.OnResponseListener;
import com.example.androidmvp.model.impl.LoginModelImpl;
import com.example.androidmvp.presenter.impl.LoginPresenterImpl;
import com.example.androidmvp.viedelegate.LoginViewDelegate;

public class LoginActivity extends
		BaseActivity<LoginViewDelegate, LoginModelImpl, LoginPresenterImpl>
		implements OnResponseListener {

	@Override
	public Class<LoginPresenterImpl> getPresenterClass() {

		return LoginPresenterImpl.class;
	}

	@Override
	public void initActionbar() {
		mPresenter.initActionbar("登录", 0, 0, this);

	}

	@Override
	public void createFirstView() {
		mPresenter.createFirstView(R.layout.activity_login, this);

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.bt_login:
			mPresenter.login(mPresenter.getLoginRequest(), this);
			break;

		default:
			break;
		}
	}

	@Override
	public void onBefore() {

	}

	@Override
	public void onAfter(boolean responseResult) {

	}

	@Override
	public void onSuccess(String json) {

	}

}
