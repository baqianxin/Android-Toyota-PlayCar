package com.toyota.playcar.util;

import java.util.Hashtable;

import android.util.Log;

/**
 * 日志工具类
 * 
 * @author ganyu
 * @created 2014-3-4
 * 
 */
public class LogUtils {
	/** 是否打印日志 */
	private static final boolean LOG_FLAG = true;
	/** 日志等级 */
	private static final int LOG_LEVEL = Log.VERBOSE;
	private static Hashtable<String, LogUtils> mLoggerTable = new Hashtable<String, LogUtils>();
	private String mClassName;
	private static final String TAG = "[PlayCar]";

	private LogUtils(String name) {
		mClassName = name;
	}
	
	private static LogUtils getLog(String className) {
		LogUtils classLogger = (LogUtils) mLoggerTable.get(className);
		if (classLogger == null) {
			classLogger = new LogUtils(className);
			mLoggerTable.put(className, classLogger);
		}
		return classLogger;
	}
	
	/**
	 * 获取功能名称
	 * @return
	 */
	private String getFunctionName() {
		StackTraceElement[] stackTraceElementArray = Thread.currentThread().getStackTrace();
		if (stackTraceElementArray == null) {
			return null;
		}
		for (StackTraceElement stackTraceElement : stackTraceElementArray) {
			if (stackTraceElement.isNativeMethod()) {
				continue;
			}
			if (stackTraceElement.getClassName().equals(Thread.class.getName())) {
				continue;
			}
			if (stackTraceElement.getClassName().equals(this.getClass().getName())) {
				continue;
			}
			return mClassName + "[ " + Thread.currentThread().getName() + ": "
					+ stackTraceElement.getFileName() + ":" 
					+ stackTraceElement.getLineNumber() + " "
					+ stackTraceElement.getMethodName() + " ]";
		}
		return null;
	}
	
	/**
	 * INFO
	 * @param className
	 * @param obj
	 */
	public static void i(String className, Object obj) {
		LogUtils.getLog(className).i(obj);
	}
	
	/**
	 * DEBUG
	 * @param className
	 * @param obj
	 */
	public static void d(String className, Object obj) {
		LogUtils.getLog(className).d(obj);
	}
	
	/**
	 * WARN
	 * @param className
	 * @param obj
	 */
	public static void w(String className, Object obj) {
		LogUtils.getLog(className).w(obj);
	}
	
	/**
	 * ERROR
	 * @param className
	 * @param obj
	 */
	public static void e(String className, Object obj) {
		LogUtils.getLog(className).e(obj);
	}
	
	/**
	 * ERROR
	 * @param className
	 * @param exception
	 */
	public static void e(String className, Exception exception) {
		LogUtils.getLog(className).e(exception);
	}
	
	/**
	 * ERROR
	 * @param className
	 * @param log
	 * @param tr
	 */
	public static void e(String className, String log, Throwable tr) {
		LogUtils.getLog(className).e(log, tr);
	}
	
	/**
	 * VERBOSE
	 * @param className
	 * @param obj
	 */
	public static void v(String className, Object obj) {
		LogUtils.getLog(className).v(obj);
	}

	/**
	 * INFO
	 * @param obj
	 */
	private void i(Object obj) {
		if (LOG_FLAG) {
			if (LOG_LEVEL <= Log.INFO) {
				String name = getFunctionName();
				if (name != null) {
					Log.i(TAG, name + " - " + obj);
				} else {
					Log.i(TAG, obj.toString());
				}
			}
		}
	}

	/**
	 * DEBUG
	 * @param obj
	 */
	private void d(Object obj) {
		if (LOG_FLAG) {
			if (LOG_LEVEL <= Log.DEBUG) {
				String name = getFunctionName();
				if (name != null) {
					Log.d(TAG, name + " - " + obj);
				} else {
					Log.d(TAG, obj.toString());
				}
			}
		}
	}

	/**
	 * VERBOSE
	 * @param obj
	 */
	private void v(Object obj) {
		if (LOG_FLAG) {
			if (LOG_LEVEL <= Log.VERBOSE) {
				String name = getFunctionName();
				if (name != null) {
					Log.v(TAG, name + " - " + obj);
				} else {
					Log.v(TAG, obj.toString());
				}
			}
		}
	}

	/**
	 * WARN
	 * @param obj
	 */
	private void w(Object obj) {
		if (LOG_FLAG) {
			if (LOG_LEVEL <= Log.WARN) {
				String name = getFunctionName();
				if (name != null) {
					Log.w(TAG, name + " - " + obj);
				} else {
					Log.w(TAG, obj.toString());
				}
			}
		}
	}

	/**
	 * ERROR
	 * @param obj
	 */
	private void e(Object obj) {
		if (LOG_FLAG) {
			if (LOG_LEVEL <= Log.ERROR) {
				String name = getFunctionName();
				if (name != null) {
					Log.e(TAG, name + " - " + obj);
				} else {
					Log.e(TAG, obj.toString());
				}
			}
		}
	}

	/**
	 * ERROR
	 * @param e
	 */
	private void e(Exception e) {
		if (LOG_FLAG) {
			if (LOG_LEVEL <= Log.ERROR) {
				Log.e(TAG, "error", e);
			}
		}
	}

	/**
	 * ERROR
	 * @param log
	 * @param tr
	 */
	private void e(String log, Throwable tr) {
		if (LOG_FLAG) {
			String line = getFunctionName();
			Log.e(TAG, "{Thread:" + Thread.currentThread().getName() + "}"
					+ "[" + mClassName + line + ":] " + log + "\n", tr);
		}
	}
	
}
