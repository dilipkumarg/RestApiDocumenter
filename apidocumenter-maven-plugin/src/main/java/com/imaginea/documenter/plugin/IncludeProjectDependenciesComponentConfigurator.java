package com.imaginea.documenter.plugin;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecution;
import org.codehaus.classworlds.ClassRealm;
import org.codehaus.plexus.component.configurator.AbstractComponentConfigurator;
import org.codehaus.plexus.component.configurator.ComponentConfigurationException;
import org.codehaus.plexus.component.configurator.ConfigurationListener;
import org.codehaus.plexus.component.configurator.converters.composite.ObjectWithFieldsConverter;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluator;
import org.codehaus.plexus.configuration.PlexusConfiguration;
import org.codehaus.plexus.util.xml.Xpp3Dom;

/**
 * A custom ComponentConfigurator which adds the project's runtime classpath
 * elements to the
 * 
 * 
 * @plexus.component 
 *                   role="org.codehaus.plexus.component.configurator.ComponentConfigurator"
 *                   role-hint="include-project-dependencies"
 * @plexus.requirement role=
 *                     "org.codehaus.plexus.component.configurator.converters.lookup.ConverterLookup"
 *                     role-hint="default"
 */
public class IncludeProjectDependenciesComponentConfigurator extends
		AbstractComponentConfigurator {

	private final String JAR_CONFIG_ENTRY = "includeJarFolders";

	public void configureComponent(Object component,
			PlexusConfiguration configuration,
			ExpressionEvaluator expressionEvaluator, ClassRealm containerRealm,
			ConfigurationListener listener)
			throws ComponentConfigurationException {

		addProjectDependenciesToClassRealm(expressionEvaluator, containerRealm);

		ObjectWithFieldsConverter converter = new ObjectWithFieldsConverter();

		converter.processConfiguration(converterLookup, component,
				containerRealm.getClassLoader(), configuration,
				expressionEvaluator, listener);

	}

	@SuppressWarnings("unchecked")
	private void addProjectDependenciesToClassRealm(
			ExpressionEvaluator expressionEvaluator, ClassRealm containerRealm)
			throws ComponentConfigurationException {
		List<String> runtimeClasspathElements;
		try {
			// noinspection unchecked

			runtimeClasspathElements = (List<String>) expressionEvaluator
					.evaluate("${project.runtimeClasspathElements}");

		} catch (ExpressionEvaluationException e) {
			throw new ComponentConfigurationException(
					"There was a problem evaluating: ${project.runtimeClasspathElements}",
					e);
		}
		runtimeClasspathElements
				.addAll(retrieveCustomJars(expressionEvaluator));

		// Add the project dependencies to the ClassRealm
		final URL[] urls = buildURLs(runtimeClasspathElements);
		for (URL url : urls) {
			containerRealm.addConstituent(url);
		}
	}

	/**
	 * This function will read the configuration from the pom and returns the
	 * list of user specified jar folders
	 * 
	 * @param expressionEvaluator
	 * @return
	 * @throws ComponentConfigurationException
	 */
	private List<String> getJarFoldersFromConfig(
			ExpressionEvaluator expressionEvaluator)
			throws ComponentConfigurationException {
		List<String> dirs = new ArrayList<String>();
		MojoExecution mojoExec;
		try {
			mojoExec = (MojoExecution) expressionEvaluator.evaluate("${mojo}");
		} catch (ExpressionEvaluationException e) {
			throw new ComponentConfigurationException(
					"There was a problem evaluating: ${mojo}", e);
		}
		Xpp3Dom jarFolders = mojoExec.getConfiguration().getChild(
				JAR_CONFIG_ENTRY);
		// if there is no jar folder specified it will returns the empty list
		if (jarFolders != null) {
			for (int i = 0; i < jarFolders.getChildCount(); i++) {
				dirs.add(jarFolders.getChild(i).getValue());
			}
		}
		return dirs;
	}

	private File getProjectBuildRoot(ExpressionEvaluator expressionEvaluator)
			throws ComponentConfigurationException {
		String finalName = "";
		try {
			finalName = (String) expressionEvaluator
					.evaluate("${project.build.finalName}");

		} catch (ExpressionEvaluationException e) {
			throw new ComponentConfigurationException(
					"There was a problem evaluating: ${project.build.finalName}",
					e);
		}
		File baseDir = null;
		try {
			baseDir = new File(
					(String) expressionEvaluator
							.evaluate("${project.build.directory}"));
		} catch (ExpressionEvaluationException e) {
			throw new ComponentConfigurationException(
					"There was a problem evaluating: ${project.build.directory}",
					e);
		}
		return new File(baseDir, finalName);
	}

	private List<String> retrieveCustomJars(
			ExpressionEvaluator expressionEvaluator)
			throws ComponentConfigurationException {
		List<String> jarDirs = getJarFoldersFromConfig(expressionEvaluator);
		List<String> paths = new ArrayList<String>();
		if (jarDirs.size() > 0) {
			File baseDir = getProjectBuildRoot(expressionEvaluator);
			try {
				for (String jarDir : jarDirs) {
					paths.addAll(getJarsRecursively(new File(baseDir, jarDir)));
				}
			} catch (IOException e) {
				throw new ComponentConfigurationException(
						"There was a problem in setting classpath", e);
			}
		}
		return paths;
	}

	private List<String> getJarsRecursively(File root) throws IOException {
		List<String> jars = new ArrayList<String>();
		if (root.exists()) {
			if (root.isDirectory()) {
				for (File f : root.listFiles()) {
					jars.addAll(getJarsRecursively(f));
				}
			} else if (root.getName().endsWith(".jar")) {
				jars.add(root.getCanonicalPath());
			}

		}
		return jars;
	}

	private URL[] buildURLs(List<String> runtimeClasspathElements)
			throws ComponentConfigurationException {
		// Add the projects classes and dependencies
		List<URL> urls = new ArrayList<URL>(runtimeClasspathElements.size());
		for (String element : runtimeClasspathElements) {

			try {
				final URL url = new File(element).toURI().toURL();
				urls.add(url);

			} catch (MalformedURLException e) {
				throw new ComponentConfigurationException(
						"Unable to access project dependency: " + element, e);
			}
		}

		// Add the plugin's dependencies (so Trove stuff works if Trove isn't on
		return urls.toArray(new URL[urls.size()]);
	}

}
