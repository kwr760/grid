package krt.password.rest;

import krt.password.api.Password;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class PasswordResource {
	@GET()
	@Produces("text/plain")
	public String generate() {
		Password password = new Password();
		
		return password.toString();
	}
}
