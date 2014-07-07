package com.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 常量类
 * */
public class Constant {

	public final static String USER = "user";
	
	public final static String BASE = "base";
	
	public final static String LUCENEINDEXPATH = "luceneindexpath";
	
	public final static int BLOGPAGE = 12;
	
	public static void main(String[] args) {
		JSONObject object = JSONObject.fromObject("{error:0}");
		System.out.println(object.get("error"));
		
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type", "click");
			jsonObject.put("name", "首页");
			jsonObject.put("url", "http://www.ofcard.com");
			jsonArray.add(jsonObject);
			jsonObject.put("type", "click");
			jsonObject.put("name", "简介");
			jsonObject.put("url", "http://www.ofcard.com");
			jsonArray.add(jsonObject);
			
			JSONArray jsonArraySub = new JSONArray();
			jsonObject.put("type", "view");
			jsonObject.put("name", "搜索");
			jsonObject.put("url", "http://www.ofcard.com");
			jsonArraySub.add(jsonObject);
			jsonObject.put("type", "view");
			jsonObject.put("name", "活动");
			jsonObject.put("url", "http://www.ofcard.com");
			jsonArraySub.add(jsonObject);
			
			JSONObject jsonObjectSub = new JSONObject();
			jsonObjectSub.put("name", "菜单");
			jsonObjectSub.put("sub_button", jsonArraySub);
			jsonArray.add(jsonObjectSub);
			
			JSONObject jsonObjectParent = new JSONObject();
			jsonObjectParent.put("button", jsonArray);
			
			System.out.println(jsonObjectParent.toString());
			
			
	}
	
}
