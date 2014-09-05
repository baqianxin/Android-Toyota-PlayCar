package com.toyota.playcar.activity;

import com.toyota.playcar.view.TitleBar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * 在线支付界面
 * 
 * @author baqianxin
 * 
 */
public class OnlinePayActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void createView() {
		setContentView(R.layout.activity_online_pay);
		// 绑定控件
		TitleBar title_bar = (TitleBar) findViewById(R.id.title_bar);
		TextView tv_expense_num = (TextView) findViewById(R.id.tv_expense_num);
		CheckBox cb_escape_clause = (CheckBox) findViewById(R.id.cb_escape_clause);
		Button btn_confirm_pay = (Button) findViewById(R.id.btn_confirm_pay);
		
		title_bar.setTitle(R.string.online_pay, true, this);
		tv_expense_num.setText("费用：3227元起");
		cb_escape_clause.setChecked(false);
		
		btn_confirm_pay.setOnClickListener(this);
	}

	@Override
	public void onWidgetClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_bar_left: {
			finish();
		}
			break;
		case R.id.btn_confirm_pay: {// 确认支付
			openActivity(OnlinePayResultActivity.class);
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
