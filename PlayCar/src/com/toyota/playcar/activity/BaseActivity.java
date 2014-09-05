package com.toyota.playcar.activity;

import com.toyota.playcar.handler.WeakHandler;
import com.toyota.playcar.util.AppManager;
import com.toyota.playcar.util.CommonConstants;
import com.toyota.playcar.util.CommonUtils;
import com.toyota.playcar.util.LogUtils;
import com.toyota.playcar.util.ToastUtils;
import com.toyota.playcar.view.LoadingDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 基类activity
 * 
 * @author ganyu
 * 
 */
public abstract class BaseActivity extends Activity implements CommonConstants, OnClickListener {
	private static final String TAG = "BaseActivity";
	private static final int ACTIVITY_RESUME = 0;
	private static final int ACTIVITY_STOP = 1;
	private static final int ACTIVITY_PAUSE = 2;
	private static final int ACTIVITY_DESTROY = 3;
	public int mActivityState;
	protected View view_nav_common;
	protected Button btn_my;
	protected Button btn_more;
	private WindowManager mWindowManager;
	/** 我的菜单窗口 */
	protected PopupWindow mMyMenuWindow;
	/** 更多菜单窗口 */
	protected PopupWindow mMoreMenuWindow;
	protected LoadingDialog mLoadingDialog;

	/**
	 * 初始化界面
	 */
	public abstract void createView();

	/**
	 * 组件点击事件处理
	 * 
	 * @param v
	 */
	public abstract void onWidgetClick(View v);

	/**
	 * 响应网络请求
	 * 
	 * @param response
	 */
	public abstract void onResponse(int msgType, String response);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 竖屏锁定
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// 判断是否允许全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 添加Activity到栈
		AppManager.getAppManager().addActivity(this);
		int dp_135 = CommonUtils.dip2px(this, 135);
		int dp_170 = CommonUtils.dip2px(this, 170);
		int dp_210 = CommonUtils.dip2px(this, 210);
		String[] myMenuArray = getResources().getStringArray(
				R.array.my_menu_name_array);
		String[] moreMenuArray = getResources().getStringArray(
				R.array.nav_menu_name_array);
		mMyMenuWindow = getOperateMenuWindow(this, myMenuArray, dp_135, dp_170,
				mOnMyMenuItemClickListener);
		mMoreMenuWindow = getOperateMenuWindow(this, moreMenuArray, dp_135,
				dp_210, mOnMoreMenuItemClickListener);

		addMenuView();

