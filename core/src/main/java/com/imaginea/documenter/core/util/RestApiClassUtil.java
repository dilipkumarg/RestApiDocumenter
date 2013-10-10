/**
 * 
 */
package com.imaginea.documenter.core.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Path;

import org.apache.log4j.Logger;

import com.sun.jersey.api.core.ScanningResourceConfig;
import com.sun.jersey.core.spi.scanning.FilesScanner;

/**
 * @author sandeep-t
 * 
 */

public final class RestApiClassUtil {

	private static final Logger LOGGER = Logger.getLogger(RestApiClassUtil.class);


	/**
	 * Maps wrapper {@code Class}es to their corresponding primitive types.
	 */
	private static final Map<Class<?>, Class<?>> wrapperPrimitiveMap = new HashMap<Class<?>, Class<?>>();
	static {
		
		wrapperPrimitiveMap.put(Boolean.class,Boolean.TYPE );
		wrapperPrimitiveMap.put(Byte.class,Byte.TYPE );
		wrapperPrimitiveMap.put( Character.class,Character.TYPE);
		wrapperPrimitiveMap.put(Short.class,Short.TYPE );
		wrapperPrimitiveMap.put( Integer.class,Integer.TYPE);
		wrapperPrimitiveMap.put(Long.class,Long.TYPE );
		wrapperPrimitiveMap.put( Double.class,Double.TYPE);
		wrapperPrimitiveMap.put(Float.class,Float.TYPE );
		wrapperPrimitiveMap.put(Void.TYPE, Void.TYPE);
	}

	/**
	 * Returns whether the given {@code type} is a primitive or primitive
	 * wrapper ({@link Boolean}, {@link Byte}, {@link Character}, {@link Short},
	 * {@link Integer}, {@link Long}, {@link Double}, {@link Float}).
	 * 
	 * @param type
	 *            The class to query or null.
	 * @return true if the given {@code type} is a primitive or primitive
	 *         wrapper ({@link Boolean}, {@link Byte}, {@link Character},
	 *         {@link Short}, {@link Integer}, {@link Long}, {@link Double},
	 *         {@link Float}).
	 * @since 3.1
	 */
	public static boolean isPrimitiveOrWrapper(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type.isPrimitive() || isPrimitiveWrapper(type);
	}

	/**
	 * Returns whether the given {@code type} is a primitive wrapper (
	 * {@link Boolean}, {@link Byte}, {@link Character}, {@link Short},
	 * {@link Integer}, {@link Long}, {@link Double}, {@link Float}).
	 * 
	 * @param type
	 *            The class to query or null.
	 * @return true if the given {@code type} is a primitive wrapper (
	 *         {@link Boolean}, {@link Byte}, {@link Character}, {@link Short},
	 *         {@link Integer}, {@link Long}, {@link Double}, {@link Float}).
	 * @since 3.1
	 */
	public static boolean isPrimitiveWrapper(Class<?> type) {
		return wrapperPrimitiveMap.containsKey(type);
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Set<Class> getannotatedClasses(Class<? extends Annotation> ac, Set<Class<?>> allAnnotatedClasses) {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Searching for " + ac + " annotated classes");	
		}
		Set<Class> s = new HashSet<Class>();
		for (Class c : allAnnotatedClasses)
			if (c.isAnnotationPresent(ac))
				s.add(c);
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Found " + s.size() + " classes annotated with " + ac);	
		}
		
		return s;
	}

	@SuppressWarnings("rawtypes")
	public static Set<Class> getPathAnnotatedClasses(String[] classPath) {
		LOGGER.debug("Searching got annotated classes in the locations " + classPath);
		ScanningResourceConfig config = new ScanningResourceConfig();
		final File[] files = getFilesList(classPath);
		config.init(new FilesScanner(files));
		Set<Class<?>> annotatedclasses = config.getClasses();
		if(LOGGER.isDebugEnabled()){
		LOGGER.debug("Total annotated classes found " + annotatedclasses.size());
		}
		return getannotatedClasses(Path.class, annotatedclasses);
	}

	/**
	 * @param classPath
	 * @return
	 */
	private static File[] getFilesList(String[] classPath) {
		final File[] files = new File[classPath.length];
		  for (int i = 0;  i < classPath.length; i++) {
	            files[i] = new File(classPath[i]);
	        }
		return files;
	}
	
/*	public static void main(String[] args) {
		System.out.println("check");
	}*/

}
