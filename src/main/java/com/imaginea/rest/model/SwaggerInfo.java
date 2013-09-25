package com.imaginea.rest.model;

import java.util.ArrayList;
import java.util.List;

public class SwaggerInfo {
	/*private final String apiVersion = "1.0.0";
	private final String swaggerVersion = "1.2";*/
	
	private List<ClassInfo> apis;
	// TODO info about api i.e project description
	// private Info info;
	
	public SwaggerInfo() {
		// TODO Auto-generated constructor stub
		apis = new ArrayList<ClassInfo>();
		apis.add(new ClassInfo("/Dog"));
		apis.add(new ClassInfo("/Cat"));
	}
	
}
