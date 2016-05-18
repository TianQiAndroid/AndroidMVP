package com.example.androidmvp.application;

import android.app.Application;

public class AppApplication extends Application {

	private static AppApplication instance = null;
	
	
	public static AppApplication getApplication(){
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}
}
