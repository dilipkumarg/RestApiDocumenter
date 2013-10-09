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

	private void addProjectDependenciesToClassRealm(
			ExpressionEvaluator expressionEvaluator, ClassRealm containerRealm)
			throws ComponentConfigurationException {
		List<String> runtimeClasspathElements;
		try {
			// noinspection unchecked
			/*
			 * runtimeClasspathElements = (List<String>) expressionEvaluator
			 * .evaluate("${project.runtimeClasspathElements}");
			 */
			runtimeClasspathElements = retrieveCustomJars(expressionEvaluator);
			MojoExecution mojoExec = (MojoExecution) expressionEvaluator
					.evaluate("${mojo}");
			Xpp3Dom attributes = mojoExec.getConfiguration();
			System.out.println(attributes.getChild("basePath").getValue());

		} catch (ExpressionEvaluationException e) {
			throw new ComponentConfigurationException(
					"There was a problem evaluating: ${project.runtimeClasspathElements}",
					e);
		}

		// Add the project dependencies to the ClassRealm
		final URL[] urls = buildURLs(runtimeClasspathElements);
		for (URL url : urls) {
			containerRealm.addConstituent(url);
		}
	}

	private File getProjectBuildRoot(ExpressionEvaluator expressionEvaluator)
			throws ComponentConfigurationException {
		String finalName = "";
		try {
			finalName = (String) expressionEvaluator
					.evaluate("${project.build.finalName}");
			
			String basePath = (String) expressionEvaluator
					.evaluate("${plugins.plugin.basePath}");
			
			System.out.println("BasePath "+basePath);
			 
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
		List<String> paths = new ArrayList<String>();
		File baseDir = getProjectBuildRoot(expressionEvaluator);
		try {
			System.out.println(baseDir.getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			paths.add(new File(baseDir, "WEB-INF/classes").getCanonicalPath());
			paths.addAll(getJarsRecursively(new File(baseDir, "WEB-INF/lib")));
		} catch (IOException e) {
			throw new ComponentConfigurationException(
					"There was a problem in setting classpath", e);
		}
		return paths;
	}

	private List<String> getJarsRecursively(File root) throws IOException {
		List<String> jars = new ArrayList<String>();
		if (root.isDirectory()) {
			for (File f : root.listFiles()) {
				if (f.isDirectory()) {
					jars.addAll(getJarsRecursively(f));
				} else {
					System.out.println(f.getName());
					if (f.getName().endsWith(".jar")) {
						jars.add(f.getCanonicalPath());
					}
				}
			}
		} else {
			if (root.getName().endsWith(".jar")) {
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
