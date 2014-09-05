package com.toyota.playcar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.toyota.playcar.adapter.RouteMonthAdapter;
import com.toyota.playcar.view.TitleBar;
/**
 * 精选自驾路线列表界面
 * 
 * @author ganyu
 *
 */
public class SelfDriveRouteActivity extends BaseActivity {
	private TextView tvMonth;
	private ListView lvRouteList;
	private String month;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void createView() {
		setContentView(R.layout.activity_of_route_list);
		TitleBar title_bar = (TitleBar) findViewById(R.id.title_bar);
		lvRouteList = (ListView) findViewById(R.id.lv_month_route_list);
		tvMonth = (TextView) findViewById(R.id.tv_month);
		
		title_bar.setTitle(R.string.featured_selfdrive, true, this);
		month = getIntent().getStringExtra("month");
		lvRouteList.setAdapter(new RouteMonthAdapter(this));
		
		tvMonth.setText(month + "月");
		lvRouteList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(SelfDriveRouteActivity.this, SelfDriveRouteDetailActivity.class);
				intent.putExtra("route", "" + (position + 1));
				startActivity(intent);
			}
		});
	}

	@Override
	public void onWidgetClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_bar_left: {
			finish();
		}
			break;
		default:
			break;
		}
	}

	@Override
	public void onResponse(int msgType, String response) {
		
	}

}
