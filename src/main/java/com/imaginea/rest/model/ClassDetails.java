package com.imaginea.rest.model;

import java.util.Map;

public class ClassDetails {
	
	String id;
	Map<String,ModelPropertyDiscriptor> properties;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map<String, ModelPropertyDiscriptor> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, ModelPropertyDiscriptor> properties) {
		this.properties = properties;
	}
	
}
