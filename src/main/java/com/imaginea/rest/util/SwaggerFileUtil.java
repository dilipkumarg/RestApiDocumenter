/**
 * 
 */
package com.imaginea.rest.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import javax.ws.rs.Path;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

import com.sun.jersey.spi.resource.Singleton;

/**
 * @author sandeep-t
 * 
 */


public class SwaggerFileUtil {

	public static void writeClassJsonMaptoFile(String key, String Value, boolean writeToexistingFile, String fileName)
					throws IOException {
		File file = new File(fileName);
		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
		// true = append file
		FileWriter fileWritter = new FileWriter(file.getName(), writeToexistingFile);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write(key + "=" + Value +"\n");
		bufferWritter.close();
	}
	
	
	public static Set<Class<? extends Object>> getallClasses(String basePackage){
		Reflections reflections = new Reflections(basePackage);
		Set<Class<? extends Object>> allClasses = reflections.getTypesAnnotatedWith(Path.class);
		return allClasses;
	}
	
	/*public static void main(String[] args) {
		Reflections reflections = new Reflections(".");
		Set<Class<? extends Object>> allClasses = reflections.getTypesAnnotatedWith(Path.class);
		System.out.println("ALL "+allClasses);
	}*/
	
	
}
