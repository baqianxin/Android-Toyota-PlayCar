package com.toyota.playcar.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import com.toyota.playcar.util.CommonConstants;

import android.text.TextUtils;

/**
 * 网络请求工具类
 * 
 * @author ganyu
 * 
 */
public class NetworkUtils implements CommonConstants {
	/** 网络连接超时时间 */
	public static final int CONNECT_TIEMOUT = 30 * 1000;
	/** 读取数据超时时间 */
	public static final int READ_TIEMOUT = 30 * 1000;
	
	/**
	 * 获取URL连接对象
	 * @param path 请求地址
	 * @param requestMethod 请求方式
	 * @return
	 */
	public static HttpURLConnection getURLConnection(String path, String requestMethod, String encode) {
		URL url;
		HttpURLConnection connection;
		// 判断地址是否为空
		if (TextUtils.isEmpty(path)) {
			return null;
		}
		// 判断请求方式是否为空
		if (TextUtils.isEmpty(requestMethod)) {
			requestMethod = HTTP_GET;
		}
		// 判断编码方式是否为空
		if (TextUtils.isEmpty(encode)) {
			encode = ENCODE_UTF_8;
		}
		try {
			url = new URL(path);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(CONNECT_TIEMOUT);
			connection.setReadTimeout(READ_TIEMOUT);
			// 判断请求方式
			if (HTTP_POST.equals(requestMethod)) {
				connection.setRequestMethod(requestMethod);
				connection.setDoOutput(true);
				connection.setUseCaches(false);
			}
			connection.setDoInput(true);
			connection.setRequestProperty("Charset", encode);
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setInstanceFollowRedirects(false);
			// 开始连接
			connection.connect();
		} catch (IOException e) {
			e.printStackTrace();
			connection = null;
		}
		return connection;
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
	 * Get请求
	 * @param path 请求地址
	 * @param params 请求参数
	 * @param encode 编码格式
	 * @return
	 */
	public static String requestGet(String url, Map<String, String> params, String encode) {
		String response = null;
		String requestParams = getParams(params, encode);
		
		// 判断传递参数是否为空
		if (requestParams != null && !"".equals(requestParams)) {
			url = url + "?" + requestParams;
		}
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		try {
			connection = getURLConnection(url, HTTP_GET, encode);
			if (connection != null) {
				int responseCode = connection.getResponseCode();
				// 判断响应是否成功
				if (responseCode == HttpURLConnection.HTTP_OK) {
					inputStream = connection.getInputStream();
					response = getResponseResult(inputStream);
				} else {
					response = null;
				}
			} else {
				response = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			response = null;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
		return response;
	}
	
	/**
	 * Post请求
	 * @param path 请求地址
	 * @param params 请求参数
	 * @param encode 编码格式
	 * @return
	 */
	public static String requestPost(String path, Map<String, String> params, String encode) {
		String response = null;
		byte[] bytes = getParams(params, encode).getBytes();
		
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		try {
			connection = getURLConnection(path, HTTP_POST, encode);
			if (connection != null) {
				DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
				dataOutputStream.write(bytes);
				dataOutputStream.flush();
				dataOutputStream.close();
				
				int responseCode = connection.getResponseCode();
				// 判断响应是否成功
				if (responseCode == HttpURLConnection.HTTP_OK) {
					inputStream = connection.getInputStream();
					response = getResponseResult(inputStream);
				} else {
					response = null;
				}
			} else {
				response = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			response = null;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
		return response;
	}

	/**
	 * 获取服务器响应数据
	 * @param inputStream
	 * @return
	 */
	public static String getResponseResult(InputStream inputStream) {
		String response = null;
		if (inputStream == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] bytes = new byte[1024];
		int len = 0;
		try {
			while((len = inputStream.read(bytes)) != -1) {
				baos.write(bytes, 0, len);
			}
			response = new String(baos.toByteArray());
		} catch (IOException e) {
			response = null;
		}
		return response;
	}
	
}
