package krt.grid.manager.jbpm;

import java.util.HashMap;
import java.util.Map;

import krt.grid.objects.Result;

import org.drools.process.instance.WorkItemHandler;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;


public class FinishHandler implements WorkItemHandler {

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		Result gridresult = (Result) workItem.getParameter("gridresult");
		
		System.out.println("FinishFlowHandler workItem.id:  " + workItem.getId());
		FinishFlowThread worker = new FinishFlowThread("FinishFlowThread" + gridresult.getJobID(), gridresult, workItem.getId(), manager);
		worker.start();
	}

	private class FinishFlowThread extends Thread {
		private Result gridresult;
		private long workItemID;
		private WorkItemManager manager;
		
		public FinishFlowThread(String name, Result gridresult, long workItemID, WorkItemManager manager) {
			  super(name);
			  this.gridresult = gridresult;
			  this.workItemID = workItemID;
			  this.manager = manager;
		}
		
		public void run() {
			System.out.println("Finishing Flow:  " + gridresult.getJobID());
			
			Map<String, Object> mp = new HashMap<String, Object>();
			manager.completeWorkItem(workItemID, mp);
		}
	}
}
