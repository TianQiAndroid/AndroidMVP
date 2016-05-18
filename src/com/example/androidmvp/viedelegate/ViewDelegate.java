package com.example.androidmvp.viedelegate;

import com.example.androidmvp.application.AppApplication;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * view代理基类
 * 
 * @author Administrator
 * 
 */
public abstract class ViewDelegate {

	/**
	 * 根布局
	 */
	private View mRootView;

	/**
	 * 根布局，不包含actionbar部分
	 */
	private FrameLayout mFrameLayoutRoot;

	/**
	 * 正在加载数据展示的view
	 */
	private View loadingView;

	/**
	 * 加载数据失败展示的view
	 */
	private View loadErrorView;

	/**
	 * 加载数据成功要展示的view
	 */

	private View loadSuccessView;
	/**
	 * 加载数据成功但是数据为空要展示的view
	 */
	private View loadEmptyView;

	/**
	 * 保存子view的集合
	 */
	private SparseArray<View> mViewsArray = new SparseArray<View>();

	/**
	 * 获取根布局对象
	 * 
	 * @return
	 */
	public View getRootView() {
		return mRootView;
	}

	/**
	 * 创建根布局
	 */
	public void createRootView() {
		mRootView = View.inflate(
				AppApplication.getApplication(),
				AppApplication
						.getApplication()
						.getResources()
						.getIdentifier("view_root", "layout",
								AppApplication.getApplication().getPackageName()),
				null);
		mFrameLayoutRoot = (FrameLayout) mRootView.findViewById(AppApplication
				.getApplication()
				.getResources()
				.getIdentifier("fl_root", "id",
						AppApplication.getApplication().getPackageName()));
	}

	/**
	 * 创建初始化界面要展示的第一个view
	 * 
	 * @param firstLayoutId
	 * @param l
	 */
	public void createFirstView(int firstLayoutId, OnCommonListener l) {

		if (loadingView == null) {
			loadingView = View.inflate(AppApplication.getApplication(),
					firstLayoutId, null);
		}
		addView(loadingView);
		initEventsListener(l);
	}

	/**
	 * 创建加载数据失败的view
	 * 
	 * @param inflater
	 * @param l
	 */
	public void createLoadErrorView(OnCommonListener l) {
		if (loadErrorView == null) {
			loadErrorView = View
					.inflate(
							AppApplication.getApplication(),
							AppApplication
									.getApplication()
									.getResources()
									.getIdentifier(
											"view_load_error",
											"layout",
											AppApplication.getApplication()
													.getPackageName()), null);
		}
		addView(loadErrorView);
		initLoadErrorViewEventsListener(l);
	}

	/**
	 * 创建加载数据为空的view
	 * 
	 * @param l
	 */
	public void createLoadEmptyView(OnCommonListener l) {
		if (loadEmptyView == null) {
			loadEmptyView = View
					.inflate(
							AppApplication.getApplication(),
							AppApplication
									.getApplication()
									.getResources()
									.getIdentifier(
											"view_load_empty",
											"layout",
											AppApplication.getApplication()
													.getPackageName()), null);
		}
		addView(loadEmptyView);
		initLoadErrorViewEventsListener(l);
	}

	/**
	 * 创建加载成功要展示的view
	 * 
	 * @param inflater
	 * @param loadSuccessLayoutId
	 * @param l
	 */
	public void createSuccessView(int loadSuccessLayoutId,
			OnCommonListener l) {
		if (loadSuccessView == null) {
			loadSuccessView = View.inflate(AppApplication.getApplication(),
					loadSuccessLayoutId, null);
		}
		addView(loadSuccessView);
		initEventsListener(l);
	}

	/**
	 * 展示load success view所需要设置的监听事件
	 * 
	 * @param l
	 */
	public abstract void initEventsListener(OnCommonListener l);

	/**
	 * 展示load error view所需要设置的监听事件
	 * 
	 * @param context
	 * @param l
	 */
	public void initLoadErrorViewEventsListener(OnCommonListener l) {
		setOnClickListener(
				l,
				AppApplication
						.getApplication()
						.getResources()
						.getIdentifier("bt_load_again", "id",
								AppApplication.getApplication().getPackageName()));
	}

