package com.toyota.playcar.entity;

import java.util.List;

/**
 * 省份实体
 * 
 * @author ganyu
 * @created 2014-5-12
 * 
 */
public class Province {
	/**
	 * 省份ID
	 */
	public String id;
	/**
	 * 省份名称
	 */
	public String name;
	/**
	 * 城市列表
	 */
	public List<City> cityList;
}
