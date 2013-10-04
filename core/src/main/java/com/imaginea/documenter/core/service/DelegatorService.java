package com.imaginea.documenter.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.imaginea.documenter.core.delegate.ClassDocumenter;
import com.imaginea.documenter.core.model.ClassResponseEntity;
import com.imaginea.documenter.core.util.RestApiClassUtil;

public class DelegatorService {
	private static Logger LOGGER = Logger.getLogger(DelegatorService.class);
	private ClassDocumenter classDoc;
	private String[] classPaths;

	public DelegatorService(String basePath, String[] classPaths) {
		this.classDoc = new ClassDocumenter(basePath);
		this.classPaths = classPaths;
	}

	@SuppressWarnings("rawtypes")
	public List<ClassResponseEntity> extractClassesInfo() throws ClassNotFoundException {
		Set<Class> classList = RestApiClassUtil.getPathAnnotatedClasses(classPaths);
		if(LOGGER.isDebugEnabled()){
		LOGGER.debug("Preparing Map of path and respective JSON, Keyset Size  " + classList.size());
		}
		List<ClassResponseEntity> classesInfo = new ArrayList<ClassResponseEntity>();
		for (Class className : classList) {
			ClassResponseEntity classInfo = classDoc.extractClassInfo(className);
			if (!classInfo.getResourcePath().equalsIgnoreCase("/apidocs")) {
				classesInfo.add(classInfo);
			}
		}
		return classesInfo;
	}
}
