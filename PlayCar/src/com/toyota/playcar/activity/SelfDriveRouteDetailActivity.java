package com.toyota.playcar.activity;

import com.toyota.playcar.view.TitleBar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 精选自驾游路线详情介绍页面
 * 
 * @author ganyu
 * 
 */
public class SelfDriveRouteDetailActivity extends BaseActivity {
	private Button btnPaySingUp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void createView() {
		setContentView(R.layout.activity_route_detail);
		// 绑定控件
		TitleBar title_bar = (TitleBar) findViewById(R.id.title_bar);
		btnPaySingUp = (Button) findViewById(R.id.btn_sing_up);
		
		title_bar.setTitle(R.string.featured_selfdrive, true, this);
		
		btnPaySingUp.setOnClickListener(this);
	}

	@Override
	public void onWidgetClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_bar_left: {
			finish();
		}
			break;
		case R.id.btn_sing_up: {// 在线付款报名
			openActivity(SelfDriveSignUpActivity.class);
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
