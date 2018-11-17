package krt.grid.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import krt.grid.objects.Result;
import krt.grid.objects.Result.Status;


@Path("/messages")
public class MessagesResource {
	public MessagesResource(){
	}
	
	@PUT
	@Consumes("application/json")
	public Response createMessage(Result result) {
		System.out.println("Created result " + result.getStatus());
		return Response.ok().build();
	}

   @GET
   @Path("{id}")
   @Produces("application/json")
   public Result getMessage(@PathParam("id") long id) {
	   Result result = new Result(id, Status.Success, "");
	   return result;
   }

   @GET
   @Path("version")
   @Produces("application/json")
   public Result version() {
	   Result result = new Result(0, Status.Success, "");
	   return result;
   }

   @POST
   @Path("{id}")
   @Consumes("application/json")
   public Response updateMessage(@PathParam("id") long id, Result result) {
		System.out.println("Created result " + result.getStatus());
		return Response.ok().build();
   }

   @DELETE
   @Path("{id}")
   public Response deleteMessage(@PathParam("id") long id) {
		return Response.ok().build();
   }
}
