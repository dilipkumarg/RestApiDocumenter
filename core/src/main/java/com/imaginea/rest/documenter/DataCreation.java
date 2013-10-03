package com.imaginea.rest.documenter;

import com.imaginea.rest.service.DelegatorService;

public abstract class DataCreation {

	protected String[] classPaths = new String[] { "/WEB-INF/lib", "/WEB-INF/classes" };
	protected String basePath;
	protected DelegatorService dlService;

	public DataCreation(String[] classPaths, String basePath) {
		if (classPaths != null) {
			this.classPaths = classPaths;
		}
		this.basePath = basePath;
		dlService = new DelegatorService(basePath, classPaths);
	}

	public abstract void createData() throws ClassNotFoundException ;

}
