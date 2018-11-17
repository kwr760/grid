package krt.grid.api.frame;

import java.util.LinkedList;

import krt.grid.api.manager.ManagerAPI;
import krt.grid.api.manager.ManagerFactory;
import krt.grid.objects.Result;
import krt.grid.objects.Task;
import krt.grid.objects.Result.Status;


public class MockGrid implements GridAPI {
	static ManagerAPI tm = ManagerFactory.getInstance();
	
    public void setHost(String host) {
    }
	
	public MockGrid() {
	}
	
	private void randomWait(int time) {
		try {
			Thread.currentThread();
			Thread.sleep((long) (time * 1000 * Math.random()));
		} catch (InterruptedException e) {
			System.out.println("Interrupted!");
		}
	}		
	
	private class SubmitResponseThread extends Thread {
		Task task;
		
		public SubmitResponseThread(String name, Task task) {
			  super(name);
			  this.task = task;
		}
		
		public void run() {
			randomWait(5);

			Status status;
			Double check = new Double(2 * Math.random());
			if (1 == check.intValue()) {
				status = Status.Success;
			}
			else {
				status = Status.Failed;
			}
			status = Status.Failed;
		    Result result = new Result(task.getJobID(), status, "Returned message");
		    LinkedList<Result> results = new LinkedList<Result>();
		    results.add(result);
		    try {
		    	tm.sendResults(results);
		    }
		    catch (Exception rce) {
		    	rce.printStackTrace();
		    }
		}
	}
	
	public boolean submitTask(Task task)
	{
		SubmitResponseThread worker = new SubmitResponseThread("SubmitResponseThread_" + task.getJobID(), task);
		worker.start();

		return true;
	}

	@Override
	public String Status() {
		return "Mock Grid Status - not implemented";
	}
}
