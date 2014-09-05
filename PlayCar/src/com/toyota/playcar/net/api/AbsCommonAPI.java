package com.toyota.playcar.net.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.toyota.playcar.util.CommonConstants;

import android.content.Context;
import android.text.TextUtils;

/**
 * 抽象网络请求接口
 * 
 * @author ganyu
 * @created 2014-7-17
 * 
 */
public abstract class AbsCommonAPI implements CommonConstants {
	/** GET请求 */
	public static final int HTTP_GET = 1;
	/** POST请求 */
	public static final int HTTP_POST = 2;
	Context mContext;
	
	public AbsCommonAPI() {
		
	}
	
	public AbsCommonAPI(Context context) {
		this.mContext = context;
	}
	
	/**
	 * 封装请求参数
	 * @param params 请求参数
	 * @param encode 编码格式
	 * @return
	 */
	public static String getParams(Map<String, String> params, String encode) {
		StringBuffer stringBuffer = new StringBuffer();
		// 判断参数是否为空
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				try {
					if (entry.getValue() == null || "".equals(entry.getValue())) {
						stringBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
					} else {
						stringBuffer.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), encode)).append("&");
					}
				} catch (UnsupportedEncodingException e) {
					stringBuffer = null;
				}
			}
			if (stringBuffer != null) {
				stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			}
		}
		return stringBuffer.toString();
	}
	
	/**
	 * 创建请求地址
	 * @param url
	 * @param params
	 * @return
	 */
	public String buildParams(String url, String params) {
		if (!TextUtils.isEmpty(params)) {
			// 判断地址是否带参数
			if (url.contains("?") || url.contains("&")) {
				url = url + "&" + params;
			} else {
				url = url + "?" + params;
			}
		}
		return url;
	}
	
	/**
	 * 获取统一请求参数
	 * @return
	 */
	public Map<String, String> getCommonParams() {
		Map<String, String> params = new HashMap<String, String>();
		
		return params;
	}

}
