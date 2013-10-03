package com.imaginea.rest;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.junit.Test;

import com.imaginea.rest.constants.RestApiConstants;
import com.imaginea.rest.delegate.ApiDocumenterDelegate;

public class TestApiDocumenterDelegate {

	String classString;
	String basePath;
	String totalNumberOfclasses;
	String pathNameToSearchInMap;
	ApiDocumenterDelegate apiDelegate;
	private static Map<String, String> pathJsonMap;
	
	
	public TestApiDocumenterDelegate() throws Exception {
		init();
		apiDelegate = new ApiDocumenterDelegate(basePath);

	}


	@Test
	public void testPreparePathJsonMap() throws ClassNotFoundException {
		String[] classArray = classString.split(",");
		Set<Class> allClasses = new HashSet<Class>();
		for (String className : classArray) {
			allClasses.add(Class.forName(className));
		}
		pathJsonMap = apiDelegate.preparePathJsonMap(allClasses);
		assertEquals("The size count should be  " + totalNumberOfclasses, pathJsonMap.size(),
						Integer.parseInt(totalNumberOfclasses));
	}

	@Test
	public void testGetClassInfoFromMap() {
		String str = pathJsonMap.get("/" + pathNameToSearchInMap);
		assertEquals("There should be a value for " + pathNameToSearchInMap + " in the map", true, str != null);

	}

	private void init() throws IOException {
		Properties appProps = new Properties();
		FileInputStream resourceAsStream = new FileInputStream(getClass().getResource(
						"/TestApiDoumenterConfig.properties").getPath());
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

		classString = appProps.getProperty("class.names");
		basePath = appProps.getProperty(RestApiConstants.BASE_PATH_URL);
		totalNumberOfclasses = appProps.getProperty("total.classes.count");
		pathNameToSearchInMap = appProps.getProperty("path.name.map.search");

	}

}
