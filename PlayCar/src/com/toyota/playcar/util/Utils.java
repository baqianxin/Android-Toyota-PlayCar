package com.toyota.playcar.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrixColorFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.toyota.playcar.common.ConstantValue;

public class Utils {
	private static boolean needProxy;

	/**
	 * 监测是否需要代理
	 */
	public static boolean checkNet() {
		String urlStr = "";
		try {
			String result = firstConnect(urlStr);
			if (result != null && !result.trim().equals("")) {
				return true;
			}
		} catch (Exception e) {
		} finally {
		}
		needProxy = true;
		if (needProxy) {
			try {
				Properties prop = System.getProperties();
				prop.setProperty("http.proxyHost", "10.0.0.172");
				prop.setProperty("http.proxyPort", "80");
				String result = firstConnect(urlStr);
				if (result != null && !result.trim().equals("")) {
					return true;
				}
			} catch (Exception e) {
			} finally {
			}
		}
		return false;
	}

	public static String firstConnect(String urlParams) {
		String httpResponse = null;
		try {

			HttpGet httpRequest = new HttpGet(urlParams);
			httpRequest.addHeader("Accept-Encoding", "gzip");
			// 获取HttpClient对象
			HttpClient httpClient = new DefaultHttpClient();
			// 获取HttpResponse实例
			HttpResponse httpResp = httpClient.execute(httpRequest);

			// 判断是够请求成功
			if (httpResp.getStatusLine().getStatusCode() == 200) {
				httpResponse = "true";
			} else {
				Log.v("myLog", "HttpGet方式请求失败");
			}

		} catch (Exception e) {

		}

		return httpResponse;
	}

	/**
	 * 将该app的语言设简体为中文
	 * 
	 * @param context
	 */
	public static void setLanguageChinese(Context context) {
		String languageToLoad = "zh";
		Locale locale = new Locale(languageToLoad);
		Locale.setDefault(locale);
		Configuration config = context.getResources().getConfiguration();
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		config.locale = Locale.SIMPLIFIED_CHINESE;
		context.getResources().updateConfiguration(config, metrics);
	}

	/**
	 * 将该app的语言设为英文
	 * 
	 * @param context
	 */
	public static void setLanguageEnglish(Context context) {
		String languageToLoad = "english";
		Locale locale = new Locale(languageToLoad);
		Locale.setDefault(locale);
		Configuration config = context.getResources().getConfiguration();
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		config.locale = Locale.ENGLISH;
		context.getResources().updateConfiguration(config, metrics);
	}

	/**
	 * 获得APP中当前的语言
	 * 
	 * @param context
	 * @return cn--中文 en-英文
	 */
	public static String getCurrentLanguage(Context context) {
		Configuration config = context.getResources().getConfiguration();
		return config.locale.toString().equals(
				Locale.SIMPLIFIED_CHINESE.toString()) ? "cn" : "en";
	}

	public static OnTouchListener ImageTouchDark;
	public static OnTouchListener RelativeTouchDark;
	public static OnTouchListener ButtonTouchDark;
	public static OnTouchListener ImageButtonTouchDark;

