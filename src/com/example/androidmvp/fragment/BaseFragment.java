package com.example.androidmvp.fragment;

import com.example.androidmvp.model.BaseModel;
import com.example.androidmvp.presenter.BasePresenter;
import com.example.androidmvp.viedelegate.OnCommonListener;
import com.example.androidmvp.viedelegate.ViewDelegate;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

public abstract class BaseFragment<T extends ViewDelegate, M extends BaseModel, P extends BasePresenter<T, M>>
		extends Fragment implements OnCommonListener {

	public P mPresenter;

	/**
	 * 构造方法
	 */
	public BaseFragment() {
		try {
			// 初始化Presenter
			mPresenter = getPresenterClass().newInstance();
		} catch (Exception e) {
			throw new RuntimeException("create ViewDelegate error!");
		}
	}

	public abstract Class<P> getPresenterClass();

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// 创建根布局
		mPresenter.createRootView();
		// 初始化actionbar
		initActionbar();
		// 创建界面要展示的第一个布局
		createFirstView();
		return mPresenter.getRootView();
	}

	/**
	 * 重写该方法 调用presenter.initActionbar or hideActionbar
	 */
	public abstract void initActionbar();

	/**
	 * 重写该方法 调用presenter.createFirstView
	 */
	public abstract void createFirstView();

	// 请求数据，需要时重写，调用presenter的initData
	public void initData() {

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == getResources().getIdentifier("bt_load_again", "id",
				getActivity().getPackageName())) {
			// 重新加载数据
			createFirstView();
			initData();
		}
	}

	@Override
	public boolean onLongClick(View v) {
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		return false;
	}
	
	/**
	 * viewpager的OnPageChangeListener，需要时重写
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	/**
	 * viewpager的OnPageChangeListener，需要时重写
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	/**
	 * viewpager的OnPageChangeListener，需要时重写
	 */
	@Override
	public void onPageSelected(int arg0) {
		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mPresenter != null) {
			mPresenter.clear();
		}
	}
}
