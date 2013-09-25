/**
 * 
 */
package com.imaginea.rest.delegate;

import com.imaginea.rest.model.ClassResponseEntity;
import com.sun.jersey.api.model.AbstractResource;
import com.sun.jersey.server.impl.modelapi.annotation.IntrospectionModeller;

public class ClassDocumenter {

	private MethodDocumenter methodDocumenter;
	private ClassDetailsExctractor classDetailsExtractor;

	public ClassDocumenter() {
		methodDocumenter = new MethodDocumenter();
		classDetailsExtractor = new ClassDetailsExctractor();

	}

	@SuppressWarnings("rawtypes")
	public ClassResponseEntity extractClassInfo(String className) throws ClassNotFoundException {
		// TODO change this hard coded path
		Class givenClass = Class.forName("com.imaginea.rest.examples." + className);
		return extractClassInfo(givenClass);
	}

	@SuppressWarnings("rawtypes")
	public ClassResponseEntity extractClassInfo(Class className) {

		AbstractResource absResource = IntrospectionModeller.createResource(className);
		ClassResponseEntity response = new ClassResponseEntity();
		response.setResourcePath(absResource.getPath().getValue());
		response.setResponseList(methodDocumenter.extractMethodsInfo(absResource));
		response.setModelList(classDetailsExtractor.extractClassDetails(absResource));

		return response;
	}

}
