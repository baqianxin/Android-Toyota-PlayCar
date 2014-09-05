package com.toyota.playcar.fragment;

import com.toyota.playcar.util.CommonConstants;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 基类Fragment
 * 
 * @author ganyu
 * @created 2014-8-24
 * 
 */
public abstract class BaseFragment extends Fragment implements CommonConstants, OnClickListener {
	protected Context mContext;
	
	/**
	 * 初始化界面
	 */
	protected abstract void initView();
	
	/**
	 * 视图点击
	 * @param v
	 */
	protected abstract void onWidgetClick(View v);
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}
	
	@Override
	public void onClick(View v) {
		onWidgetClick(v);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroyView() {
		super.onDestroy();
	}

}
