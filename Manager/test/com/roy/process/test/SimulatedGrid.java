package com.roy.process.test;

import krt.grid.api.frame.GridAPI;
import krt.grid.objects.Result;
import krt.grid.objects.Task;
import krt.grid.objects.Result.Status;


public class SimulatedGrid implements GridAPI {
	private boolean simulateError;
	
    public void setHost(String host) {
    }
	
	public SimulatedGrid(boolean simulateError) {
		this.simulateError = simulateError;
	}
	
	public boolean submitTask(Task task)
	{
		if (simulateError) {
			task.setDestinationPath(null);
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Result cdoc = new Result(0, null, null);
		
		cdoc.setJobID(task.getJobID());	
		if (task.getDestinationPath() == null) {
			cdoc.setDetails("Destination Path cannot be null");
			cdoc.setStatus(Status.Failed);
		}
		else if (task.getSourcePath() == null) {
			cdoc.setDetails("Source Path cannot be null");
			cdoc.setStatus(Status.Failed);
		}
		else {
			cdoc.setDetails("DocumentSimulatedGridCallLifeCycle Test Complete");
			cdoc.setStatus(Status.Success);
		}
		
		System.out.println("Simulated Grid work complete: " + cdoc.getJobID() + " - " + cdoc.getDetails() );

		return true;
	}

	public String Status() {
		return null;
	}
}
