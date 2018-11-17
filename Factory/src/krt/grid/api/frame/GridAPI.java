package krt.grid.api.frame;

import krt.grid.objects.Task;

public interface GridAPI {
	public void setHost(String host); 
	public boolean submitTask (Task task) throws Exception;
	public String Status();
}
