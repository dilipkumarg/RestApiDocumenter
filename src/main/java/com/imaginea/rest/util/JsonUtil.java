package com.imaginea.rest.util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.imaginea.rest.model.ClassResponseEntity;

public class JsonUtil {

	
	
	 public static JSONObject toJsonString(ClassResponseEntity searchResult) throws JSONException {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("resourcePath", searchResult.getResourcePath());
			jsonObj.put("apis", searchResult.getResponseList());
			jsonObj.put("Models", searchResult.getModelList());
		
		 return jsonObj;
	 }
}
