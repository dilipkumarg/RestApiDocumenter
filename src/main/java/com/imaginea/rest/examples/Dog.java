package com.imaginea.rest.examples;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/Testdog")
public class Dog {

	private String dogName;
	private String dogDesc;

	public String getDogName() {
		return dogName;
	}

	public void setDogName(String dogName) {
		this.dogName = dogName;
	}

	public String getDogDesc() {
		return dogDesc;
	}

	public void setDogDesc(String dogDesc) {
		this.dogDesc = dogDesc;
	}

	public Dog() {
		this.dogName = "john";
		this.dogDesc = "hutch dog";
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{dogname}")
	public Dog getDogDesc(@PathParam("dogname") String dogName) {
		// String output = dogDesc + "<br>sent Data:" + dogName;
		// return Response.status(200).entity(output).build();
		return null;

	}

	@GET
	@Path("/{dogname}/{desc}")
	public String getDogName(@PathParam("dogname") String dogN, @PathParam("desc") String desc) {
		String output = dogName + "<br> sent Parameters:" + dogN + "," + desc;
		// return Response.status(200).entity(output).build();
		return output;
	}
}
