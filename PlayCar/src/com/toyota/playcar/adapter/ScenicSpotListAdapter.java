package com.toyota.playcar.adapter;

import java.util.List;

import com.toyota.playcar.activity.R;
import com.toyota.playcar.entity.ScenicSpot;
import com.toyota.playcar.util.ImageLoader;
import com.toyota.playcar.util.ImageLoader.ImageCallback;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 景点列表适配器
 * 
 * @author ganyu
 * @created 2014-5-6
 * 
 */
public class ScenicSpotListAdapter extends BaseAdapter {
	private Context mContext;
	/** 景点列表 */
	private List<ScenicSpot> mScenicSpotList;
	private LayoutInflater mInflater;
	private ViewHolder mViewHolder;
	/** 图片加载对象 */
	private ImageLoader mImageLoader;
	private ListView mListView;
	
	public ScenicSpotListAdapter(Context context, List<ScenicSpot> scenicSpotList, ListView listView) {
		this.mContext = context;
		this.mScenicSpotList = scenicSpotList;
		this.mInflater = LayoutInflater.from(mContext);
		this.mListView = listView;
		this.mImageLoader = new ImageLoader(mContext);
		mListView.setOnScrollListener(mOnScrollListener);
	}

	@Override
	public int getCount() {
		if (mScenicSpotList != null) {
			return mScenicSpotList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return mScenicSpotList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.view_scenic_spot_list_item, null);
			mViewHolder = new ViewHolder();
			mViewHolder.tv_scenic_name = (TextView) convertView.findViewById(R.id.tv_scenic_name);
			mViewHolder.iv_scenic_icon = (ImageView) convertView.findViewById(R.id.iv_scenic_icon);
			mViewHolder.tv_sort_number = (TextView) convertView.findViewById(R.id.tv_sort_number);
			mViewHolder.tv_comment_info = (TextView) convertView.findViewById(R.id.tv_comment_info);
			mViewHolder.tv_scenic_type = (TextView) convertView.findViewById(R.id.tv_scenic_type);
			mViewHolder.tv_scenic_address = (TextView) convertView.findViewById(R.id.tv_scenic_address);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		if (mScenicSpotList != null) {
			ScenicSpot scenicSpot = mScenicSpotList.get(position);
			String imageUrl = scenicSpot.imageUrl;
			mViewHolder.tv_scenic_name.setText("" + scenicSpot.name);
			// 判断图片地址是否为空
			if (imageUrl != null && !"".equals(imageUrl)) {
				mViewHolder.iv_scenic_icon.setTag(imageUrl);
				Bitmap bitmap = mImageLoader.getBitmapFromCache(imageUrl);
				if (bitmap == null) {
					mViewHolder.iv_scenic_icon.setImageResource(R.drawable.ic_launcher);
					mImageLoader.loadImage(imageUrl, mViewHolder.iv_scenic_icon, new ImageCallback() {
						
						@Override
						public void onImageLoad(Bitmap bitmap, ImageView imageView, String imageUrl) {
							if (bitmap != null && imageView != null) {
								imageView.setImageBitmap(bitmap);
								imageView.setTag("");
								notifyDataSetChanged();
							}
						}
					});
				} else {
					mViewHolder.iv_scenic_icon.setImageBitmap(bitmap);
				}
			} else {
				mViewHolder.iv_scenic_icon.setImageResource(R.drawable.ic_launcher);
			}
			String id = scenicSpot.id;
			mViewHolder.tv_sort_number.setVisibility(View.GONE);
			if ("1".equals(id) || "2".equals(id)) {
				mViewHolder.tv_sort_number.setVisibility(View.VISIBLE);
				mViewHolder.tv_sort_number.setText(id);
			}
			mViewHolder.tv_comment_info.setText("" + scenicSpot.commentCount + "条蜂评/" + scenicSpot.travelNoteCount + "篇游记提及");
			mViewHolder.tv_scenic_type.setText("" + scenicSpot.scenicType);
			mViewHolder.tv_scenic_address.setText("" + scenicSpot.address);
		}
		return convertView;
	}
	
	static class ViewHolder {
		private TextView tv_scenic_name;
		private ImageView iv_scenic_icon;
		private TextView tv_sort_number;
		private TextView tv_comment_info;
		private TextView tv_scenic_type;
		private TextView tv_scenic_address;
	}
	
	/**
	 * 列表滑动监听事件
	 */
	private AbsListView.OnScrollListener mOnScrollListener = new AbsListView.OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			switch (scrollState) {
			case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
				mImageLoader.lock();
				break;
			case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
				mImageLoader.unlock();
				break;
			case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
				mImageLoader.lock();
				break;
			default:
				break;
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			
		}
	};

}
