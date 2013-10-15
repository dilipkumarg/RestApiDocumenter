package com.imaginea.documenter.plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Goal which creates api document from sources.
 * 
 * @goal document
 * 
 * @phase install
 * @configurator include-project-dependencies
 * @requiresDependencyResolution runtime
 */
public class DocumenterMojo extends AbstractMojo {

	/**
	 * Location of the file
	 * 
	 * @parameter default-value= "${project.build.directory}"
	 * 
	 */
	private File buildDir = new File("");

	/**
	 * @parameter expression="${project.build.finalName}"
	 */
	private String finalName;

	/**
	 * base path for resource
	 * 
	 * @parameter default-value="http://localhost:8080/api"
	 * @required
	 */
	private String basePath;

	/**
	 * Location to dump output files
	 * 
	 * @parameter default-value="restApidocs"
	 * 
	 */
	private String docOutDir;

	/**
	 * Locations to scan jar files
	 * 
	 * @parameter
	 */
	private List<String> includeJarFolders;

	String baseDir;

	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().debug("Base Path " + basePath);
		String[] classPaths = null;
		try {
			classPaths = getClassPaths();
		} catch (IOException e1) {
			getLog().error(e1.getMessage());
			return;
		}

		if (docOutDir.equals("restApidocs")) {
			docOutDir = baseDir + File.separator + docOutDir;
		}
		getLog().info("Html File will be created in " + docOutDir);
		FileDataCreation fileData = new FileDataCreation(basePath, classPaths, docOutDir);
		try {
			fileData.createData();
		} catch (ClassNotFoundException e) {
			getLog().error(e.getMessage());
		} catch (IOException e) {
			getLog().error(e.getMessage());
		}

	}

	private String[] getClassPaths() throws IOException {
		getLog().debug("Build Directory " + buildDir);
		// making baseDir as target/project_name/
		baseDir = new File(buildDir, finalName).getCanonicalPath();
		List<String> classPaths = new ArrayList<String>();
		// adding target/classes into the class paths
		classPaths.add(buildDir.getCanonicalPath() + File.separator + "classes");
		if (includeJarFolders != null) {
			for (String jarFolder : includeJarFolders) {
				getLog().debug(
						"Adding jar folder path " + jarFolder
								+ " to classpath to be scanned");
				classPaths.add(baseDir + File.separator + jarFolder);
			}
		}
		return classPaths.toArray(new String[] {});

	}

}
