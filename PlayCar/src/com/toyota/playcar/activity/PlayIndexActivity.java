package com.toyota.playcar.activity;

import com.toyota.playcar.view.TitleBar;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

/**
 * 玩主界面
 * 
 * @author ganyu
 * @created 2014-5-5
 * 
 */
public class PlayIndexActivity extends BaseActivity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

	@Override
	public void createView() {
		setContentView(R.layout.activity_play_index);
        // 绑定控件
		TitleBar title_bar = (TitleBar) findViewById(R.id.title_bar);
        Button btn_route_recommend = (Button) findViewById(R.id.btn_route_recommend);
        Button btn_featured_selfdrive = (Button) findViewById(R.id.btn_featured_selfdrive);
        
        title_bar.setTitle(R.string.play, true, R.string.selfdrive_tip, this);
        
        // 注册监听事件
        btn_route_recommend.setOnClickListener(this);
        btn_featured_selfdrive.setOnClickListener(this);
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
			// 跳转到自驾游小常识界面
			openActivity(SelfDriveTipActivity.class);
		}
			break;
		case R.id.btn_route_recommend: {// 线路推荐
			// 跳转到线路推荐界面
			openActivity(RouteRecommendActivity.class);
		}
			break;
		case R.id.btn_featured_selfdrive: {// 精选自驾
			// 跳转到精选自驾界面
			openActivity(SelfDriveMonthActivity.class);
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
