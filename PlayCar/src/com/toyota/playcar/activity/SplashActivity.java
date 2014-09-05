package com.toyota.playcar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

/**
 * 启动界面
 * 
 * @author ganyu
 * @created 2014-5-5
 * 
 */
public class SplashActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// 跳转到主界面
				Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(mainIntent);
				finish();
			}
		}, 2000);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 判断是否点击返回键
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
