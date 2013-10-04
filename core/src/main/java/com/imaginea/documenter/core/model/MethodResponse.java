package com.imaginea.documenter.core.model;

import java.util.List;



public class MethodResponse {
	
	private String path;
	private List<MethodOperations> operations;
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the operations
	 */
	public List<MethodOperations> getOperations() {
		return operations;
	}
	/**
	 * @param operations the operations to set
	 */
	public void setOperations(List<MethodOperations> operations) {
		this.operations = operations;
	}

	
	
	
}
