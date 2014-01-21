package com.imaginea.documenter.core.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public final class PropertyReader {

	private static PropertyReader instance;
	Properties appProps;
	final String FILE_NAME = "ApiDoumenterConfig.properties";

	private PropertyReader() throws IOException {
		appProps = new Properties();
		init();
	}

	public static PropertyReader getInstance() throws IOException {
		if (instance == null) {
			synchronized (PropertyReader.class) {
				if (instance == null)
					instance = new PropertyReader();
			}
		}
		return instance;
	}

	public void init() throws IOException {
		URL propUrl = this.getClass().getClassLoader().getResource(FILE_NAME);
		InputStream resourceAsStream = null;
		// trying to load properties file from the resources.
		if (propUrl != null) {
			try {
				resourceAsStream = new FileInputStream(propUrl.getPath());
			} catch (IOException e) {
				// even though custom prop file is not there, but still resource
				// url contains jar files prop file address.
				resourceAsStream = getPropFileFromJar();
			}
		} else { // if no properties file is there, then we are loading default
					// properties from the jar file.
			resourceAsStream = getPropFileFromJar();
		}
		if (resourceAsStream != null) {
			try {
				appProps.load(resourceAsStream);
			} finally {
				try {
					resourceAsStream.close();
				} catch (Exception ignore) {
				}
			}
		}
	}

	private InputStream getPropFileFromJar() {
		return this.getClass().getClassLoader().getResourceAsStream(FILE_NAME);
	}

	public String getProperty(String propName) {
		return appProps.getProperty(propName);
	}

}
