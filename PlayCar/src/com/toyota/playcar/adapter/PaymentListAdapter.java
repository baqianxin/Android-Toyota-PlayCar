package com.toyota.playcar.adapter;

import com.toyota.playcar.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PaymentListAdapter extends BaseAdapter {

	private Context context;
	private String[] payTexts = { "银联在线支付", "支付宝安全支付", "支付宝安全支付" };

	public PaymentListAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
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
		View view = LayoutInflater.from(context).inflate(
				R.layout.item_payment_list, null);
		TextView tvPayment = (TextView) view.findViewById(R.id.tv_payment_text);
		tvPayment.setText(payTexts[position]);
		return view;
	}

}
