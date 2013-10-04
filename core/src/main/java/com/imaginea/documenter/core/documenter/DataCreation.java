package com.imaginea.documenter.core.documenter;

import java.io.IOException;

import com.imaginea.documenter.core.service.DelegatorService;

public abstract class DataCreation {
	//String base="D:/ProgramFiles/apache-tomcat-7.0.42/webapps/apiservice-1.0-SNAPSHOT";
	protected String[] classPaths = new String[] {"/WEB-INF/lib","/WEB-INF/classes" };
	protected String basePath;
	protected DelegatorService dlService;

	public DataCreation(String basePath,String[] classPaths) {
		if (classPaths != null) {
			this.classPaths = classPaths;
		}
		this.basePath = basePath;
		dlService = new DelegatorService(basePath, this.classPaths);
	}

	public abstract void createData() throws ClassNotFoundException, IOException ;

}
