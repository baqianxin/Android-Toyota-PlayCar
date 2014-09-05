package com.toyota.playcar.cache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;

import com.toyota.playcar.util.LogUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;

/**
 * 图片文件缓存
 * 
 * @author lyric
 * @created 2014-2-26
 * 
 */
public class ImageFileCache {
	private static final String TAG = "ImageFileCache";
	/** 图片缓存文件夹名 */
	public static final String IMAGE_CACHE_FILE = "imgcache";
	/** 缓存文件后缀名 */
	private static final String WHOLESALE_CONV = ".cache";
	/** 文件大小单位MB */
	private static final int MB = 1024 * 1024;
	/** 文件缓存容量 */
	private static final int FILE_CACHE_SIZE = 10;
	/** SD卡空闲缓存空间 */
	private static final int FREE_SD_SPACE_NEEDED_TO_CACHE = 10;
	/** 图片缓存文件目录 */
	private static final String CACHE_DIR = "com.toyota.playcar";

	public ImageFileCache() {
		// 清理文件缓存
		removeCache(getDirectory());
	}

	/**
	 * 从缓存中获取图片
	 * @param url 图片地址
	 * @return 图片
	 */
	public Bitmap getBitmap(final String url) {
		final String path = getDirectory() + "/" + convertUrlToFileName(url);
		File file = new File(path);
		// 判断文件是否存在
		if (file.exists()) {
			Bitmap bitmap = BitmapFactory.decodeFile(path);
			// 判断图片是否为空
			if (bitmap == null) {
				file.delete();
			} else {
				// 修改文件的最后修改时间
				updateFileModifiedTime(path);
				return bitmap;
			}
		}
		return null;
	}

	/**
	 * 将图片存入文件缓存
	 * @param bitmap 图片
	 * @param url 图片地址
	 */
	public void saveBitmap(Bitmap bitmap, String url) {
		// 判断图片是否为空或SD卡上的剩余空间是否充足
		if (bitmap == null || FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSdCard()) {
			return;
		}
		String filename = convertUrlToFileName(url);
		String dir = getDirectory();
		File dirFile = new File(dir);
		// 判断文件是否存在
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		File file = new File(dir + "/" + filename);
		try {
			file.createNewFile();
			OutputStream outStream = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
			outStream.flush();
			outStream.close();
		} catch (FileNotFoundException e) {
			LogUtils.e(TAG, "FileNotFoundException:" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LogUtils.e(TAG, "IOException:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 计算存储目录下的文件大小<br />
	 * 当文件总大小大于规定的缓存容量或者SD卡剩余空间小于缓存需要的SD卡空间的规定则删除40%最近没有被使用的文件
	 */
	private boolean removeCache(String dirPath) {
		File fileDir = new File(dirPath);
		File[] fileArray = fileDir.listFiles();
		// 判断文件数组是否为空
		if (fileArray == null) {
			return true;
		}
		// 判断SD卡是否可用
		if (!isSdCardExists()) {
			return false;
		}
		int dirSize = 0;
		for (int i = 0; i < fileArray.length; i++) {
			if (fileArray[i].getName().contains(WHOLESALE_CONV)) {
				dirSize += fileArray[i].length();
			}
		}
		// 判断文件长度是否大于文件缓存大小或者SD卡剩余容量是否大于缓存大小
		if (dirSize > FILE_CACHE_SIZE * MB || FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSdCard()) {
			int removeFactor = (int) ((0.4 * fileArray.length) + 1);
			Arrays.sort(fileArray, new FileLastModifiedSort());
			for (int i = 0; i < removeFactor; i++) {
				if (fileArray[i].getName().contains(WHOLESALE_CONV)) {
					fileArray[i].delete();
				}
			}
		}
		// 判断SD卡剩余容量是否小于缓存大小
		if (freeSpaceOnSdCard() <= FILE_CACHE_SIZE) {
			return false;
		}
		return true;
	}

	/**
	 * 修改文件的最后修改时间
	 * @param path 文件路径
	 */
	public void updateFileModifiedTime(String path) {
		File file = new File(path);
		long newModifiedTime = System.currentTimeMillis();
		file.setLastModified(newModifiedTime);
	}

	/**
	 * 计算SD卡上的剩余空间
	 * @return SD卡上的剩余空间
	 */
	private int freeSpaceOnSdCard() {
		StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
		double sdFreeMB = ((double) statFs.getAvailableBlocks() * (double) statFs.getBlockSize()) / MB;
		return (int) sdFreeMB;
	}

	/**
	 * 将图片地址转成文件名
	 * @param url 图片地址
	 * @return 文件名
	 */
	private String convertUrlToFileName(String url) {
		return url.substring(url.lastIndexOf("/") + 1, url.length()) + WHOLESALE_CONV;
	}

	/**
	 * 获得缓存目录
	 * @return 缓存目录
	 */
	private String getDirectory() {
		return getSdCardPath() + "/" + CACHE_DIR + "/" + IMAGE_CACHE_FILE;
	}

	/**
	 * 获取SD卡路径
	 * @return SD卡路径
	 */
	private String getSdCardPath() {
		File sdCardDir = null;
		// 判断SD卡是否存在
		if (isSdCardExists()) {
			// 获取根目录
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
	 * 判断SD卡是否可用
	 * @return SD卡是否可用
	 */
	protected boolean isSdCardExists() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 根据文件的最后修改时间进行排序
	 */
	private class FileLastModifiedSort implements Comparator<File> {

		@Override
		public int compare(File lhs, File rhs) {
			// 判断文件修改时间是否大于上次修改时间
			if (lhs.lastModified() > rhs.lastModified()) {
				return 1;
			} else if (lhs.lastModified() == rhs.lastModified()) {
				return 0;
			} else {
				return -1;
			}
		}
	}
	
}
