package com.imaginea.rest.model;

import java.util.List;
import java.util.Map;

public class ClassResponseEntity {

	private String basePath;
	private String resourcePath;
	private String produces;
	private String consumes;
	private List<MethodResponse> apis;
	private Map<String,ClassDetails> models;

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
	 * @return the basePath
	 */
	public String getBasePath() {
		return basePath;
	}

	/**
	 * @param basePath the basePath to set
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	/**
	 * @return the apis
	 */
	public List<MethodResponse> getApis() {
		return apis;
	}

	/**
	 * @param apis the apis to set
	 */
	public void setApis(List<MethodResponse> apis) {
		this.apis = apis;
	}

	/**
	 * @return the models
	 */
	public Map<String,ClassDetails> getModels() {
		return models;
	}

	/**
	 * @param models the models to set
	 */
	public void setModels(Map<String,ClassDetails> models) {
		this.models = models;
	}

	

	
}
