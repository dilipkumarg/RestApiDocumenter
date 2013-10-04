/**
 * 
 */
package com.imaginea.documenter.core.delegate;

import com.imaginea.documenter.core.model.ClassResponseEntity;
import com.sun.jersey.api.model.AbstractResource;
import com.sun.jersey.server.impl.modelapi.annotation.IntrospectionModeller;

public class ClassDocumenter {

	private MethodDocumenter methodDocumenter;
	private ClassDetailsExctractor classDetailsExtractor;
	private String basePath;

	public ClassDocumenter(String basePath) {
		methodDocumenter = new MethodDocumenter();
		classDetailsExtractor = new ClassDetailsExctractor();
		this.basePath = basePath;

	}


	@SuppressWarnings("rawtypes")
	public ClassResponseEntity extractClassInfo(Class className) throws ClassNotFoundException {
		AbstractResource absResource = IntrospectionModeller.createResource(className);
		ClassResponseEntity response = new ClassResponseEntity();
		response.setBasePath(basePath);
		response.setResourcePath(absResource.getPath().getValue());
		response.setApis(methodDocumenter.extractMethodsInfo(absResource));
		response.setModels(classDetailsExtractor.extractClassDetails(absResource));

		return response;
	}

}
