package com.imaginea.rest.model;

import java.util.List;

public class ClassResponse {

	
	private String resourcePath;
	
	private String produces;
	
	private String consumes;
	
	private List<MethodsResponse> responseList;
	
//private Models modelList;
	
	private List<ObjectsList> modelList;

	/**
	 * @return the resourcePath
	 */
	public String getResourcePath() {
		return resourcePath;
	}

	/**
	 * @param resourcePath the resourcePath to set
	 */
	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	/**
	 * @return the produces
	 */
	public String getProduces() {
		return produces;
	}

	/**
	 * @param produces the produces to set
	 */
	public void setProduces(String produces) {
		this.produces = produces;
	}

	/**
	 * @return the responseList
	 */
	public List<MethodsResponse> getResponseList() {
		return responseList;
	}

	/**
	 * @param responseList the responseList to set
	 */
	public void setResponseList(List<MethodsResponse> responseList) {
		this.responseList = responseList;
	}

	
	/**
	 * @return the consumes
	 */
	public String getConsumes() {
		return consumes;
	}

	/**
	 * @param consumes the consumes to set
	 */
	public void setConsumes(String consumes) {
		this.consumes = consumes;
	}

	/**
	 * @return the modelList
	 */
	public List<ObjectsList> getModelList() {
		return modelList;
	}

	/**
	 * @param modelList the modelList to set
	 */
	public void setModelList(List<ObjectsList> modelList) {
		this.modelList = modelList;
	}

	
}
