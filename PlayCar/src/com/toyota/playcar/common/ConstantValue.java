package com.toyota.playcar.common;

public interface ConstantValue {
	/**
	 * 域名地址http://prepro.116.com.cn/mobileapp/ http://dev.116.com.cn/mobileapp/
	 * */
	 String BASE_URL = "http://prepro.116.com.cn/mobileapp/";
//	String BASE_URL = "http://tv.116.com.cn/mobileapp/";
	/** 首页信息接口 */
	String MAIN_ACTIVITY_URL = BASE_URL + "getmainactivity";

	/** 用户--邮箱验证接口 **/
	String VERIFI_EMAIL = BASE_URL + "users/email_check";

	/** 用户--注册接口 **/
	String USER_REGISTER = BASE_URL + "users/register";

	/** 用户--登录接口 **/
	String USER_LOGIN = BASE_URL + "users/login";

	/** 用户--登录接口 **/
	String USER_INFO = BASE_URL + "users/login";

	/** 用户--密码找回接口 **/
	String FIND_BACK_PW = BASE_URL + "users/findbackpw";

	/** 用户--获取用户详情接口 **/
	String GET_USER_INFO = BASE_URL + "users/getuserinfo";

	/** 用户--图片修改 **/
	String ALTER_USER_IMG = BASE_URL + "users/alteruserimg";
	/** 用户--信息修改 **/
	String ALTER_USER_INFO = BASE_URL + "users/alteruserinfo";

	/** 摇动精选 */
	String SHAKE_SELECTION = BASE_URL + "getmainactivity";// "shakeselection";

	/**
	 * 获取视频详情接口---
	 */
	String GET_VIDEO_DETAIL = BASE_URL + "getvideoresource/detail";
	/** 图片信息 获取图片的接口 */
	String GET_PHOTOS_URL = BASE_URL + "getvideoresource/photos";
	/**
	 * 获取视频剧集列表---
	 */
	String GET_VIDEO_QUANTITY = BASE_URL + "getvideoresource/quantity";
	/**
	 * 广场的接口，所有的原创
	 */
	String GET_VIDEO_SQUARELIST = BASE_URL + "upmedia/squarelist";
	/**
	 * 获取视频相关推荐列表---
	 */
	String GET_VIDEO_HOTLIST = BASE_URL + "getvideoresource/hotlist";

	/**
	 * 获取视频相关推荐列表---
	 */
	String GET_VIDEO_COMMENT = BASE_URL + "comments/getcommentlist";

	/**
	 * 添加留言--
	 */
	String SEND_COMMENT = BASE_URL + "comments/add";

	/**
	 * 获取筛选列表数据--
	 */
	String GET_FILTER_DATA = BASE_URL + "searchcondition";
	/**
	 * 获取筛选结果数据--
	 */
	String GET_FILTER_RESULT = BASE_URL + "filtervideo";

	/**
	 * 获取搜索结果数据--
	 */
	String GET_SEARCH_RESULT = BASE_URL + "filtervideo/search";

	/**
	 * 获取新闻数据
	 * */
	String GET_NEWS_DATA = "http://www.116.com.cn/khd/list.shtml";
	/**
	 * 获取娱乐数据
	 * */
	String GET_ENTERTAINMENT_DATA = MAIN_ACTIVITY_URL;

	/**
	 * 获取电视剧数据
	 * */
	String GET_DRAMA_DATA = MAIN_ACTIVITY_URL;

	/**
	 * 获取电影数据
	 * */
	String GET_MOVIE_DATA = MAIN_ACTIVITY_URL;

	/**
	 * 添加收藏列表
	 * */
	String ADD_FAVORITER_DATA = BASE_URL + "favourite/add";
	/**
	 * 获取收藏列表
	 * */
	String GET_FAVORITER_DATA = BASE_URL + "favourite/read";
	/**
	 * 删除收藏
	 * */
	String DEL_FAVORITER_DATA = BASE_URL + "favourite/cancel";

