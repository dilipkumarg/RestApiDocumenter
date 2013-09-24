/**
 * 
 */
package com.imaginea.rest.delegate;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * @author sandeep-t
 * 
 */
@Path("/animals")
public class AnimalsTest {

	@GET
	@Path("/{param}")
	public Dog getMsg(@PathParam("param") String msg) {
		String output = "Jersey say : " + msg;

		// AnnotatedMethod method= new AnnotatedMethod(getMsg("Spring"));
		return null;

	}

	public static void main(String[] args) {
		ApiDocumenter apiDoc = new ApiDocumenter();
		System.out.println(apiDoc.getJSONResponse(AnimalResource.class));

		
	/*	AbstractResource resource = IntrospectionModeller.createResource(AnimalResource.class);

		String str = AnimalResource.class.toString();

		System.out.println("CLASS " + str.getClass());
		
		System.out.println("Path is " + resource.getPath().getValue());

		String uriPrefix = resource.getPath().getValue();

		for (AbstractSubResourceMethod srm : resource.getSubResourceMethods())

		{

			String uri = uriPrefix + "/" + srm.getPath().getValue();
			System.out.println(srm.getHttpMethod() + " at the path " + uri + " return "
							+ srm.getReturnType().getSimpleName());
			Field[] arr=srm.getReturnType().getFields();
			//Field[] arr=srm.getReturnType().getClass().getFields();
			for(int i=0; i<arr.length;i++){
				System.out.println(arr[i].getName());
				
				System.out.println(arr[i].getType().getSimpleName());
			}
			
			
			System.out.println(srm.getMethod().getName());
			if(srm.getParameters().size() > 0) {
				
				System.out.println(srm.getParameters().get(0).getAnnotation().annotationType().getSimpleName());
			}

		}*/

	}

	class Dog {
		
		public int id;
		public String name;
		public String petname;
	}

	class Cat {
		
		public int id;
		public String name;
		public String petname;
	}

	@Path("/animals")
	class AnimalResource {

		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("dog/{param}")
		public Dog getDog(@PathParam("param") String msg,
				@QueryParam("token") String token,
				@HeaderParam("tok") String tok, String s2) {
			return null;
		}

		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("cat")
		public Cat getCat() {
			return null;
		}
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("cat")
		public String getStringAsData() {
			return null;
		}

	}
}
