package com.example.androidmvp.presenter;

import android.view.View;
import com.example.androidmvp.model.BaseModel;
import com.example.androidmvp.model.OnResponseListener;
import com.example.androidmvp.viedelegate.OnCommonListener;
import com.example.androidmvp.viedelegate.ViewDelegate;

/**
 * presenter基类
 * 
 * @author Cao Mingming
 * @date 2016-5-4 16:24:27
 * @param <T>
 *            ViewDelegate
 * @param <M>
 *            model，界面上有请求网络的操作时使用
 */
public abstract class BasePresenter<T extends ViewDelegate, M extends BaseModel>
		implements IDataBind<T> {

	protected T mViewDelegate;
	protected M mModel;

	public BasePresenter() {
		try {
			mViewDelegate = getViewDelegateClass().newInstance();
			if (getModelClass() != null) {
				mModel = getModelClass().newInstance();
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public abstract Class<T> getViewDelegateClass();

	public abstract Class<M> getModelClass();

	public View getRootView() {
		return mViewDelegate.getRootView();
	}

	public void createRootView() {
		if (mViewDelegate != null) {
			mViewDelegate.createRootView();
		}
	}

	public void initActionbar(String title, int imageViewLeftResourceId,
			int imageViewRightResourceId, OnCommonListener l) {
		if (mViewDelegate != null) {
			mViewDelegate.initActionbar(title, imageViewLeftResourceId,
					imageViewRightResourceId, l);
		}
	}

	public void hideActionbar() {
		if (mViewDelegate != null) {
			mViewDelegate.hideActionbar();
		}
	}

	public void createFirstView(int layoutId, OnCommonListener l) {
		if (mViewDelegate != null) {
			mViewDelegate.createFirstView(layoutId, l);
		}
	}

	public void createLoadErrorView(OnCommonListener l) {
		if (mViewDelegate != null) {
			mViewDelegate.createLoadErrorView(l);
		}
	}

	public void createLoadEmptyView(OnCommonListener l) {
		if (mViewDelegate != null) {
			mViewDelegate.createLoadEmptyView(l);
		}
	}

	public void createSuccessView(int layoutId, OnCommonListener l) {
		if (mViewDelegate != null) {
			mViewDelegate.createSuccessView(layoutId, l);
		}
	}

	/**
	 * 初始化加载数据的时候使用
	 * 
	 * @param service
	 * @param request
	 * @param l
	 * @param successViewLayoutId
	 */
	public <P> void initData(String service, P request,
			final OnCommonListener l, final int successViewLayoutId) {
		if (mModel != null) {
			mModel.request(service, request, new OnResponseListener() {

				@Override
				public void onBefore() {

				}

				@Override
				public void onAfter(boolean responseResult) {
					if (!responseResult) {
						createLoadErrorView(l);
					}

				}

				@Override
				public void onSuccess(String json) {

					createSuccessView(successViewLayoutId, l);
					// view绑定数据
					if (mViewDelegate != null) {
						viewBindData(mViewDelegate, json);
					}
				}
			});
		}
	}

	public void cancelRequest() {
		if (mModel != null) {
			mModel.cancelRequest();
		}
	}

	/**
	 * 界面初始化需要加载数据时调用，需要时重写
	 */
	public void viewBindData(T viewDelegate, String json) {

	};

	public void clear() {
		if (mViewDelegate != null) {
			mViewDelegate = null;
		}
		if (mModel != null) {
			mModel.cancelRequest();
			mModel = null;
		}
	}

}
