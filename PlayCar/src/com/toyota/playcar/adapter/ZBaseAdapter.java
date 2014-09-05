package com.toyota.playcar.adapter;

import java.util.List;

import com.toyota.playcar.util.ImageLoader;

import android.content.Context;
import android.widget.BaseAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * 适配器基类
 * @author ganyu
 * @created 2014-8-12
 * 
 */
public abstract class ZBaseAdapter<T> extends BaseAdapter {
	protected Context mContext;
	protected List<T> mDataList;
	protected ImageLoader mImageLoader;
	
	public ZBaseAdapter() {
		
	}
	
	public ZBaseAdapter(Context context) {
		this.mContext = context;
	}
	
	public ZBaseAdapter(Context context, List<T> dataList) {
		this.mContext = context;
		this.mDataList = dataList;
	}
	
	public ZBaseAdapter(Context context, List<T> dataList, ImageLoader imageLoader) {
		this.mContext = context;
		this.mDataList = dataList;
		this.mImageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		return mDataList != null ? mDataList.size() : 0;
	}

	@Override
	public T getItem(int position) {
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);

}
