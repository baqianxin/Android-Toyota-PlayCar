package com.toyota.playcar.activity;

import com.toyota.playcar.view.TitleBar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 精选自驾在线报名注册界面
 * 
 * @author ganyu
 * 
 */
public class SelfDriveSignUpActivity extends BaseActivity {
	private Button btnPaySingUp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void createView() {
		setContentView(R.layout.activity_signup_register);
		// 绑定控件
		TitleBar title_bar = (TitleBar) findViewById(R.id.title_bar);
		btnPaySingUp = (Button) findViewById(R.id.btn_next_step);
		
		title_bar.setTitle(R.string.signup_register, true, this);
		
		btnPaySingUp.setOnClickListener(this);
	}

	@Override
	public void onWidgetClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_bar_left: {
			finish();
		}
			break;
		case R.id.btn_next_step: {// 报名注册下一步
			openActivity(OnlinePayActivity.class);
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
