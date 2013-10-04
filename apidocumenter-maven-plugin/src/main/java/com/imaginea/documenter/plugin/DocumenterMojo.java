package com.imaginea.documenter.plugin;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Goal which creates api document from sources.
 * 
 * @goal document
 * 
 * @phase compile
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
	 * @parameter
	 * @required
	 */
	private String basePath;

	/**
	 * @parameter expression="${project.artifactId}"
	 */
	private String artifactId;
	/**
	 * @parameter expression="${project.version}"
	 */
	private String version;

	/**
	 * Location of the file
	 * 
	 * @parameter
	 * 
	 */
	private String[] classPaths;

	/**
	 * Location to dump output files
	 * 
	 * @parameter
	 * 
	 */
	private String docOutDir;

	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			docOutDir = (docOutDir == null) ? getProjectBuildDir() : docOutDir;
			basePath = (basePath == null) ? "/rest/services" : basePath;
		} catch (IOException e) {
			getLog().error(e.getMessage());
			return;
		}
		this.classPaths = getClassPaths();
		FileDataCreation fileData = new FileDataCreation(basePath, classPaths, docOutDir);
		try {
			fileData.createData();
		} catch (ClassNotFoundException e) {
			getLog().error(e.getMessage());
		} catch (IOException e) {
			getLog().error(e.getMessage());
		}

	}

	private String[] getClassPaths() {
		String[] classPaths = new String[] { "WEB-INF/lib", "WEB-INF/classes" };
		for (int i = 0; i < classPaths.length; i++) {
			classPaths[i] = docOutDir + File.separator + classPaths[i];
		}
		return classPaths;
	}

	private String getProjectBuildDir() throws IOException {
		String dirName = buildDir.getCanonicalPath();
		if (finalName != null) {
			dirName = dirName + File.separator + finalName;
		} else {
			dirName = dirName + File.separator + artifactId + "-" + version;
		}
		return dirName;
	}

}
