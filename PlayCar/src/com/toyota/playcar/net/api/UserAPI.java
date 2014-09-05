package com.toyota.playcar.net.api;

import java.util.Map;

import com.toyota.playcar.net.RequestThread;

import android.os.Handler;

/**
 * 用户接口
 * 
 * @author ganyu
 * @created 2014-8-27
 * 
 */
public class UserAPI extends AbsCommonAPI {
	
	public UserAPI() {
		
	}
	
	/**
	 * 用户登录
	 * @param mobile 手机号
	 * @param password 密码
	 * @param messageType 消息类型
	 * @param handler 消息处理对象
	 */
	public void login(String mobile, String password, int messageType, Handler handler) {
		Map<String, String> requestParams = getCommonParams();
		requestParams.put("mobile", mobile);
		requestParams.put("password", password);
		new RequestThread(LOGIN_URL, requestParams, HTTP_POST, messageType, handler).start();
	}
	
	/**
	 * 用户注册
	 * @param userName 用户名
	 * @param gender 性别
	 * @param mobile 手机号
	 * @param password 密码
	 * @param messageType 消息类型
	 * @param handler 消息处理对象
	 */
	public void register(String userName, int gender, String mobile, String password, int messageType, Handler handler) {
		Map<String, String> requestParams = getCommonParams();
		requestParams.put("name", userName);
		requestParams.put("gender", String.valueOf(gender));
		requestParams.put("mobile", mobile);
		requestParams.put("password", password);
		new RequestThread(REGISTER_URL, requestParams, HTTP_POST, messageType, handler).start();
	}
	
	/**
	 * 请求短信验证码
	 * @param mobile 手机号
	 * @param messageType
	 * @param handler
	 */
	public void requestValidateCode(String mobile, int messageType, Handler handler) {
		Map<String, String> requestParams = getCommonParams();
		requestParams.put("mobile", mobile);
		new RequestThread(VALIDATE_CODE_URL, requestParams, HTTP_GET, messageType, handler).start();
	}

}
