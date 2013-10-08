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
	 * @parameter default-value="generated"
	 * 
	 */
	private String docOutDir;

	public void execute() throws MojoExecutionException, MojoFailureException {
		basePath = (basePath == null) ? "/rest/services" : basePath;

		try {
			this.classPaths = getClassPaths();
		} catch (IOException e2) {
			getLog().error(e2.getMessage());
			return;
		}
		FileDataCreation fileData;
		fileData = new FileDataCreation(basePath, classPaths, docOutDir);
		try {
			fileData.createData();
		} catch (ClassNotFoundException e) {
			getLog().error(e.getMessage());
		} catch (IOException e) {
			getLog().error(e.getMessage());
		}

	}

	private String[] getClassPaths() throws IOException {
		// String[] classPaths = new String[] { "WEB-INF/lib", "WEB-INF/classes"
		// };
		String[] classPaths = new String[] { "WEB-INF/classes", "WEB-INF/lib" };
		for (int i = 0; i < classPaths.length; i++) {
			classPaths[i] = new File(buildDir, finalName).getCanonicalPath() + File.separator
					+ classPaths[i];
		}
		return classPaths;
	}

	/*private String getProjectBuildDir() throws IOException {
		String dirName = buildDir.getCanonicalPath();
		if (finalName != null) {
			dirName = dirName + File.separator + finalName;
		} else {
			dirName = dirName + File.separator + artifactId + "-" + version;
		}
		return dirName;
	}*/

}
