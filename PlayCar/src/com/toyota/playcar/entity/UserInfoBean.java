package com.toyota.playcar.entity;

/**
 * 用户实体
 * 
 * @author ganyu
 * @created 2014-8-24
 * 
 */
public class UserInfoBean {
	/** 用户ID */
	public String id;
	/** 用户名称 */
	public String name;
	/** 用户头像地址 */
	public String headerUrl;
	/** 用户性别 */
	public int gender;
	/** 用户手机号 */
	public String mobile;
	/** 用户邮箱 */
	public String email;
	/** 用户积分 */
	public int score;
	public String uName;
	public String uID;
	public String uPhoto;
	public String token;
	public String uBirthday;
	public String uPlace;
	public String uEmail;
	public String uAccount;// 用户账户i-币
	public String flower;// 用户获得的献花数量

	@Override
	public String toString() {
		return "UserInfo [uName=" + uName + ", uID=" + uID + ", uPhoto="
				+ uPhoto + ", token=" + token + ", uBirthday=" + uBirthday
				+ ", uPlace=" + uPlace + ", uEmail=" + uEmail + ", uAccount="
				+ uAccount + "]";
	}

	public UserInfoBean(String uName, String uID, String uPhoto, String token,
			String uBirthday, String uPlace, String uEmail, String uAccount) {
		super();
		this.uName = uName;
		this.uID = uID;
		this.uPhoto = uPhoto;
		this.token = token;
		this.uBirthday = uBirthday;
		this.uPlace = uPlace;
		this.uEmail = uEmail;
		this.uAccount = uAccount;
	}

	public UserInfoBean(String uAccount) {
		super();
		this.uAccount = uAccount;
	}

	public UserInfoBean() {
		super();
		// TODO Auto-generated constructor stub
	}

}
