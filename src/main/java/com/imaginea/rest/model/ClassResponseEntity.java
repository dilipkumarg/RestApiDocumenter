package com.imaginea.rest.model;

import java.util.List;
import java.util.Map;

public class ClassResponseEntity {

	
	private String resourcePath;
	
	private String produces;
	
	private String consumes;
	
	private List<MethodResponse> responseList;
	
//private Models modelList;
	
	Map<String,ClassDetails> modelList;

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
	public List<MethodResponse> getResponseList() {
		return responseList;
	}

	/**
	 * @param responseList the responseList to set
	 */
	public void setResponseList(List<MethodResponse> responseList) {
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
	public Map<String, ClassDetails> getModelList() {
		return modelList;
	}

	/**
	 * @param modelList the modelList to set
	 */
	public void setModelList(Map<String, ClassDetails> modelList) {
		this.modelList = modelList;
	}

	

	
}
