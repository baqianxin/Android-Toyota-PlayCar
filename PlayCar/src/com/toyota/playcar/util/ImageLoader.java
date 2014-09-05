package com.toyota.playcar.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.toyota.playcar.cache.ImageFileCache;
import com.toyota.playcar.cache.ImageMemoryCache;
import com.toyota.playcar.handler.WeakHandler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.widget.ImageView;

/**
 * 图片加载类
 * 
 * @author ganyu
 *
 */
public class ImageLoader {
	/** 图片内存缓存 */
	private ImageMemoryCache mImageMemoryCache;
	/** 图片文件缓存 */
	private ImageFileCache mImageFileCache;
	public WeakHandler mHandler;
	/** 锁对象 */
	private Object mLock = new Object();
	/** 第一次加载标记 */
	private boolean mFirstLoadFlag = true;
	/** 允许加载标记 */
	private boolean mAllowLoadFlag = true;
	
	public ImageLoader() {
		
	}
	
	public ImageLoader(Context context) {
		mImageMemoryCache = new ImageMemoryCache(context);
		mImageFileCache = new ImageFileCache();
	}
	
	/**
	 * 加锁
	 */
	public void lock() {
		mFirstLoadFlag = false;
		mAllowLoadFlag = false;
	}
	
	/**
	 * 解锁
	 */
	public void unlock() {
		mAllowLoadFlag = true;
		synchronized (mLock) {
			mLock.notifyAll();
		}
	}
	
	/**
	 * 加载图片
	 * @param imageUrl 图片地址
	 * @param imageView 图片视图
	 * @param imageCallback 图片回调接口
	 */
	public void loadImage(final String imageUrl, final ImageView imageView, final ImageCallback imageCallback) {
		// 加载网络图片
		mHandler = new WeakHandler(this) {
			
			@Override
			public void handleMessage(Message msg) {
				imageCallback.onImageLoad((Bitmap) msg.obj, imageView, imageUrl);
			}
		};
		// 创建图片加载线程
		Thread loadImageThread = new Thread() {
			
			@Override
			public void run() {
				// 判断是否允许加载
				if (!mAllowLoadFlag) {
					synchronized (mLock) {
						try {
							mLock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				Bitmap bitmap = null;
				// 判断是否允许加载或者是第一次加载
				if (mAllowLoadFlag || mFirstLoadFlag) {
					bitmap = loadImageFromUrl(imageUrl);
				}
				// 判断图片是否为空
				if (bitmap != null) {
					// 添加到内存缓存中
					mImageMemoryCache.addBitmap(imageUrl, bitmap);
					// 添加到文件缓存中
					mImageFileCache.saveBitmap(bitmap, imageUrl);
					// 发送消息更新界面
					Message msg = mHandler.obtainMessage(0, bitmap);
					mHandler.sendMessage(msg);
				}
			}
		};
		loadImageThread.start();
	}
	
	/**
	 * 读取缓存图片
	 * @param imageUrl 图片地址
	 * @return
	 */
	public Bitmap getBitmapFromCache(String imageUrl) {
		Bitmap bitmap = null;
		// 判断图片地址是否为空
		if (imageUrl != null) {
			bitmap = mImageMemoryCache.getBitmap(imageUrl);
			// 判断从内存缓存中读取的图片是否为空
			if (bitmap == null) {
				bitmap = mImageFileCache.getBitmap(imageUrl);
				// 判断从文件缓存中读取的图片是否为空
				if (bitmap != null) {
					// 添加到内存缓存中
					mImageMemoryCache.addBitmap(imageUrl, bitmap);
				}
			}
		}
		return bitmap;
	}
	
	/**
	 * 下载网络图片
	 * @param imageUrl 图片地址
	 * @return
	 */
	public Bitmap loadImageFromUrl(String imageUrl) {
		// 判断图片地址是否为空
		if (imageUrl == null) {
			return null;
		}
		URL url = null;
		InputStream inputStream = null;
		Bitmap bitmap = null;
		try {
			url = new URL(imageUrl);
			inputStream = (InputStream) url.getContent();
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = false;
			options.inSampleSize = 2;
			options.inPreferredConfig = Config.RGB_565;
			options.inPurgeable = true;
			options.inInputShareable = true;
			bitmap = BitmapFactory.decodeStream(inputStream, null, options);
			options.inJustDecodeBounds = true;
		} catch (MalformedURLException e1) {
			return null;
		} catch (IOException e2) {
			return null;
		}
		return bitmap;
	}

	/**
	 * 图片回调接口
	 */
	public interface ImageCallback {
		public void onImageLoad(Bitmap bitmap, ImageView imageView, String imageUrl);
	}
	
}
