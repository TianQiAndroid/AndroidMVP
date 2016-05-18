package com.example.androidmvp.presenter;

import com.example.androidmvp.viedelegate.ViewDelegate;

/**
 * 数据与view绑定接口
 * @author Cao Mingming
 * @date 2016-5-5 上午11:11:21
 */
public interface IDataBind<T extends ViewDelegate> {

	/**
	 * 数据与view绑定
	 * @param viewDelegate
	 * @param data
	 */
	 void viewBindData(T viewDelegate, String json);
}
