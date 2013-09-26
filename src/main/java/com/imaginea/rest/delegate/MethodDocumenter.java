package com.imaginea.rest.delegate;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import com.imaginea.rest.model.MethodOperations;
import com.imaginea.rest.model.MethodParameters;
import com.imaginea.rest.model.MethodResponse;
import com.sun.jersey.api.model.AbstractResource;
import com.sun.jersey.api.model.AbstractSubResourceMethod;
import com.sun.jersey.api.model.Parameter;

public class MethodDocumenter {

	public List<MethodResponse> extractMethodsInfo(AbstractResource absResource) {
		List<MethodResponse> methodResponseList = new ArrayList<MethodResponse>();

		for (AbstractSubResourceMethod subResourceModel : absResource.getSubResourceMethods()) {
			MethodResponse mResponse = new MethodResponse();
			// adding resource path to the method path
			mResponse.setPath(absResource.getPath().getValue() + subResourceModel.getPath().getValue());
			mResponse.setOperations(extractMethodOperationDetails(subResourceModel));
			methodResponseList.add(mResponse);
		}
		return methodResponseList;
	}

	private List<MethodOperations> extractMethodOperationDetails(AbstractSubResourceMethod subResMethod) {
		// swagger-ui expecting operations as array format so we are sending as
		// list, even though it not necessary
		List<MethodOperations> mOpList = new ArrayList<MethodOperations>();
		mOpList.add(getMethodOperationDetails(subResMethod));
		return mOpList;
	}

	/**
	 * @param subResourceModel
	 * @param mOperations
	 * @param paramsList
	 */
	private MethodOperations getMethodOperationDetails(AbstractSubResourceMethod subResourceModel) {
		MethodOperations mOperations = new MethodOperations();
		// extracting produces
		if (!subResourceModel.getSupportedOutputTypes().isEmpty()) {
			mOperations.setProduces(extractProduces(subResourceModel));
		}// extracting consumes
		if (!subResourceModel.getSupportedInputTypes().isEmpty()) {
			mOperations.setConsumes(extractConsumes(subResourceModel));
		}
		// extracting method properties
		mOperations.setMethod(subResourceModel.getHttpMethod());
		mOperations.setNickname(subResourceModel.getMethod().getName());
		mOperations.setType(subResourceModel.getReturnType().getSimpleName());
		mOperations.setParameters(extractMethodParameters(subResourceModel));
		mOperations.setResponseClass(subResourceModel.getReturnType().getSimpleName());
		return mOperations;
	}

	/**
	 * returns the produce types for method
	 * 
	 * @param subResMethod
	 * @return
	 */
	private List<String> extractProduces(AbstractSubResourceMethod subResMethod) {
		List<String> outTypes = new ArrayList<String>();
		for (MediaType type : subResMethod.getSupportedOutputTypes()) {
			outTypes.add(type.getType() + "/" + type.getSubtype());
		}
		return outTypes;
	}

	/**
	 * returns consumes part for method
	 * 
	 * @param subResMethod
	 * @return
	 */
	private List<String> extractConsumes(AbstractSubResourceMethod subResMethod) {
		List<String> inTypes = new ArrayList<String>();
		for (MediaType type : subResMethod.getSupportedInputTypes()) {
			inTypes.add(type.getType() + "/" + type.getSubtype());
		}
		return inTypes;
	}

	/**
	 * it will returns list of methods and their properties
	 * 
	 * @param subResMethod
	 * @return
	 */
	private List<MethodParameters> extractMethodParameters(AbstractSubResourceMethod subResMethod) {

		List<MethodParameters> paramsList = new ArrayList<MethodParameters>();
		for (Parameter param : subResMethod.getParameters()) {
			paramsList.add(getMethodParameters(param));
		}
		return paramsList;

	}

	/**
	 * This method will return a {@link MethodParameters} object for defining
	 * the given parameter.
	 * 
	 * @param params
	 * @param param
	 */
	private MethodParameters getMethodParameters(Parameter param) {
		MethodParameters mParameters = new MethodParameters();
		mParameters.setParamType(param.getSource().name().toLowerCase());
		mParameters.setType(param.getParameterClass().getSimpleName());
		// if annotation is not present means it is a body param
		if (param.getAnnotation() != null) {
			// we can decide required if it is a annonation
			mParameters.setRequired(param.getAnnotation().annotationType().isAnnotation());
			mParameters.setName(param.getSourceName());
		}
		return mParameters;
	}
}
