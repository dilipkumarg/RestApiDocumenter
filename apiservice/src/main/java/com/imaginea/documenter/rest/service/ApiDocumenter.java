package com.imaginea.documenter.rest.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.imaginea.documenter.core.constants.RestApiConstants;
import com.imaginea.documenter.core.util.PropertyReader;
import com.imaginea.documenter.rest.documenter.impl.WebDocumenterDelegate;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/apidocs")
public class ApiDocumenter {

	private final Logger logger = Logger.getLogger(ApiDocumenter.class);
	private Gson gson;
	private WebDocumenterDelegate apiDelegate;

	@Context
	ServletContext servletContext;

	public ApiDocumenter() {
		gson = new Gson();
	}

	/**
	 * This is the base function for Api docs, it will fetch the list of all
	 * resources
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getMetaInfo() throws ClassNotFoundException, IOException {
		apiDelegate = new WebDocumenterDelegate(PropertyReader.getInstance()
				.getProperty(RestApiConstants.BASE_PATH_URL), getClassPaths());
		apiDelegate.createData();
		return gson.toJson(apiDelegate.getListClassMetaData());
	}

	private String[] getClassPaths() throws IOException {
		String classPath = PropertyReader.getInstance().getProperty(
				RestApiConstants.CLASSPATHS);
		String[] classPaths = null;
		if (classPath == null) {
			classPaths = new String[] { "/WEB-INF/lib", "/WEB-INF/classes" };
		} else {
			classPaths = classPath.split(",");
		}
		return fixClassPaths(classPaths);
	}

	private String[] fixClassPaths(String[] classPaths) {
		List<String> fPaths = new ArrayList<String>();
		for (int i = 0; i < classPaths.length; i++) {
			String path = servletContext.getRealPath(classPaths[i]);
			if (path != null) {
				fPaths.add(new File(path).getAbsolutePath());
			}
		}
		return fPaths.toArray(new String[] {});
	}

	// com.sun.jersey.api.core.servlet.WebAppResourceConfig
	/**
	 * It will returns the class information in json information
	 * 
	 * @param className
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@GET
	@Path("/{class: .*}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getClassInfo(@PathParam("class") String className)
			throws IOException, ClassNotFoundException {
		if (logger.isDebugEnabled()) {
			logger.debug("Going to get JSON info for the ClassName  "
					+ className);
		}
		return apiDelegate.getClassInfoFromMap(className);
	}

}
