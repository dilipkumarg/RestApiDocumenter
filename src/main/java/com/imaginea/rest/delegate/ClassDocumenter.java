/**
 * 
 */
package com.imaginea.rest.delegate;

import com.imaginea.rest.model.ClassResponseEntity;
import com.sun.jersey.api.model.AbstractResource;
import com.sun.jersey.server.impl.modelapi.annotation.IntrospectionModeller;

public class ClassDocumenter {

	@SuppressWarnings("rawtypes")
	public ClassResponseEntity extractClassInfo(String className)
			throws ClassNotFoundException {
		// TODO change this hard coded path
		Class givenClass = Class.forName("com.imaginea.rest.examples."
				+ className);
		return extractClassInfo(givenClass);
	}

	@SuppressWarnings("rawtypes")
	public ClassResponseEntity extractClassInfo(Class className) {

		AbstractResource absResource = IntrospectionModeller
				.createResource(className);
		ClassResponseEntity response = new ClassResponseEntity();
		response.setResourcePath(absResource.getPath().getValue());
		response.setResponseList(new MethodDocumenter()
				.extractMethodsInfo(absResource));
		response.setModelList(new ClassDetailsExctractor()
				.extractClassDetails(absResource));

		return response;
	}

}
