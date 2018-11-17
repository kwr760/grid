/**
 * 
 */
package krt.grid.frame.Task;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import krt.grid.frame.ITask;
import krt.grid.objects.Task;



public class SimpleTask implements ITask, Runnable, Serializable {
	private static final long serialVersionUID = -1831708370151890493L;
	Date timeExecuted;
	Date timeCreated;
	Task task;
	
	public SimpleTask(Task task)
	{
		timeCreated = new Date();
		this.task = task;
	}

	public long getTimeSpan(){
		return timeExecuted.getTime() - timeCreated.getTime();
	}
	
	@Override
	public boolean isSuccess(){
		return timeExecuted != null;
	}
	
	@Override
	public void run() {
		timeExecuted = new Date();
		System.out.println(getTimeSpan());
	}

	@Override
	public Task getTask() {
		return this.task;
	}
	
	@Override
	public List<String> getTasks() {
		return task.getProcessTasks();
	}

	@Override
	public long getJobID() {
		return 0;
	}
}
