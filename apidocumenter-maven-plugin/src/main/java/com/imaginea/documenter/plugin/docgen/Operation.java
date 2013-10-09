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
		sb.append("\t" + basePath + path + "\n");

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
		sb.append("\t<tr>\n");
		sb.append("\t\t<th>Parameter</th>\n");
		sb.append("\t\t<th>Required</th>\n");
		sb.append("\t\t<th>Type</th>\n");
		sb.append("\t\t\t<th>Data Type</th>\n");
		sb.append("\t</tr>\n");
		return sb.toString();
	}

	private String getRowForParam(MethodParameters param) {
		StringBuilder sb = new StringBuilder();
		sb.append("\t<tr>\n");

		sb.append("\t\t<td>" + param.getName() + "</td>\n");
		sb.append("\t\t<td>" + param.isRequired() + "</td>\n");
		sb.append("\t\t<td>" + param.getParamType() + "</td>\n");
		sb.append("\t\t<td>" + param.getType() + "</td>\n");

		sb.append("\t</tr>\n");
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
