package com.toyota.playcar.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.toyota.playcar.activity.R;
import com.toyota.playcar.activity.SelfDriveRouteActivity;

public class MonthAdapter extends BaseAdapter {
	private Context context;

	private ArrayList<String> myList;

	public MonthAdapter(Context context, ArrayList<String> myLis) {
		this.context = context;
		this.myList = myLis;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.myList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.myList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View view = LayoutInflater.from(context).inflate(
				R.layout.vw_mounth_item, null);

		TextView tvMonth = (TextView) view.findViewById(R.id.tv_month_item);

		tvMonth.setText(this.myList.get(position).toString());
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(context, SelfDriveRouteActivity.class);
				
				intent.putExtra("month", ""+myList.get(position).toString());
				v.getContext().startActivity(intent);
			}
		});

		return view;
	}
}