	/**
	 * 初始化actionbar
	 * 
	 * @param title
	 * @param imageViewLeftResourceId
	 * @param imageViewRightResourceId
	 * @param l
	 */
	public void initActionbar(String title, int imageViewLeftResourceId,
			int imageViewRightResourceId, OnCommonListener l) {
		// title
		TextView textViewTitle = findViewById(AppApplication
				.getApplication()
				.getResources()
				.getIdentifier("tv_actionbar_title", "id",
						AppApplication.getApplication().getPackageName()));
		// left image
		ImageView imageViewLeft = findViewById(AppApplication
				.getApplication()
				.getResources()
				.getIdentifier("iv_actionbar_left", "id",
						AppApplication.getApplication().getPackageName()));
		// right image
		ImageView imageViewRight = findViewById(AppApplication
				.getApplication()
				.getResources()
				.getIdentifier("iv_actionbar_right", "id",
						AppApplication.getApplication().getPackageName()));

		if (TextUtils.isEmpty(title)) {
			textViewTitle.setVisibility(View.GONE);
		} else {
			textViewTitle.setVisibility(View.VISIBLE);
			textViewTitle.setText(title);
		}

		if (imageViewLeftResourceId == 0) {
			imageViewLeft.setVisibility(View.GONE);
		} else {
			imageViewLeft.setVisibility(View.VISIBLE);
			imageViewLeft.setBackgroundResource(imageViewLeftResourceId);
			imageViewLeft.setOnClickListener(l);
		}

		if (imageViewRightResourceId == 0) {
			imageViewRight.setVisibility(View.GONE);
		} else {
			imageViewRight.setVisibility(View.VISIBLE);
			imageViewRight.setBackgroundResource(imageViewRightResourceId);
			imageViewRight.setOnClickListener(l);
		}
	}

	/**
	 * 隐藏actionbar
	 */
	public void hideActionbar() {
		findViewById(
				AppApplication
						.getApplication()
						.getResources()
						.getIdentifier("rl_actionbar", "id",
								AppApplication.getApplication().getPackageName()))
				.setVisibility(View.GONE);
	}

	/**
	 * 添加子view的方法
	 * 
	 * @param v
	 */
	private void addView(View view) {
		mFrameLayoutRoot.removeAllViews();
		mViewsArray.clear();
		mFrameLayoutRoot.addView(view);
	}

	/**
	 * 获取指定id的view
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T findViewById(int id) {
		T view = (T) mViewsArray.get(id);
		if (view == null) {
			view = (T) mRootView.findViewById(id);
			mViewsArray.put(id, view);
		}
		return view;
	}

	/**
	 * 设置view可见性
	 * 
	 * @param id
	 * @param visibility
	 * @return
	 */
	public <T extends View> void setVisibility(int id, int visibility) {
		T view = findViewById(id);
		view.setVisibility(visibility);

	}

	/**
	 * 设置AdapterView的适配器
	 * 
	 * @param adapterViewId
	 * @param adapter
	 */
	@SuppressWarnings("unchecked")
	public ViewDelegate setAdapterViewAdapter(int adapterViewId,
			BaseAdapter adapter) {
		((AdapterView<BaseAdapter>) findViewById(adapterViewId))
				.setAdapter(adapter);
		return this;
	}

	/**
	 * 设置viewpager适配器
	 * 
	 * @param viewPagerId
	 * @param adapter
	 */
	public ViewDelegate setViewPagerAdapter(int viewPagerId,
			PagerAdapter adapter) {
		((ViewPager) findViewById(viewPagerId)).setAdapter(adapter);
		return this;
	}

	/**
	 * 获取view的文本信息
	 * 
	 * @param id
	 * @return
	 */
	public String getText(int id) {
		return ((TextView) findViewById(id)).getText().toString().trim();
	}

	/**
	 * 设置view的文本信息
	 * 
	 * @param id
	 * @param content
	 * @return
	 */
	public ViewDelegate setText(int id, CharSequence content) {
		((TextView) findViewById(id)).setText(content);
		return this;

	}

