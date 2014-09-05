package com.toyota.playcar.activity;

import com.toyota.playcar.adapter.TipsAdapter;
import com.toyota.playcar.view.TitleBar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 自驾游小常识界面
 * 
 * @author ganyu
 * @created 2014-8-25
 * 
 */
public class SelfDriveTipActivity extends BaseActivity {
	private ListView lv_selfdrive_tip_list;
	private ListView lv_selfdrive_tip_list2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void createView() {
		setContentView(R.layout.activity_selfdrive_tip);
		TitleBar title_bar = (TitleBar) findViewById(R.id.title_bar);
		lv_selfdrive_tip_list = (ListView) findViewById(R.id.lv_selfdrive_tip_list);
		lv_selfdrive_tip_list2 = (ListView) findViewById(R.id.lv_selfdrive_tip_list2);

		title_bar.setTitle(R.string.selfdrive_tip, true, this);
		String[] arrays = new String[3];
		for (int i = 0; i < arrays.length; i++) {
			arrays[i] = "自驾游小常识" + (i + 1);
		}
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// R.layout.view_menu_text_list_item, R.id.tv_text_name, arrays);

		ListAdapter adapter = new TipsAdapter(this, arrays);
		lv_selfdrive_tip_list.setAdapter(adapter);
		lv_selfdrive_tip_list2.setAdapter(adapter);
		lv_selfdrive_tip_list.setOnItemClickListener(mOnTipItemClickListener);
		lv_selfdrive_tip_list2.setOnItemClickListener(mOnTipItemClickListener);
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

	/**
	 * 自驾游小常识列表项监听事件
	 */
	private OnItemClickListener mOnTipItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// 跳转到自驾游小常识详情界面
			openActivity(SelfDriveTipDetailsActivity.class);
		}
	};

	@Override
	public void onResponse(int msgType, String response) {

	}

}
