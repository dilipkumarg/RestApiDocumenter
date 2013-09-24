package com.imaginea.rest.model;

import java.util.List;

public class ClassDetails {
	
	String className;
	List<ModelPropertyDiscriptor> propertiesList;
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
	public List<ModelPropertyDiscriptor> getPropertiesList() {
		return propertiesList;
	}
	/**
	 * @param propertiesList the propertiesList to set
	 */
	public void setPropertiesList(List<ModelPropertyDiscriptor> propertiesList) {
		this.propertiesList = propertiesList;
	}
	

}
