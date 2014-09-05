package com.toyota.playcar.net;

import java.util.Map;

import com.toyota.playcar.util.CommonConstants;

import android.os.Handler;
import android.os.Message;

/**
 * 网络请求线程
 * 
 * @author ganyu
 * @created 2014-6-30
 * 
 */
public class RequestThread extends Thread implements CommonConstants {
	/** 请求地址 */
	public String mRequestUrl;
	/** 请求参数 */
	public Map<String, String> mRequestParams;
	/** 请求方式（1代表GET，2代表POST，默认是GET） */
	public int mRequestMode;
	/** 请求类型 */
	public int mMessageType;
	/** 消息处理 */
	public Handler mHandler;
	
	/**
	 * 网络请求
	 * @param requestUrl 请求地址
	 * @param requestParams 请求参数
	 * @param requestMode 请求方式（1代表GET，2代表POST，默认是GET）
	 * @param messageType 消息类型
	 * @param handler 消息处理
	 */
	public RequestThread(String requestUrl, Map<String, String> requestParams, int requestMode, int messageType, Handler handler) {
		this.mRequestUrl = requestUrl;
		this.mRequestParams = requestParams;
		this.mRequestMode = requestMode;
		this.mMessageType = messageType;
		this.mHandler = handler;
	}
	
	@Override
	public void run() {
		String response = null;
		if (mRequestMode == 1) {
			response = HttpUtils.requestGet(mRequestUrl, mRequestParams, ENCODE_UTF_8);
		} else if (mRequestMode == 2) {
			response = HttpUtils.requestPost(mRequestUrl, mRequestParams, ENCODE_UTF_8);
		} else {
			response = HttpUtils.requestGet(mRequestUrl, mRequestParams, ENCODE_UTF_8);
		}
		Message msg = mHandler.obtainMessage(mMessageType, response);
		mHandler.sendMessage(msg);
	}
	
}
