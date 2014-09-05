package com.toyota.playcar.activity;

import java.util.List;

import com.toyota.playcar.entity.City;
import com.toyota.playcar.entity.Province;
import com.toyota.playcar.view.TitleBar;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;

/**
 * 线路推荐界面
 * 
 * @author ganyu
 * @created 2014-5-5
 * 
 */
public class RouteRecommendActivity extends BaseActivity {
	private Button btn_province;
	private Button btn_city;
	private EditText edit_destination;
	/** 省份 */
	private String mProvince;
	/** 城市 */
	private String mCity;
	/** 目的地 */
	private String mDestination;
	private PopupWindow mProvinceWindow;
	private PopupWindow mCityWindow;
	private ListView lv_province_list;
	private ListView lv_city_list;
	/** 省份列表 */
	private List<Province> mProvinceList;
	/** 城市列表 */
	private List<City> mCityList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void createView() {
		setContentView(R.layout.activity_route_recommend_search);
		// 绑定控件
		TitleBar title_bar = (TitleBar) findViewById(R.id.title_bar);
		btn_province = (Button) findViewById(R.id.btn_province);
		btn_city = (Button) findViewById(R.id.btn_city);
		Button btn_short_distance = (Button) findViewById(R.id.btn_short_distance);
		Button btn_middle_long_distance = (Button) findViewById(R.id.btn_middle_long_distance);
		edit_destination = (EditText) findViewById(R.id.edit_destination);
		Button btn_search = (Button) findViewById(R.id.btn_search);
		
		title_bar.setTitle(R.string.route_recommend, true, this);
		
		// 初始化省市窗口视图
		initAreaCityWindow();
		
		// 获取查找条件线程
		String[] provinceArray = { "北京市", "河北省", "广东省", "河南省", "湖北省", "河北省", "广东省", "河南省", "湖北省", "湖北省", "河北省", "广东省", "河南省", "湖北省" };
		String[] cityArray = { "北京市", "天津市", "广州市", "郑州市", "武汉市", "天津市", "广州市", "郑州市", "武汉市" };
		ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(this, R.layout.view_area_city_item, R.id.tv_area_city_name, provinceArray);
		lv_province_list.setAdapter(provinceAdapter);
		ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this, R.layout.view_area_city_item, R.id.tv_area_city_name, cityArray);
		lv_city_list.setAdapter(cityAdapter);
		
		// 注册监听事件
		btn_province.setOnClickListener(this);
		btn_city.setOnClickListener(this);
		btn_short_distance.setOnClickListener(this);
		btn_middle_long_distance.setOnClickListener(this);
		btn_search.setOnClickListener(this);
	}
	
	/**
	 * 初始化省市窗口视图
	 */
	private void initAreaCityWindow() {
		View view_province = getLayoutInflater().inflate(R.layout.view_area_city_window, null);
		mProvinceWindow = new PopupWindow(view_province, 240, LayoutParams.WRAP_CONTENT);
		lv_province_list = (ListView) view_province.findViewById(R.id.lv_area_city_list);
		mProvinceWindow.setAnimationStyle(R.style.PopupWindowAnimation);
		
		// 设置背景
		ColorDrawable colorDrawable = new ColorDrawable(-0000);
		mProvinceWindow.setBackgroundDrawable(colorDrawable);
		mProvinceWindow.setOutsideTouchable(true);
		mProvinceWindow.setFocusable(true);
		
		View view_city = getLayoutInflater().inflate(R.layout.view_area_city_window, null);
		mCityWindow = new PopupWindow(view_city, 240, LayoutParams.WRAP_CONTENT);
		lv_city_list = (ListView) view_city.findViewById(R.id.lv_area_city_list);
		mCityWindow.setAnimationStyle(R.style.PopupWindowAnimation);
		
		// 设置背景
		mCityWindow.setBackgroundDrawable(colorDrawable);
		mCityWindow.setOutsideTouchable(true);
		mCityWindow.setFocusable(true);
	}

	@Override
	public void onWidgetClick(View v) {
		Animation alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
		v.startAnimation(alphaAnimation);
		Intent intent;
		switch (v.getId()) {
		case R.id.btn_title_bar_left: {
			finish();
		}
			break;
		case R.id.btn_province: {
			mProvinceWindow.showAsDropDown(v, -10, 0);
		}
			break;
		case R.id.btn_city: {
			mCityWindow.showAsDropDown(v, -10, 0);
		}
			break;
		case R.id.btn_short_distance: {
			// 跳转到景点推荐界面
			intent = new Intent(this, RouteRecommendListActivity.class);
			intent.putExtra("province", mProvince);
			intent.putExtra("city", mCity);
			startActivity(intent);
		}
			break;
		case R.id.btn_middle_long_distance: {
			intent = new Intent(this, RouteRecommendListActivity.class);
			intent.putExtra("province", mProvince);
			intent.putExtra("city", mCity);
			startActivity(intent);
		}
			break;
		case R.id.btn_search: {
			mDestination = edit_destination.getText().toString().trim();
			// 判断目的地是否为空
			if (!TextUtils.isEmpty(mDestination)) {
				// 查找
				
			} else {
				showShortToast(R.string.input_destination_hint);
			}
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
