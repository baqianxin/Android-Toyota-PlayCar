package com.toyota.playcar.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
/**
 * 图片工具类
 * 
 * @author ganyu
 * @created 2013-12-12
 *
 */
public class ImageUtils {
	
	/**
	 * 转换图片：Bitmap转换为字节数组
	 * @param bitmap
	 * @return
	 */
	public static byte[] bitmapToBytes(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}
	
	/**
	 * 转换图片：字节数组转换为Bitmap
	 * @param bytes
	 * @return
	 */
	public static Bitmap bytesToBitmap(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}
	
	/**
	 * 转换图片：Drawable转换为Bitmap
	 * @param drawable
	 * @return Bitmap
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable == null) {
			return null;
		}
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), 
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}
	
	/**
	 * 转换图片：Bitmap转换为Drawable
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		}
		return new BitmapDrawable(bitmap);
	}
	
	/**
	 * 缩放图片
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		if (bitmap == null || width < 0 || height < 0) {
			return null;
		}
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
	}
	
	/**
	 * 缩放图片
	 * @param drawable
	 * @param w
	 * @param h
	 * @return
	 */
	public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
		if (drawable == null || w < 0 || h < 0) {
			return null;
		}
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		// drawable转换成bitmap
		Bitmap oldbmp = drawableToBitmap(drawable);
		// 创建操作图片用的Matrix对象
		Matrix matrix = new Matrix();
		// 计算缩放比例
		float sx = ((float) w / width);
		float sy = ((float) h / height);
		// 设置缩放比例
		matrix.postScale(sx, sy);
		// 建立新的bitmap，其内容是对原bitmap的缩放后的图
		Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);
		return new BitmapDrawable(newbmp);
	}

	/**
	 * 获取圆角图片
	 * @param bitmap
	 * @param roundPx
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		if (bitmap == null || roundPx < 0) {
			return null;
		}
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, w, h);
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		
		return output;
	} 
	
	/**
	 * 获取带倒影的图片
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getReflectionBitmap(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		}
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		Bitmap tempBitmap = Bitmap.createBitmap(bitmap, 0, height / 2, width, height / 2, matrix, false);
		Bitmap reflectionBitmap = Bitmap.createBitmap(width, (height + height / 2), Config.ARGB_8888);
		
		Canvas canvas = new Canvas(reflectionBitmap);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);
		canvas.drawBitmap(tempBitmap, 0, height + reflectionGap, null);
		
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				reflectionBitmap.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, reflectionBitmap.getHeight() + reflectionGap, paint);
		
		return reflectionBitmap;
	}
	
	/**
	 * 获取屏幕宽高
	 * @param context
	 * @return
	 */
	public static int[] getWindowMetrics(Context context) {
		Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		int[] metrics = new int[2];
		metrics[0] = outMetrics.widthPixels;
		metrics[1] = outMetrics.heightPixels;
		return metrics;
	}
	
	/**
	 * 获取屏幕截图
	 * @param context
	 * @return
	 */
	public static Bitmap getWindowBitmap(Context context) {
		WindowManager windowManager = ((Activity) context).getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		// 获取屏幕
		View decorView = ((Activity) context).getWindow().getDecorView();
		decorView.setDrawingCacheEnabled(true);
		bitmap = decorView.getDrawingCache();
		return bitmap;
	}
	
	/**
	 * 设置屏幕背景不变暗
	 * @param dialog 对话框
	 */
	public static void setWindowNoDark(Dialog dialog) {
		Window window = dialog.getWindow();
		WindowManager.LayoutParams windowParams = window.getAttributes();
		windowParams.dimAmount = 0.0f;
		window.setAttributes(windowParams);
	}
	
	/**
	 * 设置屏幕背景不变暗
	 * @param context
	 */
	public static void setWindowNoDark(Context context) {
		Window window = ((Activity) context).getWindow();
		WindowManager.LayoutParams windowParams = window.getAttributes();
		windowParams.dimAmount = 0.0f;
		window.setAttributes(windowParams);
	}
	
	/**
	 * 网络加载图片
	 * @param imageUrl 图片地址
	 * @return
	 */
	public static Bitmap loadBitmap(String imageUrl) {
		if (TextUtils.isEmpty(imageUrl)) {
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
		} catch (MalformedURLException e) {
			e.printStackTrace();
			bitmap = null;
		} catch (IOException e) {
			e.printStackTrace();
			bitmap = null;
		}
		return bitmap;
	}
	
	/**
	 * 获取图片
	 * @param context
	 * @param uri
	 * @return
	 */
	public static Bitmap getBitmap(Context context, Uri uri) {
		if (uri == null) {
			return null;
		}
		Bitmap bitmap = null;
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = false;
			options.inSampleSize = 1;
			options.inPreferredConfig = Config.RGB_565;
			options.inPurgeable = true;
			options.inInputShareable = true;
			bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
			options.inJustDecodeBounds = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
	
	/**
	 * 获取本地图片
	 * @param filePath
	 * @return
	 */
	public static Bitmap getLocaleBitmap(String filePath) {
		File file = new File(filePath);
		
		// 判断文件是否存在
		if (file.exists()) {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = false;
			options.inSampleSize = 1;
			options.inPreferredConfig = Config.RGB_565;
			options.inPurgeable = true;
			options.inInputShareable = true;
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);
			options.inJustDecodeBounds = true;
			
			// 判断图片是否为空
			if (bitmap == null) {
				file.delete();
			} else {
				return bitmap;
			}
		}
		return null;
	}
	
	/**
	 * 获取图片所占内存大小
	 * @param bitmap 图片
	 * @return
	 */
	public static int getBitmapSize(Bitmap bitmap) {
		if (bitmap == null || bitmap.isRecycled()) {
			return 0;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, baos);
		return baos.toByteArray().length / 1024;
	}
	
	/**
	 * 压缩图片{@link 按质量压缩}
	 * @param bitmap
	 * @param kbSize 图片大小KB
	 * @return
	 */
	public static Bitmap compressBitmap(Bitmap bitmap, int kbSize) {
		if (bitmap == null || kbSize < 0) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 90;
		while (baos.toByteArray().length / 1024 > kbSize) {
			baos.reset();
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
			options -= 10;
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		return BitmapFactory.decodeStream(bais, null, null);
	}
	
	/**
	 * 压缩图片{@link 按比例压缩}
	 * @param bitmapFilePath
	 * @param scaleWidth
	 * @param scaleHeight
	 * @param kbSize
	 * @return
	 */
	public static Bitmap getCompressBitmap(String bitmapFilePath, float scaleWidth, float scaleHeight, int kbSize) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(bitmapFilePath, options);
		options.inJustDecodeBounds = false;
		int outWidth = options.outWidth;
		int outHeight = options.outHeight;
		int scaleSize = 1;
		if (outWidth > outHeight && outWidth > scaleWidth) {// 如果宽度大的话根据宽度固定大小缩放
			scaleSize = (int) (options.outWidth / scaleWidth);
		} else if (outWidth < outHeight && outHeight > scaleHeight) {// 如果高度高的话根据宽度固定大小缩放
			scaleSize = (int) (options.outHeight / scaleHeight);
		}
		if (scaleSize <= 0) {
			scaleSize = 1;
		}
		options.inSampleSize = scaleSize;
		bitmap = BitmapFactory.decodeFile(bitmapFilePath, options);
		return compressBitmap(bitmap, kbSize);
	}
	
	/**
	 * 压缩图片{@link 按比例压缩}
	 * @param bitmap
	 * @param scaleWidth
	 * @param scaleHeight
	 * @param kbSize
	 * @return
	 */
	public static Bitmap getCompressBitmap(Bitmap bitmap, float scaleWidth, float scaleHeight, int kbSize) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {
			baos.reset();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap newBitmap = BitmapFactory.decodeStream(bais, null, options);
		options.inJustDecodeBounds = false;
		int outWidth = options.outWidth;
		int outHeight = options.outHeight;
		int scaleSize = 1;
		if (outWidth > outHeight && outWidth > scaleWidth) {// 如果宽度大的话根据宽度固定大小缩放
			scaleSize = (int) (options.outWidth / scaleWidth);
		} else if (outWidth < outHeight && outHeight > scaleHeight) {// 如果高度高的话根据宽度固定大小缩放
			scaleSize = (int) (options.outHeight / scaleHeight);
		}
		if (scaleSize <= 0) {
			scaleSize = 1;
		}
		options.inSampleSize = scaleSize;
		bais = new ByteArrayInputStream(baos.toByteArray());
		newBitmap = BitmapFactory.decodeStream(bais, null, options);
		return compressBitmap(newBitmap, kbSize);
	}
	
}
