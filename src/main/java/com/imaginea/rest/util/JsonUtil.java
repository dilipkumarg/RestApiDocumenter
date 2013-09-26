package com.imaginea.rest.util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.imaginea.rest.model.ClassResponseEntity;

public class JsonUtil {

	
	
	 public static JSONObject toJson(ClassResponseEntity searchResult,String basePath) throws JSONException {
			JSONObject jsonObj = new JSONObject();
			// TODO change base path from hard coded
			jsonObj.put("basePath", basePath);
			jsonObj.put("resourcePath", searchResult.getResourcePath());
			jsonObj.put("apis", searchResult.getResponseList());
			jsonObj.put("models", searchResult.getModelList());
		
		 return jsonObj;
	 }
}