		createView();
	}

	@Override
	public void onClick(View v) {
		onWidgetClick(v);
	}

	/**
	 * 创建悬浮菜单视图
	 */
	private void createMenuView() {
		view_nav_common = View.inflate(this, R.layout.view_nav_common, null);
		btn_my = (Button) view_nav_common.findViewById(R.id.btn_my);
		btn_more = (Button) view_nav_common.findViewById(R.id.btn_more);
		// 获取WindowManager
		mWindowManager = (WindowManager) getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		// 设置LayoutParams(全局变量）相关参数
		WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
		wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
		wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
		wmParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
		wmParams.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST;
		int height = CommonUtils.dip2px(this, 5);
		// 以屏幕左上角为原点，设置x、y初始值
		wmParams.x = 0;
		wmParams.y = 0 + height;
		// 设置悬浮窗口长宽数据
		wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		wmParams.format = PixelFormat.RGBA_8888;
		mWindowManager.addView(view_nav_common, wmParams);
		btn_my.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mMyMenuWindow != null) {
					int[] location = new int[2];
					v.getLocationOnScreen(location);
					int x = location[0] - mMyMenuWindow.getWidth() - v.getWidth()
							+ CommonUtils.dip2px(BaseActivity.this, 10);
					int y = location[1]
							- CommonUtils.dip2px(BaseActivity.this, 30);
					LogUtils.d(TAG, "x:" + x + ",y:" + y);
					mMyMenuWindow.showAtLocation(v, Gravity.NO_GRAVITY, x, y);
				}
			}
		});
		btn_more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mMoreMenuWindow != null) {
					int[] location = new int[2];
					v.getLocationOnScreen(location);
					int x = location[0];
					int y = location[1] - mMoreMenuWindow.getHeight();
					mMoreMenuWindow.showAtLocation(v, Gravity.NO_GRAVITY, x, y);
				}
			}
		});
	}

	/**
	 * 我的菜单视图列表项点击事件
	 */
	private OnItemClickListener mOnMyMenuItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			mMyMenuWindow.dismiss();
			switch (position) {
			case 0: {
				
			}
				break;
			case 1: {
				
			}
				break;
			case 2: {
				
			}
				break;
			case 3: {
				
			}
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 更多菜单视图列表项点击事件
	 */
	private OnItemClickListener mOnMoreMenuItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			mMoreMenuWindow.dismiss();
			switch (position) {
			case 0: {
				
			}
				break;
			case 1: {
				
			}
				break;
			case 2: {
				
			}
				break;
			case 3: {
				
			}
				break;
			case 4: {
				
			}
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 消息处理
	 */
	protected WeakHandler mBaseHandler = new WeakHandler(this) {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case REQ_ID_LOGIN: {// 登录
				onResponse(REQ_ID_LOGIN, String.valueOf(msg.obj));
			}
				break;
			case REQ_ID_REGISTER: {// 注册
				onResponse(REQ_ID_REGISTER, String.valueOf(msg.obj));
			}
				break;
			case REQ_ID_VALIDATE_CODE: {// 验证码
				onResponse(REQ_ID_VALIDATE_CODE, String.valueOf(msg.obj));
			}
				break;
			default:
				break;
			}
		}
	};

	/**
	 * Toast提示
	 * 
	 * @param messageId
	 */
	protected void showShortToast(int messageId) {
		ToastUtils.showShort(this, messageId);
	}

	/**
	 * Toast提示
	 * 
	 * @param message
	 */
	protected void showShortToast(String message) {
		ToastUtils.showShort(this, message);
	}

	/**
	 * Toast提示
	 * 
	 * @param messageId
	 */
	protected void showLongToast(int messageId) {
		ToastUtils.showLong(this, messageId);
	}

	/**
	 * Toast提示
	 * 
	 * @param message
	 */
	protected void showLongToast(String message) {
		ToastUtils.showLong(this, message);
	}

	/**
	 * 判断是否包含key
	 * 
	 * @param extraKey
	 * @return
	 */
	protected boolean hasExtra(String extraKey) {
		if (getIntent() != null) {
			return getIntent().hasExtra(extraKey);
		}
		return false;
	}

	/**
	 * 跳转Activity
	 * 
	 * @param cls
	 */
	protected void openActivity(Class<?> cls) {
		openActivity(cls, null);
	}

	/**
	 * 跳转Activity
	 * 
	 * @param cls
	 * @param bundle
	 */
	protected void openActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	/**
	 * 跳转Activity
	 * 
	 * @param action
	 */
	protected void openActivity(String action) {
		openActivity(action, null);
	}

	/**
	 * 跳转Activity
	 * 
	 * @param action
	 * @param bundle
	 */
	protected void openActivity(String action, Bundle bundle) {
		Intent intent = new Intent(action);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	/**
	 * 隐藏键盘
	 * 
	 * @param view
	 */
	protected void hideKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mActivityState = ACTIVITY_RESUME;
	}

	@Override
	protected void onPause() {
		super.onPause();
		mActivityState = ACTIVITY_PAUSE;
	}

	@Override
	protected void onStop() {
		super.onResume();
		mActivityState = ACTIVITY_STOP;
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mActivityState = ACTIVITY_DESTROY;
		removeMenuView();
	}

	/**
	 * 添加菜单视图
	 */
	protected void addMenuView() {
		if (view_nav_common == null || !view_nav_common.isShown()) {
			createMenuView();
		}
	}

	/**
	 * 删除菜单视图
	 */
	protected void removeMenuView() {
		if (view_nav_common != null && view_nav_common.isShown()) {
			mWindowManager.removeView(view_nav_common);
		}
	}

	/**
	 * 获取操作菜单窗口
	 * 
	 * @param context
	 * @param textArray
	 * @param windowWidth
	 *            窗口宽度
	 * @param windowHeight
	 *            窗口高度
	 * @param onItemClickListener
	 * @return
	 */
	public static PopupWindow getOperateMenuWindow(Context context,
			String[] textArray, int windowWidth, int windowHeight,
			OnItemClickListener onItemClickListener) {
		View view_operate_menu_window = LayoutInflater.from(context).inflate(
				R.layout.view_operate_menu_window, null);
		PopupWindow operateWindow = new PopupWindow(view_operate_menu_window,
				windowWidth, windowHeight);
		ListView lv_operate_menu_list = (ListView) view_operate_menu_window
				.findViewById(R.id.lv_operate_menu_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				R.layout.view_menu_text_list_item, R.id.tv_text_name, textArray);
		lv_operate_menu_list.setAdapter(adapter);

		ColorDrawable colorDrawable = new ColorDrawable(-0000);
		operateWindow.setBackgroundDrawable(colorDrawable);
		operateWindow.setOutsideTouchable(true);
		operateWindow.setFocusable(true);

		lv_operate_menu_list.setOnItemClickListener(onItemClickListener);
		return operateWindow;
	}

}
