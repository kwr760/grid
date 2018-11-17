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

//import org.codehaus.jackson.map.ObjectMapper;

import java.io.InputStream;

import krt.grid.exception.PersistenceException;
import krt.grid.objects.File;
import krt.grid.persistence.FilePersistence;

@Path("/files")
public class FilesResource {
	private static FilesResource instance;
//	ObjectMapper mapper = new ObjectMapper();
	
	public FilesResource(){
	}
	
	public static synchronized FilesResource getInstance()
	{
		if (instance == null)
			instance = new FilesResource();

		return instance;
	}
	
	@GET
	@Produces("text/xml")
	public Response getInfo() {
		System.out.println("getInfo (default) called on the /files web service.");
		return Response.ok("Here is some INFO").build();
	}

	@PUT
	@Consumes("application/json")
	public Response createFile(File file) {
		FilePersistence persist = new FilePersistence();
		
		try {
			persist.insert(file);	    
		}
		catch (PersistenceException fce) {
			fce.printStackTrace();
			return Response.notModified("Failed to parse the JSON string with message:  " + fce.getMessage()).build(); 
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.notModified("Failed to parse the JSON string with message:  " + e.getMessage()).build(); 
		}
		
		System.out.println("Created file " + file.getFileID());
		return Response.ok().build();
	}

   @GET
   @Path("{id}")
   @Produces("application/json")
   public File getFile(@PathParam("id") long id) {
		FilePersistence persist = new FilePersistence();
		File file;

		try {
			file = persist.get(id);	    
		}
		catch (PersistenceException fce) {
			fce.printStackTrace();
			return null; 
		}
		
		return file;
   }
   
   @POST
   @Path("{id}")
   @Consumes("application/json")
   public Response updateFile(@PathParam("id") long id, InputStream is) {
//		FilePersistence persist = new FilePersistence();
//		File file;
		
	    try {
//			file = mapper.readValue(is, File.class);
//		    file.setFileID(id);
//	    	persist.update(file);
	    }
//	    catch (PersistenceException fue) {
//			fue.printStackTrace();
//			return Response.notModified("Hibernate failed to insert data with error:  " + fue.getMessage()).build(); 
//	    }
	    catch (Exception e) {
			e.printStackTrace();
			return Response.notModified("Hibernate failed to insert data with error:  " + e.getMessage()).build(); 
	    }
   
//		System.out.println("Created file " + file.getFileID());
		return Response.ok().build();
   }

   @DELETE
   @Path("{id}")
   public Response deleteFile(@PathParam("id") long id) {
		File file = new File(id);
		FilePersistence persist = new FilePersistence();

	    try {
	    	persist.delete(file);
	    }
	    catch (PersistenceException fue) {
			fue.printStackTrace();
	    }
		return Response.ok().build();
   }
}
