package com.imaginea.rest.documenter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.imaginea.rest.constants.RestApiConstants;
import com.imaginea.rest.delegate.ApiDocumenterDelegate;
import com.imaginea.rest.util.RestApiClassUtil;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/apidocs")
public class ApiDocumenter {

	private final Logger logger = Logger.getLogger(ApiDocumenter.class);
	private Gson gson;
	private ApiDocumenterDelegate apiDelegate;

	@Context
	ServletContext servletContext;

	String basePath;

	public ApiDocumenter() throws IOException {
		init();
		gson = new Gson();
		apiDelegate = new ApiDocumenterDelegate(basePath);
	}

	/**
	 * This is the base function for Api docs, it will fetch the list of all
	 * resources
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getMetaInfo() throws ClassNotFoundException {
		String[] classPaths = new String[] {"D:/STS-Workspace/RestApiDocumenter/src/main/webapp/WEB-INF/lib","D:/STS-Workspace/RestApiDocumenter/src/main/webapp/WEB-INF/classes"};
		logger.info("Class Paths going to be scanned for ApiDocumenter " + classPaths);
		Set<Class> allClasses = RestApiClassUtil.getPathAnnotatedClasses(classPaths);
		apiDelegate.preparePathJsonMap(allClasses);
		return gson.toJson(apiDelegate.getListClassMetaData());
	}
	//com.sun.jersey.api.core.servlet.WebAppResourceConfig
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
	public String getClassInfo(@PathParam("class") String className) throws IOException, ClassNotFoundException {
		logger.debug("Going to get JSON info for the ClassName  " + className);
		return apiDelegate.getClassInfoFromMap(className);
	}

	private void init() throws IOException {
		Properties appProps = new Properties();
		InputStream resourceAsStream = this.getClass().getClassLoader()
						.getResourceAsStream("ApiDoumenterConfig.properties");
		if (resourceAsStream != null) {
			try {
				appProps.load(resourceAsStream);
			}
			finally {
				try {
					resourceAsStream.close();
				}
				catch (Exception ignore) {
				}
			}
		}
		else
			throw new IOException("Resource with name ApiDocumenterConfig.properties not found in the classpath.");

		logger.info("Property file SwaggerConfig.properties loaded sucessfully");
		basePath = appProps.getProperty(RestApiConstants.BASE_PATH_URL);
		logger.debug("RestAPI base path url " + basePath);
	}

}
