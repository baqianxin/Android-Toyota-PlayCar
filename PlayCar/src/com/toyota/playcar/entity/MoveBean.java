package com.toyota.playcar.entity;

import java.io.Serializable;

/**
 * 
 * @author Administrator
 * 
 * <br>
 *         <b>Description</b> MoveBean 影片推荐实体类<br>
 *         包含信息： videoName <b>视频标题</b><br>
 *         videoPath <b>视频资源路径</b><br>
 *         videoImage <b>视频展示图片</b><br>
 *         videoDesc <b>视频描述</b><br>
 *         viewsNumb <b>视频浏览次数</b><br>
 */
public class MoveBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public String toString() {
		return "MoveBean [coinNum=" + coinNum + ", videoFollow=" + videoFollow
				+ ", videoIntro=" + videoIntro + ", videoEntID=" + videoEntID
				+ ", videoId=" + videoId + ", videoName=" + videoName
				+ ", videoPath=" + videoPath + ", videoImage=" + videoImage
				+ ", videoDesc=" + videoDesc + ", viewsNumb=" + viewsNumb
				+ ", videoType=" + videoType + ", videoFlags=" + videoFlags
				+ ", flower_num=" + flower_num + ", videoPoint=" + videoPoint
				+ ", created=" + created + ", videoDuration=" + videoDuration
				+ ", videoLevel=" + videoLevel + ", videoIcoin=" + videoIcoin
				+ ", flag=" + flag + "]";
	}

	/**
	 * 用户的金币数量
	 */
	public String coinNum;
	/**
	 * videoFollow <b>视频赞的次数</b>
	 */
	public String videoFollow;

	/**
	 * videoIntro <b>视频请简介</b>
	 */
	public String videoIntro;

	/**
	 * videoEntID <b>视频请求评论时所需要的参数ID</b> 请求新闻时候表示的是新闻的ID
	 */
	public String videoEntID;

	/**
	 * videoId <b>视频ID</b>
	 */
	public String videoId;

	/**
	 * videoName <b>视频标题</b>
	 */
	public String videoName;
	/**
	 * videoPath <b>视频资源路径</b>
	 */
	public String videoPath;
	/**
	 * videoImage <b>视频展示图片</b>
	 */
	public String videoImage;
	/**
	 * videoDesc <b>视频描述</b>
	 */
	private String videoDesc;
	/**
	 * viewsNumb <b>视频浏览次数</b>
	 */
	public String viewsNumb;

	public String videoType;// 0 11 12
	/**
	 * 爱情、惊悚等类型
	 */
	public String videoFlags;
	/**
	 * 鲜花数目
	 */
	public String flower_num;
	/**
	 * video 观看位置记录
	 */
	public String videoPoint;
	/**
	 * video 创建日期
	 */
	public String created;

	public String videoDuration;

	public String videoLevel;
	public String videoIcoin;
	/**
	 * 标志是否转码成功 2成功 其余失败
	 */
	public String flag;

	public String getCoinNum() {
		return coinNum;
	}

	public void setCoinNum(String coinNum) {
		this.coinNum = coinNum;
	}

	public String getVideoFollow() {
		return videoFollow;
	}

	public void setVideoFollow(String videoFollow) {
		this.videoFollow = videoFollow;
	}

	public String getVideoIntro() {
		return videoIntro;
	}

	public void setVideoIntro(String videoIntro) {
		this.videoIntro = videoIntro;
	}

	public String getVideoEntID() {
		return videoEntID;
	}

	public void setVideoEntID(String videoEntID) {
		this.videoEntID = videoEntID;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getVideoType() {
		return videoType;
	}

	public void setVideoType(String videoType) {
		this.videoType = videoType;
	}

	public String getVideoFlags() {
		return videoFlags;
	}

	public void setVideoFlags(String videoFlags) {
		this.videoFlags = videoFlags;
	}

	public String getFlower_num() {
		return flower_num;
	}

	public void setFlower_num(String flower_num) {
		this.flower_num = flower_num;
	}

	public String getVideoPoint() {
		return videoPoint;
	}

	public void setVideoPoint(String videoPoint) {
		this.videoPoint = videoPoint;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getVideoDuration() {
		return videoDuration;
	}

	public void setVideoDuration(String videoDuration) {
		this.videoDuration = videoDuration;
	}

	public String getVideoLevel() {
		return videoLevel;
	}

	public void setVideoLevel(String videoLevel) {
		this.videoLevel = videoLevel;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public String getVideoImage() {
		return videoImage;
	}

	public void setVideoImage(String videoImage) {
		this.videoImage = videoImage;
	}

	public String getVideoDesc() {
		return videoDesc;
	}

	public void setVideoDesc(String videoDesc) {
		this.videoDesc = videoDesc;
	}

	public String getViewsNumb() {
		return viewsNumb;
	}

	public void setViewsNumb(String viewsNumb) {
		this.viewsNumb = viewsNumb;
	}

}
