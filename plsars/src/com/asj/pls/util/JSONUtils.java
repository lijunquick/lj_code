package com.asj.pls.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/** 
 * json转换工具类
 *  
 * @author lijun 
 *  
 */
public class JSONUtils {

	
	/**
	 * 解析单个数据的Json
	 * @param jsonStr
	 * @param key 
	 */
	public static JSONObject parseJson(String jsonStr, String key) throws JSONException{
		JSONObject jsonObjs = new JSONObject(jsonStr).getJSONObject(key);
		return jsonObjs;
	}
	
	/**
	 * 解析多个数据的Json
	 * @param jsonStr
	 * @param key 
	 */
	public static JSONArray parseJsonMulti(String jsonStr, String key) throws JSONException{
		JSONArray jsonObjs = new JSONObject(jsonStr).getJSONArray(key);
		return jsonObjs;
	}
	
}
