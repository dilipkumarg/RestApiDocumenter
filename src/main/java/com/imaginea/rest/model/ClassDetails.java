package com.imaginea.rest.model;

import java.util.Map;

public class ClassDetails {
	
	String id;
	Map<String,ModelPropertyDiscriptor> properties;
	/**
	 * @return the className
	 */
	public String getClassName() {
		return id;
	}
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.id = className;
	}
	/**
	 * @return the propertiesList
	 */
	public Map<String, ModelPropertyDiscriptor> getPropertiesList() {
		return properties;
	}
	/**
	 * @param propertiesList the propertiesList to set
	 */
	public void setPropertiesList(Map<String, ModelPropertyDiscriptor> propertiesList) {
		this.properties = propertiesList;
	}
	
	
	
	
	

}