	/**
	 * 获取观看记录
	 * */
	String GET_VIEW_HISTORY_DATA = MAIN_ACTIVITY_URL;
	/**
	 * 赞一个
	 * */
	String GADD_VIDEO_LIKE = BASE_URL + "likeit/add";
	/**
	 * 抽奖规则与算法接口
	 */
	String GET_LOTTERYRULE_URL = BASE_URL + "getlotteryinfo";
	/**
	 * 检测版本更新
	 * */
	String CHECK_VERSION = BASE_URL + "version";// http://prepro.116.com.cn/mobileapp/version
	/**
	 * 分享链接
	 * */
	String SHARE_DOWNLOAD_URL = BASE_URL + "sharepage?video_id=";// http://prepro.116.com.cn/mobileapp/version

	/**
	 * 留言--获取金币接口
	 * */
	String GET_GOLD_COMMENT = BASE_URL + "postcommentcoin";
	/**
	 * 分享--获取金币接口
	 * */
	String GET_GOLD_SHARE = BASE_URL + "postsharecoin";
	/**
	 * 播放--获取金币接口
	 * */
	String GET_GOLD_PLAY = BASE_URL + "postplaycoin";
	/**
	 * 签到--获取金币接口
	 * */
	String GET_GOLD_SING_IN = BASE_URL + "postsignin";

	/** 获取我的奖券以及每期开奖信息 */
	String GET_LOTTERYRULE_PERIODS = BASE_URL + "getuserlotterynumberlist";
	// http://prepro.116.com.cn/mobileapp/getlotterylist
	/** 获取供用户兑换奖列表券信息 */
	String GET_LOTTERYRULE_LIST = BASE_URL + "getlotterylist";

	// http://prepro.116.com.cn/mobileapp/postlotteryno
	/** 供用户兑换奖券 */
	String GET_LOTTERYRULE = BASE_URL + "postlotteryno";

	/** 查看是否收藏 */
	String CHECK_FAVOURITE = BASE_URL + "favourite/isfav";

	/** 获取ftp服务器配置信息 */
	String GET_FTP_CONFIG = BASE_URL + "upmedia/upconfig";

	/** 上传视屏介绍信息 http://prepro.116.com.cn/mobileapp/upmedia/getusermedia */
	String UPLOAD_VIDEO_INFO = BASE_URL + "upmedia/getusermedia";

	/** 获取首页横幅推荐栏 内容 */
	String GET_MAIN_BANNER_PROMOTIONS = BASE_URL + "landingpage";

	/**
	 * 获取首页横幅推荐栏 内容
	 * http://prepro.116.com.cn/mobileapp/getmainactivity/get_data?q
	 * =%E7%88%B1%E4%BD%A0&fasdfaaddaa
	 */
	String GET_SEARCH_LIST_BY_KEY = BASE_URL + "getmainactivity/get_data";
	/**
	 * 获取用户上传视频信息的接口
	 */
	String GET_MYUPLOADLIST_INFO = BASE_URL + "upmedia/upmedialist";
	/**
	 * 获取用户上传视频信息的接口
	 */
	String PRESENT_GIFT_URL = BASE_URL + "flowers/add";

	/**
	 * 获取通知列表的接口
	 */
	String GET_NOTICE_LIST = BASE_URL + "getmessageinfo";

	/**
	 * <b>字段：</b><br>
	 * 接口 <th>参数名<br>
	 * 设置参数 <th>参数名
	 * */
	String USER_PHOTO = "photo";
	String USER_NAME = "username";
	String NICK_NAME = "nickname";
	String PASS_WORD = "password";
	String USER_EMAIL = "email";
	String TOKEN = "token";
	String USER_ID = "userId";
	String KEEP_ON = "keepon";
	String ON_LINE = "online";
	String IS_FIRST = "isfirst";
	String OPEN_3G = "open_3g";
	/**
	 * Activity的标示---主要在 电影，电视，娱乐三个模块
	 */
	String ACTIVITY_FLAG = "acti";

