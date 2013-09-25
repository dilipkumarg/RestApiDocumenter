package com.imaginea.rest.util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.imaginea.rest.model.ClassResponseEntity;

public class JsonUtil {

	
	
	 public static JSONObject toJson(ClassResponseEntity searchResult) throws JSONException {
			JSONObject jsonObj = new JSONObject();
			// TODO change base path from hard coded
			jsonObj.put("basePath", "http://172.16.12.253:8080/RESTfulExample/rest");
			jsonObj.put("resourcePath", searchResult.getResourcePath());
			jsonObj.put("apis", searchResult.getResponseList());
			jsonObj.put("models", searchResult.getModelList());
		
		 return jsonObj;
	 }
}
