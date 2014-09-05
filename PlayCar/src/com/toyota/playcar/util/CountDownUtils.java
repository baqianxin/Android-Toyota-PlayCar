package com.toyota.playcar.util;

import com.toyota.playcar.activity.R;

import android.os.CountDownTimer;
import android.widget.Button;

/**
 * 倒计时工具类
 * 
 * @author ganyu
 * @created 2014-5-7
 * 
 */
public class CountDownUtils extends CountDownTimer {
	private Button mButton;

	public CountDownUtils(long millisInFuture, long countDownInterval, Button button) {
		super(millisInFuture, countDownInterval);
		this.mButton = button;
	}

	@Override
	public void onTick(long millisUntilFinished) {
		mButton.setBackgroundResource(R.drawable.btn_unenable_bg);
		mButton.setEnabled(false);
		mButton.setText("剩余" + (millisUntilFinished / 1000) + "秒");
	}

	@Override
	public void onFinish() {
		mButton.setBackgroundResource(R.drawable.btn_submit_bg_selector);
		mButton.setEnabled(true);
		mButton.setText("重新获取");
	}

}