	/**
	 * 设置view的文本颜色
	 * 
	 * @param id
	 * @param color
	 * @return
	 */
	public ViewDelegate setTextColor(int id, int color) {
		((TextView) findViewById(id)).setTextColor(color);
		return this;

	}

	/**
	 * 设置view的文字大小
	 * 
	 * @param id
	 * @param size
	 * @return
	 */
	public ViewDelegate setTextSize(int id, float size) {
		((TextView) findViewById(id)).setTextSize(size);
		return this;
	}

	/**
	 * 设置imageview图片
	 * 
	 * @param viewId
	 * @param bm
	 * @return
	 */
	public ViewDelegate setImageBitmap(int viewId, Bitmap bm) {
		((ImageView) findViewById(viewId)).setImageBitmap(bm);
		return this;
	}

	/**
	 * 设置imageview图片
	 * 
	 * @param viewId
	 * @param drawable
	 * @return
	 */
	public ViewDelegate setImageDrawable(int viewId, Drawable drawable) {
		((ImageView) findViewById(viewId)).setImageDrawable(drawable);
		return this;
	}

	/**
	 * 设置imageview图片
	 * 
	 * @param viewId
	 * @param resId
	 * @return
	 */
	public ViewDelegate setImageResource(int viewId, int resId) {
		((ImageView) findViewById(viewId)).setImageResource(resId);
		return this;
	}

	/**
	 * 设置imageview图片
	 * 
	 * @param viewId
	 * @param uri
	 * @return
	 */
	public ViewDelegate setImageURI(int viewId, Uri uri) {
		((ImageView) findViewById(viewId)).setImageURI(uri);
		return this;
	}

	/**
	 * 设置imageview图片
	 * 
	 * @param viewId
	 * @param resId
	 * @return
	 */
	public ViewDelegate setBackgroundResource(int viewId, int resId) {
		((ImageView) findViewById(viewId)).setBackgroundResource(resId);
		return this;
	}
	
	/**
	 * 设置viewpager的OnPageChangeListener
	 * @param l
	 * @param ids
	 * @return
	 */
	public ViewDelegate setOnPageChangeListener(OnCommonListener l, int... ids) {
		if (ids == null) {
			return this;
		}

		for (int id : ids) {
			View view = findViewById(id);
			if (view != null) {
				((ViewPager)view).setOnPageChangeListener(l);
			}
		}
		return this;
	}

	/**
	 * 设置view的点击事件
	 * 
	 * @param l
	 * @param ids
	 * @return
	 */
	public ViewDelegate setOnClickListener(OnCommonListener l, int... ids) {
		if (ids == null) {
			return this;
		}

		for (int id : ids) {
			View view = findViewById(id);
			if (view != null) {
				view.setOnClickListener(l);
			}
		}
		return this;
	}

	/**
	 * 设置view的长按点击事件
	 * 
	 * @param l
	 * @param ids
	 * @return
	 */
	public ViewDelegate setOnOnLongClickListener(OnCommonListener l,
			int... ids) {
		if (ids == null) {
			return this;
		}
		for (int id : ids) {
			View view = findViewById(id);
			if (view != null) {
				view.setOnLongClickListener(l);
			}
		}
		return this;
	}

	/**
	 * 设置AdapterView的条目点击事件
	 * 
	 * @param l
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ViewDelegate setOnItemClickListener(OnCommonListener l,
			int... ids) {
		if (ids == null) {
			return this;
		}
		for (int id : ids) {
			AdapterView adapterView = (AdapterView) findViewById(id);
			if (adapterView != null)
				adapterView.setOnItemClickListener(l);
		}
		return this;
	}

	/**
	 * 设置vie的条目长按点击事件
	 * 
	 * @param l
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ViewDelegate setOnItemLongClickListener(OnCommonListener l,
			int... ids) {
		if (ids == null) {
			return this;
		}
		for (int id : ids) {
			AdapterView adapterView = (AdapterView) findViewById(id);
			if (adapterView != null)
				adapterView.setOnItemLongClickListener(l);
		}
		return this;
	}

}