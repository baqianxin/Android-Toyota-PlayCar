package com.toyota.playcar.net;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.toyota.playcar.util.CommonConstants;
import com.toyota.playcar.util.LogUtils;

/**
 * 网络请求工具类
 * 
 * @author ganyu
 * @created 2013-11-21
 * 
 */
public class HttpUtils implements CommonConstants {
	private static final String TAG = "HttpUtils";
	/** 请求超时时间 */
	public static final int SOCKET_TIMEOUT = 30 * 1000;
	/** 连接超时时间 */
	public static final int CONNECTION_TIMEOUT = 30 * 1000;
	
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
					LogUtils.e(TAG, "UnsupportedEncodingException:" + e.getMessage());
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
	 * @param url 请求地址
	 * @param params 请求参数
	 * @param encode 编码格式
	 * @return
	 */
	public static String requestGet(String url, Map<String, String> params, String encode) {
		String response = null;
		
		// 判断地址是否为空
		if (url == null || "".equals(url)) {
			return null;
		}
		
		// 判断编码格式是否为空
		if (encode == null || "".equals(encode)) {
			encode = ENCODE_UTF_8;
		}
		
		// 获取请求参数
		String reqParams = getParams(params, encode);
		// 判断传递参数是否为空
		if (reqParams != null && !"".equals(reqParams)) {
			// 判断地址是否带参数
			if (url.contains("?") || url.contains("&")) {
				url = url + "&" + reqParams;
			} else {
				url = url + "?" + reqParams;
			}
		}
		
		// 设置请求参数（请求超时、连接超时）
		HttpParams httpParams = new BasicHttpParams();
		httpParams.setParameter(HttpConnectionParams.SO_TIMEOUT, SOCKET_TIMEOUT);
		httpParams.setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
		
		HttpClient httpClient = new DefaultHttpClient(httpParams);
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpGet);
			int responseCode = httpResponse.getStatusLine().getStatusCode();
			// 判断请求是否成功
			if (responseCode == HttpStatus.SC_OK) {
				response = EntityUtils.toString(httpResponse.getEntity(), encode);
			} else {
				response = "failed";
			}
		} catch (ClientProtocolException e) {
			LogUtils.e(TAG, "ClientProtocolException:" + e.getMessage());
			response = null;
		} catch (IllegalStateException e) {
			LogUtils.e(TAG, "IllegalStateException:" + e.getMessage());
			response = null;
		} catch (IOException e) {
			LogUtils.e(TAG, "IOException:" + e.getMessage());
			response = null;
		}
		return response;
	}

	/**
	 * Post请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * @param encode 编码格式
	 * @return
	 */
	public static String requestPost(String url, Map<String, String> params, String encode) {
		// 判断地址是否为空
		if (url == null || "".equals(url)) {
			return null;
		}
		
		// 判断编码格式是否为空
		if (encode == null || "".equals(encode)) {
			encode = ENCODE_UTF_8;
		}
		
		String response = null;
		// 获取请求参数
		List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				nameValuePairList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		try {
			// 设置请求参数
			HttpEntity httpEntity = new UrlEncodedFormEntity(nameValuePairList, encode);
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(httpEntity);
			
			// 请求超时、连接超时
			HttpParams httpParams = new BasicHttpParams();
			httpParams.setParameter(HttpConnectionParams.SO_TIMEOUT, SOCKET_TIMEOUT);
			httpParams.setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
			
			HttpClient httpClient = new DefaultHttpClient(httpParams);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int responseCode = httpResponse.getStatusLine().getStatusCode();
			// 判断请求是否成功
			if (responseCode == HttpStatus.SC_OK) {
				response = EntityUtils.toString(httpResponse.getEntity(), encode);
			} else {
				response = "failed";
			}
		} catch (UnsupportedEncodingException e) {
			LogUtils.e(TAG, "UnsupportedEncodingException:" + e.getMessage());
			response = null;
		} catch (ClientProtocolException e) {
			LogUtils.e(TAG, "ClientProtocolException:" + e.getMessage());
			response = null;
		} catch (IOException e) {
			LogUtils.e(TAG, "IOException:" + e.getMessage());
			response = null;
		}
		return response;
	}
	
	/**
	 * 上传文件
	 * 
	 * @param actionUrl 请求地址
	 * @param params
	 * @param fileParams
	 * @return
	 * @throws IOException
	 */
	public static String uploadFile(String actionUrl, Map<String, String> params, Map<String, File> fileParams) throws IOException {
		String response = null;
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FORM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		
		URL url = new URL(actionUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(30 * 1000);
		connection.setReadTimeout(30 * 1000);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("connection", "keep-alive");
		connection.setRequestProperty("Charset", CHARSET);
		connection.setRequestProperty("Content-Type", MULTIPART_FORM_DATA + ";boundary=" + BOUNDARY);
		
		DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
		// 首先组拼文本类型的参数
		StringBuilder stringBuilder_1 = new StringBuilder();
		if (params != null) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				stringBuilder_1.append(PREFIX);
				stringBuilder_1.append(BOUNDARY);
				stringBuilder_1.append(LINEND);
				stringBuilder_1.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
				stringBuilder_1.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
				stringBuilder_1.append("Content-Transfer-Encoding: 8bit" + LINEND);
				stringBuilder_1.append(LINEND);
				stringBuilder_1.append(entry.getValue());
				stringBuilder_1.append(LINEND);
			}
			dataOutputStream.write(stringBuilder_1.toString().getBytes());
		}
		
		InputStream inputStream = null;
		// 发送文件数据
		if (fileParams != null) {
			for (Map.Entry<String, File> file : fileParams.entrySet()) {
				StringBuilder stringBuilder_2 = new StringBuilder();
				stringBuilder_2.append(PREFIX);
				stringBuilder_2.append(BOUNDARY);
				stringBuilder_2.append(LINEND);
				stringBuilder_2.append("Content-Disposition: form-data; name=\"Filedata\"; filename=\"" + file.getKey() + "\"" + LINEND);
				stringBuilder_2.append(LINEND);
				
				dataOutputStream.write(stringBuilder_2.toString().getBytes());
				InputStream is = new FileInputStream(file.getValue());
				byte[] buffer = new byte[1024];
				int len = -1;
				while ((len = is.read(buffer)) != -1) {
					dataOutputStream.write(buffer, 0, len);
				}
				is.close();
				dataOutputStream.write(LINEND.getBytes());
			}
		}
		// 请求结束标志
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		dataOutputStream.write(end_data);
		dataOutputStream.flush();
		
		int responseCode = connection.getResponseCode();
		// 判断请求是否成功
		if (responseCode == HttpURLConnection.HTTP_OK) {
			inputStream = connection.getInputStream();
			int len = -1;
			StringBuilder stringBuilder_3 = new StringBuilder();
			while ((len = inputStream.read()) != -1) {
				stringBuilder_3.append((char) len);
			}
			response = stringBuilder_3.toString();
		} else {
			response = "failed";
		}
		dataOutputStream.close();
		connection.disconnect();
		return response;
	}
	
}
