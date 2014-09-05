package com.toyota.playcar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.toyota.playcar.activity.R;

public class TipsAdapter extends BaseAdapter {
	private Context context;
	private String[] strList;

	public TipsAdapter(Context context, String[] strList) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.strList = strList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.strList.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View view = LayoutInflater.from(context).inflate(R.layout.item_tips,
				null);
		TextView tvTip = (TextView) view.findViewById(R.id.item_tv_tips);
	tvTip.setText(strList[position]);
		return view;
	}

}
