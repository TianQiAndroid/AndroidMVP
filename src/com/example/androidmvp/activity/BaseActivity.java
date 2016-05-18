package com.example.androidmvp.activity;

import com.example.androidmvp.model.BaseModel;
import com.example.androidmvp.presenter.BasePresenter;
import com.example.androidmvp.viedelegate.OnCommonListener;
import com.example.androidmvp.viedelegate.ViewDelegate;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;

/**
 * 
 * @author Cao Mingming
 * @date 2016-5-4 下午5:03:19
 * @param <T>
 *            ViewDelegate
 * @param <M>
 *            Model，界面有请求网络的操作时使用
 * @param <P>
 *            Presenter
 */
public abstract class BaseActivity<T extends ViewDelegate, M extends BaseModel, P extends BasePresenter<T, M>>
		extends FragmentActivity implements OnCommonListener {

	public P mPresenter;

	/**
	 * 构造方法
	 */
	public BaseActivity() {
		try {
			// 初始化Presenter
			mPresenter = getPresenterClass().newInstance();
		} catch (Exception e) {
			throw new RuntimeException("create ViewDelegate error!");
		}
	}

	public abstract Class<P> getPresenterClass();

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
			finish();
			return;
		}
		// 去掉app默认title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 创建根布局
		mPresenter.createRootView();
		// 初始化actionbar
		initActionbar();
		// 创建界面要展示的第一个布局
		createFirstView();
		setContentView(mPresenter.getRootView());
		// 获取上一个activity通过intent传递过来的数据
		getIntentData();
		// 请求数据
		initData();

	}

	/**
	 * 重写该方法 调用presenter.initActionbar or hideActionbar
	 */
	public abstract void initActionbar();

	/**
	 * 重写该方法 调用presenter.createFirstView
	 */
	public abstract void createFirstView();

	/**
	 * 获取上一个activity传入的intent数据，需要时重写
	 */
	public void getIntentData() {

	}

	/**
	 * 界面初始化需要加载数据时重写，调用presenter的initData
	 */
	public void initData() {

	}

	/**
	 * 点击监听，需要时重写
	 */
	@Override
	public void onClick(View v) {
		if (v.getId() == getResources().getIdentifier("bt_load_again", "id",
				getPackageName())) {
			// 重新加载数据
			createFirstView();
			initData();
		}
	}

	/**
	 * 长按点击监听，需要时重写
	 */
	@Override
	public boolean onLongClick(View v) {
		return false;
	}

	/**
	 * 条目点击监听，需要时重写
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	/**
	 * 条目长按点击监听，需要时重写
	 */
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
	protected void onDestroy() {
		super.onDestroy();
		if (mPresenter != null) {
			mPresenter.clear();
			mPresenter = null;
		}
	}

}
