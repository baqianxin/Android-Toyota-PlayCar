package com.toyota.playcar.util;

/**
 * 常量接口
 * 
 * @author ganyu
 * @created 2014-5-7
 * 
 */
public interface CommonConstants {
	/** UTF-8编码 */
	public static final String ENCODE_UTF_8 = "UTF-8";
	/** GBK编码 */
	public static final String ENCODE_GBK = "GBK";
	/** POST请求 */
	public static final String HTTP_POST = "POST";
	/** GET请求 */
	public static final String HTTP_GET = "GET";
	/** 百度定位APP KEY */
	public static final String BAIDU_LOC_APP_KEY = "ktGXCKz9R8oQPoefm9T3KAQR";
	/** 新浪微博APP KEY */
	public static final String SINA_WEIBO_APP_KEY = "1796438321";
	/** 新浪微博APP SECRET */
	public static final String SINA_WEIBO_APP_SECRET = "b39172ee23883c293a26e817e12f7975";
	/** 新浪微博回调地址 */
	public static final String SINA_WEIBO_REDIRECT_URL = "http://www.sina.com.cn/";
	/** 新浪微博Scope权限 */
	public static final String SINA_WEIBO_SCOPE = "email,direct_messages_read,direct_messages_write," 
			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read," + "follow_app_official_microblog," + "invitation_write";
	/** QQ APP ID */
	public static final String QQ_APP_ID = "101088790";
	/** QQ APP KEY */
	public static final String QQ_APP_KEY = "73434324519caebb4d7599d8c28b021a";
	/** QQ SCOPE */
	public static final String QQ_SCOPE = "get_simple_userinfo,add_share,add_topic,list_album,upload_pic,add_album";
	
	/** 基础请求地址 */
	public static final String BASE_URL = "";
	/** 注册地址 */
	public static final String REGISTER_URL = BASE_URL + "";
	/** 登录地址 */
	public static final String LOGIN_URL = BASE_URL + "";
	/** 获取验证码地址 */
	public static final String VALIDATE_CODE_URL = BASE_URL + "";
	/** 查找城市地址 */
	public static final String SEARCH_CITY_URL = BASE_URL + "";
	/** 景点查找地址 */
	public static final String SCENIC_SPOT_SEARCH_URL = BASE_URL + "";
	
	/** 请求码：注册 */
	public static final int REQ_ID_REGISTER = 0x1001;
	/** 请求码：登录 */
	public static final int REQ_ID_LOGIN = 0x1002;
	/** 请求码：验证码 */
	public static final int REQ_ID_VALIDATE_CODE = 0x1003;
	
}
