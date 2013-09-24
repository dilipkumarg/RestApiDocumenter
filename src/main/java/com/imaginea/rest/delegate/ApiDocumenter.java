/**
 * 
 */
package com.imaginea.rest.delegate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.imaginea.rest.model.ClassResponse;
import com.imaginea.rest.model.MethodOperations;
import com.imaginea.rest.model.MethodParameters;
import com.imaginea.rest.model.MethodsResponse;
import com.imaginea.rest.model.Models;
import com.imaginea.rest.model.ObjectsList;
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
		
		List<ObjectsList> objectslist= new ArrayList<ObjectsList>();
		for (AbstractSubResourceMethod srm : resource.getSubResourceMethods()) {
			MethodsResponse mResponse = new MethodsResponse();
			mResponse.setPath(uriPrefix + "/" + srm.getPath().getValue());

			MethodOperations mOperations = new MethodOperations();
			mOperations.setMethod(srm.getHttpMethod());
			mOperations.setNickName(srm.getMethod().getName());
			mOperations.setType(srm.getReturnType().getSimpleName());

			List<MethodParameters> params = new ArrayList<MethodParameters>();
			for (Parameter param : srm.getParameters()) {
				MethodParameters mParameters = new MethodParameters();
				mParameters.setName(param.getSourceName());
				mParameters.setParamType(param.getAnnotation().annotationType().getSimpleName());
				// we can decide required if it is a annonation
				
				mParameters.setRequired(param.getAnnotation().annotationType().isAnnotation());
				mParameters.setType(param.getParameterType().toString());
				params.add(mParameters);
			}
			
			Field[] fieldsInReturnType = srm.getReturnType().getFields();
			
			if(fieldsInReturnType.length>0){
				ObjectsList list= new ObjectsList();
				List<PropertyDiscriptor> propDescList= new ArrayList<PropertyDiscriptor>();
				list.setClassName(srm.getReturnType().getSimpleName());
				for(int i=0; i<fieldsInReturnType.length;i++){
					PropertyDiscriptor desc= new PropertyDiscriptor();
					desc.setPropertyName(fieldsInReturnType[i].getName());
					desc.setType(fieldsInReturnType[i].getType().getSimpleName());
					desc.setDescription(fieldsInReturnType[i].getName() +" should be of  "+fieldsInReturnType[i].getType().getSimpleName()+" type ");
					propDescList.add(desc);
				}
				list.setPropertiesList(propDescList);
				objectslist.add(list);
				
			}
			
			mOperations.setParameterList(params);
			mOperations.setReturnType(srm.getReturnType().getSimpleName());

			mResponse.setOperations(mOperations);
			methodResponseList.add(mResponse);
		}
		
		response.setResponseList(methodResponseList);
		response.setModelList(objectslist);

		return new Gson().toJson(response, ClassResponse.class);

	}

}
