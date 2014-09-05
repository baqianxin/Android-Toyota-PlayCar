package com.toyota.playcar.adapter;

import com.toyota.playcar.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RouteMonthAdapter extends BaseAdapter {

	Context context;

	public RouteMonthAdapter(Context context) {

		this.context = context;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
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

		View view = LayoutInflater.from(context).inflate(
				R.layout.vw_route_item, null);
		TextView tvRouteNumb = (TextView) view
				.findViewById(R.id.tv_route_number_item);
		TextView tvRouteName = (TextView) view
				.findViewById(R.id.tv_route_place_name_item);
		tvRouteName.setText("北京--上海"); 
		tvRouteNumb.setText("线路 " + (position + 1));
		return view;
	}
}
