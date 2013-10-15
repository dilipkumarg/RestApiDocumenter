package com.imaginea.documenter.core.documenter;

import java.io.IOException;

import com.imaginea.documenter.core.service.DelegatorService;

public abstract class DataCreation {
	protected String[] classPaths;
	protected String basePath;
	protected DelegatorService dlService;

	public DataCreation(String basePath, String[] classPaths) {
		this.classPaths = classPaths;
		this.basePath = basePath;
		dlService = new DelegatorService(basePath, this.classPaths);
	}

	public abstract void createData() throws ClassNotFoundException,
			IOException;

}
