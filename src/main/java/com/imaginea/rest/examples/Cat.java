package com.imaginea.rest.examples;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/Test/Cat")
public class Cat {

	private String catName;
	private String catDesc;

	public Cat() {
		this.catName = "pinky";
		this.catDesc = "black cat";
	}

	@GET
	@Path("dilip/{catname}")
	public Response getCaDesc(@PathParam("catname") String catName) {
		String output = catDesc + "<br>sent Data:" + catName;
		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/{catname}/{desc}")
	public Response getCatName(@PathParam("catname") String catN,
			@PathParam("desc") String desc) {
		
		String output = catName + "<br> sent Parameters:" + catN + "," + desc;
		return Response.status(200).entity(output).build();
	}
}
