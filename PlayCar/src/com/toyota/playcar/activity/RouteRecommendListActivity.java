package com.toyota.playcar.activity;

import java.util.ArrayList;
import java.util.List;

import com.toyota.playcar.adapter.ScenicSpotListAdapter;
import com.toyota.playcar.entity.ScenicSpot;
import com.toyota.playcar.view.TitleBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 景点推荐界面
 * 
 * @author ganyu
 * @created 2014-5-5
 * 
 */
public class RouteRecommendListActivity extends BaseActivity {
	private ListView lv_scenic_spot_list;
	/** 景点列表 */
	private List<ScenicSpot> mScenicSpotList;
	private ScenicSpotListAdapter mScenicSpotListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void createView() {
		setContentView(R.layout.activity_route_recommend_list);
		// 绑定控件
		TitleBar title_bar = (TitleBar) findViewById(R.id.title_bar);
		lv_scenic_spot_list = (ListView) findViewById(R.id.lv_scenic_spot_list);
		
		title_bar.setTitle(R.string.route_recommend, true, this);
		mScenicSpotList = new ArrayList<ScenicSpot>();
		ScenicSpot scenicSpot = null;
		for (int i = 0; i < 20; i++) {
			scenicSpot = new ScenicSpot();
			scenicSpot.id = "" + (i + 1);
			scenicSpot.name = "月亮湾";
			if (i % 2 == 1) {
				scenicSpot.imageUrl = "http://img1.qunarzz.com/p/tts1/201404/14/6d11060fe4985307c8d65eac.jpg_r_390x260x90_6f0ac236.jpg";
			} else {
				scenicSpot.imageUrl = "http://img1.qunarzz.com/p/p45/201311/04/b85d548c6150c6b193835fbb.jpg_r_612x345_201db07b.jpg";
			}
			scenicSpot.commentCount = 185;
			scenicSpot.travelNoteCount = 7;
			scenicSpot.scenicType = "必游 自然风光";
			scenicSpot.address = "阿勒泰地区布尔津县";
			
			mScenicSpotList.add(scenicSpot);
		}
		
		mScenicSpotListAdapter = new ScenicSpotListAdapter(RouteRecommendListActivity.this, mScenicSpotList, lv_scenic_spot_list);
		lv_scenic_spot_list.setAdapter(mScenicSpotListAdapter);
		
		// 注册监听事件
		lv_scenic_spot_list.setOnItemClickListener(mOnScenicItemClickListener);
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
	
	private OnItemClickListener mOnScenicItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// 跳转到景点介绍界面
			ScenicSpot scenicSpot = mScenicSpotList.get(position);
			Intent introIntent = new Intent(RouteRecommendListActivity.this, ScenicSpotIntroActivity.class);
			introIntent.putExtra("scenic_spot", scenicSpot);
			startActivity(introIntent);
		}
	};

	@Override
	public void onResponse(int msgType, String response) {
		
	}
	
}