	/**
	 * 点击ImageView添加的点击效果的listener
	 * 
	 * @param activity
	 * @return onTouchListener
	 */
	public static OnTouchListener getImageViewListener(final Activity activity) {
		return ImageTouchDark = new OnTouchListener() {

			public final float[] BT_SELECTED = new float[] { 1, 0, 0, 0, 30, 0,
					1, 0, 0, 30, 0, 0, 1, 0, 30, 0, 0, 0, 1, 0 };
			public final float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0,
					0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView iv = (ImageView) activity.findViewById(v.getId());
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					iv.setColorFilter(new ColorMatrixColorFilter(
							BT_NOT_SELECTED));
					// iv.clearColorFilter();
				}
				return false;
			}
		};
	}

	/**
	 * 点击ImageButton添加的点击效果的listener
	 * 
	 * @param activity
	 * @return onTouchListener
	 */
	public static OnTouchListener getImageButtonListener(final Activity activity) {
		return ImageButtonTouchDark = new OnTouchListener() {

			public final float[] BT_SELECTED = new float[] { 1, 0, 0, 0, -50,
					0, 1, 0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0 };
			public final float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0,
					0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageButton iv = (ImageButton) activity.findViewById(v.getId());
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					iv.setColorFilter(new ColorMatrixColorFilter(
							BT_NOT_SELECTED));
				}
				return false;
			}
		};
	}

	/**
	 * 点击Button添加的点击效果的listener
	 * 
	 * @param activity
	 * @return onTouchListener
	 */
	public static OnTouchListener getButtonListener(final Activity activity) {
		return ButtonTouchDark = new OnTouchListener() {

			public final float[] BT_SELECTED = new float[] { 1, 0, 0, 0, -50,
					0, 1, 0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0 };
			public final float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0,
					0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0 };

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Button iv = (Button) activity.findViewById(v.getId());
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					iv.getBackground().setColorFilter(
							new ColorMatrixColorFilter(BT_SELECTED));
					iv.setBackgroundDrawable(v.getBackground());
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					iv.getBackground().setColorFilter(
							new ColorMatrixColorFilter(BT_NOT_SELECTED));
					iv.setBackgroundDrawable(v.getBackground());
				}
				return false;
			}
		};
	}

	/**
	 * 点击relativeLayout添加的点击效果的listener
	 */
	public static OnTouchListener getRelativeListener() {
		return RelativeTouchDark = new OnTouchListener() {

			public final float[] BT_SELECTED = new float[] { 1, 0, 0, 0, -50,
					0, 1, 0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0 };
			public final float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0,
					0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.getBackground().setColorFilter(
							new ColorMatrixColorFilter(BT_SELECTED));
					v.setBackgroundDrawable(v.getBackground());
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.getBackground().setColorFilter(
							new ColorMatrixColorFilter(BT_NOT_SELECTED));
					v.setBackgroundDrawable(v.getBackground());
				}
				return false;
			}
		};
	}

	public static boolean isAvailable(String result) {
		if (result != null && !result.trim().equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 判断当前网络连接状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		NetworkInfo networkInfo = ((ConnectivityManager) context
				.getApplicationContext().getSystemService("connectivity"))
				.getActiveNetworkInfo();
		if (networkInfo != null) {
			return networkInfo.isConnectedOrConnecting();
		}
		return false;
	}

	/**
	 * 获取屏幕宽
	 * 
	 * @param activity
	 * @return
	 */
	public static int getScreenWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 获取屏幕高
	 * 
	 * @param activity
	 * @return
	 */
	public static int getScreenHeight(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * dip 转 px
	 * 
	 * @param activity
	 * @param pxValue
	 * @return
	 */
	public static int dip2px(Activity activity, float dpValue) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		float density = dm.density;
		return (int) (dpValue / density + 0.5f);
	}

	/**
	 * px 转 dip
	 * 
	 * @param activity
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Activity activity, float pxValue) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		float density = dm.density;
		return (int) (pxValue / density + 0.5f);
	}

	/** 回收bitmap */
	public static void recycleBitmap(Bitmap bitmap) {
		if (bitmap != null) {
			bitmap.recycle();
			bitmap = null;
		}
	}

	/**
	 * 将key-value值转成get方式请求的参数，如果参数中有中文，请自行URLEncoder，再传进来
	 * 
	 * @param params
	 * @return 例如：gender=男&url=http://www.baidu.com&city=北京
	 */
	public static String generateGetParameter(Map<String, String> params) {
		StringBuilder encodedParams = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			encodedParams.append(entry.getKey());
			encodedParams.append('=');
			encodedParams.append(URLEncoder.encode(entry.getValue()));
			encodedParams.append('&');
		}

		Log.i("params", encodedParams.toString() + "============");
		// 删除最后一个多余的&符号
		if (encodedParams.length() <= 0) {
			return "";
		}
		encodedParams.deleteCharAt(encodedParams.length() - 1);
		return encodedParams.toString();
	}

	/**
	 * 将Bitmap转换成字符串
	 * 
	 * @param bitmap
	 * @return
	 */
	public static String bitmaptoString(Bitmap bitmap) {
		String string = null;
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 40, bStream);
		byte[] bytes = bStream.toByteArray();
		string = Base64.encodeToString(bytes, Base64.NO_WRAP);
		return string;
	}

	/**
	 * 将Bitmap转换成字符串
	 * 
	 * @param bitmap
	 * @return
	 */
	public static String filetoString(Bitmap bitmap) {
		String string = null;
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 40, bStream);
		byte[] bytes = bStream.toByteArray();
		string = Base64.encodeToString(bytes, Base64.NO_WRAP);
		return string;
	}

	// Stream stream = FileUpload.PostedFile.InputStream;
	// BinaryReader br = new BinaryReader(stream);
	// byte[] fileByte = br.ReadBytes((int)stream.Length);
	// string baseFileString = Convert.ToBase64String(fileByte);

	public static Bitmap stringtoBitmap(String string) {
		// 将字符串转换成Bitmap类型
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(string, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
					bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	/**
	 * 保存图片
	 * 
	 * @param bmp
	 * @param name
	 * @return
	 */
	public static String savePicPath(Bitmap bmp, String name) {
		String root_path;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			root_path = Environment.getExternalStorageDirectory().toString();
			File temp = new File(root_path + "/hyatt");
			if (!temp.exists()) {
				temp.mkdirs();
			}
			File temppic = new File(root_path + "/hyatt/pic");
			if (!temppic.exists()) {
				temppic.mkdirs();
			}
			File file = new File(root_path + "/hyatt/pic", name);
			try {
				FileOutputStream fos = new FileOutputStream(file);
				bmp.compress(CompressFormat.JPEG, 80, fos);
				return file.getAbsolutePath();
			} catch (Exception e) {

			}
		}
		return null;
	}

	public static String getPath() {
		String root_path;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			root_path = Environment.getExternalStorageDirectory().toString();
			File temp = new File(root_path + "/hyatt");
			if (!temp.exists()) {
				temp.mkdirs();
			}
			File temppic = new File(root_path + "/hyatt/pic");
			if (!temppic.exists()) {
				temppic.mkdirs();
			}
			return temppic.getAbsolutePath();
		}
		return null;
	}

	/**
	 * 根据两个位置的坐标点算直线距离 <dt>单位:m
	 * 
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return
	 */
	public static double getDistanceByPoi(double lat1, double lon1,
			double lat2, double lon2) {
		double radLat1 = lat1 * Math.PI / 180;
		double radLat2 = lat2 * Math.PI / 180;
		double a = radLat1 - radLat2;
		double b = lon1 * Math.PI / 180 - lon2 * Math.PI / 180;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
		s = Math.round(s * 10000) / 10000;

		return s;
	}

	/**
	 * 获取应用版本号
	 * 
	 * @param context
	 *            上下文对象
	 * @return 版本号
	 */
	public static String getVersionName(Context context) {
		String versionName = null;// 版本号
		// 获取PackageManager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
			versionName = packInfo.versionName;
		} catch (NameNotFoundException e) {
			versionName = null;
		}
		Log.v("Version-App-ttzx", versionName);
		return versionName;
	}

	/**
	 * 获取设备名称
	 * 
	 * @return 手机设备名称
	 */
	public static String getDeviceName() {
		return Build.DEVICE;
	}

	/**
	 * 获取当前系统版本号
	 * 
	 * @return 系统的版本号
	 */
	public static int getSystemVersion() {
		return VERSION.SDK_INT;
	}

	public static void name(View imageButton) {
		imageButton.setDrawingCacheEnabled(true);
		if (imageButton.getDrawingCache() != null
				&& imageButton.getDrawingCache().isRecycled() == false) {
			imageButton.getDrawingCache().recycle();
			Log.v("1023", "哇靠！！！！留言图片，尽然进行了回收！！！！！！！！！！！！！！");
		}
		imageButton.setDrawingCacheEnabled(false);
	}

	/**
	 * 判断是否是一个汉字
	 * 
	 * @param ch
	 * @return
	 */
	public static boolean isChineseChar(char ch) {
		try {
			// 汉字的字节数大于1
			return String.valueOf(ch).getBytes("GBK").length > 1;
		} catch (UnsupportedEncodingException e) {
			return false;
		}
	}

	/**
	 * 获取含中文的字符串长度
	 * 
	 * @param value
	 *            字符串
	 * @return
	 */
	public static int getLength(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}

	/** 取SD卡路径 **/
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory(); // 获取根目录
		}
		if (sdDir != null) {
			return sdDir.toString();
		} else {
			return "";
		}
	}

	public static String getMD5(String plainText) {
		StringBuffer buf = new StringBuffer("");
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			System.out.println("result: " + buf.toString());// 32位的加密

			System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buf.toString();
	}

	/** 收起键盘 */
	public static void closeBoard(Context mcontext) {
		InputMethodManager imm = (InputMethodManager) mcontext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (((Activity) mcontext).getCurrentFocus() == null) {
			return;
		}
		if (((Activity) mcontext).getCurrentFocus().getWindowToken() == null) {
			return;
		}
		if (imm.hideSoftInputFromWindow(((Activity) mcontext).getCurrentFocus()
				.getWindowToken(), 0)) {
			imm.showSoftInput(((Activity) mcontext).getCurrentFocus(), 0);
			// 软键盘已弹出
			imm.hideSoftInputFromWindow(((Activity) mcontext).getCurrentFocus()
					.getApplicationWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		} else {
			// 软键盘未弹出
			// this.getActivity(). finish();
		}
	}

	/** 弹出键盘 */
	public static void openBoard(Context mcontext) {
		InputMethodManager imm = (InputMethodManager) mcontext
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		// imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
		imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
	}

	public static boolean checkUser(MySharedPreferences pf) {
		boolean onLine = false;
		if (pf.getValue(ConstantValue.KEEP_ON, "").equalsIgnoreCase("true")) {
			// 如果保存自动登录--则后台自动登录更新用户数据
			onLine = true;
			pf.setValue(ConstantValue.ON_LINE, "true");
		}

		return onLine;
		// TODO Auto-generated method stub

	}

	/**
	 * 判断当前wifi可不可以用
	 */
	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 判断手机网络可以用否
	 */
	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 获取当前时间格式 "yyyy年-MM月dd日-HH时mm分ss秒"
	 */
	public static String getCurrentDat() {
		long currentTime = System.currentTimeMillis();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年-MM月dd日-HH时mm分ss秒");
		Date date = new Date(currentTime);
		return formatter.format(date);
	}

	/**
	 * 获取时间从1970 年 1 月 1 日午夜之间的时间差单位秒
	 */
	public static String getDat() {
		// long currentTime = System.currentTimeMillis();
		String str = String.valueOf(System.currentTimeMillis() / 1000);
		return str;
	}

	/**
	 * 获取时间从1970 年 1 月 1 日午夜之间的时间差单位毫秒
	 */
	public static String getDats() {
		// long currentTime = System.currentTimeMillis();
		String str = String.valueOf(System.currentTimeMillis());
		return str;
	}

	/**
	 * 判断邮箱格式 strEmail.getBytes().length!=strEmail.length()包含了汉字
	 * 邮箱必须包括@和.com，帐号不能是汉字
	 * 
	 * @param strEmail
	 *            ****@***.com
	 * @return
	 */
	public static boolean isEmail(String strEmail) {
		String str[] = strEmail.split("@");
		if (strEmail.contains("@") && strEmail.contains(".com")
				&& strEmail.getBytes().length == strEmail.length()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断手机号格式是否正确 手机号为空也合格
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		if (mobiles == null || mobiles.length() == 0) {
			return true;
		}
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return true 空 FALSE 不为空
	 */
	public static boolean checkStr(String str) {
		if (str == null || str.trim().length() == 0
				|| str.toLowerCase().equals("null")) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串长度是否大于300，小于300合格true反之
	 * 
	 * @param strLength
	 * @return
	 */
	public static boolean checkStrLength(String strLength) {
		if (strLength.toString().trim().length() > 300) {
			return false;
		}
		return true;
	}

	/**
	 * 判断字符串长度是否大于10，大于10合格反之
	 */
	public static boolean checkStrLong(String str) {
		if (str.length() < 10) {
			return false;
		}
		return true;
	}

	/**
	 * 关掉手机2G/3G网络
	 */
	public static void colseInternet() {

	}

	/**
	 * 开启手机2G/3G网络
	 */
	public static void startInternet() {

	}

	// 获取用户 邮箱地址
	public static String getUserEmail(Context context) {
		String email = MySharedPreferences.getMySharedPreferences(context)
				.getValue(ConstantValue.USER_EMAIL, "");
		return email;
	}

}
