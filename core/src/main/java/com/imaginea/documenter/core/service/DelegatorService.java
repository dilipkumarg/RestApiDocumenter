package com.imaginea.documenter.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.imaginea.documenter.core.constants.RestApiConstants;
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

	public List<ClassResponseEntity> extractClassesInfo() throws ClassNotFoundException {
		Set<Class> classList = RestApiClassUtil.getPathAnnotatedClasses(classPaths);
		LOGGER.debug("Preparing Map of path and respective JSON, Keyset Size  " + classList.size());
		List<ClassResponseEntity> classesInfo = new ArrayList<ClassResponseEntity>();
		for (Class className : classList) {
			if (!(className == (Class.forName(RestApiConstants.REST_API_MAIN_CLASS_NAME)))) {
				ClassResponseEntity classInfo = classDoc.extractClassInfo(className);
				classesInfo.add(classInfo);
			}
		}
		return classesInfo;
	}
}
