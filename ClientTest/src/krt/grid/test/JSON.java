package krt.grid.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import krt.grid.objects.Result;
import krt.grid.objects.Task;
import krt.grid.objects.Result.Status;

import org.junit.Test;
import org.codehaus.jackson.map.ObjectMapper;


public class JSON {
	ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void TestGridResultMap() {
		Result result = new Result(0, Status.Success, null);
		result.setJobID(1);
		result.setStatus(Status.Success);
		result.setDetails("details");
		
		try {
			System.out.println("result: " + mapper.writeValueAsString(result));			
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		Task task = new Task();
		task.setCallback("callback");
		task.setDestinationPath("destinationPath");
		task.setDocumentID(1);
		Map<String, String> po = new HashMap<String, String>();
		po.put("result", "TODO: NEED real wo call result: ");
		task.setProcessingOptions(po);
		
		List<String> pt = new LinkedList<String>();
		pt.add("task one");

		task.setProcessTasks(pt);
		task.setSourcePath("sourcePath");
		task.setWorkItemID(1);
		
		try {
			System.out.println("task: " + mapper.writeValueAsString(task));			
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	

}
