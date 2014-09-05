package com.toyota.playcar.net;
/**
 * 网络请求回调监听类
 * 
 * @author ganyu
 * @created 2014-6-30
 * 
 */
public interface IRequestListener {
	/**
	 * 请求完成
	 * @param messageType
	 * @param response
	 */
	void onComplete(int messageType, String response);
	
	/**
	 * 请求异常
	 * @param messageType
	 * @param exception
	 */
	void onException(int messageType, String exception);
}
