package krt.grid.manager.service;

import java.io.IOException;
import java.util.LinkedList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import krt.grid.manager.TaskQueue;
import krt.grid.objects.Result;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.inject.Inject;

@Path("/")
public class Manager {
	private static TaskQueue processQueue;
	private ObjectMapper mapper = new ObjectMapper();

	@SuppressWarnings("static-access")
	@Inject
	public Manager(TaskQueue processQueue){
		this.processQueue = processQueue;
	}
	public static TaskQueue queue() {
		return processQueue;
	}
	@DELETE
	@Path("halt")
	public Response halt() {
		System.out.println("Halt Process Queue");
		processQueue.halt();
		return Response.ok().build();
	}
	@POST
	@Path("test")
	public Response test() {
		System.out.println("Set the Process Queue for testing");
		processQueue.test();
		return Response.ok().build();
	}
	@POST
	@Path("reset")
	public Response reset() {
		System.out.println("Reset the Process Queue from testing");
		processQueue.reset();
		return Response.ok().build();
	}
	
	@GET
	@Path("version")
	public String version(){
		return "0.0.0.0";
	}
	@PUT
	@Path("documents/results")
	@Consumes("application/json")
	@Produces("application/json")
	public Response documentResults(LinkedList<Result> resultList) {
		try {
			System.out.println("Document results: "
					+ mapper.writeValueAsString(resultList));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return Response.ok().build();
	}
}
