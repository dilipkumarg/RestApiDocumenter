package com.imaginea.rest.model;

import java.util.List;
import java.util.Map;

public class ClassDetails {
	
	String className;
	Map<String,ModelPropertyDiscriptor> propertiesList;
	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * @return the propertiesList
	 */
	public Map<String, ModelPropertyDiscriptor> getPropertiesList() {
		return propertiesList;
	}
	/**
	 * @param propertiesList the propertiesList to set
	 */
	public void setPropertiesList(Map<String, ModelPropertyDiscriptor> propertiesList) {
		this.propertiesList = propertiesList;
	}
	
	
	
	
	

}
