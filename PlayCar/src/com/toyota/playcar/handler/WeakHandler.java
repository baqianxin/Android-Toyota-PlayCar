package com.toyota.playcar.handler;

import java.lang.ref.WeakReference;

import android.os.Handler;

/**
 * Handler
 * 
 * @author ganyu
 * @created 2014-3-30
 * 
 */
public class WeakHandler extends Handler {
	WeakReference<Object> mRefObject;
	
	public WeakHandler(Object obj) {
		mRefObject = new WeakReference<Object>(obj);
	}
	
}
