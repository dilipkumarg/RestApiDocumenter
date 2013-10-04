package com.imaginea.rest;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import com.imaginea.documenter.core.constants.RestApiConstants;
import com.imaginea.documenter.core.delegate.ClassDocumenter;
import com.imaginea.documenter.core.model.ClassResponseEntity;

public class ClassDocumenterTest {

	
	String basePath;
	String classString;
	private ClassDocumenter clsDocumenter;
	
	public ClassDocumenterTest() throws IOException {
		init();
		clsDocumenter= new ClassDocumenter(basePath);
	}
	
	@Test
	public void testExtractClassInfo() throws IOException, ClassNotFoundException {
		String[] classArray = classString.split(",");
		ClassResponseEntity responseEntity=clsDocumenter.extractClassInfo(Class.forName(classArray[0]));
		assertEquals("There should be a response for the class "+classArray[0],true,responseEntity!=null);
		
	}
	
	
	private void init() throws IOException {
		Properties appProps = new Properties();
		String path = getClass().getResource(
						"/TestApiDoumenterConfig.properties").getPath();
		FileInputStream resourceAsStream = new FileInputStream(path);
		if (path!=null && resourceAsStream != null) {
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
			basePath = appProps.getProperty(RestApiConstants.BASE_PATH_URL);
			classString = appProps.getProperty("class.names");

	}

}
