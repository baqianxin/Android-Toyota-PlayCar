package com.toyota.playcar.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
/**
 * 主界面
 * 
 * @author ganyu
 *
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
	@Override
	public void createView() {
		setContentView(R.layout.activity_main);
        // 绑定控件
        ImageView iv_app_name = (ImageView) findViewById(R.id.iv_app_name);
        Button btn_title_bar_right = (Button) findViewById(R.id.btn_title_bar_right);
        Button btn_play = (Button) findViewById(R.id.btn_play);
        Button btn_maintain = (Button) findViewById(R.id.btn_maintain);
        Button btn_use = (Button) findViewById(R.id.btn_use);
        Button btn_exchange = (Button) findViewById(R.id.btn_exchange);
        
        iv_app_name.setVisibility(View.VISIBLE);
        btn_title_bar_right.setVisibility(View.VISIBLE);
        btn_title_bar_right.setText(R.string.login);
        
        // 注册监听事件
        btn_title_bar_right.setOnClickListener(this);
        btn_play.setOnClickListener(this);
        btn_maintain.setOnClickListener(this);
        btn_use.setOnClickListener(this);
        btn_exchange.setOnClickListener(this);
	}

	@Override
	public void onWidgetClick(View v) {
		Animation alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
		v.startAnimation(alphaAnimation);
		switch (v.getId()) {
		case R.id.btn_title_bar_right: {
			// 跳转到登录界面
			openActivity(LoginActivity.class);
		}
			break;
		case R.id.btn_play: {
			// 跳转到玩主界面
			openActivity(PlayIndexActivity.class);
		}
			break;
		case R.id.btn_maintain: {
			
		}
			break;
		case R.id.btn_use: {
			
		}
			break;
		case R.id.btn_exchange: {
			
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
