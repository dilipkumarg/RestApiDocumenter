package com.imaginea.documenter.plugin.docgen;

import com.imaginea.documenter.core.model.ClassResponseEntity;
import com.imaginea.documenter.core.model.MethodResponse;

public class ApiDocument {
	private final int index;
	private final String basePath;
	private final ClassResponseEntity classRes;

	public ApiDocument(int index, String basePath, ClassResponseEntity classRes) {
		this.index = index;
		this.classRes = classRes;
		this.basePath = basePath;
	}

	public String toMarkDown() {
		StringBuilder sb = new StringBuilder();
		sb.append("###" + this.index + ". " + this.classRes.getResourcePath()
				+ "\n");
		sb.append("Overview" + "\n");

		int i = 1;
		for (MethodResponse api : this.classRes.getApis()) {
			sb.append(new Api(index + "." + i++, basePath, api).toMarkDown() + "\n");
		}
		return sb.toString();
	}

}
