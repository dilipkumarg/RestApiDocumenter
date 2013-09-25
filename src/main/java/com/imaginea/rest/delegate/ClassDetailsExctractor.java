package com.imaginea.rest.delegate;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.imaginea.rest.model.ClassDetails;
import com.imaginea.rest.model.ModelPropertyDiscriptor;
import com.sun.jersey.api.model.AbstractResource;
import com.sun.jersey.api.model.AbstractSubResourceMethod;

public class ClassDetailsExctractor {

	public Map<String, ClassDetails> extractClassDetails(
			AbstractResource absResource) {
		Map<String, ClassDetails> map = new HashMap<String, ClassDetails>();

		for (AbstractSubResourceMethod subResourceModel : absResource
				.getSubResourceMethods()) {
			Field[] fieldsInReturnType = subResourceModel.getReturnType()
					.getFields();
			if (fieldsInReturnType.length > 0) {
				ClassDetails detail = getClassDetails(subResourceModel
						.getReturnType().getSimpleName(), fieldsInReturnType);
				map.put(detail.getClassName(), detail);
			}
		}

		return map;
	}

	/**
	 * This method will return a {@link ClassDetails} object with properties set
	 * for the return types.
	 * 
	 * @param objectslist
	 * @param subResourceModel
	 * @param fieldsInReturnType
	 */
	private ClassDetails getClassDetails(String className,
			Field[] fieldsInReturnType) {
		ClassDetails classDetail = new ClassDetails();
		classDetail.setClassName(className);
		Map<String, ModelPropertyDiscriptor> modelPropertyMap = new HashMap<String, ModelPropertyDiscriptor>();
		
		for (int i = 0; i < fieldsInReturnType.length; i++) {
			ModelPropertyDiscriptor desc = getFieldsDescription(fieldsInReturnType[i]);
			modelPropertyMap.put(fieldsInReturnType[i].getName(), desc);
		}
		classDetail.setPropertiesList(modelPropertyMap);
		return classDetail;

	}

	/**
	 * This method return a {@link ModelPropertyDiscriptor} object defining a
	 * given Field.
	 * 
	 * @param fieldsInReturnType
	 * @param i
	 * @return
	 */
	private ModelPropertyDiscriptor getFieldsDescription(Field field) {
		ModelPropertyDiscriptor desc = new ModelPropertyDiscriptor();
		desc.setPropertyName(field.getName());
		desc.setType(field.getType().getSimpleName());
		desc.setDescription(field.getName() + " should be of  "
				+ field.getType().getSimpleName() + " type ");
		return desc;
	}
}
