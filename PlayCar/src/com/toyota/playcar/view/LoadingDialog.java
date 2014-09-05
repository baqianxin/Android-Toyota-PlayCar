package com.toyota.playcar.view;

import com.toyota.playcar.activity.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 自定义加载对话框
 * 
 * @author ganyu
 * @created 2013-12-13
 *
 */
public class LoadingDialog extends ProgressDialog {
	/** 文本 */
	private String mText;
	/** 文本资源ID */
	private int mTextId = -1;
	/** 屏幕是否变暗 */
	private boolean mIsWindowDim = false;

	public LoadingDialog(Context context) {
		super(context);
	}
	
	/**
	 * 加载对话框
	 * @param context
	 * @param text
	 * @param cancelable 返回取消
	 * @param isWindowDim 屏幕是否变暗
	 */
	public LoadingDialog(Context context, String text, boolean cancelable, boolean isWindowDim) {
		super(context);
		this.mText = text;
		this.setCancelable(cancelable);
		this.setCanceledOnTouchOutside(false);
		this.mIsWindowDim = isWindowDim;
	}
	
	/**
	 * 加载对话框
	 * @param context
	 * @param textId
	 * @param cancelable 返回取消
	 * @param isWindowDim 屏幕是否变暗
	 */
	public LoadingDialog(Context context, int textId, boolean cancelable, boolean isWindowDim) {
		super(context);
		this.mTextId = textId;
		this.setCancelable(cancelable);
		this.setCanceledOnTouchOutside(false);
		this.mIsWindowDim = isWindowDim;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_dialog_progress);
		ImageView iv_in_loading = (ImageView) findViewById(R.id.iv_in_loading);
		TextView tv_message = (TextView) findViewById(R.id.tv_dialog_message);
		final AnimationDrawable animationDrawable = (AnimationDrawable) iv_in_loading.getBackground();
		iv_in_loading.post(new Runnable() {
			
			@Override
			public void run() {
				animationDrawable.start();
			}
		});
		
		// 判断文本是否为空
		if (mText != null) {
			tv_message.setText(mText);
		} else {
			if (mTextId != -1) {
				tv_message.setText(mTextId);
			}
		}
		
		if (!mIsWindowDim) {
			// 设置屏幕背景不变暗
			Window window = this.getWindow();
			WindowManager.LayoutParams params = window.getAttributes();
			params.dimAmount = 0.0f;
			window.setAttributes(params);
		}
	}
	
}
