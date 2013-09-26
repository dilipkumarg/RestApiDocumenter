/**
 * 
 */
package com.imaginea.rest.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
}
