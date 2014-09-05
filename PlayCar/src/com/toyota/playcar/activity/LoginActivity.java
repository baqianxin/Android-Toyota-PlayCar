package com.toyota.playcar.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.toyota.playcar.net.api.UserAPI;
import com.toyota.playcar.util.CommonUtils;
import com.toyota.playcar.util.LogUtils;
import com.toyota.playcar.view.LoadingDialog;
import com.toyota.playcar.view.TitleBar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

/**
 * 登录界面
 * 
 * @author ganyu
 * @created 2014-5-11
 * 
 */
public class LoginActivity extends BaseActivity {
	private static final String TAG = "LoginActivity";
	private EditText edit_mobile;
	private EditText edit_password;
	/** 手机号 */
	private String mMobile;
	/** 密码 */
	private String mPassowrd;
	/** 新浪微博认证 */
	private WeiboAuth mSinaWeiboAuth;
	/** 新浪微博SSO授权 */
	private SsoHandler mSinaSsoHandler;
	/** 腾讯QQ对象 */
	private Tencent mTencent;
	private UserAPI mUserAPI;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void createView() {
		setContentView(R.layout.activity_login);
		mSinaWeiboAuth = new WeiboAuth(this, SINA_WEIBO_APP_KEY, SINA_WEIBO_REDIRECT_URL, SINA_WEIBO_SCOPE);
		mSinaSsoHandler = new SsoHandler(this, mSinaWeiboAuth);
		mTencent = Tencent.createInstance(QQ_APP_ID, getApplicationContext());
		
		// 绑定控件
		TitleBar title_bar = (TitleBar) findViewById(R.id.title_bar);
		edit_mobile = (EditText) findViewById(R.id.edit_mobile);
		edit_password = (EditText) findViewById(R.id.edit_password);
		Button btn_login = (Button) findViewById(R.id.btn_login);
		Button btn_register = (Button) findViewById(R.id.btn_register);
		Button btn_weibo_login = (Button) findViewById(R.id.btn_weibo_login);
		Button btn_qq_login = (Button) findViewById(R.id.btn_qq_login);
		
		mUserAPI = new UserAPI();
		title_bar.setTitle(R.string.login, true, this);
		
		// 注册监听事件
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		btn_weibo_login.setOnClickListener(this);
		btn_qq_login.setOnClickListener(this);
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
		case R.id.btn_login: {
			mMobile = edit_mobile.getText().toString().trim();
			mPassowrd = edit_password.getText().toString().trim();
			// 判断手机号是否为空
			if (TextUtils.isEmpty(mMobile)) {
				edit_mobile.requestFocus();
				showShortToast(R.string.mobile_null_hint);
				break;
			}
			// 判断手机号格式是否正确
			if (!CommonUtils.isMobileNumber(mMobile)) {
				edit_mobile.requestFocus();
				showShortToast(R.string.mobile_format_error_hint);
				break;
			}
			// 判断密码是否为空
			if (TextUtils.isEmpty(mPassowrd)) {
				edit_password.requestFocus();
				showShortToast(R.string.password_null_hint);
				break;
			}
			CommonUtils.hideInputMethod(this, edit_password);
			mLoadingDialog = new LoadingDialog(this, R.string.loading, false, true);
			mLoadingDialog.show();
			// 登录
			mUserAPI.login(mMobile, mPassowrd, REQ_ID_LOGIN, mBaseHandler);
		}
			break;
		case R.id.btn_register: {
			// 跳转到注册界面
			openActivity(RegisterActivity.class);
		}
			break;
		case R.id.btn_weibo_login: {// 新浪微博登录
			// 判断用户是否安装新浪微博客户端
			if (mSinaSsoHandler != null) {
				// SSO授权认证
				mSinaSsoHandler.authorize(new SinaWeiboAuthListener());
			} else {
				// 普通认证
				mSinaWeiboAuth.anthorize(new SinaWeiboAuthListener());
			}
		}
			break;
		case R.id.btn_qq_login: {// QQ登录
			if (mTencent.isSessionValid()) {
				mTencent.logout(this);
			}
			mTencent.login(this, QQ_SCOPE, new BaseUiListener());
		}
			break;
		default:
			break;
		}
	}

	@Override
	public void onResponse(int msgType, String response) {
		if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
			mLoadingDialog.dismiss();
		}
		if (TextUtils.isEmpty(response)) {
			showShortToast(R.string.request_failed);
			return;
		}
		switch (msgType) {
		case REQ_ID_LOGIN: {
			try {
				JSONObject jsonObject = new JSONObject(response);
				int status = jsonObject.optInt("status", 0);
				if (status == 1) {
					showShortToast(R.string.login_success);
				} else {
					showShortToast(R.string.request_failed);
				}
			} catch (JSONException e) {
				LogUtils.e(TAG, "JSONException:" + e.getMessage());
				e.printStackTrace();
				showShortToast(R.string.request_failed);
			}
		}
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (mSinaSsoHandler != null) {
			mSinaSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
		if (mTencent != null) {
			mTencent.onActivityResult(requestCode, resultCode, data);
		}
	}
	
	/**
	 * 新浪微博认证
	 */
	private class SinaWeiboAuthListener implements WeiboAuthListener {

		@Override
		public void onCancel() {
			showShortToast(R.string.login_canceled);
		}

		@Override
		public void onComplete(Bundle values) {
			if (values != null) {
				showShortToast("sina:" + values);
			}
		}

		@Override
		public void onWeiboException(WeiboException exception) {
			if (exception != null) {
				showShortToast("WeiboException:" + exception.getMessage());
			}
		}
	}
	
	/**
	 * QQ认证
	 */
	private class BaseUiListener implements IUiListener {
		
		@Override
		public void onError(UiError e) {
			if (e != null) {
				showShortToast("UiError:" + e.errorMessage);
			}
		}

		@Override
		public void onCancel() {
			showShortToast(R.string.login_canceled);
		}

		@Override
		public void onComplete(Object values) {
			if (values != null) {
				showShortToast("qquser:" + values.toString());
			}
		}
	}

}
