package com.imaginea.documenter.plugin.docgen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.imaginea.documenter.core.model.ClassDetails;
import com.imaginea.documenter.core.model.ClassResponseEntity;

public class ApiDocumenter {

	private final List<ClassResponseEntity> classRes;
	private final String basePath;

	public ApiDocumenter(List<ClassResponseEntity> classesRes, String basePath) {
		this.classRes = classesRes;
		this.basePath = basePath;
	}

	private String getHeader() {
		StringBuilder sb = new StringBuilder();

		sb.append("<!DOCTYPE html>\n");
		sb.append("<html>\n");
		sb.append("<title>API Document</title>\n");
		sb.append("<xmp theme='united' style='display:none;'>\n");

		return sb.toString();
	}

	private String getFooter() {
		StringBuilder sb = new StringBuilder();

		sb.append("</xmp>\n");
		sb.append("<script src='http://strapdownjs.com/v/0.2/strapdown.js'></script>\n");
		sb.append("</html>");

		return sb.toString();
	}

	private Map<String, ClassDetails> getUniqueModels(
			List<ClassResponseEntity> classRes) {
		Map<String, ClassDetails> models = new HashMap<String, ClassDetails>();
		for (ClassResponseEntity clsRes : classRes) {
			models.putAll(clsRes.getModels());
		}
		return models;
	}

	public String toMarkDown() {
		StringBuilder sb = new StringBuilder();
		sb.append(getHeader());

		sb.append("#API Document\n");
		sb.append("## Base Path:" + basePath + "\n");

		sb.append("##Resources\n");
		int i = 1;
		for (ClassResponseEntity clsRes : classRes) {
			sb.append(new ApiDocument(i++, basePath, clsRes).toMarkDown());
		}

		sb.append("##Data Types\n");
		for (Entry<String, ClassDetails> key : getUniqueModels(classRes)
				.entrySet()) {
			sb.append(new ModelDoc(key.getValue()).toMarkDown());
		}

		sb.append(getFooter());
		return sb.toString();
	}
}
