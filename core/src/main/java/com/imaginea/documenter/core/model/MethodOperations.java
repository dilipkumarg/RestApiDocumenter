package com.imaginea.documenter.core.model;

import java.util.List;

public class MethodOperations {

	private String method;
	private String type;
	private String nickname;
	private List<String> produces;
	private List<String> consumes;
	private List<MethodParameters> parameters;
	private String responseClass;

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method
	 *            the method to set
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
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public List<MethodParameters> getParameters() {
		return parameters;
	}

	public void setParameters(List<MethodParameters> parameters) {
		this.parameters = parameters;
	}

	public String getResponseClass() {
		return responseClass;
	}

	public void setResponseClass(String responseClass) {
		this.responseClass = responseClass;
	}

	public List<String> getProduces() {
		return produces;
	}

	public void setProduces(List<String> produces) {
		this.produces = produces;
	}

	/**
	 * @return the consumes
	 */
	public List<String> getConsumes() {
		return consumes;
	}

	/**
	 * @param consumes
	 *            the consumes to set
	 */
	public void setConsumes(List<String> consumes) {
		this.consumes = consumes;
	}

}
