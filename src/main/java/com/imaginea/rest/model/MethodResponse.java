package com.imaginea.rest.model;



public class MethodResponse {
	
	private String path;
	private MethodOperations operations;
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
	public MethodOperations getOperations() {
		return operations;
	}
	/**
	 * @param operations the operations to set
	 */
	public void setOperations(MethodOperations operations) {
		this.operations = operations;
	}

	
	
	
}
