package com.imaginea.rest.documenter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;

import org.reflections.Reflections;

import com.google.gson.Gson;
import com.imaginea.rest.delegate.ClassDocumenter;
import com.imaginea.rest.model.ClassInfo;
import com.imaginea.rest.model.SwaggerInfo;
import com.imaginea.rest.util.JsonUtil;

@Path("/apidocs")
public class ApiDocumenter {
	
	private ClassDocumenter apiDoc;
	private Properties props =null;
	public ApiDocumenter() throws FileNotFoundException, IOException {
		props= new Properties();
		props.load(new FileInputStream(this.getClass().getResource("/ClassJsonMap.properties").getPath()));
		apiDoc = new ClassDocumenter();
	}



	/**
	 * This is the base function for Api docs, it will fetch the list of all
	 * resources
	 * 
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getMetaInfo() {
		 List<ClassInfo> classInfoList=getListClassMetaData();
		 System.out.println("List Size "+classInfoList.size());
		 JSONObject jsonObj = new JSONObject();
		 jsonObj.put("apis",classInfoList);
		 return jsonObj.toString();
		
	}

	/**
	 * It will returns the class information in json information
	 * 
	 * @param className
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@GET
	@Path("/{class}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getClassInfo(@PathParam("class") String className) throws IOException, ClassNotFoundException {
		return getJsonStringForClass(className);
	}

	public static void main(String[] args) throws ClassNotFoundException, IOException {/*
		
		Set<Class<? extends Object>> allClasses = reflections.getTypesAnnotatedWith(Path.class);
		for (Class className : allClasses) {
			System.out.println("ClassName "+className.getSimpleName());
			SwaggerFileUtil.writeClassJsonMaptoFile(className.getSimpleName(), doc.getClassInfo(className.getName()), true,
							"ClassJsonMap.properties");
		}
		
		
	*/}

	
	
	public List<ClassInfo> getListClassMetaData() {
		List<ClassInfo> apis = new ArrayList<ClassInfo>();
		Set<Object> pathKeySet=props.keySet();
		for(Object path: pathKeySet){
			ClassInfo classDesc = new ClassInfo();
			classDesc.setPath("/"+path);
			apis.add(classDesc);	
		}
		return apis;
	}
	
	public String getJsonStringForClass(String className){
		return (String) props.get(className);
		
	}
	
	
	
	
}
