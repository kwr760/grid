package krt.grid.manager.jbpm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import krt.grid.objects.Task;

import org.codehaus.jackson.map.ObjectMapper;
import org.drools.process.instance.WorkItemHandler;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;


public class RetrieveHandler implements WorkItemHandler {
	ObjectMapper mapper = new ObjectMapper();
	
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		System.out.println("RetrieveDocumentHandler workItem.id:  " + workItem.getId());
		RetrieveDocumentThread worker = new RetrieveDocumentThread("RetrieveDocument" + workItem.getId(), workItem.getId(), manager);
		worker.start();
	}
	
	private class RetrieveDocumentThread extends Thread {
		private long jobID;
		private WorkItemManager manager;
		
		public RetrieveDocumentThread(String name, long jobID, WorkItemManager manager) {
			  super(name);
			  this.jobID = jobID;
			  this.manager = manager;
		}
		
		public void run() {
			System.out.println("Retrieve document from DB:  " + jobID);
			
			Task gridtask = new Task();
			Map<String, String> po = new HashMap<String, String>();
			po.put("result", "TODO: NEED real wo call result: ");
			gridtask.setProcessingOptions(po);
			List<String> pt = new LinkedList<String>();
			pt.add("text_extract");
			gridtask.setProcessTasks(pt);
			gridtask.setJobID(jobID);

			Map<String, Object> mp = new HashMap<String, Object>();
			mp.put("gridtask", gridtask);
			manager.completeWorkItem(jobID, mp);
		}
	}
}
