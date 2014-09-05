package com.toyota.playcar.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.toyota.playcar.net.api.UserAPI;
import com.toyota.playcar.util.CommonUtils;
import com.toyota.playcar.util.CountDownUtils;
import com.toyota.playcar.util.ToastUtils;
import com.toyota.playcar.view.LoadingDialog;
import com.toyota.playcar.view.TitleBar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

/**
 * 手机注册界面
 * 
 * @author ganyu
 * @created 2014-5-5
 * 
 */
public class RegisterActivity extends BaseActivity {
	private EditText edit_user_name;
	private RadioButton radio_man;
	private RadioButton radio_woman;
	private EditText edit_mobile;
	private EditText edit_validate_code;
	private Button btn_get_validate_code;
	private EditText edit_password;
	private Button btn_finish;
	/** 用户名 */
	private String mUserName;
	/** 性别  */
	private int mGender = 1;
	/** 手机号 */
	private String mMobile;
	/** 输入验证码 */
	private String mInputValidateCode;
	/** 验证码 */
	private String mValidateCode;
	/** 密码 */
	private String mPassword;
	/** 剩余时间 */
	private int mCountDownTime = 60;
	/** 获取验证码次数 */
	private int mGetValidateCodeCount = 0;
	private UserAPI mUserAPI;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void createView() {
		setContentView(R.layout.activity_register);
		// 绑定控件
		TitleBar title_bar = (TitleBar) findViewById(R.id.title_bar);
		edit_user_name = (EditText) findViewById(R.id.edit_user_name);
		radio_man = (RadioButton) findViewById(R.id.radio_man);
		radio_woman = (RadioButton) findViewById(R.id.radio_woman);
		edit_mobile = (EditText) findViewById(R.id.edit_mobile);
		edit_validate_code = (EditText) findViewById(R.id.edit_validate_code);
		btn_get_validate_code = (Button) findViewById(R.id.btn_get_validate_code);
		edit_password = (EditText) findViewById(R.id.edit_password);
		btn_finish = (Button) findViewById(R.id.btn_finish);
		
		mUserAPI = new UserAPI();
		title_bar.setTitle(R.string.mobile_register, true, this);
		
		// 注册监听事件
		btn_get_validate_code.setOnClickListener(this);
		btn_finish.setOnClickListener(this);
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
		case R.id.btn_get_validate_code: {
			mMobile = edit_mobile.getText().toString().trim();
			// 判断手机号是否为空
			if (mMobile == null || "".equals(mMobile)) {
				edit_mobile.requestFocus();
				ToastUtils.showShort(RegisterActivity.this, R.string.mobile_null_hint);
				break;
			}
			// 判断手机号格式是否正确
			if (!CommonUtils.isMobileNumber(mMobile)) {
				edit_mobile.requestFocus();
				ToastUtils.showShort(RegisterActivity.this, R.string.mobile_format_error_hint);
				break;
			}
			// 获取验证码
			mLoadingDialog = new LoadingDialog(this, R.string.loading, true, true);
			mLoadingDialog.show();
			mUserAPI.requestValidateCode(mMobile, REQ_ID_VALIDATE_CODE, mBaseHandler);
		}
			break;
		case R.id.btn_finish: {
			mUserName = edit_user_name.getText().toString().trim();
			mMobile = edit_mobile.getText().toString().trim();
			mInputValidateCode = edit_validate_code.getText().toString().trim();
			mPassword = edit_password.getText().toString().trim();
			// 判断用户名是否为空
			if (TextUtils.isEmpty(mUserName)) {
				edit_user_name.requestFocus();
				showShortToast(R.string.input_user_name_hint);
				break;
			}
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
			// 判断验证码是否为空
			if (TextUtils.isEmpty(mInputValidateCode)) {
				edit_validate_code.requestFocus();
				showShortToast(R.string.validate_code_null_hint);
				break;
			}
			// 判断验证码是否正确
			if (mValidateCode == null || mValidateCode.equals(mInputValidateCode)) {
				edit_validate_code.requestFocus();
				ToastUtils.showShort(RegisterActivity.this, R.string.validate_code_error_hint);
				break;
			}
			// 判断密码是否为空
			if (TextUtils.isEmpty(mPassword)) {
				edit_password.requestFocus();
				showShortToast(R.string.password_null_hint);
				break;
			}
			// 判断性别
			if (radio_man.isChecked()) {
				mGender = 1;
			} else if (radio_woman.isChecked()) {
				mGender = 2;
			} else {
				mGender = 0;
			}
			// 注册
			mLoadingDialog = new LoadingDialog(this, R.string.loading, true, true);
			mLoadingDialog.show();
			mUserAPI.register(mUserName, mGender, mMobile, mPassword, REQ_ID_REGISTER, mBaseHandler);
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
		case REQ_ID_REGISTER: {
			try {
				JSONObject jsonObject = new JSONObject(response);
				int status = jsonObject.optInt("status", 0);
				if (status == 1) {
					
				} else {
					showShortToast(R.string.request_failed);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				showShortToast(R.string.request_failed);
			}
		}
			break;
		case REQ_ID_VALIDATE_CODE: {
			try {
				JSONObject jsonObject = new JSONObject(response);
				int status = jsonObject.optInt("status", 0);
				if (status == 1) {
					mGetValidateCodeCount++;
					// 判断获取验证码次数
					if (mGetValidateCodeCount == 1) {
						mCountDownTime = 60;
					} else if (mGetValidateCodeCount == 2) {
						mCountDownTime = 120;
					} else if (mGetValidateCodeCount == 3) {
						mCountDownTime = 240;
					} else if (mGetValidateCodeCount == 4) {
						mCountDownTime = 480;
					} else {
						mCountDownTime = 960;
					}
					// 开启倒计时
					new CountDownUtils(mCountDownTime * 1000, 1000, btn_get_validate_code).start();
					
					showShortToast("包含验证码的短信已发送至：" + mMobile);
					mValidateCode = jsonObject.optString("code", "");
				} else {
					showShortToast(R.string.request_failed);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				showShortToast(R.string.request_failed);
			}
		}
			break;
		default:
			break;
		}
	}
	
}
