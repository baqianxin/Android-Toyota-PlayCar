package com.toyota.playcar.entity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 景点实体类
 * 
 * @author ganyu
 * @created 2014-5-6
 * 
 */
public class ScenicSpot implements Serializable {
	private static final long serialVersionUID = 1946531207531402198L;
	/** 景点ID */
	public String id;
	/** 景点名称 */
	public String name;
	/** 景点图片地址 */
	public String imageUrl;
	/** 景点评分 */
	public String commentScore;
	/** 蜂评数量 */
	public int commentCount;
	/** 游记相关数量 */
	public int travelNoteCount;
	/** 景点类型 */
	public String scenicType;
	/** 景点地址 */
	public String address;
	/** 景点费用 */
	public String price;
	/** 景点介绍 */
	public String scenicIntro;
	
	public static ScenicSpot parse(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return ScenicSpot.parse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static ScenicSpot parse(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }
        ScenicSpot scenicSpot = new ScenicSpot();
        scenicSpot.id 				= jsonObject.optString("id", "");
        scenicSpot.name 			= jsonObject.optString("name", "");
        scenicSpot.imageUrl 		= jsonObject.optString("image_url", "");
        scenicSpot.commentScore 	= jsonObject.optString("comment_score", "");
        scenicSpot.commentCount 	= jsonObject.optInt("comment_count", 0);
        scenicSpot.travelNoteCount 	= jsonObject.optInt("travel_note_count", 0);
        scenicSpot.scenicType 		= jsonObject.optString("type", "");
        scenicSpot.price 			= jsonObject.optString("price", "");
        scenicSpot.scenicIntro 		= jsonObject.optString("intro", "");
        
        return scenicSpot;
    }
    
}
