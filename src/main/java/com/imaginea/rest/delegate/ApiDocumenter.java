/**
 * 
 */
package com.imaginea.rest.delegate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.imaginea.rest.model.ClassDetails;
import com.imaginea.rest.model.ClassResponse;
import com.imaginea.rest.model.MethodOperations;
import com.imaginea.rest.model.MethodParameters;
import com.imaginea.rest.model.MethodsResponse;
import com.imaginea.rest.model.PropertyDiscriptor;
import com.sun.jersey.api.model.AbstractResource;
import com.sun.jersey.api.model.AbstractSubResourceMethod;
import com.sun.jersey.api.model.Parameter;
import com.sun.jersey.server.impl.modelapi.annotation.IntrospectionModeller;

/**
 * @author sandeep-t
 * @param <T>
 * 
 */
public class ApiDocumenter {

	public String getJSONResponse(Class className) {

		AbstractResource resource = IntrospectionModeller.createResource(className);

		ClassResponse response = new ClassResponse();

		response.setResourcePath(resource.getPath().getValue());

		String uriPrefix = resource.getPath().getValue();

		List<MethodsResponse> methodResponseList = new ArrayList<MethodsResponse>();

		List<ClassDetails> classDetailList = null;
		for (AbstractSubResourceMethod srm : resource.getSubResourceMethods()) {
			MethodsResponse mResponse = new MethodsResponse();
			MethodOperations mOperations =null;
			mResponse.setPath(uriPrefix + "/" + srm.getPath().getValue());
			List<MethodParameters> paramsList = null;
			for (Parameter param : srm.getParameters()) {
				paramsList = prepareParameterList(param);
			}

			Field[] fieldsInReturnType = srm.getReturnType().getFields();

			if (fieldsInReturnType.length > 0) {
				classDetailList = prepareModelList(srm, fieldsInReturnType);

			}
			mOperations=getMethodOperationsList(srm, paramsList);
			mResponse.setOperations(mOperations);
			methodResponseList.add(mResponse);
		}

		response.setResponseList(methodResponseList);
		response.setModelList(classDetailList);

		return new Gson().toJson(response, ClassResponse.class);

	}
	
	

	/**
	 * @param srm
	 * @param mOperations
	 * @param paramsList
	 */
	private MethodOperations getMethodOperationsList(AbstractSubResourceMethod srm, 
					List<MethodParameters> paramsList) {
		MethodOperations mOperations = new MethodOperations();
		mOperations.setMethod(srm.getHttpMethod());
		mOperations.setNickName(srm.getMethod().getName());
		mOperations.setType(srm.getReturnType().getSimpleName());
		mOperations.setParameterList(paramsList);
		mOperations.setReturnType(srm.getReturnType().getSimpleName());
		return mOperations;
	}

	/**
	 * @param params
	 * @param param
	 */
	private List<MethodParameters> prepareParameterList(Parameter param) {
		List<MethodParameters> params = new ArrayList<MethodParameters>();
		MethodParameters mParameters = new MethodParameters();
		mParameters.setParamType(param.getSource().name());
		mParameters.setType(param.getParameterClass().getSimpleName());
		// if annotation is not present means it is a body param
		if (param.getAnnotation() != null) {
			// we can decide required if it is a annonation
			mParameters.setRequired(param.getAnnotation()
					.annotationType().isAnnotation());
			mParameters.setName(param.getSourceName());
		}

		params.add(mParameters);
		return params;
	}

	/**
	 * @param objectslist
	 * @param srm
	 * @param fieldsInReturnType
	 */
	private List<ClassDetails> prepareModelList(AbstractSubResourceMethod srm, Field[] fieldsInReturnType) {
		List<ClassDetails> objectslist = new ArrayList<ClassDetails>();
		ClassDetails classDetail = new ClassDetails();
		List<PropertyDiscriptor> propDescList = new ArrayList<PropertyDiscriptor>();
		classDetail.setClassName(srm.getReturnType().getSimpleName());
		for (int i = 0; i < fieldsInReturnType.length; i++) {
			PropertyDiscriptor desc = new PropertyDiscriptor();
			desc.setPropertyName(fieldsInReturnType[i].getName());
			desc.setType(fieldsInReturnType[i].getType().getSimpleName());
			desc.setDescription(fieldsInReturnType[i].getName() + " should be of  "
							+ fieldsInReturnType[i].getType().getSimpleName() + " type ");
			propDescList.add(desc);
		}
		classDetail.setPropertiesList(propDescList);
		objectslist.add(classDetail);
		return objectslist;
	}

}
