package com.imaginea.rest.documenter;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.imaginea.rest.delegate.ClassDocumenter;
import com.imaginea.rest.model.SwaggerInfo;
import com.imaginea.rest.util.JsonUtil;

@Path("/apidocs")
public class ApiDocumenter {
	private ClassDocumenter apiDoc;

	public ApiDocumenter() {
		apiDoc = new ClassDocumenter();
	}

	/**
	 * This is the base function for Api docs, it will fetch the list of all
	 * resources
	 * 
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getMetaInfo() {
		SwaggerInfo si = new SwaggerInfo();
		Gson gson = new Gson();
		return gson.toJson(si, SwaggerInfo.class);
	}

	/**
	 * It will returns the class information in json information
	 * 
	 * @param className
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@GET
	@Path("/{class}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getClassInfo(@PathParam("class") String className) throws IOException, ClassNotFoundException {
		// todo extract to class variable
		return JsonUtil.toJson(apiDoc.extractClassInfo(className)).toString();
	}
}
