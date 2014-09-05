package com.toyota.playcar.activity;

import java.net.URISyntaxException;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.toyota.playcar.entity.ScenicSpot;
import com.toyota.playcar.util.CommonUtils;
import com.toyota.playcar.util.ImageLoader;
import com.toyota.playcar.util.ImageLoader.ImageCallback;
import com.toyota.playcar.view.LoadingDialog;
import com.toyota.playcar.view.TitleBar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 景点介绍界面
 * 
 * @author ganyu
 * @created 2014-5-5
 * 
 */
public class ScenicSpotIntroActivity extends BaseActivity {
	/** 百度定位 */
	private LocationClient mLocationClient;
	private LoadingDialog mLoadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void createView() {
		setContentView(R.layout.activity_scenicspot_intro);
		// 绑定控件
		TitleBar title_bar = (TitleBar) findViewById(R.id.title_bar);
		TextView tv_scenic_name = (TextView) findViewById(R.id.tv_scenic_name);
		ImageView iv_scenic_icon = (ImageView) findViewById(R.id.iv_scenic_icon);
		TextView tv_scenic_address = (TextView) findViewById(R.id.tv_scenic_address);
		TextView tv_comment_score = (TextView) findViewById(R.id.tv_comment_score);
		TextView tv_comment_info = (TextView) findViewById(R.id.tv_comment_info);
		TextView tv_price_info = (TextView) findViewById(R.id.tv_price_info);
		TextView tv_scenic_intro = (TextView) findViewById(R.id.tv_scenic_intro);
		Button btn_want_go = (Button) findViewById(R.id.btn_want_go);
		
		// 设置百度定位服务
		mLocationClient = new LocationClient(getApplicationContext());
		mLocationClient.setAK(BAIDU_LOC_APP_KEY);
		mLocationClient.registerLocationListener(mBDLocationListener);
		// 初始化定位参数
		initLocationParams();
		
		Intent dataIntent = this.getIntent();
		ScenicSpot scenicSpot = (ScenicSpot) dataIntent.getSerializableExtra("scenic_spot");
		String scenicId = scenicSpot.id;
		String scenicName = scenicSpot.name;
		String imageUrl = scenicSpot.imageUrl;
		String address = scenicSpot.address;
		String commentScore = scenicSpot.commentScore;
		int commentCount = scenicSpot.commentCount;
		String price = scenicSpot.price;
		String scenicIntro = scenicSpot.scenicIntro;
		
		title_bar.setTitle(R.string.scenic_spot_intro, true, this);
		if (!TextUtils.isEmpty(scenicName)) {
			tv_scenic_name.setText(scenicName);
		}
		if (!TextUtils.isEmpty(imageUrl)) {
			ImageLoader mImageLoader = new ImageLoader(this);
			Bitmap bitmap = mImageLoader.getBitmapFromCache(imageUrl);
			if (bitmap == null) {
				iv_scenic_icon.setImageResource(R.drawable.ic_launcher);
				mImageLoader.loadImage(imageUrl, iv_scenic_icon, new ImageCallback() {
					
					@Override
					public void onImageLoad(Bitmap bitmap, ImageView imageView, String imageUrl) {
						if (bitmap != null && imageView != null) {
							imageView.setImageBitmap(bitmap);
						}
					}
				});
			} else {
				iv_scenic_icon.setImageBitmap(bitmap);
			}
		} else {
			iv_scenic_icon.setImageResource(R.drawable.ic_launcher);
		}
		if (address != null) {
			tv_scenic_address.setText(address);
		}
		commentScore = "4.5";
		if (commentScore != null) {
			tv_comment_score.setText(commentScore + "分");
		}
		tv_comment_info.setText("" + commentCount + "条蜂评");
		price = "3277";
		if (price != null) {
			tv_price_info.setText("费用：" + price + "元起");
		}
		if (scenicId != null && ("1" .equals(scenicId) || "3" .equals(scenicId) || "5" .equals(scenicId))) {
			scenicIntro = "北京成团、去程体验高速动车新快感、全程陪同人员2.返程确保火车中下铺，游览胶东半岛当地全部知名景点北京成团、" +
					"去程体验高速动车新快感、全程陪同人员2.返程确保火车中下铺，游览胶东半岛当地全部知名景点北京成团、去程体验高速动车新快感、全程陪同人员" +
					"2.返程确保火车中下铺，游览胶东半岛当地全部知名景点北京成团、去程体验高速动车新快感、全程陪同人员2.返程确保火车中下铺，游览胶东半岛当地全部知名景点";
		} else {
			scenicIntro = "月亮湾是红原大草原上的精华景点，距红原县城3公里。因草原上的河流弯曲像弯月，故名“月亮湾”。公路在一座丘原上横穿，" +
					"丘原下是一马平川的草滩，落差约50米，大自然就把景色布置在这片草滩上。月亮湾是红原大草原上的精华景点，" +
					"距红原县城3公里。因草原上的河流弯曲像弯月，故名“月亮湾”。公路在一座丘原上横穿，丘原下是一马平川的草滩，" +
					"落差约50米，大自然就把景色布置在这片草滩上。月亮湾是红原大草原上的精华景点，距红原县城3公里。" +
					"因草原上的河流弯曲像弯月，故名“月亮湾”。公路在一座丘原上横穿，丘原下是一马平川的草滩，落差约50米，" +
					"大自然就把景色布置在这片草滩上。月亮湾是红原大草原上的精华景点，距红原县城3公里。因草原上的河流弯曲像弯月，" +
					"故名“月亮湾”。公路在一座丘原上横穿，丘原下是一马平川的草滩，落差约50米，大自然就把景色布置在这片草滩上。" +
					"月亮湾是红原大草原上的精华景点，距红原县城3公里。因草原上的河流弯曲像弯月，故名“月亮湾”。公路在一座丘原上横穿，" +
					"丘原下是一马平川的草滩，落差约50米，大自然就把景色布置在这片草滩上。月亮湾是红原大草原上的精华景点，距红原县城3公里。" +
					"因草原上的河流弯曲像弯月，故名“月亮湾”。公路在一座丘原上横穿，丘原下是一马平川的草滩，落差约50米，" +
					"大自然就把景色布置在这片草滩上。月亮湾是红原大草原上的精华景点，距红原县城3公里。因草原上的河流弯曲像弯月，故名“月亮湾”。" +
					"公路在一座丘原上横穿，丘原下是一马平川的草滩，落差约50米，大自然就把景色布置在这片草滩上。月亮湾是红原大草原上的精华景点，" +
					"距红原县城3公里。因草原上的河流弯曲像弯月，故名“月亮湾”。公路在一座丘原上横穿，丘原下是一马平川的草滩，落差约50米，大自然就把景色布置在这片草滩上。";
		}
		if (scenicIntro != null) {
			tv_scenic_intro.setText(scenicIntro);
		}
		
		// 注册监听事件
		btn_want_go.setOnClickListener(this);
	}
	
