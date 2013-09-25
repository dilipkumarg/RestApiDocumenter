/**
 * 
 */
package com.imaginea.rest.delegate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import com.imaginea.rest.model.ClassDetails;
import com.imaginea.rest.model.ClassResponseEntity;
import com.imaginea.rest.model.MethodOperations;
import com.imaginea.rest.model.MethodParameters;
import com.imaginea.rest.model.MethodResponse;
import com.imaginea.rest.model.ModelPropertyDiscriptor;
import com.imaginea.rest.util.JsonUtil;
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

		AbstractResource absResponse = IntrospectionModeller.createResource(className);

		ClassResponseEntity response = new ClassResponseEntity();

		response.setResourcePath(absResponse.getPath().getValue());

		List<MethodResponse> methodResponseList = new ArrayList<MethodResponse>();

	
		Map<String,ClassDetails> map= new HashMap<String,ClassDetails>();
		
		for (AbstractSubResourceMethod subResourceModel : absResponse.getSubResourceMethods()) {
	
			methodResponseList.add(getMethodResponse(absResponse, subResourceModel));
			Field[] fieldsInReturnType = subResourceModel.getReturnType().getFields();

			if (fieldsInReturnType.length > 0) {
				
				ClassDetails detail=getClassDetails(subResourceModel.getReturnType().getSimpleName(), fieldsInReturnType);
				map.put(detail.getClassName(), detail);
			}
		}
		response.setResponseList(methodResponseList);
		response.setModelList(map);
	
		return JsonUtil.toJsonString(response).toString();
	}

	/**
	 * @param absResponse
	 * @param methodResponseList
	 * @param classDetailList
	 * @param subResourceModel
	 */
	private MethodResponse getMethodResponse(AbstractResource absResponse,
					 AbstractSubResourceMethod subResourceModel) {
		MethodResponse mResponse = new MethodResponse();
		MethodOperations mOperations = null;
		mResponse.setPath(absResponse.getPath().getValue() + "/" + subResourceModel.getPath().getValue());
		List<MethodParameters> paramsList = new ArrayList<MethodParameters>();

		for (Parameter param : subResourceModel.getParameters()) {
			paramsList.add(getMethodParameters(param));
		}
		
		mOperations = getMethodOperationDetails(subResourceModel, paramsList);
		mResponse.setOperations(mOperations);
		return mResponse;
		
	}

	/**
	 * @param subResourceModel
	 * @param mOperations
	 * @param paramsList
	 */
	private MethodOperations getMethodOperationDetails(AbstractSubResourceMethod subResourceModel, List<MethodParameters> paramsList) {
		MethodOperations mOperations = new MethodOperations();

		if (!subResourceModel.getSupportedOutputTypes().isEmpty()) {
			List<String> outTypes = new ArrayList<String>();
			for (MediaType type : subResourceModel.getSupportedOutputTypes()) {
				outTypes.add(type.getType() + "/" + type.getSubtype());
			}
			mOperations.setProduces(outTypes);
		}

		if (!subResourceModel.getSupportedInputTypes().isEmpty()) {
			List<String> inTypes = new ArrayList<String>();
			for (MediaType type : subResourceModel.getSupportedInputTypes()) {
				inTypes.add(type.getType() + "/" + type.getSubtype());
			}
			mOperations.setConsumes(inTypes);
		}
		mOperations.setMethod(subResourceModel.getHttpMethod());
		mOperations.setNickName(subResourceModel.getMethod().getName());
		mOperations.setType(subResourceModel.getReturnType().getSimpleName());
		mOperations.setParameterList(paramsList);
		mOperations.setReturnType(subResourceModel.getReturnType().getSimpleName());
		return mOperations;
	}

	/**
	 * This method will return a {@link MethodParameters} object for defining the given parameter.
	 * @param params
	 * @param param
	 */
	private MethodParameters getMethodParameters(Parameter param) {
	
		MethodParameters mParameters = new MethodParameters();
		mParameters.setParamType(param.getSource().name());
		mParameters.setType(param.getParameterClass().getSimpleName());
		// if annotation is not present means it is a body param
		if (param.getAnnotation() != null) {
			// we can decide required if it is a annonation
			mParameters.setRequired(param.getAnnotation().annotationType().isAnnotation());
			mParameters.setName(param.getSourceName());
		}
		return mParameters;
	}

	/**
	 * This method will return a {@link ClassDetails} object with properties set for the return types.
	 * @param objectslist
	 * @param subResourceModel
	 * @param fieldsInReturnType
	 */
	private ClassDetails getClassDetails(String className, Field[] fieldsInReturnType) {
	
		ClassDetails classDetail = new ClassDetails();
	
		classDetail.setClassName(className);
		Map<String,ModelPropertyDiscriptor> modelPropertyMap= new HashMap<String,ModelPropertyDiscriptor>();
		for (int i = 0; i < fieldsInReturnType.length; i++) {

			ModelPropertyDiscriptor desc = getFieldsDescription(fieldsInReturnType[i]);
			
			modelPropertyMap.put(fieldsInReturnType[i].getName(), desc);
			
		}
		classDetail.setPropertiesList(modelPropertyMap);
		return classDetail;
		
	}

	/**
	 * This method return a {@link ModelPropertyDiscriptor} object defining a given Field.
	 * @param fieldsInReturnType
	 * @param i
	 * @return
	 */
	private ModelPropertyDiscriptor getFieldsDescription(Field field) {
		
		ModelPropertyDiscriptor desc = new ModelPropertyDiscriptor();
		desc.setPropertyName(field.getName());
		desc.setType(field.getType().getSimpleName());
		desc.setDescription(field.getName() + " should be of  "+field.getType().getSimpleName() + " type ");
		
		return desc;
	}

}