	/** 观看记录表名 */
	String TABLE_VIEWS_HISTORY = "historys";
	/** 观看记录表-ID列 */
	String COLUMN_ID = "_id";
	/** 观看记录表-videoID列 */
	String COLUMN_VIDEO_ID = "video_id";
	/** 观看记录表-videoID列 */
	String COLUMN_ENTITY_ID = "video_entid";
	/** 观看记录表-videoURL列 */
	String COLUMN_VIDEO_URL = "video_url";
	/** 观看记录表-观看至--时间点- */
	String COLUMN_IN_DATE = "intime";
	/** 观看记录表-图片地址 */
	String COLUMN_PIC_URL = "picurl";
	/** 观看记录表-标题列 */
	String COLUMN_MODEL_NAME = "title";
	/** 观看记录表-视频类别-单集多集 */
	String COLUMN_TYPE = "type";
	/** 观看记录表-标签 */
	String COLUMN_MARK = "tags";

	/** 观看记录表-标签 */
	String COLUMN_VIEWS_NUMBER = "views";

	/** 编码格式 */
	String ENCODING = "utf-8";

	/** 应用点评下载页面 **/
	public static final String APPCOMMENT = "http://zhushou.360.cn/detail/index/soft_id/1634490";

	/** 微信APP ID */
	public static final String WX_APP_ID = "wx030a159d471a3cb7";// "wxee2467ee0ee310d3";//
																// "wxde3bb1068e72f49e";
	/** 新浪微博APP KEY */
	public static final String SINA_APP_KEY = "3033554896";
	// public static final String SINA_APP_KEY = "921067888";
	public static final String SINA_APP_SECREAT = "24b161a468f07b96112f868b46a41f44";
	/** 新浪微博回调地址 */
	public static final String SINA_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
	/** 新浪微博Scope权限 */
	public static final String SINA_SCOPE = "email,direct_messages_read,direct_messages_write,"
			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
			+ "follow_app_official_microblog," + "invitation_write";

	/** 打开设置页面 */
	public static final int SHOW_SETTING_PAGER = 0x700;
	public static final int MSG_SHARE_VIDEO = 0x1008;
	/** 加载完成一次数据后，发送的消息 */
	public static final int REQUEST_BACK = 0x1003;
	/** 加载更多的时候回调通知 */
	public static final int REQUEST_MORE = 0x1001;
	/** 加载首页Banner信息成功 */
	public static final int REQUEST_BANNER_BACK = 0x1005;
	public static final int ACTIVITY_CLOSE = 0x1009;
	public static final int SHOW_MAIN_MENU = 0x2001;
	public static final int SHOW_SETTING_MENU = 0x2002;
	public static final int SEARCH_START = 0x2003;
	public static final int SEARCH_PREP = 0x2004;
	public static final int CLOSE_SEARCH = 0x2005;
	public static final int UPDATE_APP = 0x2006;
	public static final int SIGN_IN = 0x2007;
	public static final int ACTIVITY_LOGIN_OUT = 0x2008;
	public static final int GET_KEY_LIST = 0x2009;// 查询时候 获取关键字列表

	public static final int ERROR = 0x113;// 加载错误标志
	public static final int GET_GOLD = 0x116;// 获得金币后的消息标志
	public static final String KEY = "116.com.cnapp";
	public static final int SHARE_NOT_LOGIN = 0X117;

	public static final int MSG_UPLOAD_SUCESS = 0x300;
	public static final int MSG_UPLOAD_BEGIN = 0x301;
	public static final int MSG_UPLOAD_FAIL = 0x302;
	public static final int MSG_UPLOAD_PROGRESS = 0x303;
	public static final int MSG_UPLOAD_ROMOTEPATH = 0x304;
	public static final int MSG_COMMIT_SUCESS = 0x305;
	public static final int MSG_COMMIT_SUCESS_CHECK_MYUPLOAD = 0x306;
}