	/**
	 * 设置定位参数
	 */
	private void initLocationParams() {
		LocationClientOption clientOption = new LocationClientOption();
		clientOption.setOpenGps(true);
		// 设置网络优先
		clientOption.setPriority(LocationClientOption.NetWorkFirst);
		// 设置返回结果包含的地址信息
		clientOption.setAddrType("all");
		// 设置百度经纬度类型，默认为gcj02
		clientOption.setCoorType("bd09ll");
		// 设置百度定位服务名称
		clientOption.setServiceName("com.baidu.location.service_v2.5.3");
		// 设置定位请求的间隔时间为5000ms
		clientOption.setScanSpan(5000);
		// 禁止启用缓存定位
		clientOption.disableCache(true);
		mLocationClient.setLocOption(clientOption);
	}

	@Override
	public void onWidgetClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_bar_left: {
			finish();
		}
			break;
		case R.id.btn_want_go: {
			mLoadingDialog = new LoadingDialog(this, "定位中...", true, true);
			mLoadingDialog.show();
			mLoadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					dialog.dismiss();
					mLocationClient.stop();
				}
			});
			// 开始定位
			mLocationClient.start();
			// 请求位置
			if (mLocationClient != null && mLocationClient.isStarted()) {
				mLocationClient.requestLocation();
			}
		}
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onResponse(int msgType, String response) {
		
	}
	
	/**
	 * 百度定位监听事件
	 */
	private BDLocationListener mBDLocationListener = new BDLocationListener() {
		
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
				mLoadingDialog.dismiss();
			}
			// 判断位置信息是否为空
			if (location == null) {
				return;
			}
			// 判断定位是否成功
			if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeNetWorkLocation) {
				String city = location.getCity();
				String address = location.getAddrStr();
				double mLongitude = location.getLongitude();
				double mLatitude = location.getLatitude();
				
				mLocationClient.stop();
				// 判断用户是否安装百度地图客户端
				if (CommonUtils.isAppInstalled(ScenicSpotIntroActivity.this, "com.baidu.BaiduMap")) {
					try {
						String intentUri = "intent://map/direction?origin=latlng:" + mLatitude +"," + mLongitude 
								+ "|name:当前位置&destination=" + address +"&mode=driving&region=" + city + "&referer=ET|PlayCar#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";
						Intent baiduMapIntent = Intent.getIntent(intentUri);
						startActivity(baiduMapIntent);
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				} else {
					String mapUri = "http://api.map.baidu.com/direction?origin=latlng:" + mLatitude +"," + mLongitude 
							+ "|name:当前位置&destination=" + address +"&mode=driving&region=" + city + "&output=html";
					Uri uri = Uri.parse(mapUri);
					Intent viewIntent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(viewIntent);
				}
			}
		}
		
		@Override
		public void onReceivePoi(BDLocation poiLocation) {
			if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
				mLoadingDialog.dismiss();
			}
			if (poiLocation == null) {
                return;
            }
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if (mLocationClient != null) {
			mLocationClient.stop();
		}
	}

}
