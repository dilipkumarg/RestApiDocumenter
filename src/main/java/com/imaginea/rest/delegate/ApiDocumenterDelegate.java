package com.imaginea.rest.delegate;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.imaginea.rest.documenter.ApiDocumenter;
import com.imaginea.rest.model.ApiInfo;
import com.imaginea.rest.model.ClassInfo;
import com.imaginea.rest.model.ClassResponseEntity;





public class ApiDocumenterDelegate {
	
	private final Logger logger = Logger.getLogger(ApiDocumenter.class);
	private Map<String, String> pathJsonMap = null;
	private ClassDocumenter classDoc;
	private Gson gson;
	
	public ApiDocumenterDelegate(String basePath) {
		classDoc= new ClassDocumenter(basePath);
		gson= new Gson();
	}
	
	/**
	 * @param allClasses
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, String> preparePathJsonMap(Set<Class> allClasses) throws ClassNotFoundException {
		logger.debug("Preparing Map of path and respective JSON, Keyset Size  " + allClasses.size());
		pathJsonMap = new HashMap<String, String>();
		for (Class className : allClasses) {
			ClassResponseEntity classInfo = classDoc.extractClassInfo(className);
			pathJsonMap.put(classInfo.getResourcePath(), gson.toJson(classInfo));
		}
		logger.debug("Path Json Map Prepared Sucessfully, Total elements " + pathJsonMap.size());
		return pathJsonMap;
	}
	
	/**
	 * This Method will return the list of Classes having @Path annotation. from
	 * the file already created and stored.
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public ApiInfo getListClassMetaData() {
		ApiInfo apisInfo = new ApiInfo();
		List<ClassInfo> apis = new ArrayList<ClassInfo>();
		Set<String> pathKeySet = pathJsonMap.keySet();
		for (String path : pathKeySet) {
			logger.debug("Inserting " + path + " for ClassInfo ");
			ClassInfo classDesc = new ClassInfo();
			classDesc.setPath(path);
			apis.add(classDesc);
		}
		logger.debug("Total number of path list prepared " + apis.size());
		apisInfo.setApis(apis);
		return apisInfo;
	}
	
	
	public String getClassInfoFromMap(String className){
		return pathJsonMap.get("/" + className);
		
	}
	
}
