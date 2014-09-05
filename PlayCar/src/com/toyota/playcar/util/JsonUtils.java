package com.toyota.playcar.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.toyota.playcar.entity.MoveBean;
import com.toyota.playcar.entity.UserInfoBean;

public class JsonUtils {

	public static UserInfoBean getLoginInfo(String strJson) {

		UserInfoBean userInfo = new UserInfoBean();
		if (Utils.checkStr(strJson)) {
			return null;
		}

		if (getResultStatus(strJson).equals("1")) {
		}

		return userInfo;

	}

	private static String getResultStatus(String jsonStr) {
		// TODO Auto-generated method stub
		try {

			JSONObject jobc = new JSONObject(jsonStr);
			// 解析Json字符串，获取News对象
			if (!Utils.checkStr(jobc.getString("status"))) {

				return jobc.getString("status");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static boolean checkJsonString(String strJson) {
		try {
			if (strJson != null && !strJson.equals("")) {
				JSONObject jobc = new JSONObject(strJson);
				// 解析Json字符串，获取News对象
				if (jobc.getString("status").equals("1")) {

					// Log.i("8848", strJson);

					return true;

				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static MoveBean getVideoDetail(String result) {
		MoveBean movie = new MoveBean();
		// intent.putExtra("entity_id", moveList.get(k).videoEntID); // 视频ID
		// intent.putExtra("video_id", moveList.get(k).videoId); // 视频ID
		// intent.putExtra("img_url", moveList.get(k).videoImage);// 视频标题
		// intent.putExtra("video_name", moveList.get(k).videoName);// 视频标题
		// intent.putExtra("video_url", moveList.get(k).videoPath);// 视频地址
		// intent.putExtra("video_type", moveList.get(k).videoType);// 视频地址
		// intent.putExtra("video_flags", moveList.get(k).videoFlags);// 视频地址
		// intent.putExtra("video_views", moveList.get(k).viewsNumb);// 观看次数
		// "status": 1,
		// "video_url":
		// "http://vmsvideo.116.com.cn/fms/upload/5406/2014/07/21/M6UVR31IR_7box.mp4",
		// "items": {
		// "title": "aaaaaaaaaaaaaadddd",
		// "info": "sdaffffffffffffffffff",
		// "coin_total": "905",
		// "coin_permonth": "905",
		// "follow_num": "12",
		// "views": "91",
		// "video_type": "12",
		// "encrypt_id": "YMTc1MzY0",
		// "imageurl":
		// "http://prepro.116.com.cn//mobileapp/getmainactivity/get_vimg/YMTc1MzY0/6",
		// "username": "haochangyu@116.com.cn",
		// "nickname": "1237891",
		// "userid": "5406"
		// }
		if (JsonUtils.checkJsonString(result)) {
			try {
				JSONObject json = new JSONObject(result);
				JSONObject item = json.getJSONObject("items");
				movie.videoType = item.optString("video_type");
				// if (movie.videoType.equals("0")) {
				movie.videoPath = json.optString("video_url");
				movie.videoIntro = item.optString("info");
				movie.videoName = item.optString("title");
				movie.videoImage = item.optString("imageurl");
				movie.videoEntID = item.optString("encrypt_id");
				movie.videoFollow = item.optString("follow_num");
				movie.viewsNumb = item.optString("views");
				movie.videoIcoin = item.optString("coin_total");

				// }
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return movie;

	}

	/*
	 * "video_id": "24462", "number": "铁血娇娃", "video_url":
	 * "http://vmsvideo.116.com.cn/fms/vod/2014/03/10/6ZOB6KGA5aiH5aiDbox.mp4",
	 * "imgurl":
	 * "http://prepro.116.com.cn//mobileapp/getmainactivity/get_vimg/YMTcxMjM0/6"
	 * , "enity_id": "YMTcxMjM0", "video_type": "爱情|动作|惊悚|功夫|冒险|", "follow_num":
	 * "0", "views": "1021"
	 */

	/**
	 * 热播列表
	 */
	public static ArrayList<MoveBean> getHotList(String result) {
		ArrayList<MoveBean> list = new ArrayList<MoveBean>();
		try {

			JSONObject json = new JSONObject(result);

			JSONArray jarry = json.getJSONArray("items");

			for (int i = 0; i < jarry.length(); i++) {

				MoveBean move = new MoveBean();

				JSONObject jobc = jarry.getJSONObject(i);
				String imgurl = jobc.getString("imgurl");
				String id = jobc.getString("video_id");
				String title = jobc.getString("number");
				String detail = jobc.getString("video_url");
				// String mediaType = jobc.optString("media_type");
				String type = jobc.getString("media_type");
				String tags = jobc.getString("video_type");
				String views = jobc.getString("views");
				String entId = jobc.getString("enity_id");
				String followNum = jobc.optString("follow_num");
				move.videoId = id;
				move.videoEntID = entId;
				// Log.i("8024-留言获取", ":留言" + entId);
				move.videoImage = imgurl;
				move.videoName = title;
				move.videoPath = detail;
				move.videoType = type;
				move.viewsNumb = views;
				move.videoFlags = tags;
				move.videoFollow = followNum;
				// move.videoType=mediaType;
				list.add(move);
				Log.i("mainJson", imgurl + "\n" + id + "\n" + title + "\n"
						+ detail);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * 解析 影片信息查询结果
	 */
	public static ArrayList<MoveBean> getMoveList(String result) {
		ArrayList<MoveBean> list = new ArrayList<MoveBean>();
		try {

			JSONObject json = new JSONObject(result);

			JSONArray jarry = json.getJSONArray("items");

			for (int i = 0; i < jarry.length(); i++) {

				MoveBean move = new MoveBean();
				JSONObject jobc = jarry.getJSONObject(i);
				String imgurl = jobc.getString("imgurl");
				String id = jobc.getString("id");
				String title = jobc.getString("title");
				String detail = jobc.getString("contenturl");
				String flags = jobc.getString("video_type");
				String type = jobc.getString("type");
				String views = jobc.getString("views");
				String entId = jobc.getString("encrypt_id");
				move.videoId = id;
				move.videoEntID = entId;
				// Log.i("8024-留言获取", ":留言" + entId);
				move.videoImage = imgurl;
				move.videoName = title;
				move.videoPath = detail;
				move.videoType = type;
				move.viewsNumb = views;
				move.videoFlags = flags;
				list.add(move);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;

	}

	public static ArrayList<MoveBean> getFavoriteList(String results)
			throws JSONException {
		ArrayList<MoveBean> list;
		list = new ArrayList<MoveBean>();
		if (JsonUtils.checkJsonString(results)) {

			// 解析

			JSONObject json = new JSONObject(results);

			JSONArray jarry = json.getJSONArray("items");

			for (int i = 0; i < jarry.length(); i++) {

				MoveBean move = new MoveBean();
				JSONObject jobc = jarry.getJSONObject(i);
				String imgurl = jobc.getString("image_url");
				String id = jobc.getString("video_id");

				String entID = jobc.getString("encrypt_id");
				String title = jobc.getString("title");
				String detail = jobc.getString("video_url");
				String type = jobc.getString("type");
				String videoflag = jobc.getString("video_type");

				String views = jobc.getString("views");
				move.videoId = id;
				move.videoEntID = entID;
				move.videoImage = imgurl;
				move.videoName = title;
				move.videoPath = detail;
				move.videoType = type;
				move.viewsNumb = views;
				move.videoFlags = videoflag;

				list.add(0, move);

			}
		} else {
			Log.i("8012--收藏提示：", "获取收藏失败" + results);
		}

		return list;
	}

	/*
	 * * "imgurl":
	 * "http://prepro.116.com.cn/mobileapp/getmainactivity/get_vimg/YMTcxNjk2/6"
	 * , "title": "背水一战", "id": "24528", "date": "1387814400", "type": "0",
	 * "contenturl":
	 * "http://vmsvideo.116.com.cn/fms/vod/2014/03/10/6IOM5rC05LiA5oiYbox.mp4",
	 * "level": "0", "views": "1018"
	 */
	public static ArrayList<MoveBean> getResultsList(String results)
			throws JSONException {
		ArrayList<MoveBean> list;
		list = new ArrayList<MoveBean>();
		if (JsonUtils.checkJsonString(results)) {

			// 解析

			JSONObject json = new JSONObject(results);

			JSONArray jarry = json.getJSONArray("items");

			for (int i = 0; i < jarry.length(); i++) {

				MoveBean move = new MoveBean();
				JSONObject jobc = jarry.getJSONObject(i);
				String imgurl = jobc.getString("imgurl");
				String id = jobc.getString("id");

				String title = jobc.getString("title");
				String detail = jobc.getString("contenturl");
				String type = jobc.getString("type");
				String views = jobc.getString("views");
				move.videoId = id;
				move.videoImage = imgurl;
				move.videoName = title;
				move.videoPath = detail;
				move.videoType = type;
				move.viewsNumb = views;

				list.add(move);
				// Log.i("mainJson", imgurl + "\n" + id + "\n" + title + "\n"
				// + detail);

			}
		} else {
			Log.i("8012--筛选结果：", "获取失败" + results);
		}

		return list;
	}

	public static UserInfoBean getUser(String results) throws JSONException {
		UserInfoBean user = null;
		if (JsonUtils.checkJsonString(results)) {
			// 解析
			user = new UserInfoBean();
			JSONObject jobc = new JSONObject(results);

			// "": "5163",
			// "pwd_auth": "8bdad7763108b66b4fe22677187e29c3",
			// "find_pwd_time": "1394531072",
			// "sex": "0",
			// "birthday": null,
			// "education": null,
			// "occupation": null,
			// "country": null,
			// "intrest": null,
			// "receive": null,
			// "personal_img":
			// "http://prepro.116.com.cn/uploads/user/20140320/upload_file.jpg",
			// "active_code": "4ea4594c1fb6c3b8796f358de762e30e",
			// "status": 1

			/*
			 * { "status": 1, "personal_img":
			 * "http://prepro.116.com.cn/uploads/user/20140325/user_photo.jpg",
			 * "email": "185403612@qq.com", "nickname": "美亚用户", "place":
			 * "中国-北京", "birthday": "0000-00-00" }
			 */
			String uPhoto = jobc.getString("personal_img");
			String uName = jobc.getString("nickname");
			String uBirthday = jobc.getString("birthday");
			String email = jobc.getString("email");
			String uAccount = jobc.getString("account");
			String uPlace = jobc.getString("place");
			String uFlower = jobc.optString("integral");

			user.uAccount = uAccount;
			user.uPhoto = uPhoto;
			user.uName = uName;
			user.uEmail = email;
			user.uBirthday = uBirthday;
			user.uPlace = uPlace;
			user.flower = uFlower;

		} else {
			Log.i("8012--用户信息结果：", "获取失败" + results);
		}
		return user;
	}

	/**
	 * 传入参数results是获取用户的信息的返回结果 方法功能是解析成功，结果并赋值给一个对象UserInfoBean 否则返回null
	 * 
	 * @param results
	 * @return
	 */
	public static UserInfoBean getUserInfoBean(String results) {
		UserInfoBean user = null;
		if (JsonUtils.checkJsonString(results)) {
			// 解析
			user = new UserInfoBean();
			JSONObject jobc;
			try {
				jobc = new JSONObject(results);
				String uPhoto = jobc.getString("personal_img");
				String uName = jobc.getString("nickname");
				String uBirthday = jobc.getString("birthday");
				String email = jobc.getString("email");
				String uAccount = jobc.getString("account");
				String uPlace = jobc.getString("place");
				user.uAccount = uAccount;
				user.uPhoto = uPhoto;
				user.uName = uName;
				user.uEmail = email;
				user.uBirthday = uBirthday;
				user.uPlace = uPlace;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			return null;
		}
		return user;
	}

	/**
	 * <b>信鸽推送消息</b> </b> 解析字符串返回MoveBean对象 对应的是 {key：{key,value}}
	 */
	public static MoveBean getMoveInfo(JSONObject json) {
		MoveBean move = new MoveBean();
		try {
			// 获取{conten:{key:value}}
			String contentToStr = json.getString("content");
			// 第二次解析,解析成{key:value,key2:value2}
			JSONObject jsoncontent = new JSONObject(contentToStr);
			// 获取字符串里的value
			move.videoImage = jsoncontent.getString("imgurl");
			move.videoName = jsoncontent.getString("title");
			move.created = jsoncontent.getString("date");
			move.videoType = jsoncontent.getString("type");
			move.videoLevel = jsoncontent.getString("level");
			move.videoDuration = jsoncontent.getString("duration");
			move.viewsNumb = jsoncontent.getString("views");
			move.videoEntID = jsoncontent.getString("encrypt_id");
			move.videoId = jsoncontent.getString("id");
			move.videoPath = jsoncontent.getString("contenturl");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return move;
	}

	public static ArrayList<String> getImagesData(String jsonStr) {
		if (jsonStr != null && !(jsonStr.equals(""))) {// 不等于空执行
			try {
				JSONObject json = new JSONObject(jsonStr);
				JSONArray array = json.getJSONArray("items");
				ArrayList<String> list = new ArrayList<String>();
				for (int i = 0; i < array.length(); i++) {// 对应的是一个json数组
					JSONObject jStr = array.getJSONObject(i);// 获取一个从服务器返回的Lotteryperiod的信息

					String strImage = jStr.getString("path");
					list.add(strImage);
				}
				return list;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获取FTP配置信息
	 * 
	 * {"status":"1","server":"119.161.217.126","user":"userupload","pass":
	 * "11qq```","port":"11621","path":"2014/07/16"}
	 * 
	 */

	/** 获取首页重点推广信息 */
	public static ArrayList<MoveBean> getVideoList(String strJson) {
		try {
			if (JsonUtils.checkJsonString(strJson)) {
				ArrayList<MoveBean> moveList = new ArrayList<MoveBean>();
				JSONObject json = new JSONObject(strJson);
				JSONArray jarry = json.getJSONArray("items");

				for (int i = 0; i < jarry.length(); i++) {

					MoveBean move = new MoveBean();
					JSONObject jobc = jarry.getJSONObject(i);
					String imgurl = jobc.optString("imgurl");
					String id = jobc.optString("id");
					String title = jobc.optString("title");
					String detail = jobc.optString("contenturl");
					String type = jobc.optString("type");
					String views = jobc.optString("views");
					String videoflags = jobc.optString("video_type");
					String entId = jobc.optString("encrypt_id");
					move.videoId = id;
					move.videoEntID = entId;
					move.videoImage = imgurl;
					move.videoName = title;
					move.videoPath = detail;
					move.videoType = type;
					move.viewsNumb = views;
					move.videoFlags = videoflags;

					moveList.add(move);
				}
				return moveList;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	// "imgurl":
	// "http://prepro.116.com.cn//mobileapp/getmainactivity/get_vimg/YMTc0OTkz/6",
	// "title": "化妆师",
	// "date": "1400688000",
	// "type": "0",
	// "level": "0",
	// "duration": "0",
	// "views": "403",
	// "encrypt_id": "YMTc0OTkz",
	// "summary":
	/** 获取横幅推广信息 */
	public static ArrayList<MoveBean> getMainBanner(String strJson) {

		try {
			if (checkJsonString(strJson)) {
				ArrayList<MoveBean> moveList = new ArrayList<MoveBean>();
				JSONObject json = new JSONObject(strJson);
				JSONArray jarry = json.getJSONArray("photo_list");
				for (int i = 0; i < jarry.length(); i++) {
					JSONArray jarry2 = jarry.getJSONObject(i).getJSONArray(
							"itmes");

					for (int j = 0; j < jarry2.length(); j++) {
						MoveBean move = new MoveBean();
						JSONObject jobc = jarry2.getJSONObject(j);
						String imgurl = jobc.optString("imgurl");
						String id = jobc.optString("id");
						String title = jobc.optString("title");
						String detail = jobc.optString("contenturl");
						String type = jobc.optString("type");
						String views = jobc.optString("views");
						String videoflags = jobc.optString("video_type");
						String entId = jobc.optString("encrypt_id");
						String summary = jobc.optString("summary");
						move.videoId = id;
						move.videoEntID = entId;
						move.videoImage = imgurl;
						move.videoName = title;
						move.videoPath = detail;
						move.videoType = type;
						move.viewsNumb = views;
						move.videoFlags = videoflags;
						move.videoIntro = summary;

						moveList.add(move);
					}
				}
				return moveList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取首页推荐的电影或者剧集列表信息
	 * 
	 * @param flag
	 * <br>
	 *            <dt>0 获取电影列表<br> <dt>1 获取剧集列表
	 */
	public static ArrayList<MoveBean> getMainMovie(String strJson, int flag) {
		try {
			String key1 = "";
			if (flag == 0) {
				key1 = "film_list";
			} else {
				key1 = "serie_list";
			}
			if (checkJsonString(strJson)) {

				ArrayList<MoveBean> moveList = new ArrayList<MoveBean>();
				JSONObject json = new JSONObject(strJson);
				JSONObject jarry = json.getJSONObject(key1);
				if (!checkJsonString(jarry.toString())) {
					return null;
				}
				JSONArray jarry2 = jarry.getJSONArray("itmes");
				Log.i("XXXXX--", "" + jarry2.length());
				for (int i = 0; i < jarry2.length(); i++) {
					MoveBean move = new MoveBean();
					JSONObject jobc = jarry2.getJSONObject(i);
					String imgurl = jobc.optString("imgurl");
					String id = jobc.optString("id");
					String title = jobc.optString("title");
					String detail = jobc.optString("contenturl");
					String type = jobc.optString("type");
					String views = jobc.optString("views");
					String videoflags = jobc.optString("video_type");
					String entId = jobc.optString("encrypt_id");
					String summary = jobc.optString("summary");
					move.videoId = id;
					move.videoEntID = entId;
					move.videoImage = imgurl;
					move.videoName = title;
					move.videoPath = detail;
					move.videoType = type;
					move.viewsNumb = views;
					move.videoFlags = videoflags;
					move.videoIntro = summary;

					moveList.add(move);
				}
				return moveList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String[] getPicUrls(String result) {
		// TODO Auto-generated method stub

		String[] strUrl;
		if (checkJsonString(result)) {
			try {

				JSONObject job = new JSONObject(result);
				JSONArray jay = job.getJSONArray("pics");
				strUrl = new String[jay.length()];
				for (int i = 0; i < jay.length(); i++) {
					strUrl[i] = jay.getJSONObject(i).optString("url");
				}
				return strUrl;
			} catch (Exception exception) {
				exception.printStackTrace();
				return null;
			}
		}

		return null;
	}

	/**
	 * 功能：在获取原创列表时，解析从服务器返回的json字符串
	 * 
	 * @param result
	 *            服务器返回的信息 格式是:参考MoveBean中的参数信息
	 * @return 返回带有Move对象的list集合
	 */
	public static List<MoveBean> getUpLoadMoveInfo(String result) {
		if (result.equals("") || result == null) {
			return null;
		}
		try {
			JSONObject json = new JSONObject(result);
			JSONArray jsonArray = json.getJSONArray("videos");
			List<MoveBean> myMoveList = new ArrayList<MoveBean>();
			for (int i = 0; i < jsonArray.length(); i++) {
				MoveBean move = new MoveBean();
				JSONObject obj = jsonArray.getJSONObject(i);
				move.videoEntID = obj.optString("encrypt_id");
				move.flower_num = obj.optString("coin_total");// 鲜花数暂时显示金币数
				move.videoIntro = obj.optString("content");
				move.videoId = obj.optString("id");
				move.videoType = obj.optString("type");
				move.viewsNumb = obj.optString("views");
				move.videoName = obj.optString("title");
				move.videoImage = obj.optString("photo_paths" + "");
				move.videoPath = obj.optString("contenturl");
				move.coinNum = obj.optString("coin_total");
				move.flag = obj.optString("VMS_status");
				Log.i("9999999999999999999999", move.toString());
				myMoveList.add(move);

			}
			return myMoveList;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取原创广场的列表页数
	 * 
	 * @param results
	 * @return
	 */
	public static String totalPage(String results) {
		if (results.equals("") || results == null) {
			return null;
		}
		JSONObject json;
		String result = null;
		try {
			json = new JSONObject(results);
			result = json.getString("pagenums");
			Log.i("页数", result + "ppp");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return null;
		}
		return result;
	}

	/**
	 * http://prepro.116.com.cn/mobileapp/landingpage 解析photo_list
	 * 
	 * @param result
	 * @return
	 */
	public static List<MoveBean> getMoveBeanList(String result) {
		if (result.equals("") || result == null) {
			return null;
		}
		List<MoveBean> list = new ArrayList<MoveBean>();
		JSONObject json;
		try {
			json = new JSONObject(result);
			JSONArray jsonArray = json.getJSONArray("photo_list");
			for (int i = 0; i < jsonArray.length(); i++) {// items
				JSONObject jobj = jsonArray.getJSONObject(i);
				Log.i("第一步", i + " " + jobj.toString());
				JSONArray obj2 = jobj.getJSONArray("itmes");// move
				for (int j = 0; j < obj2.length(); j++) {
					Log.i("第二步", j + " " + obj2.getJSONObject(j).toString());
					JSONObject jobj3 = obj2.getJSONObject(j);
					MoveBean move = new MoveBean();
					move.videoEntID = jobj3.optString("encrypt_id");
					move.videoId = jobj3.optString("id");
					move.videoType = jobj3.optString("type");
					move.videoLevel = jobj3.optString("level");
					move.videoDuration = jobj3.optString("duration");
					move.videoIntro = jobj3.optString("summary");
					move.viewsNumb = jobj3.optString("views");
					move.videoName = jobj3.optString("title");
					move.videoImage = jobj3.optString("imgurl");
					move.videoPath = jobj3.optString("contenturl");
					move.created = jobj3.optString("date");
					Log.i("第三步", j + " " + move.toString());
					list.add(move);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public static String[] getKeyFilterList(String result) {
		// TODO Auto-generated method stub
		String strList[] = null;

		if (!checkJsonString(result)) {

			return null;

		}
		try {
			JSONObject job = new JSONObject(result);
			JSONArray jay = job.getJSONArray("items");
			strList = new String[jay.length()];

			for (int i = 0; i < jay.length(); i++) {
				strList[i] = jay.getString(i);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return strList;
	}
}
