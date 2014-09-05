package com.toyota.playcar.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashMap;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
/**
 * 使用内存缓存图片
 * 
 * @author lyric
 *
 */
public class ImageMemoryCache {
	/** 硬引用缓存 */
    private static LruCache<String, Bitmap> mLruCache;
    /** 软引用缓存 */
    private static HashMap<String, SoftReference<Bitmap>> mSoftCache;
    /** 软引用缓存容量 */
	private static final int SOFT_CACHE_SIZE = 15;
	/** 缓存过期时间（3G/2G） */
    public static final int MOBILE_CACHE_TIMEOUT = 3600 * 1000;
    /** 缓存过期时间（WIFI） */
	public static final int WIFI_CACHE_TIMEOUT = 300 * 1000;
    
    public ImageMemoryCache(Context context) {
        int memoryClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        // 硬引用缓存容量，为系统可用内存的1/4
        int hardCacheSize = 1024 * 1024 * memoryClass / 4;
        mLruCache = new LruCache<String, Bitmap>(hardCacheSize) {
        	
            @Override
            protected int sizeOf(String key, Bitmap value) {
            	// 判断图片是否为空
                if (value != null) {
                	return value.getRowBytes() * value.getHeight();
                } else {
                	return 0;
                }
            }
                                                                                          
            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                if (oldValue != null) {
                	// 硬引用缓存容量满的时候，会根据LRU算法把最近没有被使用的图片转入此软引用缓存
                    mSoftCache.put(key, new SoftReference<Bitmap>(oldValue));
                }
            }
            
        };
        mSoftCache = new LinkedHashMap<String, SoftReference<Bitmap>>(SOFT_CACHE_SIZE, 0.75f, true) {
            private static final long serialVersionUID = 6040103833179403725L;
            
            @Override
			protected boolean removeEldestEntry(Entry<String, SoftReference<Bitmap>> eldest) {
            	// 判断缓存容量是否大于规定缓存大小
				if (size() > SOFT_CACHE_SIZE) {
					return true;
				}
				return false;
			}
            
        };
    }
                                                                                  
    /**
     * 从缓存中获取图片
     * @param imageUrl 图片地址
     * @return
     */
    public Bitmap getBitmap(String imageUrl) {
        Bitmap bitmap;
        // 先从硬引用缓存中获取
        synchronized (mLruCache) {
        	// 获取图片
            bitmap = mLruCache.get(imageUrl);
            // 判断图片是否为空
            if (bitmap != null) {
                // 如果找到的话，把元素移到LinkedHashMap的最前面，从而保证在LRU算法中是最后被删除
                mLruCache.remove(imageUrl);
                mLruCache.put(imageUrl, bitmap);
                return bitmap;
            }
        }
        // 如果硬引用缓存中找不到，到软引用缓存中找
		synchronized (mSoftCache) {
			SoftReference<Bitmap> bitmapReference = mSoftCache.get(imageUrl);
			// 判断软引用图片缓存是否为空
			if (bitmapReference != null) {
				bitmap = bitmapReference.get();
				// 判断图片是否为空
				if (bitmap != null) {
					// 将图片移回硬缓存
					mLruCache.put(imageUrl, bitmap);
					mSoftCache.remove(imageUrl);
					return bitmap;
				} else {
					mSoftCache.remove(imageUrl);
				}
			}
		}
        return null;
    } 
                                                                                  
    /**
     * 添加图片到缓存
     * @param imageUrl 图片地址
     * @param bitmap 图片
     */
    public void addBitmap(String imageUrl, Bitmap bitmap) {
    	// 判断图片和图片地址是否为空
        if (bitmap != null && imageUrl != null) {
            synchronized (mLruCache) {
                mLruCache.put(imageUrl, bitmap);
            }
        }
    }
    
    /**
     * 清除缓存
     */
    public void clearCache() {
        mSoftCache.clear();
    }
    
}
