package com.toyota.playcar.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.toyota.playcar.adapter.MonthAdapter;
import com.toyota.playcar.view.TitleBar;

/**
 * 精选自驾月份界面
 * 
 * @author ganyu
 * @created 2014-5-5
 * 
 */

public class SelfDriveMonthActivity extends BaseActivity {
	private ArrayList<String> monthList;
	private GridView gvMonth;
	private ListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void createView() {
		setContentView(R.layout.activity_featured_selfdrive);
		// 绑定控件
		TitleBar title_bar = (TitleBar) findViewById(R.id.title_bar);
		gvMonth = (GridView) findViewById(R.id.gv_month);
		
		title_bar.setTitle(R.string.featured_selfdrive, true, R.string.filter, this);
		monthList = new ArrayList<String>();
		for (int i = 1; i < 13; i++) {
			monthList.add("" + i);
		}
		adapter = new MonthAdapter(this, monthList);

		gvMonth.setAdapter(adapter);
	}

	@Override
	public void onWidgetClick(View v) {
		Animation alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
		v.startAnimation(alphaAnimation);
		switch (v.getId()) {
		case R.id.btn_title_bar_left: {
			finish();
		}
			break;
		case R.id.btn_title_bar_right: {
			
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
