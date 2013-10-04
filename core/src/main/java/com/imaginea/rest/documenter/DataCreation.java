package com.imaginea.rest.documenter;

import com.imaginea.rest.service.DelegatorService;

public abstract class DataCreation {
	String base="D:/ProgramFiles/apache-tomcat-7.0.42/webapps/apiservice-1.0-SNAPSHOT";
	protected String[] classPaths = new String[] {base+"/WEB-INF/lib", base+"/WEB-INF/classes" };
	protected String basePath;
	protected DelegatorService dlService;

	public DataCreation(String basePath,String[] classPaths) {
		if (classPaths != null) {
			this.classPaths = classPaths;
		}
		this.basePath = basePath;
		dlService = new DelegatorService(basePath, this.classPaths);
	}

	public abstract void createData() throws ClassNotFoundException ;

}
