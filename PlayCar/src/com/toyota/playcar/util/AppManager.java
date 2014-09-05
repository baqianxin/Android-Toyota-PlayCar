package com.toyota.playcar.util;

import java.util.Stack;

import com.toyota.playcar.activity.BaseActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 * 
 * @author ganyu
 * @created 2014-8-6
 * 
 */
public class AppManager {
	private static Stack<BaseActivity> mActivityStack;
	private static AppManager mInstance;

	private AppManager() {
		
	}

	/**
	 * 单实例 , UI无需考虑多线程同步问题
	 */
	public static AppManager getAppManager() {
		if (mInstance == null) {
			mInstance = new AppManager();
		}
		return mInstance;
	}

	/**
	 * 添加Activity到栈
	 */
	public void addActivity(BaseActivity activity) {
		if (mActivityStack == null) {
			mActivityStack = new Stack<BaseActivity>();
		}
		mActivityStack.add(activity);
	}

	/**
	 * 获取当前Activity（栈顶Activity）
	 */
	public BaseActivity getCurrentActivity() {
		if (mActivityStack == null || mActivityStack.isEmpty()) {
			return null;
		}
		BaseActivity activity = mActivityStack.lastElement();
		return activity;
	}

	/**
	 * 获取当前Activity（栈顶Activity） 没有找到则返回null
	 */
	public BaseActivity findActivity(Class<?> cls) {
		BaseActivity activity = null;
		for (BaseActivity aty : mActivityStack) {
			if (aty.getClass().equals(cls)) {
				activity = aty;
				break;
			}
		}
		return activity;
	}

	/**
	 * 结束当前Activity（栈顶Activity）
	 */
	public void finishActivity() {
		BaseActivity activity = mActivityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束指定的Activity(重载)
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			mActivityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定的Activity(重载)
	 */
	public void finishActivity(Class<?> cls) {
		for (BaseActivity activity : mActivityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
	 * 
	 * @param cls
	 */
	public void finishOthersActivity(Class<?> cls) {
		for (BaseActivity activity : mActivityStack) {
			if (!(activity.getClass().equals(cls))) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = mActivityStack.size(); i < size; i++) {
			if (null != mActivityStack.get(i)) {
				mActivityStack.get(i).finish();
			}
		}
		mActivityStack.clear();
	}

	/**
	 * 应用程序退出
	 */
	public void exit(Context context) {
		try {
			finishAllActivity();
			ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			am.killBackgroundProcesses(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
