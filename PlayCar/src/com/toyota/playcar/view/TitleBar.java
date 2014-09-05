package com.toyota.playcar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toyota.playcar.activity.R;
import com.toyota.playcar.util.CommonConstants;
import com.toyota.playcar.util.CommonUtils;

/**
 * 自定义标题栏
 * 
 * @author ganyu
 * @created 2014-8-11
 * 
 */
public class TitleBar extends RelativeLayout implements CommonConstants {
	private Context mContext;
	private Button btn_title_bar_left;
	private TextView tv_title_name;
	private Button btn_title_bar_right;

	public TitleBar(Context context) {
		super(context);
		this.mContext = context;
		init(mContext);
	}

	public TitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		init(mContext);
	}

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init(mContext);
	}
	
	private void init(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.view_title_bar, null);
		int dp_45 = CommonUtils.dip2px(context, 45);
		RelativeLayout.LayoutParams relativeLayoutparams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, dp_45);
		relativeLayoutparams.addRule(RelativeLayout.ALIGN_TOP);
		this.addView(view, relativeLayoutparams);
		btn_title_bar_left = (Button) findViewById(R.id.btn_title_bar_left);
		tv_title_name = (TextView) findViewById(R.id.tv_title_name);
		btn_title_bar_right = (Button) findViewById(R.id.btn_title_bar_right);
		
		btn_title_bar_left.setVisibility(View.GONE);
		btn_title_bar_right.setVisibility(View.GONE);
	}
	
	/**
	 * 设置标题
	 * @param titleResId 标题文字索引
	 * @param isBack
	 * @param handler
	 */
	public void setTitle(int titleResId, boolean isBack, OnClickListener onClickListener) {
		if (titleResId != -1) {
			tv_title_name.setText(titleResId);
		}
		btn_title_bar_left.setVisibility(View.VISIBLE);
		// 判断是否显示返回按钮
		if (isBack) {
			btn_title_bar_left.setBackgroundResource(R.drawable.ic_btn_back_bg);
		} else {
			btn_title_bar_left.setVisibility(View.GONE);
		}
		btn_title_bar_left.setOnClickListener(onClickListener);
	}
	
	/**
	 * 设置标题
	 * @param titleResId 标题文字索引
	 * @param isBack
	 * @param rightResId
	 * @param onClickListener
	 */
	public void setTitle(int titleResId, boolean isBack, int rightResId, OnClickListener onClickListener) {
		if (titleResId != -1) {
			tv_title_name.setText(titleResId);
		}
		btn_title_bar_left.setVisibility(View.VISIBLE);
		// 判断是否显示返回按钮
		if (isBack) {
			btn_title_bar_left.setBackgroundResource(R.drawable.ic_btn_back_bg);
		} else {
			btn_title_bar_left.setVisibility(View.GONE);
		}
		btn_title_bar_right.setVisibility(View.VISIBLE);
		if (rightResId != -1) {
			btn_title_bar_right.setText(rightResId);
		}
		btn_title_bar_left.setOnClickListener(onClickListener);
		btn_title_bar_right.setOnClickListener(onClickListener);
	}

}
