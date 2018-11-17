package krt.grid.frame;

import java.util.List;

import krt.grid.objects.Task;


public interface ITask {
	public long getJobID();
	public Task getTask();
	public boolean isSuccess();
	public List<String> getTasks();
}
