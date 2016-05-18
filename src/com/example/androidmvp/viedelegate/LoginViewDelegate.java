package com.example.androidmvp.viedelegate;

import com.example.androidmvp.R;

public class LoginViewDelegate extends ViewDelegate {

	@Override
	public void initEventsListener(OnCommonListener l) {
		setOnClickListener(l, R.id.bt_login);
	}

}
