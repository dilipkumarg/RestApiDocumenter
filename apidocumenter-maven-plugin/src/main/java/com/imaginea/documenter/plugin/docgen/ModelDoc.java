package com.imaginea.documenter.plugin.docgen;

import java.util.Map.Entry;

import com.imaginea.documenter.core.model.ClassDetails;
import com.imaginea.documenter.core.model.ModelPropertyDiscriptor;

public class ModelDoc {
	private final ClassDetails classInfo;

	public ModelDoc(ClassDetails classInfo) {
		this.classInfo = classInfo;
	}

	private String getTableHeader() {
		StringBuilder sb = new StringBuilder();

		sb.append("<table>\n");
		sb.append("<tr>\n");
		sb.append("<th>name</th>\n");
		sb.append("<th>type</th>\n");
		sb.append("<th>description</th>\n");
		sb.append("</tr>\n");

		return sb.toString();
	}

	private String getRow(ModelPropertyDiscriptor desc) {
		StringBuilder sb = new StringBuilder();

		sb.append("<tr>\n");
		sb.append("<td>" + desc.getPropertyName() + "<td>\n");
		sb.append("<td>" + desc.getType() + "<td>\n");
		sb.append("<td>" + desc.getDescription() + "<td>\n");
		sb.append("</tr>\n");

		return sb.toString();
	}

	public String toMarkDown() {
		StringBuilder sb = new StringBuilder();

		sb.append("##<a name=" + classInfo.getId() + ">" + classInfo.getId()
				+ "</a>\n");
		sb.append(getTableHeader());

		for (Entry<String, ModelPropertyDiscriptor> key : classInfo
				.getProperties().entrySet()) {
			if (key.getValue() != null) {
				sb.append(getRow(key.getValue()));
			}
		}
		sb.append("</table>\n\n");

		return sb.toString();
	}
}
