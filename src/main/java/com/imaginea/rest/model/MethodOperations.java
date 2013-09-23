package com.imaginea.rest.model;

import java.util.List;

public class MethodOperations {
	
	

	
	
	private String method;
	private String type;
	private String nickName;
	private String produces;
	private List<MethodParameters> parameterList;
	private String returnType;
	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * @return the parameterList
	 */
	public List<MethodParameters> getParameterList() {
		return parameterList;
	}
	/**
	 * @param parameterList the parameterList to set
	 */
	public void setParameterList(List<MethodParameters> parameterList) {
		this.parameterList = parameterList;
	}
	
	/**
	 * @return the returnType
	 */
	public String getReturnType() {
		return returnType;
	}
	/**
	 * @param returnType the returnType to set
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	/**
	 * @return the produces
	 */
	public String getProduces() {
		return produces;
	}
	/**
	 * @param produces the produces to set
	 */
	public void setProduces(String produces) {
		this.produces = produces;
	}
	
	
	
	
	



}
