package com.imaginea.documenter.plugin.docgen;

import com.imaginea.documenter.core.model.MethodOperations;
import com.imaginea.documenter.core.model.MethodParameters;

public class Operation {

	private final String index;
	private final MethodOperations operation;
	private final String path;
	private final String basePath;

	public Operation(String index, String path, MethodOperations operation,
			String basePath) {
		this.index = index;
		this.operation = operation;
		this.path = path;
		this.basePath = basePath;
	}

	public String toMarkDown() {
		StringBuilder sb = new StringBuilder();
		sb.append("#####" + index + ". " + operation.getNickname() + "\n");
		sb.append("**" + operation.getMethod() + "**  `" + path + "` \n");

		sb.append("###### URL\n");
		sb.append(basePath + path + "\n");

		sb.append("######Parameters\n");
		sb.append(getParams());

		sb.append("######Response\n");
		sb.append("[" + operation.getResponseClass() + "](#"
				+ operation.getResponseClass() + ")\n");

		return sb.toString();
	}

	private String getTableHeader() {
		StringBuilder sb = new StringBuilder();
		sb.append("<table>\n");
		sb.append("<tr>\n");
		sb.append("<th>Parameter</th>\n");
		sb.append("<th>Required</th>\n");
		sb.append("<th>Type</th>\n");
		sb.append("<th>Data Type</th>\n");
		sb.append("</tr>\n");
		return sb.toString();
	}

	private String getRowForParam(MethodParameters param) {
		StringBuilder sb = new StringBuilder();
		sb.append("<tr>\n");

		sb.append("<th>" + param.getName() + "</th>\n");
		sb.append("<th>" + param.isRequired() + "</th>\n");
		sb.append("<th>" + param.getParamType() + "</th>\n");
		sb.append("<th>" + param.getType() + "</th>\n");

		sb.append("</tr>\n");
		return sb.toString();
	}

	private String getParams() {
		StringBuilder sb = new StringBuilder();
		sb.append(getTableHeader());

		for (MethodParameters param : operation.getParameters()) {
			sb.append(getRowForParam(param));
		}

		sb.append("</table>\n\n");

		return sb.toString();
	}
}
