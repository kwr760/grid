package krt.grid.manager.jbpm;

import java.util.HashMap;
import java.util.Map;

import krt.grid.objects.Result;

import org.drools.process.instance.WorkItemHandler;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;


public class TaskErrorJobHandler implements WorkItemHandler {
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		Result gridresult = (Result) workItem.getParameter("gridresult");
 
		System.out.println("Calling Document Error processing:  " + gridresult.getJobID() + ": " + gridresult.getDetails());
		System.out.println("GridTaskErrorWorkItemHandler workItem.id:  " + workItem.getId());

		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("gridresult", gridresult);
		manager.completeWorkItem(workItem.getId(), mp);
	}
}
