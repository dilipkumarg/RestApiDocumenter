package com.imaginea.documenter.plugin.docgen;

import com.imaginea.documenter.core.model.MethodOperations;
import com.imaginea.documenter.core.model.MethodResponse;

public class Api {
	private final String index;
	private final String basePath;
	private final MethodResponse api;

	public Api(String index, String basePath, MethodResponse api) {
		this.api = api;
		this.index = index;
		this.basePath = basePath;
	}

	public String toMarkDown() {
		StringBuilder sb = new StringBuilder();
		sb.append("####" + index + ". `" + api.getPath() + "`\n");

		int i = 1;
		for (MethodOperations operation : api.getOperations()) {
			Operation operationDoc = new Operation(index + "." + i++,
					api.getPath(), operation, basePath);
			sb.append(operationDoc.toMarkDown() + "\n");
		}

		return sb.toString();
	}
}
