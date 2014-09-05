package com.toyota.playcar.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.Selection;
import android.text.Spannable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;

/**
 * 通用工具类
 * 
 * @author ganyu
 * @created 2014-5-5
 * 
 */
public class CommonUtils {
	
	/**
	 * 获取配置文件数据
	 * @param context 上下文对象
	 * @param metaKey 标签
	 * @return 配置文件APP KEY
	 */
	public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
        	return null;
        }
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA);
            // 判断应用信息是否为空
            if (appInfo != null) {
                metaData = appInfo.metaData;
            }
            // 判断配置文件数据是否为空
            if (metaData != null) {
            	apiKey = metaData.getString(metaKey);
            }
        } catch (NameNotFoundException e) {
        	e.printStackTrace();
        	return null;
        }
        return apiKey;
    }
	
	/**
	 * 判断应用是否首次启动
	 * @param context
	 * @return
	 */
	public static boolean isAppFirstStart(Context context) {
		boolean firstStartFlag = false;
		String packageName = context.getPackageName();
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
			int currentVersion = packageInfo.versionCode;
			String versionName = packageInfo.versionName;
			SharedPreferences shared_prefs = context.getSharedPreferences("native_app_info", Context.MODE_PRIVATE);
			int lastVersion = shared_prefs.getInt("version_code", 0);
			// 判断版本是否更新
			if (currentVersion > lastVersion) {
				firstStartFlag = true;
			}
			// 存储当前版本
			Editor editor = shared_prefs.edit();
			editor.putInt("version_code", currentVersion);
			editor.putString("version_name", versionName);
			editor.commit();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			firstStartFlag = false;
		}
		return firstStartFlag;
	}
	
	/**
	 * 判断网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			return false;
		} else {
			NetworkInfo[] networkInfoList = cm.getAllNetworkInfo();
			if (networkInfoList != null) {
				for (int i = 0; i < networkInfoList.length; i++) {
					// 判断网络是否已连接
					if (networkInfoList[i].getState().equals(NetworkInfo.State.CONNECTED)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 判断WIFI是否连接
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		State wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		if (wifiState == State.CONNECTED) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断SD卡是否存在
	 * @return
	 */
	public static boolean isSdCardExists() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
	
	/**
	 * 获取SD卡路径
	 * @return
	 */
	public static String getSdCardDir() {
		File sdCardDir = null;
		
		// 判断SD卡是否存在
		if (isSdCardExists()) {
			sdCardDir = Environment.getExternalStorageDirectory();
		}
		
		// 判断根目录是否为空
		if (sdCardDir != null) {
			return sdCardDir.toString();
		} else {
			return "";
		}
	}
	
	/**
	 * 判断字符是否为汉字
	 * @param ch
	 * @return
	 */
	public static boolean isChineseChar(char ch) {
		try {
			// 汉字的字节数大于1
			return String.valueOf(ch).getBytes("GBK").length > 1;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 获取含中文的字符串长度
	 * @param value 字符串
	 * @return
	 */
	public static int getCharLength(String value) {
		int valueLength = 0;
		String chineseChar = "[\u0391-\uFFE5]";
		// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
		for (int i = 0; i < value.length(); i++) {
			// 获取一个字符
			String temp = value.substring(i, i + 1);
			// 判断是否为中文字符
			if (temp.matches(chineseChar)) {
				// 中文字符长度为2
				valueLength += 2;
			} else {
				// 非中文字符长度为1
				valueLength += 1;
			}
		}
		return valueLength;
	}
	
	/**
	 * 获取应用名称
	 * @param context
	 * @return
	 */
	public static String getAppName(Context context) {
		String appName = null;
		PackageManager packageManager = context.getPackageManager();
		try {
			ApplicationInfo appInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
			appName = (String) packageManager.getApplicationLabel(appInfo);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			appName = null;
		}
		return appName;
	}
	
	/**
	 * 获取应用版本号
	 * @param context 上下文对象
	 * @return 版本号
	 */
	public static String getAppVersion(Context context) {
		String appVersion = null;
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			appVersion = packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			appVersion = null;
		}
		return appVersion;
	}
	
	/**
	 * 获取设备名称
	 * @return 手机设备名称
	 */
	public static String getDevice() {
		return Build.DEVICE;
	}
	
	/**
	 * 获取设备型号
	 * @return 手机设备型号
	 */
	public static String getDeviceModel() {
		return Build.MODEL;
	}
	
	/**
	 * 获取当前系统的android版本号
	 * @return 系统的版本号
	 */
	public static int getSystemVersion() {
		return VERSION.SDK_INT;
	}
	
	/** 
     * 检查手机上是否安装了指定的软件
     * @param context 
     * @param packageName 应用包名
     * @return 
     */  
	public static boolean isAppInstalled(Context context, String packageName) {
		PackageManager packageManager = context.getPackageManager();
		// 获取所有已安装程序的包信息
		List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
		List<String> packageNameList = new ArrayList<String>();
		if (packageInfoList != null) {
			for (int i = 0; i < packageInfoList.size(); i++) {
				String packName = packageInfoList.get(i).packageName;
				packageNameList.add(packName);
			}
		}
		// 判断包名列表中是否有目标程序的包名
		return packageNameList.contains(packageName);
	}
	
	/**
	 * 判断应用是否启动
	 * @param context
	 * @return
	 */
	public static boolean isAppRunning(Context context) {
		final int maxRunningTaskNum = 200;
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskList = activityManager.getRunningTasks(maxRunningTaskNum);
		String packageName = context.getPackageName();
		for (RunningTaskInfo runningTaskInfo : runningTaskList) {
			if (runningTaskInfo.topActivity.getPackageName().equals(packageName) && runningTaskInfo.baseActivity.getPackageName().equals(packageName)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断服务是否已启动
	 * @param context
	 * @param serviceName
	 * @return
	 */
	public static boolean isServiceRunning(Context context, String serviceName) {
		if (serviceName == null) {
			return false;
		}
		final int maxRunningServiceNum = 200;
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> runningServiceList = activityManager.getRunningServices(maxRunningServiceNum);
		for (RunningServiceInfo runningServiceInfo : runningServiceList) {
			if (runningServiceInfo.service.getClassName().equals(serviceName)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 隐藏键盘
	 * @param context
	 * @param v
	 */
	public static void hideInputMethod(Context context, View v) {
		InputMethodManager inputMethodManager = null;
		try {
			inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		} catch (Throwable t) {
			t.printStackTrace();
			inputMethodManager = null;
		}
		if (inputMethodManager != null) {
			inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
	}
	
	/**
	 * 判断是否在窗口外部
	 * @param event
	 * @param context
	 * @param window
	 * @return
	 */
	public static boolean isOutOfBounds(MotionEvent event, Context context, PopupWindow window) {
		final int x = (int) event.getX();
		final int y = (int) event.getY();
		int slop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
		View decorView = window.getContentView();
		return (x < -slop) || (y < -slop) || (x > (decorView.getWidth() + slop)) || (y > (decorView.getHeight() + slop));
	}
	
	/**
	 * 将dip转换为px
	 * @param context
	 * @param dipValue dip
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f * (dipValue >= 0 ? 1 : -1));
	}
	
	/**
	 * 将px转换为dip
	 * @param context
	 * @param pxValue px
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f * (pxValue >= 0 ? 1 : -1));
	}
	
	/**
	 * 设置输入框光标位置
	 * @param context 上下文
	 * @param editText
	 */
	public static void setCursorLocation(Context context, EditText editText) {
		CharSequence text = editText.getText();
		if (text instanceof Spannable) {
			Spannable spanText = (Spannable) text;
			Selection.setSelection(spanText, text.length());
		}
	}
	
	/**
	 * 获取总的内存空间
	 * @return
	 */
	public static long getTotalMemory() {
		return Runtime.getRuntime().totalMemory();
	}
	
	/**
	 * 获取空余内存空间
	 * @return
	 */
	public static long getFreeMemory() {
		return Runtime.getRuntime().freeMemory();
	}
	
	/**
	 * 获取手机IMEI码（手机序列号，用于识别每一部独立的手机）
	 * @param context
	 * @return 手机IMEI码
	 */
	public static String getIMEICode(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}
	
	/**
	 * 获取手机IMSI码（国际移动用户识别码，用于唯一识别移动用户的一个号码）
	 * @param context 上下文对象
	 * @return 手机IMSI码
	 */
	public static String getIMSICode(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getSubscriberId();
	}
	
	/**
	 * 验证手机号码
	 * @param number 手机号码
	 * @return 验证状态
	 */
	public static boolean isMobileNumber(String number) {
		String regExp = "^13[0-9]{1}[0-9]{8}$|15[0125689]{1}[0-9]{8}$|18[0-3,5-9]{1}[0-9]{8}$";
		Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
	}
	
	/**
	 * 验证Email
	 * @param email 邮箱
	 * @return 验证状态
	 */
	public static boolean isEmail(String email) {
		String regExp = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
}
