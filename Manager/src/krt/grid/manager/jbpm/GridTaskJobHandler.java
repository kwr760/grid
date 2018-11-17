package krt.grid.manager.jbpm;

import krt.grid.api.frame.GridFactory;
import krt.grid.objects.Task;

import org.drools.process.instance.WorkItemHandler;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;


public class GridTaskJobHandler implements WorkItemHandler {
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		Task gridtask = (Task) workItem.getParameter("gridtask");
		
		System.out.println("Calling Grid:  " + gridtask.getJobID());
		System.out.println("GridTaskWorkItemHandler workItem.id:  " + workItem.getId());

		gridtask.setJobID(workItem.getId());
		
		try {
			boolean result = GridFactory.getInstance().submitTask(gridtask);
			System.out.println("The grid returned the value of :" + result + ".");
		}
		catch (Exception rce) {
			rce.printStackTrace();
		}
	}
}
