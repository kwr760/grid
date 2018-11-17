package krt.grid.frame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import krt.grid.objects.Task;

import org.jboss.resteasy.annotations.providers.jaxb.WrappedMap;

import com.google.inject.Inject;

@Path("/GridService")
public class GridService {
	private TaskBroker broker;
	
	@Inject
	public GridService(TaskBroker broker){
		this.broker = broker;
	}

	@POST
	@Path("/submit")
	@Consumes(MediaType.APPLICATION_JSON) 
	public boolean submit(Task taskItem) throws Exception{
		boolean bOK = false;
		try
		{
			bOK = broker.submitTask(taskItem);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		
		return bOK;
	}
	
	@GET
    @Path("/checkHealth")
    @Produces("application/json")
    @WrappedMap
    public Map<String, String> checkHealth() {
        HashMap<String, String> healthMap = new HashMap<String, String>();
        healthMap.put("Status", "Running");
        healthMap.put("version", this.getVersion());
        healthMap.put("name",this.getClass().getSimpleName());
        return healthMap;       
    }
	
	@POST
	@Path("/cancel/{id}")
	public boolean cancel(@PathParam("id") long DocumentID) {
		boolean bReturn = false;
		
		try
		{
			bReturn = broker.cancelTask(DocumentID);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return bReturn;
	}
	
	@GET
	@Path("/taskStatus/{id}")
	public String taskStatus(@PathParam("id") long documentID){
		
		TaskStatus status = broker.getTaskStatus(documentID);
		return status.toString();
	}
	
	@GET
	@Path("/taskStatus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@WrappedMap
	public Map<Long, String> taskStatus(List<Long> DocumentIDList){
		HashMap<Long, String> hMap = new HashMap<Long, String>();
		
		for (long i : DocumentIDList)
		{
			hMap.put(i,broker.getTaskStatus(i).toString());
		}
		
		return hMap;
	}
	
	@GET
	@Path("/getVersion")
	public String getVersion(){
		return "1.0.0.0";
	}
	
	@GET
	@Path("/Status")
	public String Status(){
		return broker.getStatus();
	}
}
