package com.example.androidmvp.viedelegate;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

/**
 * 常用监听事件接口
 * 
 * @author Administrator
 * 
 */
public interface OnCommonListener extends OnClickListener,
		OnLongClickListener, OnItemClickListener, OnItemLongClickListener, OnPageChangeListener{

}