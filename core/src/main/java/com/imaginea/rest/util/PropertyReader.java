package com.imaginea.rest.util;

import java.io.IOException;
import java.io.InputStream;
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
			synchronized (PropertyReader.class) { // 1
				if (instance == null) // 2
					instance = new PropertyReader(); // 3
			}
		}
		return instance;
	}

	public void init() throws IOException {

		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(FILE_NAME);
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
	}

	public String getProperty(String propName) {
		return appProps.getProperty(propName);
	}

}
