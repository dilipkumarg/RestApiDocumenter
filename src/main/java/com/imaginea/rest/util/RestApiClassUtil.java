/**
 * 
 */
package com.imaginea.rest.util;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.Path;

import com.sun.jersey.api.core.ScanningResourceConfig;
import com.sun.jersey.spi.scanning.servlet.WebAppResourcesScanner;

/**
 * @author sandeep-t
 * 
 */

public final class RestApiClassUtil {

	/**
	 * Maps primitive {@code Class}es to their corresponding wrapper
	 * {@code Class}.
	 */
	private static final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<Class<?>, Class<?>>();
	static {
		primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
		primitiveWrapperMap.put(Byte.TYPE, Byte.class);
		primitiveWrapperMap.put(Character.TYPE, Character.class);
		primitiveWrapperMap.put(Short.TYPE, Short.class);
		primitiveWrapperMap.put(Integer.TYPE, Integer.class);
		primitiveWrapperMap.put(Long.TYPE, Long.class);
		primitiveWrapperMap.put(Double.TYPE, Double.class);
		primitiveWrapperMap.put(Float.TYPE, Float.class);
		primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
	}

	/**
	 * Maps wrapper {@code Class}es to their corresponding primitive types.
	 */
	private static final Map<Class<?>, Class<?>> wrapperPrimitiveMap = new HashMap<Class<?>, Class<?>>();
	static {
		for (Class<?> primitiveClass : primitiveWrapperMap.keySet()) {
			Class<?> wrapperClass = primitiveWrapperMap.get(primitiveClass);
			if (!primitiveClass.equals(wrapperClass)) {
				wrapperPrimitiveMap.put(wrapperClass, primitiveClass);
			}
		}
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

	/*
	 * public static Set<Class<?>> getallClasses(String basePackage){
	 * Reflections reflections = new Reflections(basePackage); Set<Class<?>>
	 * allClasses = reflections.getTypesAnnotatedWith(Path.class); return
	 * allClasses; }
	 */

	@SuppressWarnings({ "rawtypes" })
	public static Set<Class> getannotatedClasses(Class<? extends Annotation> ac, Set<Class<?>> allAnnotatedClasses) {
		Set<Class> s = new HashSet<Class>();
		for (Class c : allAnnotatedClasses)
			if (c.isAnnotationPresent(ac))
				s.add(c);
		return s;
	}
	
	public static Set<Class> getPathAnnotatedClasses(String[] args, ServletContext servletContext) {
		ScanningResourceConfig config = new ScanningResourceConfig();
		config.init(new WebAppResourcesScanner(args, servletContext));
		Set<Class<?>> annotatedclasses = config.getClasses();
		return getannotatedClasses(Path.class, annotatedclasses);
	}

}
